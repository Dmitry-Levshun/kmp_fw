package org.scnsoft.fidekmp.data.repository

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import org.scnsoft.fidekmp.data.api.profile.ProfileApi
import org.scnsoft.fidekmp.data.api.profile.dto.PersonInfoRequestDto
import org.scnsoft.fidekmp.data.settings.AppSettingsDataSource
import org.scnsoft.fidekmp.domain.model.profile.PersonInfo
import org.scnsoft.fidekmp.domain.model.profile.ProfileExtInfo
import org.scnsoft.fidekmp.domain.model.profile.ProfileInfo
import org.scnsoft.fidekmp.domain.repository.CommonResult
import org.scnsoft.fidekmp.domain.repository.ProfileRepository
import org.scnsoft.fidekmp.ui.postlogin.profile.BottomBarUserType
import org.scnsoft.fidekmp.utils.NetworkUtils
import org.scnsoft.fidekmp.utils.jsonToObject
import org.scnsoft.fidekmp.utils.objectToJson

class ProfileRepositoryImpl(
    private val userManagementService: ProfileApi,
    private val appSettingsDataSource: AppSettingsDataSource,
) : ProfileRepository {

    override val profileFlow: StateFlow<ProfileExtInfo> get() = _profileFlow
    private val _profileFlow = MutableStateFlow(ProfileExtInfo(ProfileInfo.Empty(), isConsumer = false, isIntermediate = false))
    override val personInfoFlow: StateFlow<PersonInfo> get() = _personFlow
    private val _personFlow = MutableStateFlow(PersonInfo.Empty())

    override val userName: Flow<String?> = appSettingsDataSource.userName
    override val secretKey: Flow<String?> = appSettingsDataSource.secretKey

    override suspend fun logout() {
        appSettingsDataSource.setProfileInfo("")
    }
    override suspend fun getCurrentProfile(): ProfileResult {
        Napier.d("getCurrentProfile")
        if (_profileFlow.value.profile.isNotEmpty()) return ProfileResult.Success(_profileFlow.value)
        if (NetworkUtils.isNetworkAvailable()) {
            val resp = userManagementService.getCurrentUserProfile()
            resp.onSuccess { dto ->
                val profile = ProfileInfo.profileFromDto(dto)
                val accType = dto.accountType.displayName
                val isConsumer = accType.contains("consume", true)
                val isIntermediate =
                    accType.contains("intermediate", true) || accType.contains("wineshop", true)
                profile.userType =
                    if (isConsumer) BottomBarUserType.CONSUMER else if (isIntermediate) BottomBarUserType.INTERMEDIATE else BottomBarUserType.WINEYARD
                Napier.d("getCurrentProfile $isIntermediate")
                val profileExtInfo = ProfileExtInfo(profile = profile, isConsumer = isConsumer, isIntermediate = isIntermediate)
                _profileFlow.emit(profileExtInfo)
                val profileInfo = objectToJson(profile)
                appSettingsDataSource.setProfileInfo(profileInfo)
//                val json = objectToJsonEx(profile)
//                Timber.d("getCurrentProfile json ${json}")
//                val prof : ProfileInfo? = jsonToObjectEx( json)
//                Timber.d("getCurrentProfile obj ${prof}")
                Napier.d("getCurrentUserInfo")
                if (isConsumer) {
                    val respP = userManagementService.getCurrentUserInfo()
                    respP.onSuccess { dto ->
                        val person = PersonInfo.personFromDto(dto.person)
                        _personFlow.value = person
                    }
                }
                return ProfileResult.Success(profileExtInfo)
            }
                .onFailure { e ->
                    return ProfileResult.Error(e)
                }
            return ProfileResult.Error(IllegalArgumentException("getCurrentProfile no info"))
        } else {
            val info = appSettingsDataSource.profileInfo.first() ?: return ProfileResult.Error(
                IllegalArgumentException("getCurrentProfile no info in storage")
            )
            val profile: ProfileExtInfo = jsonToObject(info) ?: return ProfileResult.Error(
                IllegalArgumentException("getCurrentProfile conversion error")
            )
            _profileFlow.emit(profile)
            return ProfileResult.Success(profile)
        }
    }

    /*
    override suspend fun getProfileById(id: String): ProfileResult {
        val resp = userManagementService.gerProfileById(id)
        resp.onSuccess { dto ->
                return ProfileResult.Success(ProfileInfo.profileFromDto(dto))
            }
            .onFailure {  e ->
                return ProfileResult.Error(e)
            }
        return ProfileResult.Error(IllegalArgumentException("no info in storage"))
    }

     */

    override suspend fun setCurrentUserInfo(person: PersonInfo): CommonResult {
        val currPerson = _personFlow.value
        /*
        val request = PersonInfoRequestDto(
//            avatar = person.avatar,
            name = if (person.name != currPerson.name) person.name else null,
            surname = if (person.surname != currPerson.surname) person.surname else null,
//            email = person.email,
            phone = if (person.phone != currPerson.phone) person.phone else null,
            address = if (person.address != currPerson.address) person.address else null,
            zipCode = if (person.zipCode != currPerson.zipCode) person.zipCode else null,
            languageId = 2)
                    */
        val request = PersonInfoRequestDto(
            avatar = person.avatar,
            name = person.name,
            surname = person.surname,
//            email = person.email,
            phone = person.phone,
            address = person.address,
            zipCode = person.zipCode,
            languageId = 2)

        val res = userManagementService.setCurrentUserInfo(request)
        val result = when (res.isSuccess) {
            true -> CommonResult.Success
            false -> CommonResult.Error(res.exceptionOrNull())

            }
        return result
    }

    override suspend fun deleteCurrentUser(): CommonResult {
        val resp = userManagementService.deleteCurrentUser()
        val result = when (resp.isSuccess) {
            true -> CommonResult.Success
            false -> CommonResult.Error(resp.exceptionOrNull())
        }
        return result
    }
}
sealed class ProfileResult {
    data class Success(val profile: ProfileExtInfo) : ProfileResult()
    data class Error(val e:Throwable): ProfileResult()

    override fun toString(): String {
        return "ProfileResult " +
        when(this) {
            is Success -> "Success $profile"
            is Error ->"Error $e"
            else -> "Error unknown"
        }
    }

}


