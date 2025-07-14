package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import app.cash.paging.cachedIn
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptDeliveryInstructionItemBottleType
import org.scnsoft.fidekmp.data.api.untracked.dto.AddUntrackedWineRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedCustomWineRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemById
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineReviewRequest
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineReviewRequestDrunkItem
import org.scnsoft.fidekmp.domain.usecase.OnUntrackedMainWineSearchUseCase
import org.scnsoft.fidekmp.domain.model.PackageItem
import org.scnsoft.fidekmp.domain.repository.UntrackedWineListResult
import org.scnsoft.fidekmp.domain.usecase.OnAddUntrackedWineUseCase
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.utils.toSimpleMonthBackEndString
import  org.scnsoft.fidekmp.domain.repository.CommonResult
import org.scnsoft.fidekmp.domain.repository.UntrackedUserWineResult
import org.scnsoft.fidekmp.domain.repository.UntrackedWineResult
import org.scnsoft.fidekmp.domain.usecase.AddUntrackedCustomWineUseCase
import org.scnsoft.fidekmp.domain.usecase.AddUntrackedWineReviewUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedUserWineByIdUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedUserWineItemsPagedUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedWineByIdUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedWineItemsPagedUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedWineProducersUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedWinesFlowUseCase

class UntrackedViewModel(
    private val onUntrackedMainWineSearchUseCase: OnUntrackedMainWineSearchUseCase,
    private val onAddUntrackedWineUseCase: OnAddUntrackedWineUseCase,
    private val getUntrackedWineByIdUseCase: GetUntrackedWineByIdUseCase,
    private val addUntrackedWineReviewUseCase: AddUntrackedWineReviewUseCase,
    private val getUntrackedWinesFlowUseCase: GetUntrackedWinesFlowUseCase,
    private val getUntrackedWineProducersUseCase: GetUntrackedWineProducersUseCase,
    private val addUntrackedCustomWineUseCase: AddUntrackedCustomWineUseCase,
    private val getUntrackedWineItemsPagedUseCase: GetUntrackedWineItemsPagedUseCase,
    private val getUntrackedUserWineByIdUseCase: GetUntrackedUserWineByIdUseCase,
    private val getUntrackedUserWineItemsPagedUseCase: GetUntrackedUserWineItemsPagedUseCase,
    ): ViewModel(), IUntrackedModel {
    private val _uiResult: MutableStateFlow<UiResult?> = MutableStateFlow(null)
    override val uiResult: StateFlow<UiResult?> get() = _uiResult

    override val untrackedWineListState: Flow<List<UntrackedWineItem>> get() = _untrackedWineListState
    private val _untrackedWineListState = MutableStateFlow<List<UntrackedWineItem>>(listOf())

    override val untrackedSearchText: StateFlow<String> get() = _untrackedSearchText
    private val _untrackedSearchText = MutableStateFlow("")

    override val untrackedSelectedWine: StateFlow<PackageItem> get() = _untrackedSelectedWine
    private val _untrackedSelectedWine: MutableStateFlow<PackageItem> = MutableStateFlow(PackageItem.Empty())

    override val untrackedWineDetailsInfo: StateFlow<PackageItem> get() = _untrackedWineDetailsInfo
    private val _untrackedWineDetailsInfo: MutableStateFlow<PackageItem> = MutableStateFlow(PackageItem.Empty())

    override val untrackedUserWineInfoState: Flow<List<UntrackedUserWineItemById>> get() = _untrackedUserWineInfoState
    private val _untrackedUserWineInfoState: MutableStateFlow<List<UntrackedUserWineItemById>> = MutableStateFlow(listOf())

    override val untrackedWineInfoState: Flow<List<UntrackedWineItem>> get() = _untrackedWineInfoState
    private val _untrackedWineInfoState: MutableStateFlow<List<UntrackedWineItem>> = MutableStateFlow(listOf())


    override val bottleList: StateFlow<List<DptDeliveryInstructionItemBottleType>> get() = _bottleList
    private val _bottleList: MutableStateFlow<List<DptDeliveryInstructionItemBottleType>> = MutableStateFlow(listOf())

    private val _untrackedCustomWineSearchText = MutableStateFlow("")
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override val untrackedCustomWines: Flow<List<UntrackedWineItem>> = _untrackedCustomWineSearchText.debounce(300)
        .flatMapLatest { query ->
            getUntrackedWinesFlowUseCase(query)
        }
    private val _untrackedCustomProducerSearchText = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override val untrackedCustomWineProducers: Flow<List<UntrackedWineItem>> = _untrackedCustomProducerSearchText.debounce(300)
        .flatMapLatest { query ->
            getUntrackedWineProducersUseCase(query)
        }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override val untrackedWines: Flow<PagingData<UntrackedWineItem>> = _untrackedSearchText.debounce(300)
        .flatMapLatest { query ->
            getUntrackedWineItemsPagedUseCase(query)
        }.cachedIn(viewModelScope)


    override val untrackedUserWines: Flow<PagingData<UntrackedUserWineItem>> = getUntrackedUserWineItemsPagedUseCase().cachedIn(viewModelScope)

    private val _errorToast = MutableStateFlow("")
    override val errorToast: StateFlow<String> get() = _errorToast

    private val dispatcherProvider =  Dispatchers.IO
    override fun resetUiResult() {
        _uiResult.value = null
    }

    private var debounceJob: Job? = null
    override fun onUntrackMainWineSearch(query: String) {
        _untrackedWineListState.value = listOf()
        _untrackedSearchText.value = query
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch(dispatcherProvider) {
            delay(500)
            val result = onUntrackedMainWineSearchUseCase(query)
            if (result is UntrackedWineListResult.Success) _untrackedWineListState.value = result.list
        }
    }

    override fun addUntrackedWine(
        vintage: Int,
        bottleVolume: Double,
        externalWineIdUrl: String,
        qty: Int,
        price: Double,
        vendorName: String,
        dateOfPurchase: LocalDateTime
    ) {
        Napier.d("addUntrackedWine")
        _uiResult.value = null
        viewModelScope.launch(dispatcherProvider) {
            val dto = AddUntrackedWineRequest(
                vintage = vintage,
                bottleVolume = bottleVolume,
                externalWine = externalWineIdUrl,
                qty = qty,
                price = price,
                vendorName = vendorName,
                dateOfPurchase = dateOfPurchase.date.toSimpleMonthBackEndString()
            )
            val res = onAddUntrackedWineUseCase(dto)
            _uiResult.value = when (res) {
                is CommonResult.Success -> UiResult.Success
                is CommonResult.Failure -> UiResult.Error(res.toString())
                is CommonResult.Error -> UiResult.Error(res.e.toString())
            }//            if (BuildConfig.DEBUG && CLEAR_SCANNED) appSettingsDataSource.setScannedData("")

        }
    }

    override fun getUntrackedUserWines() {
        Napier.d("getUntrackedWines Not Used")
    }

    override fun getUntrackedWineById(id: Int) {
        Napier.d("getUntrackedWineById $id")
        viewModelScope.launch(dispatcherProvider) {
            val result = getUntrackedWineByIdUseCase(id)
            if (result is UntrackedWineResult.Success) _untrackedWineInfoState.value = listOf(result.wine)
            else _errorToast.value = result.toString()
        }
    }

    override fun addUntrackedWineReview(
        userExternalWine: String,
        course: String,
        rating: Int,
        dateOfTest: LocalDate,
        review: String,
        tastedWith: String?,
        locationOfTest: String?,
        volumes: List<VolumesToBottleMutable>
    ) {
        Napier.d("addUntrackedWineReview userExternalWine:$userExternalWine course:$course rating:$rating dateOfTest:$dateOfTest review:$review tastedWith:$tastedWith locationOfTest:$locationOfTest volumes:$volumes")
        _uiResult.value = null
        val drunkList = volumes.map { UntrackedWineReviewRequestDrunkItem( volume = it.volume, qty = it.qty) }
        viewModelScope.launch(dispatcherProvider) {
            val dto = UntrackedWineReviewRequest(
                userExternalWine = userExternalWine,
                drunkItems = drunkList,
                course = course,
                description = review,
                tastedWith = tastedWith,
                locationOfTest = locationOfTest,
                rating = rating,
                dateOfTest = dateOfTest.toSimpleMonthBackEndString() )
            val res = addUntrackedWineReviewUseCase(dto)
            _uiResult.value = when (res) {
                is CommonResult.Success -> UiResult.Success
                is CommonResult.Failure -> UiResult.Error(res.toString())
                is CommonResult.Error -> UiResult.Error(res.e.toString())
            }//            if (BuildConfig.DEBUG && CLEAR_SCANNED) appSettingsDataSource.setScannedData("")
        }

    }

    override fun onUntrackCustomWineSearch(query: String) {
        Napier.d("onUntrackCustomWineSearch $query")
        if (query.isNotBlank()) _untrackedCustomWineSearchText.value = query
    }

    override fun onUntrackCustomProducerSearch(query: String) {
        if (query.isNotBlank()) _untrackedCustomProducerSearchText.value = query
    }

    override fun addUntrackedCustomWine(
        vintage: Int,
        bottleVolume: Double,
        wineType: String,
        qty: Int,
        price: Double,
        name: String,
        producerName: String,
        vendorName: String,
        country: String,
        region: String,
        appellation: String,
        classification: String,
        color: String,
        dateOfPurchase: LocalDateTime
    ) {
        Napier.d("addUntrackedCustomWine")
        _uiResult.value = null
        viewModelScope.launch(dispatcherProvider) {
            val dto = UntrackedCustomWineRequest( vintage = vintage,
                bottleVolume = if (bottleVolume > 0.0)bottleVolume else null,
                wineType = wineType,
                qty = qty,
                price = if (price > 0.0) price else null,
                vendorName = vendorName.ifBlank { null },
                dateOfPurchase = dateOfPurchase.date.toSimpleMonthBackEndString(),
                name = name,
                producerName = producerName.ifBlank { null },
                country = country.ifBlank { null },
                region = region.ifBlank { null },
                appellation = appellation.ifBlank { null },
                classification = classification.ifBlank { null },
                color = color
            )
            val res = addUntrackedCustomWineUseCase(dto)
            _uiResult.value = when (res) {
                is CommonResult.Success -> UiResult.Success
                is CommonResult.Failure -> UiResult.Error(res.apiFailure.message)
                is CommonResult.Error -> UiResult.Error(res.e.toString())
            }//            if (BuildConfig.DEBUG && CLEAR_SCANNED) appSettingsDataSource.setScannedData("")
        }

    }

    override fun getUntrackedUserWineById(id: Int) {
        _errorToast.value = ""
        viewModelScope.launch(dispatcherProvider) {
                val result = getUntrackedUserWineByIdUseCase(id)
                if (result is UntrackedUserWineResult.Success) _untrackedUserWineInfoState.value =
                    listOf(result.userWine)
                else _errorToast.value = result.toString()
        }
    }

    override fun onUntrackWineSearch(query: String) {
        _untrackedSearchText.value = query
    }

}
