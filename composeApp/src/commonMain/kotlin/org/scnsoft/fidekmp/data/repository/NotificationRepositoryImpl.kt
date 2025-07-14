package org.scnsoft.fidekmp.data.repository

import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.data.api.notification.dto.ReadRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.scnsoft.fidekmp.data.api.notification.NotificationApi
import org.scnsoft.fidekmp.data.settings.AppSettingsDataSource
import org.scnsoft.fidekmp.domain.model.NotificationItem
import org.scnsoft.fidekmp.domain.model.NotificationResult
import org.scnsoft.fidekmp.domain.repository.LoginRepository
import org.scnsoft.fidekmp.domain.repository.NotificationRepository
import org.scnsoft.fidekmp.utils.currentUtcDateTime

private const val IS_SERVICE = true

class NotificationRepositoryImpl(
    private val service: NotificationApi,
    private val appSettingsDataSource: AppSettingsDataSource,
//    private val device: LocalDevice,
    private val loginRepository: LoginRepository
) : NotificationRepository {
    val loginStateFlow = loginRepository.loginStateFlow
    override val notifications: StateFlow<List<NotificationItem>> get() = _notifications
    private val _notifications = MutableStateFlow(mutableListOf(NotificationItem.Empty()))
    override val notificationTotalCount: StateFlow<Int> get() = _notificationTotalCount
    val _notificationTotalCount =  MutableStateFlow(0)
    //    override val notificationNotReadCount: StateFlow<Int> get() = MutableStateFlow(_notifications.value.count { !it.isRead })
    override val notificationNotReadCount: StateFlow<Int> get() = _notificationNotReadCount
    val _notificationNotReadCount =  MutableStateFlow(0)

    override val errorStr: StateFlow<String> get() = _errorStr
    private val _errorStr = MutableStateFlow<String>("")

    override suspend fun setDeviceInfo() {}
/*
    override suspend fun setDeviceInfo() {
        var cnt = 0
        while(!setDeviceInfoInt()) {
            if (!loginStateFlow.first()) return
            delay(10000)
            cnt++
            if (cnt > 3) return
        }
    }
    private suspend fun setDeviceInfoInt(): Boolean {
        val token = device.getFcmToken()
        val os = LocalDevice.DEVICE_OS
        Timber.d("setDeviceInfoInt token : $token")
        if (token.isNullOrEmpty() || !appSettingsDataSource.isConnected()) return false
        Timber.d("setDeviceInfo $os ${token.take(10)}")
        val result = when (val response =
            apiRequestSender.sendRequest {
                service.setDeviceInfo(
                    DeviceInfoRequestDto(
                        os = os,
                        fcmToken = token
                    )
                )
            }) {
            is ApiResult.Success -> {
                Timber.d("setDeviceInfoInt Success")
                true
            }

            is ApiResult.Failure -> {
                Timber.d("setDeviceInfoInt Failure ${response.apiFailure}")
                false
            }

            is ApiResult.Error -> {
                Timber.d("setDeviceInfoInt Error ${response.e}")
                false
            }
        }
//        if (result && BuildConfig.DEBUG) {
//            Timber.d("postMessage")
//            postMessage()
//        }
        return result
    }
 */
    override suspend fun getNotifications(): NotificationResult {
        Napier.d("getMessages")
        if (IS_SERVICE) {
            _notifications.value.clear()
            for (page in 1 until 50) {
                val res = getNotificationsInternal(page, 30)
                if (res is NotificationResult.Success  && res.list.isEmpty()) return res
                if (res !is NotificationResult.Success) return res
            }
        } else return fakeNotificationList()
        return NotificationResult.Success(listOf())
    }

    private suspend fun getNotificationsInternal(page: Int?, itemsPerPage: Int?): NotificationResult {
        val response = service.getMessages(page, itemsPerPage)
        response.onSuccess { dto ->
                _notificationTotalCount.value = dto.notificationTotalItems
                val items = dto.items.map { NotificationItem.fromResponse(it) }
                if (items.isNotEmpty()) {
                    _notifications.value += items
                    val v = _notifications.value.sortedByDescending { it.date }
                    _notifications.value = _notifications.value.sortedByDescending { it.date }.toMutableList()
                    _notificationNotReadCount.value =  _notifications.value.count { !it.isRead }
                }
                Napier.d("getNotifications Success")
                return NotificationResult.Success(items)
            }
            .onFailure { e ->
                Napier.d("setDeviceInfoInt Error ${e}")
                return NotificationResult.Error(e)
            }

        return NotificationResult.Error(null)
    }

    override suspend fun markAsRead(id: Int) {
        Napier.d("markAsRead $id")
        val body = ReadRequest(true)
        if (IS_SERVICE) {
            val resp = service.markAsReadMessage(id.toString(), body)
            resp.onSuccess {
                _notifications.value.find {it.id == id}?.isRead = true
            }
        } else {
            _notifications.value.forEach { if (it.id == id) it.isRead = true }
        }
        _notificationNotReadCount.value =  _notifications.value.count { !it.isRead }
    }

    override suspend fun markAsReadAll() {
        Napier.d("markAsReadAll")
        val body = ReadRequest(true)
        if (IS_SERVICE) {
            val resp = service.markAsReadAllMessages(body)
            resp.onSuccess {
                _notifications.value.forEach { it.isRead = true }
            }
        }  else {
            _notifications.value.forEach { it.isRead = true }
        }
        _notificationNotReadCount.value =  _notifications.value.count { !it.isRead }
    }

    override suspend fun deleteNotification(id: Int) {
        Napier.d("deleteNotification $id")
        if (IS_SERVICE) {
            val resp =  service.deleteMessage(id.toString())
            resp.onSuccess {
                val el = _notifications.value.find { it.id == id }
                _notifications.value.remove(el)
            }
        } else {
            val i = _notifications.value.find { it.id == id }
            val list = _notifications.value.toMutableList()
            if (i != null) {
                list.remove(i)
                _notifications.value = list
            }
        }
        _notificationNotReadCount.value =  _notifications.value.count { !it.isRead }
    }

    private fun fakeNotificationList(): NotificationResult {
        val list = mutableListOf<NotificationItem>()
        repeat(50) { ind ->
            list += NotificationItem(
                id = ind,
                title = "Delivery Instructions Received $ind",
                subTitle = null,
                body = "Wine preservation during shipping is an important topic for anyone in the wine supply chain who want to protect the quality and safety of their products during transit. Imagine receiving your shipment of wine, only to find the wine is undrinkable due to damage in transit, or less attractive at the point of sale due to label damage. This can leave you needing to juggle insurance claims, loss of sales, and out of stocks, all of which are damaging for brand integrity and the bottom line of your business.",
                from = "Frank Giroud",
                role = "Distributor",
                isRead = (ind % 2 == 1),
                date = currentUtcDateTime(),
                url = ""

            )
        }
        _notifications.value = list
        return NotificationResult.Success(list)
    }
}