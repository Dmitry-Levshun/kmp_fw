package org.scnsoft.fidekmp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.scnsoft.fidekmp.domain.model.profile.PersonInfo
import org.scnsoft.fidekmp.domain.model.profile.ProfileInfo
import org.scnsoft.fidekmp.domain.repository.CommonResult
import org.scnsoft.fidekmp.domain.repository.ProfileResult

interface ProfileRepository {
    suspend fun getCurrentProfile(): ProfileResult
    suspend fun getProfileById(id: String): ProfileResult
    suspend fun setCurrentUserInfo(person: PersonInfo): CommonResult
    suspend fun logout()
    suspend fun deleteCurrentUser(): CommonResult

    val profileFlow: StateFlow<ProfileInfo>
    val userName: Flow<String?>
    val secretKey: Flow<String?>
    val isConsumerFlow: StateFlow<Boolean>
    val isIntermediateFlow: StateFlow<Boolean>
    val personInfoFlow: StateFlow<PersonInfo>
}
