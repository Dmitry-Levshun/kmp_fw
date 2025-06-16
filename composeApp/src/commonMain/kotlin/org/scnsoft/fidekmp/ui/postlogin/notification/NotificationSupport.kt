package org.scnsoft.fidekmp.ui.postlogin.notification

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.scnsoft.fidekmp.domain.model.NotificationItem
import org.scnsoft.fidekmp.ui.LoadingInterface
import org.scnsoft.fidekmp.utils.currentUtcDateTime

interface INotificationData {
    val notifications: StateFlow<List<NotificationItem>>
    val notificationCount: StateFlow<Int>
    val notificationNotReadCount: StateFlow<Int>
}
interface NotificationInterface: LoadingInterface, INotificationData {
        fun getNotifications()
        fun markAsRead(id: Int)
        fun markAsReadAll()
        fun deleteNotification(id: Int)
}
class NotificationPreview : NotificationInterface {
    override val notifications: StateFlow<List<NotificationItem>> get() = MutableStateFlow(fakeNotification())
    override val notificationCount : StateFlow<Int> get() = MutableStateFlow(1233)
    override val notificationNotReadCount : StateFlow<Int> get() = MutableStateFlow(12)
    override val isloadingState: StateFlow<Boolean> get() =MutableStateFlow(false)
    override fun getNotifications() {}
    override fun markAsRead(id: Int) {}
    override fun markAsReadAll() {}
    override fun deleteNotification(id: Int) {}
    private fun fakeNotification(): List<NotificationItem> {
        return listOf(NotificationItem(id = 1,
            title = "Delivery Instructions Received", body = "Wine preservation during shipping is an important topic for anyone in the wine supply chain who want to protect the quality and safety of their products during transit. Imagine receiving your shipment of wine, only to find the wine is undrinkable due to damage in transit, or less attractive at the point of sale due to label damage. This can leave you needing to juggle insurance claims, loss of sales, and out of stocks, all of which are damaging for brand integrity and the bottom line of your business.",
            from = "Frank Giroud", role = "Distributor",isRead = false, date = currentUtcDateTime(), subTitle = null
        ))
    }
}