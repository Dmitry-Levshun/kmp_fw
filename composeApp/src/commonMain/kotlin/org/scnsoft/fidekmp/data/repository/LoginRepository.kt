package org.scnsoft.fidekmp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.scnsoft.fidekmp.data.api.auth.dto.SignUpRequestDto

interface LoginRepository {
    suspend fun login(email: String, password: String, ): LoginResult
    suspend fun loginRefresh(): LoginResult
    var loginStateFlow: Flow<Boolean>
    val countryListFlow: StateFlow<List<String>>
    fun currentAccessToken(): String?
    suspend fun logout(): Boolean
    suspend fun changePassword(oldPass: String, newPass: String): ChangePasswordResult
    suspend fun getCountryList(): CountryListResult
    suspend fun signUp(request: SignUpRequestDto): SignApiResult
    suspend fun resendEmail(email: String): SignApiResult
    suspend fun forgetPassword(email: String): SignApiResult
    suspend fun checkPassToken(token: String): SignApiResult
    suspend fun resetPassword(newPassword: String, token: String): SignApiResult
}
