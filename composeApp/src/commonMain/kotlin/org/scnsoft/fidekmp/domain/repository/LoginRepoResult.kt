package org.scnsoft.fidekmp.domain.repository

import org.scnsoft.fidekmp.data.api.auth.dto.CountryListDto
import org.scnsoft.fidekmp.data.api.auth.dto.LoginResponseDto

sealed class LoginResult {
    class Success(val login: LoginState) : LoginResult()
    class Error(val e: Throwable?) : LoginResult()
    override fun toString(): String {
        return "LoginResult " +
                when(this) {
                    is LoginResult.Success -> "Success $login"
                    is LoginResult.Error ->"Error $e"

                }
    }

}
sealed class ChangePasswordResult {
    object Success : ChangePasswordResult()
    class Error(val e: Throwable?) : ChangePasswordResult()
}

class LoginState(
    val accessToken: String,
    val tokenType: String,
    val tokenId: String,
    val refreshToken: String
) {
    companion object {
        fun from(responseDto: LoginResponseDto): LoginState {
            val tokenId = responseDto.tokenID ?: responseDto.tokenId
            return LoginState(
                responseDto.accessToken,
                responseDto.tokenType,
                tokenId!!,
                refreshToken = responseDto.refreshToken!!
            )
        }
    }
}

sealed class CountryListResult {
    class Success(val countries: List<String>?) : CountryListResult()
    class Error(val e: Throwable?) : CountryListResult()
    override fun toString(): String {
        return "CountryListResult " +
                when(this) {
                    is CountryListResult.Success -> "Success $countries"
                    is CountryListResult.Error ->"Error $e"

                }
    }
    companion object {
        fun fromResult(result: Result<CountryListDto>): CountryListResult {
            result
                .onSuccess { value ->
                    return Success(value.hydraMembers.map { it.name })
                }
                .onFailure { error ->
                    return Error(error)
                }
            return Error(null)
        }
    }
}

sealed class SignApiResult {
    object Success : SignApiResult()
    class Error(val e: Throwable?) : SignApiResult()
    override fun toString(): String {
        return "SignApiResult " +
                when(this) {
                    is Success -> "Success"
                    is Error ->"Error $e"
                }
    }
}
