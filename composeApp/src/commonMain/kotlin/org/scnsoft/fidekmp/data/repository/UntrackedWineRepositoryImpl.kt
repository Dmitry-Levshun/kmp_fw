package org.scnsoft.fidekmp.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.scnsoft.fidekmp.data.api.ApiRequestSender
import org.scnsoft.fidekmp.data.api.ApiResult
import org.scnsoft.fidekmp.data.api.ErrorParser
import org.scnsoft.fidekmp.data.api.untracked.UntrackedWineApi
import org.scnsoft.fidekmp.data.api.untracked.dto.AddUntrackedWineRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedCustomWineRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineReviewRequest
import org.scnsoft.fidekmp.data.settings.AppSettingsDataSource
import org.scnsoft.fidekmp.domain.repository.CommonResult
import org.scnsoft.fidekmp.domain.repository.UntrackedUserWineListResult
import org.scnsoft.fidekmp.domain.repository.UntrackedUserWineResult
import org.scnsoft.fidekmp.domain.repository.UntrackedWineListResult
import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository
import org.scnsoft.fidekmp.domain.repository.UntrackedWineResult
import org.scnsoft.fidekmp.utils.getTickCount

class UntrackedWineRepositoryImpl(
    private val untrackedWineService: UntrackedWineApi,
    private val appSettingsDataSource: AppSettingsDataSource,
    private val errorParser: ErrorParser,
    private val apiRequestSender: ApiRequestSender
    ): UntrackedWineRepository {

    override suspend fun addUntrackedWine(request: AddUntrackedWineRequest): CommonResult {
            Napier.d("addUntrackedWine $request")
            val result = untrackedWineService.addUntrackedWine(request)
            return if (result.isSuccess) {
                CommonResult.Success
            } else {
                val e = result.exceptionOrNull() ?: return CommonResult.Error(Exception("Unknown error"))
                if (errorParser.parse(e).code != -1 ) return CommonResult.Failure(errorParser.parse(e))
                CommonResult.Error(e)
            }
        }

        override suspend fun addUntrackedWineReview(request: UntrackedWineReviewRequest): CommonResult {
            Napier.d("addUntrackedWineReview $request")
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
            Napier.d("addUntrackedWineReview result$result")
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
                if ( response.isSuccess) {
                    val dto = response.getOrNull() ?: return@flow
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
                if (response.isSuccess) {
                    val dto = response.getOrNull() ?: return@flow
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

        private var getWineIdTime: Long = 0
        private var _untrackedUserWineInfoState: UntrackedWineItem? = null
        override suspend fun getUntrackedWineById(id: Int): UntrackedWineResult {
            if (getTickCount() - getWineIdTime < 1000 && _untrackedUserWineInfoState != null) {
                return UntrackedWineResult.Success(_untrackedUserWineInfoState!!)
            }
            getWineIdTime = getTickCount()
            val result = when (val response =
                apiRequestSender.sendRequest {
                    untrackedWineService.getUntrackedWineById(id)
                }) {
                is ApiResult.Success -> {
                    _untrackedUserWineInfoState = response.dto
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

        override suspend fun getWineItemsPaged(query: String): Flow<PagingData<UntrackedWineItem>> = Pager(
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