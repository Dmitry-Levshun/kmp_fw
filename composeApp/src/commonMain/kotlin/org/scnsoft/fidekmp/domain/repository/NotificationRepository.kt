package org.scnsoft.fidekmp.domain.repository

import kotlinx.coroutines.flow.StateFlow
import org.scnsoft.fidekmp.domain.model.NotificationItem
import org.scnsoft.fidekmp.domain.model.NotificationResult

private const val IS_SERVICE = true

interface NotificationRepository {
    val errorStr: StateFlow<String>
    val notifications: StateFlow<List<NotificationItem>>
    val notificationTotalCount: StateFlow<Int>
    val notificationNotReadCount: StateFlow<Int>
    suspend fun setDeviceInfo()
    suspend fun getNotifications(): NotificationResult
    suspend fun markAsRead(id: Int)
    suspend fun markAsReadAll()
    suspend fun deleteNotification(id: Int)
}
