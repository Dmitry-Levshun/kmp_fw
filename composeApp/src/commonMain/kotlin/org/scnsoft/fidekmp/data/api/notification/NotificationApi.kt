package org.scnsoft.fidekmp.data.api.notification

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import org.scnsoft.fidekmp.data.api.notification.dto.MessageContentRequest
import org.scnsoft.fidekmp.data.api.notification.dto.MessageSendContentRequest
import org.scnsoft.fidekmp.data.api.notification.dto.NotificationResponseDto
import org.scnsoft.fidekmp.data.api.notification.dto.NotificationResponseItem
import org.scnsoft.fidekmp.data.api.notification.dto.ReadRequest
import org.scnsoft.fidekmp.data.api.notification.dto.DeviceInfoRequestDto

interface NotificationApi {
//    @POST("wineyard/notification/devices")
    suspend fun setDeviceInfo( deviceInfoRequestDto: DeviceInfoRequestDto): Result<HttpResponse>

//    @GET("wineyard/notification/user/messages") //?page=1&itemsPerPage=30
    suspend fun getMessages(page: Int? = null,itemsPerPage: Int? = null): Result<NotificationResponseDto>

//    @DELETE("wineyard/notification/user/messages/{id}")
    suspend fun deleteMessage(id: String): Result<HttpResponse>

//    @PATCH("wineyard/notification/user/messages/{id}")
    suspend fun markAsReadMessage(id: String, patchBody: ReadRequest): Result<NotificationResponseItem>

//    @PATCH("wineyard/notification/user/messages")
    suspend fun markAsReadAllMessages(patchBody: ReadRequest): Result<HttpResponse>

//    @POST("wineyard/notification/messages")
    suspend fun putMessage(body: MessageContentRequest): Result<NotificationResponseItem>

//    @POST("wineyard/notification/user/messages")
    suspend fun sendMessage(body: MessageSendContentRequest): Result<HttpResponse>

}

class NotificationApiImpl(private val client: HttpClient) : NotificationApi {
    //    @POST("wineyard/notification/devices")
    override suspend fun setDeviceInfo(deviceInfoRequestDto: DeviceInfoRequestDto): Result<HttpResponse> = runCatching {
        client.post("wineyard/notification/devices") {
            setBody(deviceInfoRequestDto)
        }
    }

    //    @GET("wineyard/notification/user/messages") //?page=1&itemsPerPage=30
    override suspend fun getMessages(page: Int?, itemsPerPage: Int?): Result<NotificationResponseDto> = runCatching {
        client.get("wineyard/notification/user/messages") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
        }.body()
    }

    //    @DELETE("wineyard/notification/user/messages/{id}")
    override suspend fun deleteMessage(id: String): Result<HttpResponse> = runCatching {
        client.delete("wineyard/notification/user/messages/$id")
    }

    //    @PATCH("wineyard/notification/user/messages/{id}")
    override suspend fun markAsReadMessage(id: String, patchBody: ReadRequest): Result<NotificationResponseItem> = runCatching {
        client.patch("wineyard/notification/user/messages/$id") {
            setBody(patchBody)
        }.body()
    }

    //    @PATCH("wineyard/notification/user/messages")
    override suspend fun markAsReadAllMessages(patchBody: ReadRequest): Result<HttpResponse> = runCatching {
        client.patch("wineyard/notification/user/messages") {
            setBody(patchBody)
        }
    }

    //    @POST("wineyard/notification/messages")
    override suspend fun putMessage(body: MessageContentRequest): Result<NotificationResponseItem> = runCatching {
        client.post("wineyard/notification/messages") {
            setBody(body)
        }.body()
    }

    //    @POST("wineyard/notification/user/messages")
    override suspend fun sendMessage(body: MessageSendContentRequest): Result<HttpResponse> = runCatching {
        client.post("wineyard/notification/user/messages") {
            setBody(body)
        }
    }

}