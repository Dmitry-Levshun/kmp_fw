package org.scnsoft.fidekmp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.scnsoft.fidekmp.data.api.untracked.dto.AddUntrackedWineRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedCustomWineRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineReviewRequest
import org.scnsoft.fidekmp.domain.repository.CommonResult
import org.scnsoft.fidekmp.domain.repository.UntrackedUserWineListResult
import org.scnsoft.fidekmp.domain.repository.UntrackedUserWineResult
import org.scnsoft.fidekmp.domain.repository.UntrackedWineListResult
import org.scnsoft.fidekmp.domain.repository.UntrackedWineResult

interface UntrackedWineRepository {
    suspend fun addUntrackedWine(request: AddUntrackedWineRequest): CommonResult
    suspend fun getUntrackedWines(page: Int? = null, itemsPerPage: Int? = null, name: String? = null): UntrackedWineListResult
    suspend fun getUntrackedWineProducers(page: Int?, itemsPerPage: Int?, producerName: String?): Flow<List<UntrackedWineItem>>
    suspend fun getUntrackedWinesFlow(page: Int?, itemsPerPage: Int?, wineName: String?): Flow<List<UntrackedWineItem>>

    suspend fun getUntrackedUserWines(page: Int? = null, itemsPerPage: Int? = null, name: String? = null): UntrackedUserWineListResult
    suspend fun getUntrackedWineById(id: Int): UntrackedWineResult
    suspend fun getUntrackedUserWineById(id: Int): UntrackedUserWineResult
    suspend fun addUntrackedWineReview(request: UntrackedWineReviewRequest): CommonResult
    suspend fun addUntrackedCustomWine(request: UntrackedCustomWineRequest): CommonResult

    suspend fun getWineItemsPaged(query: String): Flow<PagingData<UntrackedWineItem>>
    fun getUserWineItemsPaged(query: String): Flow<PagingData<UntrackedUserWineItem>>
}
/*
class UntrackedWineRepositoryImpl @Inject constructor(
    private val untrackedWineService: UntrackedWineService,
    private val apiRequestSender: ApiRequestSender,
    private val appSettingsDataSource: AppSettingsDataSource
): UntrackedWineRepository {
    override suspend fun addUntrackedWine(request: AddUntrackedWineRequest): CommonResult {
        Timber.d("addUntrackedWine $request")
        val result = when (val response =
            apiRequestSender.sendRequest {
                untrackedWineService.addUntrackedWine(request)
            }) {
            is ApiResult.Success -> {
                CommonResult.Success
            }

            is ApiResult.Failure -> {
                CommonResult.Failure(response.apiFailure)
            }

            is ApiResult.Error -> {
                CommonResult.Error(response.e)
            }
        }
        Timber.d("addUntrackedWine result$result")
        return result
    }
    override suspend fun addUntrackedWineReview(request: UntrackedWineReviewRequest): CommonResult {
        Timber.d("addUntrackedWineReview $request")
        val result = when (val response =
            apiRequestSender.sendRequest {
                untrackedWineService.addUntrackedWineReview(request)
            }) {
            is ApiResult.Success -> {
                CommonResult.Success
            }
            is ApiResult.Failure -> {
                CommonResult.Failure(response.apiFailure)
            }

            is ApiResult.Error -> {
                CommonResult.Error(response.e)
            }
        }
        Timber.d("addUntrackedWineReview result$result")
        return result

    }
    override suspend fun getUntrackedWines(
        page: Int?,
        itemsPerPage: Int?,
        name: String?
    ): UntrackedWineListResult {

        val result = when (val response =
            apiRequestSender.sendRequest {
                untrackedWineService.getUntrackedWines(
                    page = page,
                    itemsPerPage = itemsPerPage,
                    name = name
                )
            }) {
            is ApiResult.Success -> {
                UntrackedWineListResult.Success(response.dto.wineList)
            }

            is ApiResult.Failure -> {
                UntrackedWineListResult.Failure(response.apiFailure)
            }

            is ApiResult.Error -> {
                UntrackedWineListResult.Error(response.e)
            }
        }
        return result
    }

    override suspend fun getUntrackedWineProducers(page: Int?, itemsPerPage: Int?, producerName: String?): Flow<List<UntrackedWineItem>> {
        return flow {
            val response =
                untrackedWineService.getUntrackedWineProducers(
                    page = page,
                    itemsPerPage = itemsPerPage,
                    producerName = producerName
                )
            if (response != null && response.isSuccessful) {
                val dto = response.body() ?: return@flow
                emit(dto.wineList)
            }
        }
    }
    override suspend fun getUntrackedWinesFlow(page: Int?, itemsPerPage: Int?, wineName: String?): Flow<List<UntrackedWineItem>> {
        return flow {
            val response =
                untrackedWineService.getUntrackedWinesbyWineName(
                    page = page,
                    itemsPerPage = itemsPerPage,
                    wineName = wineName
                )
            if (response != null && response.isSuccessful) {
                val dto = response.body() ?: return@flow
                emit(dto.wineList)
            }
        }
    }

    override suspend fun getUntrackedUserWines(
        page: Int?,
        itemsPerPage: Int?,
        name: String?
    ): UntrackedUserWineListResult {
        val result = when (val response =
            apiRequestSender.sendRequest {
                untrackedWineService.getUntrackedUserWines(
                    page = page,
                    itemsPerPage = itemsPerPage,
                    name = name
                )
            }) {
            is ApiResult.Success -> {
                UntrackedUserWineListResult.Success(response.dto.wineList)
            }

            is ApiResult.Failure -> {
                UntrackedUserWineListResult.Failure(response.apiFailure)
            }

            is ApiResult.Error -> {
                UntrackedUserWineListResult.Error(response.e)
            }
        }
        return result
    }

    override suspend fun getUntrackedWineById(id: Int): UntrackedWineResult {
        val result = when (val response =
            apiRequestSender.sendRequest {
                untrackedWineService.getUntrackedWineById(id)
            }) {
            is ApiResult.Success -> {
                UntrackedWineResult.Success(response.dto)
            }

            is ApiResult.Failure -> {
                UntrackedWineResult.Failure(response.apiFailure)
            }

            is ApiResult.Error -> {
                UntrackedWineResult.Error(response.e)
            }
        }
        return result
    }

    override suspend fun getUntrackedUserWineById(id: Int): UntrackedUserWineResult {
        val result = when (val response =
            apiRequestSender.sendRequest {
                untrackedWineService.getUntrackedUserWineById(id)
            }) {
            is ApiResult.Success -> {
                UntrackedUserWineResult.Success(response.dto)
            }

            is ApiResult.Failure -> {
                UntrackedUserWineResult.Failure(response.apiFailure)
            }

            is ApiResult.Error -> {
                UntrackedUserWineResult.Error(response.e)
            }
        }
        return result
    }

    override  suspend fun addUntrackedCustomWine(request: UntrackedCustomWineRequest): CommonResult{
        val result = when (val response =
            apiRequestSender.sendRequest {
                untrackedWineService.addUntrackedCustomWine(request)
            }) {
            is ApiResult.Success -> {
                CommonResult.Success
            }
            is ApiResult.Failure -> {
                CommonResult.Failure(response.apiFailure)
            }

            is ApiResult.Error -> {
                CommonResult.Error(response.e)
            }
        }
        return result

    }

    override fun getWineItemsPaged(query: String): Flow<PagingData<UntrackedWineItem>> = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            WinesDataSource(wineApiService = untrackedWineService, query = query)
        }
    ).flow

    override fun getUserWineItemsPaged(query: String): Flow<PagingData<UntrackedUserWineItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            UserWinesDataSource(wineApiService = untrackedWineService, query = query)
        }
    ).flow

}

 */