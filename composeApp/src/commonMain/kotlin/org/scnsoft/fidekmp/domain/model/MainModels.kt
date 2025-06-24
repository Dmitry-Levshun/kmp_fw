package org.scnsoft.fidekmp.domain.model

import io.github.aakira.napier.Napier
import kotlinx.datetime.LocalDate
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DigitalPassportTransferStatus
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.IntermediateSyncInstructionItem
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.InventoryBottle
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.NoLoginWine
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.SyncDeliveryInstructionItemBottle
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineExtendedInfo
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineItem
import org.scnsoft.fidekmp.utils.currentUtcDateTime
import org.scnsoft.fidekmp.utils.toFixed
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class FindCodeInfoResult (
    val passId: Int = 0,
    val isSellable: Boolean,
    val isSealBroken: Boolean,
    val headInfo: PackageItem,
    val boxInfo: BoxInfo,
    val shippingInfo: FindCodeShippingInfo,
    val nftId: String = "",
    val qrWineInfo: FindCodeQrWineInfo? = null,
    var winePackageInfo: PackageItem? = null,
    var wineInfo: WineItem? = null,
    val owner: String = "",
    val isHasCase: Boolean = false,
    val additionalCodeInfo: AdditionalCodeInfo = AdditionalCodeInfo(),
) {
    companion object {
        fun Empty(): FindCodeInfoResult = FindCodeInfoResult(
            0,
            false,
            false,
            PackageItem.Empty(),
            BoxInfo.Empty(),
            FindCodeShippingInfo.Empty(),
            ""
        )
    }
    fun isEmpty(): Boolean = headInfo.isEmpty() && boxInfo.isEmpty()
}
data class AdditionalCodeInfo(
    var description: String = "",
    val review: List<WineReview>? = null,
    var wineDetailsPairs: List<Pair<String, String>> = listOf(),
    val scoresPairs: List<Pair<String, String>> = listOf(),
)
data class FindCodeQrWineInfo(
    val wineName: String,
    val wineId: Int,
)

data class FindCodeShippingInfo(
    val shippingType: String,
    val deliveryTo: String,
    val pickingDate: String,
    val comment: String,
){
    companion object {
        fun Empty(): FindCodeShippingInfo = FindCodeShippingInfo("", "", "", "" )
    }
}

data class ClientInfo(
    val id: String,
    var clientName: String,
    val reference: String,
    var orders: List<ClientOrder>,
    var isVisible: Boolean = true
) {
    fun contains(s: String): Boolean = this.clientName.contains(s, true) || reference.contains(s, true) || orders.filter { it.contains(s) }.isNullOrEmpty().not()
    companion object {
        fun Empty(): ClientInfo = ClientInfo("","", "", listOf(ClientOrder.Empty()))
    }
    fun setVisibility() {
        orders.forEach{it.isVisible = true}
        isVisible = true
    }
}
data class ClientOrder(
    val orderId: String,
    val orderDateStart: LocalDate,
    val orderDateEnd: LocalDate,
    val status: String,
    val isPrimeur: Boolean = true,
    var isVisible: Boolean = true,
    val synthStatus: String ="",
    val vintages: List<String> = listOf(),
    val domains: List<String> = listOf(),
    val isIncoming: Boolean = false,
    val isCompleted: Boolean = false,
) {
    fun contains(s: String): Boolean = orderId.contains(s)
    companion object {
        fun Empty(): ClientOrder = ClientOrder(orderId = "", currentUtcDateTime().date, currentUtcDateTime().date, "" )
    }

}
data class PackageInfo(
    val packageName: String,
    val additionalInfo: String,
    val packageItems: List<PackageItem>
) {
    fun contains(s: String): Boolean = packageName.contains(s, true) || additionalInfo.contains(s, true) || packageItems.filter { it.contains(s) }.isNullOrEmpty().not()
    companion object {
        fun Empty(): PackageInfo = PackageInfo("", "", listOf(PackageItem("", "")))
    }
}
data class PackageItem(
    var itemName: String,
    val itemData: String,
    var domainName: String = "",
    val id: String  = "",
    val imageUrl: String? = "",
    val price: PriceWithSymbol= PriceWithSymbol.Empty(),
    var isVisible: Boolean = true,
    var vintage: String = "",
    val bottleSize: String = "",
    val alcohol: String = "",
    var color: String = "",
    var query: String = "",
    val bottleType: String = ""
) {
    fun contains(s: String): Boolean = itemData.contains(s, true) || itemName.contains(s,true) || domainName.contains(s,true)
    fun setDomain(domain: String):PackageItem {
        this.domainName = domain
        return this
    }
    fun isEmpty() = itemName.isBlank() && itemData.isBlank() && domainName.isBlank() && id.isBlank()
    companion object {
        fun Empty(): PackageItem = PackageItem("", "")
    }
}

data class WineInfoItem(
    val itemName: String,
    val itemData: String,
    val subInfoItems: List<SubInfoItem>,
    var progress: Int,
    val vintage: Int = 0,
    val imageUrl: String? = "",
    val itemData1: String = "",
){
    companion object {
        fun Empty(): WineInfoItem = WineInfoItem("", "", listOf(SubInfoItem.Empty()), 0)
    }
}
data class SubInfoItem(
    val itemName: String,
    val qty: String,
    val itemData: String,
    val isProcessed: Boolean,
    val bottlesQty: Int = 0,
    val itemData1: String = "",
    val deliveryId: String = "",
    val scannedBottlesQty: Int = 0,
) {
    companion object {
        fun Empty(): SubInfoItem = SubInfoItem(itemName = "", itemData = "", qty = "", isProcessed = false)
    }
}

data class AgreementInfo(
    val clientName: String,
    val reference: String,
    val wine: String,
    val cases: String,
    val caseQuantity: String,
    val bottles: String,
    val bottleQuantity: String,
    val details: String,
    val volume: String,
    val comments: String,
    val isProcessed: Boolean,
    val isIncoming: Boolean,
    val hasBoxes: Boolean = true,
    val dptId: Int,
    val bottlesInCase: Int,
    val caseTypeId: Int? = null,
    var scannedBottlesQty: Int = 0,
) {
    companion object {
        fun Empty() = AgreementInfo("", "", "", "", "", "", "", "","", "", false, false, false, 0, 0)
    }
}

data class HabillageScanResultInfo(
    val procButtles: String,
    val totalButtles: String,
    val cases: String,
    val boxes: List<BoxInfo>,
    val syncBottles: List<SyncDeliveryInstructionItemBottle>?,
    val intermediateSyncInfo: List<IntermediateSyncInstructionItem>?,
) {
    companion object {
        fun Empty(): HabillageScanResultInfo = HabillageScanResultInfo( "","", "", listOf(), listOf(), null)
    }
}
data class BoxInfo(
    val boxId: String,
    val bottles: List<BottleInfo>,
    var isSealBroken: Boolean = false
) {
    companion object {
        fun Empty(): BoxInfo = BoxInfo( "", listOf())
    }
    fun isEmpty() = boxId.isNullOrBlank() && bottles.isNullOrEmpty()
}
data class BottleInfo(
    val bottleId: String,
    val wineName: String,
    val volume: String,
    val wineData: String
){
    companion object {
        fun Empty(): BottleInfo = BottleInfo( "","", "", "")
    }
}

data class DomainInfo(
    val id: Int,
    val domainName: String,
    val wines: List<PackageItem>,
    var isVisible: Boolean = true
) {
    fun contains(s: String): Boolean = this.domainName.contains(s, true) || wines.filter { it.contains(s) }.isNullOrEmpty().not()
    fun setVisibility() {
        isVisible = true
        wines.forEach{it.isVisible = true}
    }

    companion object {
        fun Empty(): DomainInfo = DomainInfo(0,"", listOf(PackageItem.Empty()))
    }
}
data class WineDetailInfo(
    val detailName: String,
    val datailParams: List<Pair<String, String?>>?
) {
    companion object {
        fun Empty(): WineDetailInfo = WineDetailInfo( "", null)
    }
}

data class TransferDetailsInfo(
    val seller: WineClient,
    val buyer: WineClient,
    val dptId: String,
    val createDate: String,
    val expiryDate: String,
    val wines: List<TransferDetailWineItem>,
    val stickStatus: String, // Primeur/Livrable
    val qty: Int,
    val price: String,
    val transferStatus: String,
    val transferStatusEnum: DigitalPassportTransferStatus,
    val sellerIsPayer: Boolean,
    val isIncoming: Boolean,
    val isSubmitted: Boolean,
    val dptIdInt: Int = 0,
    var paymentUrl: String?,
    var isVisible: Boolean = true
) {
    fun getTranferStatusTextId(): StringResource = getTranferStatusTextId(transferStatusEnum)

    fun getTranferStatusColor(): Long {
        return if (transferStatus.contains("pending", true)) 0xFFFE9B27L
        else if (transferStatus.contains("expired", true)
            || transferStatus.contains("cancel", true)
            || transferStatus.contains("reject", true)
            || transferStatus.contains("lose", true)) 0xFFFF0000L
        else if (transferStatus.contains("paid", true)) 0xFF33B958L
        else if (transferStatus.contains("complete", true)) 0x8033B958L
        else 0xFF808080L
    }
    fun getStickStatusColor():Long {
        return getStickStatusColor(stickStatus)
    }

    fun toLines(): List<String> {
        val l1 = " $dptId | ${buyer.name} | $createDate"
        var l2 = if (wines.size > 0) "${wines[0].name} | ${wines[0].vintage}" else "No Wine"
        val winesNum = wines.size
        if (winesNum > 1) l2 += " + $winesNum more"

        return listOf(l1, l2)
    }
    companion object {
        fun Empty() = TransferDetailsInfo(
            seller = WineClient.Empty(),
            buyer = WineClient.Empty(),
            dptId = "",
            createDate = "",
            expiryDate = "",
            wines = listOf(),
            stickStatus = "",
            qty = 0,
            price = "",
            transferStatus = "",
            isIncoming = false,
            isSubmitted = false,
            paymentUrl = "",
            sellerIsPayer = false,
            transferStatusEnum = DigitalPassportTransferStatus.Paid)
        fun getTranferStatusTextId(transferStatusEnum: DigitalPassportTransferStatus): StringResource =
            when (transferStatusEnum) {
                DigitalPassportTransferStatus.PendingApproval -> Res.string.pending_approval
                DigitalPassportTransferStatus.PendingPayment -> Res.string.pending_payment
                DigitalPassportTransferStatus.Paid -> Res.string.paid
                DigitalPassportTransferStatus.Approved -> Res.string.approved
                DigitalPassportTransferStatus.RejectedPassport -> Res.string.rejected_passport
                DigitalPassportTransferStatus.Closed -> Res.string.closed
                DigitalPassportTransferStatus.Submitted -> Res.string.submitted
                DigitalPassportTransferStatus.SubmittedNoTransfer -> Res.string.submitted_no_transfer
                DigitalPassportTransferStatus.Completed -> Res.string.completed
                DigitalPassportTransferStatus.CompletedNoTransfer -> Res.string.completed_no_transfer
            }
    }
}

data class WineClient(
    val name: String,
    val address: String,
    val email: String,
    val phone: String,
) {
    companion object {
        fun Empty() = WineClient( "","","","")
    }
}


data class TransferDetailWineItem(
    val name: String,
    val domain: String,
    var qty: Int,
    val maxQty: Int,
    val price: PriceWithSymbol,
    val passportPrice: Double,
    val alc: String = "",
    var selected: Boolean = false,
    val id: Int = 0,
    val idUrl: String ="",
    val imageUrl: String? = null,
    val vintage: String = "",
    val isPrimeur: Boolean = true,
    val passId: Int = 0,
    val caseId: String = "",
    val bottleVolume: String = "",
    var bottlesInBox: Int = 0,
    var caseQty: Int = 0,
    var bottleQty: Int = 1,
//    var passportPrice: Double? = null
) {
    //    val passportPrice: Double  get() = calcRate()
//    private fun calcRate(): Double {
//        var rate = price.value * 0.04
//        if (rate < 1) rate = 1.0
//        else rate = roundOffDecimal(rate)
//        return rate
//    }
    companion object {
        fun Empty() = TransferDetailWineItem( "","",0, 0, PriceWithSymbol.Empty(), 0.0)
    }
}

data class PriceWithSymbol(
    var value: Double,
    var priceSymbol: String = "€",
) {
    override fun toString(): String {
        return if (priceSymbol == "€") "${value.toFixed(2)}$priceSymbol"
        else "$priceSymbol ${value.toFixed(2)}"
    }
    fun plus(v: Double): Double {
        Napier.d("PriceWithSymbol plus $v  val:${value.toFixed(2)}")
        value += v
        Napier.d("PriceWithSymbol plus $v  result:${value.toFixed(2)}")
        return value
    }

    companion object {
        fun Empty() = PriceWithSymbol(value = 0.0)
    }

}

data class TransactionItem(
    val name: String,
    val transactionStatus:  String,
    var price: PriceWithSymbol,
) {
    fun getTransactionStatusColor(): Long {
        return if (transactionStatus.contains("receive", true)) 0xFF33B958
        else if (transactionStatus.contains("pending", true)) 0xFF847782
        else if (transactionStatus.contains("cancel", true)) 0xFFFF0000L
        else if (transactionStatus.contains("paid", true)) 0xFF847782
        else 0xFFFFFFFFL
    }
    fun getTransactionStatusIcon(): DrawableResource {
        return if (transactionStatus.contains("pending", true)) Res.drawable.ic_status_pending
        else if (transactionStatus.contains("cancel", true)) Res.drawable.ic_status_cancel
        else if (transactionStatus.contains("paid", true)) Res.drawable.ic_status_paid
        else if (transactionStatus.contains("receive", true)) Res.drawable.ic_status_received
        else Res.drawable.ic_status_pending
    }

    companion object {
        fun Empty() = TransferDetailWineItem( "","",0, 0, PriceWithSymbol.Empty(), 0.0)
    }
}
data class DeliveryInstruction (
    val cases:  List<Case>,
    val deliveryInstructionId: String,
    val digitalPassportTransfer: String?,  //digitalPassportTransfer aka orderId"
    val orderId: String?
)

data class Case (
    val bottles: List<Bottle> ,
    val caseId: String,
    val qrCode: String?
)

data class Bottle (
    val bottleId: String,
    val linksToPhoto: List<String>?,
    val nfcCodes: List<String>? ,
    val qrCodes: List<String>?,
    val volume: String?
)
data class ScannedInfoMap(
    val map: MutableMap<Int, ScannedInfo> = mutableMapOf()
) {
    fun addItem(key: Int, item: ScannedInfo) {
        map[key] = item
    }
    fun removeItem(key: Int) {
        map.remove(key)
    }

}
data class ScannedInfo (
    val bottleList: List<String>,
    val resultList: List<String>,
    val currentQty: Int,
    val scanDate: LocalDate,
    val codeToId: Map<String, Int>
) {
    companion object {
        fun Empty() = ScannedInfo(listOf(), listOf(), 0, LocalDate(1,1,1), mapOf())
    }
}
data class NfcData(
    val code: String,
    val text: String,
) {
    companion object {
        fun Empty() = NfcData("", "")
    }
}

data class CellarWineDetails(
    var info: PackageItem,
    var wineDetailsPairs: List<Pair<String, String>>,
    var description: String,
    val scoresPairs: List<Pair<String, String>>,
    var wine: WineItem?,
    var wineExtended: WineExtendedInfo?,
    var nftId: String? = null
) {
    companion object {
        fun Empty() = CellarWineDetails(PackageItem.Empty(), listOf(), "", listOf(), null, null)
    }
}

data class NoLoginWineDetails(
    var headInfo: PackageItem,
    val bottlingDate: String,
    val vintage: String,
    val owner: String,
    var description: String,
    val review: List<WineReview>? = null,
    var wineDetailsPairs: List<Pair<String, String>>,
    val scoresPairs: List<Pair<String, String>>,
    val wineItem: NoLoginWine?,
) {
    companion object {
        fun Empty() = NoLoginWineDetails(PackageItem.Empty(), "", "", "", "", null,  listOf(),  listOf(), null)
    }
}
data class WineReview(
    val reviewer: String,
    val score: String,
    val description: String,
)

data class FilterEntity(
    val name: String,
    val id: Int,
    val data: List<FilterData>
) {
    fun clearAllSelected() {
        data.forEach{it.selected = false}
    }
    fun setSelected(name: String, selected: Boolean) {
        data.find{it.name == name}?.selected = selected
    }
    fun toggleSelected(name: String) {
        val v = data.find{it.name == name} ?: return
        v.selected = !v.selected
    }
    fun selectNameAll(selected: Boolean) {
        data.forEach{it.selected = false}
        data[0].selected = selected
    }
    fun clearNameAll() {
        data[0].selected = false
    }
}

data class FilterData(
    val name: String,
    var selected: Boolean = false
)

enum class PassInfo{LOADING, EXIST,NOT_EXIST}

enum class IntermediateBoxInfoResult { NONE, SUCCESS, NOT_MATCH, FAIL }
data class IntermediateBoxInfoData(
    val result: IntermediateBoxInfoResult = IntermediateBoxInfoResult.NONE,
    val wine: String = "",
    val volume: String = ""
)

data class InventoryStartInfo (
    val wineId: Int,
    val wineName: String,
    val wineColor: String,
    val vintage: Int,
    val bottleVolume: String,
    val caseTypeId: Int,
    val bottleInCase: Int,
    val hasBoxes: Boolean,
    val bottles: MutableList<InventoryBottle> = mutableListOf(),
    val boxes: MutableList<BoxInfo> = mutableListOf(),
    val bottlesBox: MutableList<BottleInfo> = mutableListOf(),
    var currBoxId: String? = null,
    var currentBottleNum: Int = 0,
    var currentCaseNum: Int = 0
) {
    fun onUndoClick(): String {
        Napier.d("onUndoClick start $this")
        var bottle = ""
        if (currentBottleNum > 0) {
            currentBottleNum--
            bottle = bottles.removeLast().vendorSerialNumber
            if (bottlesBox.isNotEmpty()) bottlesBox.removeLast()
            if (hasBoxes && currentBottleNum+1 % bottleInCase == 0) {
                boxes.removeLast()
                currentCaseNum--
            }
        }
        Napier.d("onUndoClick finish $this")

//-----
        /*
        sti.currentBottleNum++
        if (sti.hasBoxes && sti.currentBottleNum % sti.bottleInCase == 0) {
            sti.currBoxId = CASE_PREFIX + String.format("%08d", System.currentTimeMillis())
        }
        sti.bottlesBox += BottleInfo(bottleId = code,
            wineName = sti.wineName,
            volume = sti.bottleVolume,
            "")
        sti.bottles += InventoryBottle(vendorSerialNumber = code,
            passportAuthData = listOf(InventoryPassportAuthData(dataType = dataType, value = code)),
            caseId = sti.currBoxId,
            bottlingDate = getCurrentTimeUtc() / 1000
        )
        if (sti.hasBoxes && sti.currentBottleNum % sti.bottleInCase == 0) {
            val bi = sti.bottlesBox.toMutableList()
            sti.boxes += BoxInfo(sti.currBoxId!!, bi)
            sti.bottlesBox.clear()
            sti.currentCaseNum++
        }
        _inventoryStartInfo.value = sti
*/
//-----
        return bottle
    }
}

data class MapItemInfo(
    val geoPoint: GeoPoint,
    val city: String,
    val country: String,
    val distance: Double?,
    val date: LocalDate,
    val sellerType: Int = 0,
    val sellerTypeName: String = "",
    val buyerType: Int = 0,
    val buyerTypeName: String = ""

)

data class GeoPoint(val Latitude: Double, val aLongitude: Double)

fun getStickStatusColor(stickStatus: String):Long {
    return if (stickStatus.contains("Prime")) 0xFF6F7E45L else 0xFF634D79L
}
