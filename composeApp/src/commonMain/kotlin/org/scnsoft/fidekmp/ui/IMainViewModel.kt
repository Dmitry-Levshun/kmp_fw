package org.scnsoft.fidekmp.ui

import org.scnsoft.fidekmp.domain.model.profile.ProfileInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.scnsoft.fidekmp.domain.model.FilterEntity
import org.scnsoft.fidekmp.domain.model.profile.ProfileExtInfo

interface LoadingInterface {
    val isloadingState: StateFlow<Boolean>
}
interface UiResultInterface {
    val uiResult: StateFlow<UiResult?>
    fun resetUiResult()
}
interface IProfileInfo {
    val profileInfo: StateFlow<ProfileExtInfo>
}

interface IFilter {
    val filterItemsState: StateFlow<Map<String, List<FilterEntity>>>
    fun updateFilter()
    fun clearFilter()
}

interface IConnection{
    fun isConnected(): Boolean
}
/*

interface IExternalQrCode{
    val nfcCodeState: StateFlow<NfcData>
    val nfcAvailabilityState: StateFlow<Boolean>
    val isScannerConnected: StateFlow<Boolean>
    val externalQRCodeState: Flow<String>
    val scanSourceTabIndexState: StateFlow<Int>

    fun getBarcodeView(): BarCodeView?
    fun initExternalScanner(context: Context): Boolean
    fun selectScanSourceTab(tabInd: Int)
}
 */
interface ISnackMessage{
    fun showSnackMessage(text: String)
    fun showSnackMessage(textId: Int)
}

interface IResetQrCode{
    fun resetQrCodeInfo()
}

sealed class UiResult {
    object Success : UiResult()
    class Error(val errorMessage: String?) : UiResult()
    class SyncError(val batchId: Int?) : UiResult()
    override fun toString(): String {
        return "UiResult " +
                when(this) {
                    is Success -> "Success"
                    is Error ->"Error $errorMessage"
                    is SyncError -> "SyncError $batchId"
                }
    }
}