package org.scnsoft.fidekmp.ui.postlogin.profile

import org.scnsoft.fidekmp.domain.model.profile.PersonInfo
import org.scnsoft.fidekmp.domain.model.profile.ProfileInfo
import org.scnsoft.fidekmp.domain.model.NotificationItem
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.ui.UiResultInterface
import org.scnsoft.fidekmp.ui.IProfileInfo
import org.scnsoft.fidekmp.ui.postlogin.notification.INotificationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IProfileModel: IProfileInfo, UiResultInterface, INotificationData {
    val personInfo: StateFlow<PersonInfo>
    fun logout()
    fun getCurrentProfile()
    fun setCurrentUserInfo(info: PersonInfo)
    fun deleteAccount()
}
class ProfileModelPreview : IProfileModel {
    override val personInfo: StateFlow<PersonInfo> get() = MutableStateFlow(PersonInfo("user ID","avatar", "wine", "John Doe", "sale", "user", "email@gmail.com", "02-6286", "2024-10-16"))
    override val profileInfo: StateFlow<ProfileInfo> get() = MutableStateFlow(ProfileInfo("avatar", "wine", "John Doe", "sale", "user",0, "email@gmail.com", "Winemaker", 0, "", listOf("notifications")))

    override val isConsumer: StateFlow<Boolean> get() = MutableStateFlow(false)
    override val isIntermediate: StateFlow<Boolean> get() = MutableStateFlow(false)
    override val uiResult: StateFlow<UiResult?> get() = MutableStateFlow(null)
    override val notifications: StateFlow<List<NotificationItem>> get() = MutableStateFlow(listOf())
    override val notificationCount: StateFlow<Int> get() = MutableStateFlow(5)
    override val notificationNotReadCount: StateFlow<Int> get() = MutableStateFlow(4)

    override fun logout() {}
    override fun getCurrentProfile() {}
    override fun resetUiResult() {}
    override fun setCurrentUserInfo(info: PersonInfo) {}
    override fun deleteAccount() {
    }
}
