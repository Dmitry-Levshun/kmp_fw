package org.scnsoft.fidekmp.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import org.scnsoft.fidekmp.data.api.notification.dto.NotificationResponseItem
import org.scnsoft.fidekmp.utils.currentUtcDateTime

data class NotificationItem (
    val id: Int,
    val title: String,
    val subTitle: String?,
    val body: String,
    val from: String,
    val role: String,
    var isRead: Boolean,
    val date: LocalDateTime,
    val url: String? = null
) {
    companion object {
        @OptIn(ExperimentalTime::class)
        fun Empty() = NotificationItem(id = 0, title = "", body = "",
            from = "", role = "",isRead = false, subTitle = null,
            date = currentUtcDateTime()
        )

        fun fromResponse(respItem: NotificationResponseItem) = NotificationItem(
            id = respItem.id,
            title = respItem.title,
            subTitle = respItem.subtitle,
            body = respItem.body,
            from = respItem.from,
            role = respItem.role,
            isRead = respItem.isRead,
            date = respItem.date,
            url = respItem.url
        )
    }
}

sealed class NotificationResult {
    class Success(val list: List<NotificationItem>) : NotificationResult()
    class Error(val e: Throwable?) : NotificationResult()
    override fun toString(): String {
        return "NotificationResult " +
                when(this) {
                    is Success -> "Success $list"
                    is Error ->"Error $e"
                }
    }
}