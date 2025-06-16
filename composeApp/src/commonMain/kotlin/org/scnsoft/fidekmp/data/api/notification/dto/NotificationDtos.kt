package org.scnsoft.fidekmp.data.api.notification.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class DeviceInfoRequestDto (
    val os: String,
    val fcmToken: String
)

data class ReadRequest (
    val isRead: Boolean
)
@Serializable
data class NotificationResponseDto(
    @SerialName("@context")  // "@context":"\/api\/v1\/wineyard\/contexts\/Notification\/User\/Message"
    val context: String,
    @SerialName("@id")       // "@id":"\/api\/v1\/wineyard\/notification\/user\/messages"
    val idUrl: String,
    @SerialName("@type")     // "@type":"hydra:Collection"
    val type: String,
    @SerialName("hydra:totalItems")  // "hydra:totalItems":5
    val notificationTotalItems: Int,
    @SerialName("hydra:member")      // "hydra:member":[{
    val items: List<NotificationResponseItem>,
    @SerialName("hydra:view")
    val hydraView: NotificationResponseHydraView,
)
@Serializable
data class NotificationResponseItem(
    @SerialName("@id")       // "@id":"\/api\/v1\/wineyard\/notification\/user\/messages\/is-all-read"
    val idUrl: String,
    @SerialName("@type")     // "@type":"Notification\/User\/Message"
    val type: String,
    @SerialName("id")        // "id":5,"message"
    val id: Int,
    val message: String,            // "message":"\/api\/v1\/wineyard\/notification\/messages\/5"
    val isRead: Boolean,            // "isRead":false
    val title: String,              // "title":"Test notification 5"
    val body: String,               // "body":"Body of the test notification 5"
    val subtitle: String?,          // "subtitle":"Subtitle of the test notification 5"
    val from: String,               // "from":""
    val role: String,               // "role":""
    val date: LocalDateTime,               // "date":"2024-02-24T13:59:31+00:00"
    val url: String?
)

@Serializable
data class NotificationResponseHydraView(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
)
@Serializable
data class MessageContentRequest(
    val title: String,
    val subtitle: String,
    val body: String,
    val date: LocalDateTime,
    val url: String? = null
)
data class MessageSendContentRequest(
    var userId: String = "a8a5baef-600b-40ce-82ee-73b5a59c6037",
    val message: Int
)
