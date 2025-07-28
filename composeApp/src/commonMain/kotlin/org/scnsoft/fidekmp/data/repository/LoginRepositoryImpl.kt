package org.scnsoft.fidekmp.data.repository

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.scnsoft.fidekmp.data.api.auth.AuthApi
import org.scnsoft.fidekmp.data.api.auth.Authenticator
import org.scnsoft.fidekmp.data.api.auth.dto.ChangePasswordRequestDto
import org.scnsoft.fidekmp.data.api.auth.dto.ForgetPasswordRequestDto
import org.scnsoft.fidekmp.data.settings.AppSettingsDataSource
import org.scnsoft.fidekmp.data.api.auth.dto.LoginRequestDto
import org.scnsoft.fidekmp.data.api.auth.dto.LoginResponseDto
import org.scnsoft.fidekmp.data.api.auth.dto.ResetPasswordRequestDto
import org.scnsoft.fidekmp.data.api.auth.dto.SignUpRequestDto
import org.scnsoft.fidekmp.domain.repository.ChangePasswordResult
import org.scnsoft.fidekmp.domain.repository.CountryListResult
import org.scnsoft.fidekmp.domain.repository.LoginRepository
import org.scnsoft.fidekmp.domain.repository.LoginResult
import org.scnsoft.fidekmp.domain.repository.LoginState
import org.scnsoft.fidekmp.domain.repository.SignApiResult
import org.scnsoft.fidekmp.utils.NetworkUtils

class LoginRepositoryImpl(
    private val authenticationService: AuthApi,
    private val appSettingsDataSource: AppSettingsDataSource,
    private val authenticator: Authenticator,
) : LoginRepository {

    private val accessTokenFlow = MutableStateFlow<String?>(null)
    private val refreshTokenFlow = MutableStateFlow<String?>(null)
    private val isFakeLogin = MutableStateFlow<Boolean>(false)
    val scope = CoroutineScope(Dispatchers.IO + Job())
    override var loginStateFlow: Flow<Boolean> = accessTokenFlow.map { it != null }//.distinctUntilChanged()
    init{
        Napier.d("LoginRepository init")
        authenticator.setOnRefreshFunc(onRefresh = {
            Napier.d("setOnRefresh")
            scope.launch { loginRefresh() }
        })
        /*
        scope.launch { appSettingsDataSource.setRefreshToken("abbra")
            delay(300)
            val v = appSettingsDataSource.refreshToken.first()
            Napier.d("LoginRepository init $v")
            appSettingsDataSource.setRefreshToken("")
        }

         */
    }
    private val _countryListFlow = MutableStateFlow<MutableList<String>>(mutableListOf())
    override val countryListFlow: StateFlow<List<String>>
        get() = _countryListFlow

    override fun currentAccessToken(): String? = accessTokenFlow.value

    override suspend fun getCountryList(): CountryListResult {
        val itemsPerPage = 30
        for (page in 1 until 50) {
            val resp = authenticationService.getCountryList(page = page, itemsPerPage = itemsPerPage)
            resp.onSuccess { items ->
                _countryListFlow.value.addAll(items.hydraMembers.map { it.name })
                if (items.hydraMembers.size < itemsPerPage) return CountryListResult.Success(_countryListFlow.value)
            }.onFailure {
                return CountryListResult.Error(it)
            }
        }
        val result = CountryListResult.Success(_countryListFlow.value)
        Napier.d("getCountryList $result")
        return result
    }

    override suspend fun login(email: String, password: String, ): LoginResult {
        val requestDto = LoginRequestDto(email, password)
        Napier.d("login $requestDto")
        val loginResult =
            onResponse(authenticationService.login(requestDto))
        return loginResult
    }

    override suspend fun loginRefresh(): LoginResult {
        Napier.d("loginRefresh")
        if (!NetworkUtils.isNetworkAvailable()) return LoginResult.Error(IllegalStateException("device offline"))
        val refToken = refreshTokenFlow.value ?: appSettingsDataSource.refreshToken.first()
                ?: return LoginResult.Error(IllegalArgumentException("no refresh token error"))
        val tokens = refToken.split(";")
        if (tokens.size < 2) return LoginResult.Error(IllegalArgumentException("refresh token error"))
        authenticator.updateToken(tokens[0], tokens[1])
        val loginResult =
            onResponse( authenticationService.refreshToken() )
        return loginResult
    }

    override suspend fun logout(): Boolean {
        val result =  authenticationService.logout()
        Napier.d("logout $result")
        accessTokenFlow.value = null
        refreshTokenFlow.value = ""
        appSettingsDataSource.setRefreshToken("")
        authenticator.clearToken()
        return result.isSuccess
    }

    override suspend fun signUp(request: SignUpRequestDto): SignApiResult {
        val result = authenticationService.signUp(request =request)
        result.onSuccess {  return SignApiResult.Success }.onFailure { error ->
            return SignApiResult.Error(error)
        }
        return SignApiResult.Error(null)
    }

    override suspend fun resendEmail(email: String): SignApiResult {
        val result = authenticationService.resendEmail(email = email)
        Napier.d("resendEmail $result")
        result.onSuccess {  return SignApiResult.Success }.onFailure { error ->
            return SignApiResult.Error(error)
        }
        return SignApiResult.Error(null)
    }

    override suspend fun changePassword(oldPass: String, newPass: String): ChangePasswordResult {
        val request = ChangePasswordRequestDto(currentPassword = oldPass, newPassword = newPass)
        val result = authenticationService.changePassword(request)
        Napier.d("changePassword $result")
        result.onSuccess {
            return ChangePasswordResult.Success
        }.onFailure{ error ->
            return ChangePasswordResult.Error(error)
        }
        return ChangePasswordResult.Error(null)
    }
    override suspend fun forgetPassword(email: String): SignApiResult {
        val result = authenticationService.forgetPassword(request = ForgetPasswordRequestDto(email))
        Napier.d("forgetPassword $result")
        result.onSuccess {  return SignApiResult.Success }.onFailure { error -> return SignApiResult.Error(error) }
        return SignApiResult.Error(null)
    }

    override suspend fun checkPassToken(token: String): SignApiResult {
        val result = authenticationService.checkPassToken(token)
        Napier.d("checkPassToken $result")
        result.onSuccess {  return SignApiResult.Success }.onFailure { error -> return SignApiResult.Error(error) }
        return SignApiResult.Error(null)
    }

    override suspend fun resetPassword(newPassword: String, token: String): SignApiResult {
        val result = authenticationService.resetPassword(ResetPasswordRequestDto(newPassword, token))
        Napier.d("clickRecoveryPassword $result")
        result.onSuccess {  return SignApiResult.Success }.onFailure { error -> return SignApiResult.Error(error) }
        return SignApiResult.Error(null)
    }

    private suspend fun onResponse(result: Result<LoginResponseDto>): LoginResult {
        Napier.d("onResponse $result")
        result
            .onSuccess { value ->
                val login = LoginState.from(value)
                onLoginSuccess(login)
                return LoginResult.Success(login)
            }
            .onFailure { error ->
                onLoginFailure()
                return LoginResult.Error(error)
            }
        return LoginResult.Error(null)
    }

    private suspend fun onLoginSuccess(login: LoginState) {
        authenticator.updateToken(login.tokenType, login.accessToken)
        accessTokenFlow.emit(login.accessToken)
        val refToken = "${login.tokenType};${login.refreshToken}"
        refreshTokenFlow.emit(refToken)
        appSettingsDataSource.setRefreshToken(refToken)
    }

    private suspend fun onLoginFailure() {
        accessTokenFlow.emit(null)
        authenticator.clearToken()
        appSettingsDataSource.setRefreshToken("")
    }
}

