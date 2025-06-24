package org.scnsoft.fidekmp.data.api.untracked

import org.scnsoft.fidekmp.data.api.untracked.dto.AddUntrackedWineRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedCustomWineRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemById
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineListDto
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineListDto
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineReviewRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

interface UntrackedWineApi {
//    @POST("wineyard/wines/user/external")
    suspend fun addUntrackedWine(request: AddUntrackedWineRequest): Result<HttpResponse>

//    @GET("wineyard/wines/user/external")
    suspend fun getUntrackedUserWines(page: Int? = null, itemsPerPage: Int? = null,  name: String? = null,): Result<UntrackedUserWineListDto>

    //        case name
//        case search
//        case producerName = "producer.name"
//        case producerTitle = "producer.title"
//    @GET("wineyard/wines/external")
    suspend fun getUntrackedWineProducers(page: Int? = null, itemsPerPage: Int? = null, producerName: String? = null,): Result<UntrackedWineListDto>
//    @GET("wineyard/wines/external")
    suspend fun getUntrackedWinesbyWineName(page: Int? = null, itemsPerPage: Int? = null, wineName: String? = null,): Result<UntrackedWineListDto>

    //@GET("wineyard/wines/external")
    suspend fun getUntrackedWines(page: Int? = null, itemsPerPage: Int? = null, name: String? = null,): Result<UntrackedWineListDto>

    //@GET("wineyard/wines/external/{id}")
    suspend fun getUntrackedWineById(id: Int): Result<UntrackedWineItem>

    //@GET("wineyard/wines/user/external/{id}")
    suspend fun getUntrackedUserWineById(id: Int): Result<UntrackedUserWineItemById>

//    @POST ("wineyard/wines/external/review")
    suspend fun addUntrackedWineReview(request: UntrackedWineReviewRequest): Result<HttpResponse>

//    @POST ("wineyard/wines/external")
    suspend fun addUntrackedCustomWine(request: UntrackedCustomWineRequest): Result<HttpResponse>

}
class UntrackedWineApiImpl(private val client: HttpClient): UntrackedWineApi {

//    @POST("wineyard/wines/user/external")
    override suspend fun addUntrackedWine(request: AddUntrackedWineRequest): Result<HttpResponse> = runCatching {
        client.post("wineyard/wines/user/external") { setBody(request) }.body()
    }

//    @GET("wineyard/wines/user/external")
    override suspend fun getUntrackedUserWines(page: Int?, itemsPerPage: Int?, name: String? ,): Result<UntrackedUserWineListDto> = runCatching {
        client.get("wineyard/wines/user/external") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("search", name)
        }.body()
    }

    //        case name
//        case search
//        case producerName = "producer.name"
//        case producerTitle = "producer.title"
    //@GET("wineyard/wines/external")
    override suspend fun getUntrackedWineProducers(page: Int? , itemsPerPage: Int?, producerName: String?): Result<UntrackedWineListDto>  = runCatching {
        client.get("wwineyard/wines/external") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("producer.name", producerName)
        }.body()
    }
    //@GET("wineyard/wines/external")
    override suspend fun getUntrackedWinesbyWineName(page: Int?, itemsPerPage: Int?, wineName: String?): Result<UntrackedWineListDto> = runCatching {
        client.get("wwineyard/wines/external") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("name", wineName)
        }.body()
    }

//    @GET("wineyard/wines/external")
override suspend fun getUntrackedWines(page: Int?, itemsPerPage: Int?, name: String?): Result<UntrackedWineListDto> = runCatching {
    client.get("wwineyard/wines/external") {
        parameter("page", page)
        parameter("itemsPerPage", itemsPerPage)
        parameter("search", name)
    }.body()
}

    //@GET("wineyard/wines/external/{id}")
    override suspend fun getUntrackedWineById(id: Int): Result<UntrackedWineItem> = runCatching {
        client.get("wineyard/wines/external/$id").body()
    }

//    @GET("wineyard/wines/user/external/{id}")
    override suspend fun getUntrackedUserWineById(id: Int): Result<UntrackedUserWineItemById> = runCatching {
        client.get("ineyard/wines/user/external/$id").body()
    }

//    @POST ("wineyard/wines/external/review")
    override suspend fun addUntrackedWineReview(request: UntrackedWineReviewRequest): Result<HttpResponse> = runCatching {
        client.post("wineyard/wines/external/review") { setBody(request) }
    }
    //@POST ("wineyard/wines/external")
    override suspend fun addUntrackedCustomWine(request: UntrackedCustomWineRequest): Result<HttpResponse> = runCatching {
        client.post("wineyard/wines/external/review") { setBody(request) }
    }
}