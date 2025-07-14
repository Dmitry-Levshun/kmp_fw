package org.scnsoft.fidekmp.ui.postlogin.cellar

import androidx.paging.PagingData
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.ui.IFilter
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.ICellarUntracked
import org.scnsoft.fidekmp.ui.postlogin.inventory.ICellarInventory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemById
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem
import org.scnsoft.fidekmp.domain.model.CellarWineDetails
import org.scnsoft.fidekmp.domain.model.DomainInfo
import org.scnsoft.fidekmp.domain.model.FilterEntity
import org.scnsoft.fidekmp.domain.model.PackageItem
import org.scnsoft.fidekmp.domain.model.profile.ProfileExtInfo
import org.scnsoft.fidekmp.domain.model.profile.ProfileInfo
import org.scnsoft.fidekmp.ui.IProfileInfo
import org.scnsoft.fidekmp.ui.LoadingInterface
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.ui.UiResultInterface

interface ICellarModel: ICellarUntracked, LoadingInterface, UiResultInterface, IProfileInfo,
    ICellarInventory, IFilter {
    val cellarTabIndexState: StateFlow<Int>
    val domainListState: Flow<List<DomainInfo>>
    fun prepareCreatePassportWines(stickStatus: String, wineItem: PackageItem? = null): String
}
class CellarModelPreview : ICellarModel {
    override val cellarTabIndexState: StateFlow<Int>
        get() = MutableStateFlow(0)
    override val domainListState: Flow<List<DomainInfo>>
        get() = MutableStateFlow(listOf())

    override fun prepareCreatePassportWines(stickStatus: String, wineItem: PackageItem?): String {
        return ""
    }

//    override val untrackedUserWineListState: Flow<List<UntrackedUserWineItem>>
//        get() = MutableStateFlow(listOf())
    override val untrackedUserWines: Flow<PagingData<UntrackedUserWineItem>>
        get() = flowOf()
    override val untrackedWineListState: Flow<List<UntrackedWineItem>> get() = flowOf()

    override fun getUntrackedUserWineById(id: Int) {

    }
    override val isloadingState: StateFlow<Boolean>
        get() = MutableStateFlow(false)
    override val uiResult: StateFlow<UiResult?>
        get() = MutableStateFlow(null)

    override fun resetUiResult() {

    }

    override val profileInfo: StateFlow<ProfileExtInfo>
        get() = MutableStateFlow(ProfileExtInfo(ProfileInfo.Empty(), false, false))
    override val cellarWineDetails: StateFlow<CellarWineDetails>
        get() = MutableStateFlow(fakeWineDetails())
    override val untrackedUserWineInfoState: Flow<List<UntrackedUserWineItemById>> get() = flowOf()

    override fun onUntrackMainWineSearch(query: String) {
    }

    override fun selectCellarTab(tabInd: Int) {
    }

    override fun getWines() {
    }

    override fun setWineDetailInfo(info: PackageItem) {
    }

    override val filterItemsState: StateFlow<Map<String, List<FilterEntity>>>
        get() = MutableStateFlow(mapOf())

    override fun updateFilter() {
    }

    override fun clearFilter() {

    }
    private fun fakeWineDetails(): CellarWineDetails {
        val wineDetail =
            PackageItem(itemName = "Chateau Léoville Poyferré",
                itemData = "Max Qty: 1000 | Stock Qty: 900 | 13% alc.",
                domainName = "Château de Salles")
        val nft = "27650049777775429551395684938872302338219790739578926778347067436979426378912"
        val desc = "Traditionally, Bordeaux estates were named after their owners or founders. But history’s path to the ownership of Chateau Leoville Poyferre is a long and winding road shared with Chateau Leoville Las Cases starting all the way back to 1638.\\r\\n\\r\\nJean de Moytie a member of the Bordeaux Parliament owned a Bordeaux vineyard. Moytie called it Mont-Moytie. "
        val scores = listOf(Pair("description", desc), Pair("reviewer", "reviewer1"), Pair("score", "reviewer1"))
        return CellarWineDetails(info = wineDetail, wineDetailsPairs = listOf(), description = desc, nftId = nft, scoresPairs = scores, wine = null, wineExtended = null)
    }
}