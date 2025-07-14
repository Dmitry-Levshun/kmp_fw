package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.paging.PagingData
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptDeliveryInstructionItemBottleType
import org.scnsoft.fidekmp.data.api.untracked.dto.ExternalUserWine
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemById
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemDrunkItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemPurchase
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemReview
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.VolumesToBottle
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.ui.UiResultInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.scnsoft.fidekmp.domain.model.PackageItem
import org.scnsoft.fidekmp.utils.currentUtcDateTime

interface ICellarUntracked {
//    val untrackedUserWineListState: Flow<List<UntrackedUserWineItem>>
    val untrackedUserWines: Flow<PagingData<UntrackedUserWineItem>>
    val untrackedWineListState: Flow<List<UntrackedWineItem>>
    val untrackedUserWineInfoState: Flow<List<UntrackedUserWineItemById>>
    fun getUntrackedUserWineById(id: Int)
    fun onUntrackMainWineSearch(query: String)
}

interface IUntrackedModel : UiResultInterface, ICellarUntracked {
//    val untrackedWineListState: Flow<List<UntrackedWineItem>>
//    val untrackedUserWineListState: Flow<List<UntrackedUserWineItem>>
    val untrackedSearchText: StateFlow<String>
    val untrackedSelectedWine: StateFlow<PackageItem>
    val untrackedWineDetailsInfo: StateFlow<PackageItem>
    val untrackedWineInfoState: Flow<List<UntrackedWineItem>>
    val untrackedWines: Flow<PagingData<UntrackedWineItem>>
//    val untrackedUserWines: Flow<PagingData<UntrackedUserWineItem>>
    val bottleList: StateFlow<List<DptDeliveryInstructionItemBottleType>>
    val untrackedCustomWines: Flow<List<UntrackedWineItem>>
    val untrackedCustomWineProducers: Flow<List<UntrackedWineItem>>
    val errorToast: StateFlow<String>

//    fun onUntrackMainWineSearch(query: String)
    fun addUntrackedWine(vintage: Int, bottleVolume: Double,externalWineIdUrl: String, qty: Int, price: Double, vendorName: String, dateOfPurchase: LocalDateTime)
    fun getUntrackedUserWines()
    fun getUntrackedWineById(id: Int)
    fun addUntrackedWineReview(userExternalWine: String, course: String, rating: Int, dateOfTest: LocalDate, review: String, tastedWith: String? = null, locationOfTest: String? = null, volumes: List<VolumesToBottleMutable> )
    fun onUntrackCustomWineSearch(query: String)
    fun onUntrackCustomProducerSearch(query: String)
    fun addUntrackedCustomWine(vintage: Int, bottleVolume: Double,wineType: String, qty: Int, price: Double, name: String, producerName: String,vendorName: String,
                               country: String, region: String, appellation: String, classification: String, color: String, dateOfPurchase: LocalDateTime)
    fun onUntrackWineSearch(query: String)

}

class UntrackedModelPreview : IUntrackedModel {
    override val untrackedWineListState: Flow<List<UntrackedWineItem>> get() = flowOf()
//    override val untrackedUserWineListState: Flow<List<UntrackedUserWineItem>> get() = flowOf()

    override val untrackedSearchText: StateFlow<String> get() = MutableStateFlow("")
    override val untrackedSelectedWine: StateFlow<PackageItem> get() = MutableStateFlow(PackageItem( "Wine Name $1", "Max Qty: 1000 | Stock Qty: 900 | 13% alc.", "Domain Name $1", query = "nam", vintage = "2019"))
    override val untrackedWineDetailsInfo: StateFlow<PackageItem> get() = MutableStateFlow(PackageItem( "Wine Name $1", "Max Qty: 1000 | Stock Qty: 900 | 13% alc.", "Domain Name $1", query = "nam", vintage = "2019"))
    override val uiResult: StateFlow<UiResult?> get() = MutableStateFlow(null)
    override val untrackedUserWineInfoState: Flow<List<UntrackedUserWineItemById>> get() = flowOf(listOf(
        untrackedUserWineItemById
    ))
    override val untrackedWineInfoState: Flow<List<UntrackedWineItem>> get() = flowOf(listOf(
        untrackedWineItem
    ))
    override val untrackedWines: Flow<PagingData<UntrackedWineItem>> get() = flowOf()
    override val untrackedUserWines: Flow<PagingData<UntrackedUserWineItem>> get() = flowOf()

    override val bottleList: StateFlow<List<DptDeliveryInstructionItemBottleType>> get() = MutableStateFlow(listOf())
    override val untrackedCustomWines: Flow<List<UntrackedWineItem>> = flowOf()
    override val untrackedCustomWineProducers: Flow<List<UntrackedWineItem>> = flowOf()
    override val errorToast: StateFlow<String> = MutableStateFlow("")
    override fun onUntrackWineSearch(query: String){}

    override fun onUntrackMainWineSearch(query: String){}

    override fun addUntrackedWine(vintage: Int, bottleVolume: Double,externalWineIdUrl: String, qty: Int, price: Double, vendorName: String, dateOfPurchase: LocalDateTime) {}
    override fun resetUiResult() {}
//    override fun getUntrackedWines() {}
    override fun getUntrackedWineById(id: Int) {}
    override fun getUntrackedUserWineById(id: Int) {}
    override fun getUntrackedUserWines() {}
    override fun addUntrackedWineReview(userExternalWine: String, course: String, rating: Int, dateOfTest: LocalDate, review: String, tastedWith: String?, locationOfTest: String?, volumes: List<VolumesToBottleMutable>) {}
    override fun onUntrackCustomWineSearch(query: String) {}
    override fun onUntrackCustomProducerSearch(query: String) {}
    override fun addUntrackedCustomWine(vintage: Int, bottleVolume: Double,wineType: String, qty: Int, price: Double, name: String, producerName: String,vendorName: String,
                               country: String, region: String, appellation: String, classification: String, color: String, dateOfPurchase: LocalDateTime) {}


}
val untrackedWineItem = UntrackedWineItem(
    idUrl = "https://example.com/wine/123",
    type = "wine",
    id = 123,
    lwinId = 456,
    wineType = "Red",
    drinkType = "Wine",
    wineName = "Example Wine",
    country = "France",
    region = "Bordeaux",
    color = "Red",
    producerName = "Example Producer",
    appellation = "Medoc",
    classification = "Grand Cru Classe",
    reviews = emptyList(),
    query = "Bordeaux Wine"
)
val externalUserWine = ExternalUserWine(
    idUrl = "https://example.com/wine/456",type = "wine",
    id = 456,
    lwinId = 789L,
    wineType = "White",
    drinkType = "Wine",
    wineName = "Another Example Wine",
    color = "White",
    country = "Italy",
    region = "Tuscany",
    appellation = "Chianti Classico",
    classification = "DOCG",
    producerName = "Another Example Producer"
)

val untrackedUserWineItem = UntrackedUserWineItem(
    idUrl = "https://example.com/wine/123",
    type = "wine",
    id = 123,
    vintage = 2022,
    qty = 10,
    drunkQty= 5,
    externalWine = externalUserWine,
    reviewsCount = 3
)
//-------
val externalUserWine1 = ExternalUserWine(
    idUrl = "https://example.com/wine/456",type = "wine",
    id = 456,
    lwinId = 789L,
    wineType = "White",
    drinkType = "Wine",
    wineName = "Another Example Wine",
    color = "White",
    country = "Italy",
    region = "Tuscany",
    appellation = "Chianti Classico",
    classification = "DOCG",
    producerName = "Another Example Producer"
)

val purchase = UntrackedUserWineItemPurchase(
    id = 1,
    dateOfPurchase = currentUtcDateTime(),
    vendorName = "Example Vendor",
    price = 25.99,
    qty = 2,
    bottleVolume = 0.75
)

val volumesToBottle = VolumesToBottle(
    volume =0.75,
    qty = 6
)

val drunkItem = UntrackedUserWineItemDrunkItem(
    volume = 0.75,
    qty = 1)

val untrackedUserWineItemReview = UntrackedUserWineItemReview(
    id = 1,
    dateOfTest = currentUtcDateTime(), // Current date and time
    locationOfTest = "Home",
    rating = 4,
    tastedWith = "Friends",course = "Dinner",
    description = "A very nice wine!",
    externalUserId = "user123",
    drunkItems = listOf(drunkItem)
)
val untrackedUserWineItemById = UntrackedUserWineItemById(
    id = 123,
    userOwnerId = "user123",
    externalWine = externalUserWine1,
    vintage = 2020,
    qty = 6,
    drunkQty = 2,
    purchases = listOf(purchase),
    reviews = listOf(untrackedUserWineItemReview),
    volumesToBottles = listOf(volumesToBottle)
)
//-------

data class UntrackedCellarWineItem(
    val id: Int,
    val wineName: String,
    val vintage: Int,
    val color: String,
    val domainName: String = "",
    var isVisible: Boolean = true,
    val reviewsCount: Int = 0,
    val query: String = "",
) {
    companion object {
        fun fromUntrackedUserWineItem(item: UntrackedUserWineItem) =
            UntrackedCellarWineItem(
                id = item.id,
                wineName = item.externalWine.wineName,
                vintage = item.vintage,
                color = item.externalWine.color ?: "red",
                reviewsCount = item.reviewsCount
            )
        fun fromUntrackedWineItem(item: UntrackedWineItem, query: String = "") =
            UntrackedCellarWineItem(
                id = item.id,
                wineName = item.wineName,
                vintage = 0,
                color = item.color ?: "red",
                domainName = item.producerName ?: "",
                query = query
            )
    }
}

data class UntrackedDetailWineItem(
    val id: Int,
    val wineName: String,
    val country: String,
    val region: String,
    var vintage: Int,
    val color: String,
    val domainName: String,
    val appellation: String,
    val classification: String,
    val reviews: List<Any?>,
    val imageUrl: String = "",
    val wineType: String = ""
) {
    companion object {
        fun fromUntrackedUserWineItem(item: UntrackedUserWineItemById) =
            UntrackedDetailWineItem(
                id = item.id,
                wineName = item.externalWine.wineName,
                vintage = item.vintage,
                domainName = item.externalWine.producerName ?: "",
                color = item.externalWine.color ?: "red",
                country = item.externalWine.country ?: "",
                appellation = item.externalWine.appellation ?:"",
                classification = item.externalWine.classification ?:"",
                region = item.externalWine.region ?: "",
                reviews = listOf(),
                wineType = item.externalWine.wineType
            )
        fun fromUntrackedWineItem(item: UntrackedWineItem) =
            UntrackedDetailWineItem(
                id = item.id,
                wineName = item.wineName,
                vintage = 0,
                color = item.color ?: "red",
                domainName = item.producerName ?: "",
                country = item.country ?: "",
                appellation = item.appellation ?:"",
                classification = item.classification ?:"",
                region = item.region ?: "",
                reviews = listOf()
            )

    }
}
data class VolumesToBottleMutable(
    var volume: Double,
    var qty: Int,
) {
    companion object {
        fun fromVolumesToBottle(item: VolumesToBottle) =
            VolumesToBottleMutable(item.volume, item.qty)
    }
}