package org.scnsoft.fidekmp.ui.postlogin.inventory

import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptDeliveryInstructionItemCaseType
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.InventoryBottle
import org.scnsoft.fidekmp.ui.IFilter
import org.scnsoft.fidekmp.ui.ISnackMessage
import org.scnsoft.fidekmp.ui.LoadingInterface
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.ui.UiResultInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import org.scnsoft.fidekmp.domain.model.BottleInfo
import org.scnsoft.fidekmp.domain.model.BoxInfo
import org.scnsoft.fidekmp.domain.model.CellarWineDetails
import org.scnsoft.fidekmp.domain.model.DomainInfo
import org.scnsoft.fidekmp.domain.model.FilterEntity
import org.scnsoft.fidekmp.domain.model.InventoryStartInfo
import org.scnsoft.fidekmp.domain.model.PackageItem
import org.scnsoft.fidekmp.ui.IConnection

interface ICellarInventory {
    val cellarWineDetails: StateFlow<CellarWineDetails>
    fun selectCellarTab(tabInd: Int)
    fun getWines()
    fun setWineDetailInfo(info: PackageItem)
}

interface IPrinter {
    val isPrintingState: StateFlow<Boolean>
    fun disconnectPrinter()
    fun printReceipt()
    fun printReceiptInventory()
}

interface IInventoryModel : LoadingInterface, IFilter, UiResultInterface, ICellarInventory,
    IConnection, /*IExternalQrCode,*/ IPrinter, ISnackMessage {
    val caseList: StateFlow<List<DptDeliveryInstructionItemCaseType>>
    val inventoryStartInfo: StateFlow<InventoryStartInfo?>
    val inventoryListState: Flow<List<DomainInfo>>

//    fun selectCellarTab(tabInd: Int)
    fun syncInventory()
    fun setInventoryStartInfo(wineId: Int, putInCases: Boolean, caseIndex: Int = -1)
    fun onUndoClick(): String
    suspend fun checkPasspIsExists(scanType: String, code: String): Boolean
    suspend fun addCodeToInventory(code: String, dataType: String)
}

class InventoryModelPreview : IInventoryModel {
    override val caseList: StateFlow<List<DptDeliveryInstructionItemCaseType>>
        get() = MutableStateFlow(
            listOf()
        )
    override val cellarWineDetails: StateFlow<CellarWineDetails> get() = MutableStateFlow(CellarWineDetails.Empty())
    override val inventoryStartInfo: StateFlow<InventoryStartInfo?>
        get() = MutableStateFlow(geInventoryStartInfo())
    override val inventoryListState: Flow<List<DomainInfo>> get() = flowOf(listOf<DomainInfo>())
    override val isloadingState: StateFlow<Boolean> get() = MutableStateFlow(false)
    override val filterItemsState: StateFlow<Map<String, List<FilterEntity>>>  get() = MutableStateFlow(mapOf())
    override val uiResult: StateFlow<UiResult?> get() = MutableStateFlow(null)

    override fun updateFilter() {}

    override fun clearFilter() {}

    override fun resetUiResult() {}
    override fun isConnected(): Boolean {
        return true
    }

    override fun getWines() {}
    override fun setWineDetailInfo(info: PackageItem) {}
    override fun selectCellarTab(tabInd: Int) {}
    override fun syncInventory() {}
    override fun setInventoryStartInfo(wineId: Int, putInCases: Boolean, caseIndex: Int){}
    override fun onUndoClick(): String {
        return ""
    }

    override suspend fun checkPasspIsExists(scanType: String, code: String): Boolean {
        return false
    }

    override suspend fun addCodeToInventory(code: String, dataType: String) {
    }

    override fun printReceiptInventory() {}
    override fun showSnackMessage(text: String) {
    }

    override fun showSnackMessage(textId: Int) {
    }
/*
    override val nfcCodeState: StateFlow<NfcData>
        get() = MutableStateFlow(NfcData.Empty())
    override val nfcAvailabilityState: StateFlow<Boolean>
        get() = MutableStateFlow(true)
    override val isScannerConnected: StateFlow<Boolean>
        get() = MutableStateFlow(false)
    override val externalQRCodeState: Flow<String>
        get() = MutableStateFlow("")
    override val scanSourceTabIndexState: StateFlow<Int>
        get() = MutableStateFlow(0)

    override fun getBarcodeView(): BarCodeView? {
        return null
    }

    override fun initExternalScanner(context: Context): Boolean {
        return true
    }

    override fun selectScanSourceTab(tabInd: Int) {
    }
*/
    override val isPrintingState: StateFlow<Boolean>  get() = MutableStateFlow(false)

    override fun disconnectPrinter() {}

    override fun printReceipt() {
    }

    private fun geInventoryStartInfo(): InventoryStartInfo {
        val l1 = mutableListOf<BottleInfo>()
        val l = mutableListOf<BoxInfo>()
        repeat(3) { ind -> l1 += BottleInfo("E26P8DNNU$ind", "Wine Name $ind", "750ml", "" ) }
        repeat(1) { ind -> l += BoxInfo("000000+$ind", l1, ) }
        val info = InventoryStartInfo(122, "Wine Name ", wineColor = "Red", vintage = 2017, "750ml",  7, 12, true, mutableListOf<InventoryBottle>(), l, l1,  "360",62, 5 )
        return info
    }
}