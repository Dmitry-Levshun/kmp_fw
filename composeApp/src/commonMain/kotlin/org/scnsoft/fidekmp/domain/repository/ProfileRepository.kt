package org.scnsoft.fidekmp.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.scnsoft.fidekmp.data.repository.ProfileResult
import org.scnsoft.fidekmp.domain.model.profile.PersonInfo
import org.scnsoft.fidekmp.domain.model.profile.ProfileExtInfo

interface ProfileRepository {
    suspend fun getCurrentProfile(): ProfileResult
//    suspend fun getProfileById(id: String): ProfileResult
    suspend fun setCurrentUserInfo(person: PersonInfo): CommonResult
    suspend fun logout()
    suspend fun deleteCurrentUser(): CommonResult

    val profileFlow: StateFlow<ProfileExtInfo>
    val userName: Flow<String?>
    val secretKey: Flow<String?>
    val personInfoFlow: StateFlow<PersonInfo>
}
