package org.scnsoft.fidekmp.data.api.profile

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import org.scnsoft.fidekmp.data.api.profile.dto.PersonInfoRequestDto
import org.scnsoft.fidekmp.data.api.profile.dto.PersonInfoResponseDto
import org.scnsoft.fidekmp.data.api.profile.dto.ProfileResponseDto
import org.scnsoft.fidekmp.data.api.profile.dto.UserInfoResponseDto


interface ProfileApi {
    suspend fun getCurrentUserProfile(): Result<ProfileResponseDto>

    suspend fun gerProfileById(id: String): Result<ProfileResponseDto>

    suspend fun getCurrentUserExtendInfo(): Result<UserInfoResponseDto>

    suspend fun setCurrentUserInfo( request: PersonInfoRequestDto): Result<HttpResponse>
    suspend fun getCurrentUserInfo(): Result<PersonInfoResponseDto>
    suspend fun deleteCurrentUser(): Result<HttpResponse>
}

class ProfileApiImpl(private val client: HttpClient) : ProfileApi {
//    @GET("users/me")
    override suspend fun getCurrentUserProfile(): Result<ProfileResponseDto> = runCatching {
        client.get("users/me").body()
    }

//    @GET("users/{id}")
    override suspend fun gerProfileById(id: String): Result<ProfileResponseDto> = runCatching {
        client.get("users/$id").body()
    }

//    @GET("account/settings/info")
    override suspend fun getCurrentUserExtendInfo(): Result<UserInfoResponseDto> = runCatching {
        client.get("account/settings/info").body()
    }

//    @PUT("account/settings/person")
    override suspend fun setCurrentUserInfo(request: PersonInfoRequestDto) = runCatching {
        client.put("account/settings/person") {
            setBody(request)
        }
    }
//    @GET("account/settings/person")
    override suspend fun getCurrentUserInfo(): Result<PersonInfoResponseDto> = runCatching {
        client.get("account/settings/person").body()
    }

//    @DELETE("users/delete")
    override suspend fun deleteCurrentUser():Result<HttpResponse> = runCatching {
        client.delete("users/delete")
    }

}
