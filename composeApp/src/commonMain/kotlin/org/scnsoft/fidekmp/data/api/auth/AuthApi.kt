package org.scnsoft.fidekmp.data.api.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import org.scnsoft.fidekmp.data.api.auth.dto.ChangePasswordRequestDto
import org.scnsoft.fidekmp.data.api.auth.dto.CountryListDto
import org.scnsoft.fidekmp.data.api.auth.dto.LoginRequestDto
import org.scnsoft.fidekmp.data.api.auth.dto.LoginResponseDto
import org.scnsoft.fidekmp.data.api.auth.dto.ResetPasswordRequestDto
import org.scnsoft.fidekmp.data.api.auth.dto.SignUpRequestDto
import org.scnsoft.fidekmp.data.api.auth.dto.VerifyResponseDto
import org.scnsoft.fidekmp.data.api.auth.dto.ForgetPasswordRequestDto


interface AuthApi {
    suspend fun login(request: LoginRequestDto): Result<LoginResponseDto>
    suspend fun logout(): Result<HttpResponse>
    suspend fun signUp(request: SignUpRequestDto): Result<HttpResponse>
    suspend fun refreshToken(): Result<LoginResponseDto>
    suspend fun verifyToken(): Result<VerifyResponseDto>
    suspend fun resendEmail(email: String, resend: Boolean = true): Result<HttpResponse>
    suspend fun changePassword(request: ChangePasswordRequestDto): Result<HttpResponse>
    suspend fun getCountryList(page: Int? = null, itemsPerPage: Int? = null,): Result<CountryListDto>
    suspend fun forgetPassword(request: ForgetPasswordRequestDto): Result<HttpResponse>
    suspend fun checkPassToken(token: String): Result<HttpResponse>
    suspend fun resetPassword(request: ResetPasswordRequestDto): Result<HttpResponse>
}

class AuthApiImpl(private val client: HttpClient) : AuthApi {

    companion object {
        const val GRANT_TYPE_PASSWORD = "password"
        const val GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials"
    }

//    @POST("auth/login")
    override suspend fun login(request: LoginRequestDto): Result<LoginResponseDto> = runCatching {
         client.post("auth/login") { setBody(request) }.body()
    }
    /*
    Ktor.post("/user") { setBody(user) }.body()
    Result<Users> = runCatching {
        Ktor.get("/user").body()
    }

     */
//    @GET("auth/logout")
    override suspend fun logout() = runCatching {
        client.get("auth/logout")
    }

//    @POST("auth/signup")
    override suspend fun signUp(request: SignUpRequestDto): Result<HttpResponse> = runCatching {
        val agent = "Android"
        client.post("auth/signup"){
            headers{
                append("User-Agent", agent)
            }
            setBody(request)
        }
    }

    //    @GET("auth/refresh")
    override suspend fun refreshToken(): Result<LoginResponseDto> = runCatching {
        client.get("auth/refresh").body()
    }

//    @GET("auth/verify")
    override suspend fun verifyToken(): Result<VerifyResponseDto> = runCatching {
        client.get("auth/verify").body()
    }

//    @GET("auth/verify-email")//?email=<string>&code=<string>&resend=<boolean>
    override suspend fun resendEmail(email: String, resend: Boolean) = runCatching {
        client.get("auth/verify-email") {
            parameter("email", email)
            parameter("resend", resend)
        }
    }

//    @PUT("auth/password")
    override suspend fun changePassword(request: ChangePasswordRequestDto): Result<HttpResponse> =
        kotlin.runCatching {
            client.put("auth/password") {
                setBody(request)
            }
        }

    //    @GET("wineyard/countries")
//    @GET("wineyard/geo/countries")
    override suspend fun getCountryList(page: Int?, itemsPerPage: Int?): Result<CountryListDto> = runCatching {
        client.get("wineyard/geo/countries") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
        }.body()
    }

//    @POST("auth/forgot-password")
    override suspend fun forgetPassword(request: ForgetPasswordRequestDto) = runCatching {
        client.post("auth/forgot-password") {
            setBody(request)
        }
    }

//    @GET("auth/check-password-token/{token}")
    override suspend fun checkPassToken(token: String) = runCatching {
        client.get("auth/check-password-token/$token")
    }

//    @POST("auth/reset-password")
    override suspend fun resetPassword(request: ResetPasswordRequestDto) = runCatching {
        client.post("auth/reset-password") {
            setBody(request)
        }
    }
/*
    @POST("wineyard/digital-passports/find-in-scanning-mode")
    suspend fun getQrCodeInfoScanMode(@Body request: QrCodeInfoRequest): Response<QrCodeInfoResponse>

    @POST("wineyard/digital-passports/find-in-wallet-mode")
    suspend fun getQrCodeInfoWalletMode(@Body request: QrCodeInfoRequest): Response<QrCodeInfoResponse>

    @POST("wineyard/packages/get-bottles-by-case")
    suspend fun getQrCodeBoxInfoInfo(@Body request: QrCodeBoxInfoRequest): Response<QrBoxCodeInfoResponse>
*/
}

interface Authenticator {
    var token: String?
    var tokenType: String?
    var onRefresh: (() -> Unit)?
    fun updateToken(tokenType: String, token: String)
    fun clearToken()
    fun setOnRefreshFunc(onRefresh: ()->Unit)
}