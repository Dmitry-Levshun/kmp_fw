package org.scnsoft.fidekmp.ui.postlogin.cellar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import app.cash.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.scnsoft.fidekmp.data.api.ApiResult
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptDeliveryInstructionItemBottleType
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemById
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem
import org.scnsoft.fidekmp.data.repository.ProfileResult
import org.scnsoft.fidekmp.domain.model.CellarWineDetails
import org.scnsoft.fidekmp.domain.model.DomainInfo
import org.scnsoft.fidekmp.domain.model.FilterEntity
import org.scnsoft.fidekmp.domain.model.PackageItem
import org.scnsoft.fidekmp.domain.model.profile.ProfileExtInfo
import org.scnsoft.fidekmp.domain.model.profile.ProfileInfo
import org.scnsoft.fidekmp.domain.repository.UntrackedUserWineResult
import org.scnsoft.fidekmp.domain.repository.UntrackedWineListResult
import org.scnsoft.fidekmp.domain.usecase.GetCurrentProfileUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedUserWineByIdUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedUserWineItemsPagedUseCase
import org.scnsoft.fidekmp.domain.usecase.OnUntrackedMainWineSearchUseCase
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.IUntrackedModel

class CellarViewModel(
    private val getUntrackedUserWineByIdUseCase: GetUntrackedUserWineByIdUseCase,
    private val getUntrackedUserWineItemsPagedUseCase: GetUntrackedUserWineItemsPagedUseCase,
    private val onUntrackedMainWineSearchUseCase: OnUntrackedMainWineSearchUseCase,
    private val getCurrentProfileUseCase: GetCurrentProfileUseCase
) : ViewModel(), ICellarModel {

    private val _uiResult: MutableStateFlow<UiResult?> = MutableStateFlow(null)
    override val uiResult: StateFlow<UiResult?> get() = _uiResult

    override val isloadingState: StateFlow<Boolean> get() = _isloadingState
    private val _isloadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val cellarTabIndexState: StateFlow<Int> get() = _cellarTabIndexState
    private val _cellarTabIndexState: MutableStateFlow<Int> = MutableStateFlow(2)
    private val _domainListState = MutableStateFlow(listOf<DomainInfo>())
    private val _domainPrimeurListState = MutableStateFlow(listOf<DomainInfo>())
    private val _domainLivrableListState = MutableStateFlow(listOf<DomainInfo>())

    override val domainListState: Flow<List<DomainInfo>>  get() = _domainListState
    override val untrackedUserWines: Flow<PagingData<UntrackedUserWineItem>> = getUntrackedUserWineItemsPagedUseCase().cachedIn(viewModelScope)
    override val untrackedUserWineInfoState: Flow<List<UntrackedUserWineItemById>> get() = _untrackedUserWineInfoState
    private val _untrackedUserWineInfoState: MutableStateFlow<List<UntrackedUserWineItemById>> = MutableStateFlow(listOf())

    override val untrackedWineListState: Flow<List<UntrackedWineItem>> get() = _untrackedWineListState
    private val _untrackedWineListState = MutableStateFlow<List<UntrackedWineItem>>(listOf())

    private val _untrackedSearchText = MutableStateFlow("")
    override val profileInfo: StateFlow<ProfileExtInfo> get() = _profileInfo
    private val _profileInfo = MutableStateFlow(ProfileExtInfo(ProfileInfo.Empty(), false, false))
    override val cellarWineDetails: StateFlow<CellarWineDetails> get() = _cellarWineDetails
    private val _cellarWineDetails = MutableStateFlow(CellarWineDetails.Empty())

    override val filterItemsState: StateFlow<Map<String, List<FilterEntity>>> get() = _filterItemsState
    private  val _filterItemsState = MutableStateFlow(mapOf<String, List<FilterEntity>>())

    private val _errorToast = MutableStateFlow("")
    private val dispatcherProvider =  Dispatchers.IO

    init{
        viewModelScope.launch(dispatcherProvider) {
            val res = getCurrentProfileUseCase()
            if (res is ProfileResult.Success) _profileInfo.value = res.profile

        }
    }
    override fun resetUiResult() {
        _uiResult.value = null
    }

    override fun prepareCreatePassportWines(stickStatus: String, wineItem: PackageItem?): String {
        TODO("Not yet implemented")
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

    var debounceJob: Job? = null
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

    override fun selectCellarTab(tabInd: Int) {
        _cellarTabIndexState.value = tabInd
        _domainListState.value = when(tabInd) {
            0 -> _domainPrimeurListState.value
            1 -> _domainLivrableListState.value
//            2 -> listOf()
            else -> _domainPrimeurListState.value + _domainLivrableListState.value
        }
    }

    override fun getWines() {
        TODO("Not yet implemented")
    }

    override fun setWineDetailInfo(info: PackageItem) {
        TODO("Not yet implemented")
    }

    override fun updateFilter() {
        TODO("Not yet implemented")
    }

    override fun clearFilter() {
        TODO("Not yet implemented")
    }


}