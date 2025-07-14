package org.scnsoft.fidekmp.domain.repository

import kotlinx.datetime.LocalDateTime
import org.scnsoft.fidekmp.data.api.ApiFailure
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.AppellationItem
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.ContactItem
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.CreateDigitalPassportTransferRequestWine
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.CustomerItemDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DeliveryInstructionDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DigitalPassportTransfer
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptDeliveryInstructionItemBottleType
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptDeliveryInstructionItemCaseType
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.InvoiceItem
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.OrCodeResponseNoLogin
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.Organisation
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrBoxCodeInfoResponse
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrCodeInfoResponse
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.TransferHistoryItemDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.VersionResponse
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineExtendedInfo
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemById
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem

sealed class SyncResult {
    object Success : SyncResult()
    class SyncError(val batchId: Int?) : SyncResult()
    class Error(val e: Exception?) : SyncResult()
    override fun toString(): String {
        return "" +
                when(this) {
                    is Success -> "Success"
                    is Error ->"Error ${e?.message}"
                    is SyncError -> "SyncError $batchId"
                }
    }
}

sealed class WinesResult {
    class Success(val list: List<WineItem>) : WinesResult()
    class Failure(val apiFailure: ApiFailure) : WinesResult()
    class Error(val e: Exception?) : WinesResult()
    override fun toString(): String {
        return "WinesResult " +
                when(this) {
                    is Success -> "Success ${list.size}"
                    is Failure ->"${apiFailure.message}"
                    is Error ->"Error $e"
                }
    }
}
sealed class WinesExtResult {
    class Success(val wine: WineExtendedInfo) : WinesExtResult()
    class Failure(val apiFailure: ApiFailure) : WinesExtResult()
    class Error(val e: Exception?) : WinesExtResult()
    override fun toString(): String {
        return "WinesExtResult " +
                when(this) {
                    is Success -> "Success ${wine.id}"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class DeliveryInstructionResult {
    class Success(val dto: DeliveryInstructionDto) : DeliveryInstructionResult()
    class Failure(val apiFailure: ApiFailure) : DeliveryInstructionResult()
    class Error(val e: Exception?) : DeliveryInstructionResult()
    override fun toString(): String {
        return "DeliveryInstructionResuult " +
                when(this) {
                    is Success -> "Success $dto"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}
sealed class DigitalPassportTransferResult {
    class Success(val list: List<DigitalPassportTransfer>) : DigitalPassportTransferResult()
    class Failure(val apiFailure: ApiFailure) : DigitalPassportTransferResult()
    class Error(val e: Exception?) : DigitalPassportTransferResult()
    override fun toString(): String {
        return "DigitalPassportTransfer " +
                when(this) {
                    is Success -> "Success ${list.size}"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class ContactResult {
    class Success(val list: List<ContactItem>) : ContactResult()
    class Failure(val apiFailure: ApiFailure) : ContactResult()
    class Error(val e: Exception?) : ContactResult()
    override fun toString(): String {
        return "ContactResult " +
                when(this) {
                    is Success -> "Success $list"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}


sealed class AppellationResult {
    class Success(val list: List<AppellationItem>) : AppellationResult()
    class Failure(val apiFailure: ApiFailure) : AppellationResult()
    class Error(val e: Exception?) : AppellationResult()
    override fun toString(): String {
        return "AppellationResult " +
                when(this) {
                    is Success -> "Success $list"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class InvoiceResult {
    class Success(val list: List<InvoiceItem>) : InvoiceResult()
    class Failure(val apiFailure: ApiFailure) : InvoiceResult()
    class Error(val e: Exception?) : InvoiceResult()
    override fun toString(): String {
        return "InvoiceResult " +
                when(this) {
                    is Success -> "Success $list"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class DptResult {
    class Success(val passport: DigitalPassportTransfer) : DptResult()
    class Failure(val apiFailure: ApiFailure) : DptResult()
    class Error(val e: Exception?) : DptResult()
    override fun toString(): String {
        return "DptResult " +
                when(this) {
                    is Success -> "Success $passport"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class OrganisationResult {
    class Success(val list: List<Organisation>) : OrganisationResult()
    class Failure(val apiFailure: ApiFailure) : OrganisationResult()
    class Error(val e: Exception?) : OrganisationResult()
    override fun toString(): String {
        return "OrganisationResult " +
                when(this) {
                    is Success -> "Success $list"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

data class CreateDigitalPassportTransferData(
    var deliveryInstructionsDueDate: LocalDateTime, //"2023-11-17T10:26:24.839Z",
    var domainName: String,
    var selfProvideDeliveryInstruction: Boolean,
    var organizationId: String?, //"/api/v1/wineyard/organizations/1",
    var recipientId: String?,  //"/api/v1/wineyard/organizations/2",
    var sellerIsPayeer: Boolean, //false,
    var isGift: Boolean, //false,
    var buyerEmail: String, //"fidewinebuyer@gmail.com",
    var items: List<CreateDigitalPassportTransferRequestWine>,
    var currency: String // "EUR"
)

sealed class QrCodeInfoResult {
    class Success(val passport: QrCodeInfoResponse) : QrCodeInfoResult()
    class Failure(val apiFailure: ApiFailure) : QrCodeInfoResult()
    class Error(val e: Exception?) : QrCodeInfoResult()
    override fun toString(): String {
        return "" +
            when(this) {
                is Success -> "Success //$passport"
                is Failure -> "${apiFailure.message}"
                is Error ->"Error ${e?.message}"
            }
    }
}
sealed class QrCodeInfoResultNoLogin {
    class Success(val passport: OrCodeResponseNoLogin) : QrCodeInfoResultNoLogin()
    class Failure(val apiFailure: ApiFailure) : QrCodeInfoResultNoLogin()
    class Error(val e: Exception?) : QrCodeInfoResultNoLogin()
    override fun toString(): String {
        return "QrCodeInfo " +
                when(this) {
                    is Success -> "Success //$passport"
                    is Failure ->"Failure ${apiFailure.message}"
                    is Error ->"Error ${e?.message}"
                }
    }
}

sealed class QrBoxCodeInfoResult {
    class Success(val passport: QrBoxCodeInfoResponse) : QrBoxCodeInfoResult()
    //    class Success() : QrCodeInfoResult()
    class Failure(val apiFailure: ApiFailure) : QrBoxCodeInfoResult()
    class Error(val e: Exception?) : QrBoxCodeInfoResult()
    override fun toString(): String {
        return "QrBoxCodeInfoResult " +
                when(this) {
                    is Success -> "Success //$passport"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class CommonResult {
    object Success : CommonResult()
    class Failure(val apiFailure: ApiFailure) : CommonResult()
    class Error(val e: Throwable?) : CommonResult()

    override fun toString(): String {
        return "" +
                when (this) {
                    is Success -> "Success"
                    is Error -> "Error ${e?.message}"
                    is Failure -> "Failure ${apiFailure.code} ${apiFailure.message}"
                }
    }
}

sealed class CustomerResult {
    class Success(val list: List<CustomerItemDto>) : CustomerResult()
    class Failure(val apiFailure: ApiFailure) : CustomerResult()
    class Error(val e: Exception?) : CustomerResult()
    override fun toString(): String {
        return "CustomerResult " +
                when(this) {
                    is Success -> "Success ${list.size}"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class BottleResult {
    class Success(val list: List<DptDeliveryInstructionItemBottleType>) : BottleResult()
    class Failure(val apiFailure: ApiFailure) : BottleResult()
    class Error(val e: Exception?) : BottleResult()
    override fun toString(): String {
        return "BottleResult " +
                when(this) {
                    is Success -> "Success ${list.size}"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class CaseResult {
    class Success(val list: List<DptDeliveryInstructionItemCaseType>) : CaseResult()
    class Failure(val apiFailure: ApiFailure) : CaseResult()
    class Error(val e: Exception?) : CaseResult()
    override fun toString(): String {
        return "CaseResult " +
                when(this) {
                    is Success -> "Success ${list.size}"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}


sealed class TransferHistoryResult {
    class Success(val list: List<TransferHistoryItemDto>) : TransferHistoryResult()
    class Failure(val apiFailure: ApiFailure) : TransferHistoryResult()
    class Error(val e: Exception?) : TransferHistoryResult()
    override fun toString(): String {
        return "TransferHistoryResult " +
                when(this) {
                    is Success -> "Success ${list.size}"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class UntrackedWineListResult {
    class Success(val list: List<UntrackedWineItem>) : UntrackedWineListResult()
    class Failure(val apiFailure: ApiFailure) : UntrackedWineListResult()
    class Error(val e: Throwable?) : UntrackedWineListResult()
    override fun toString(): String {
        return "UntrackedWineListResult " +
                when(this) {
                    is Success -> "Success ${list.size}"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class UntrackedUserWineListResult {
    class Success(val list: List<UntrackedUserWineItem>) : UntrackedUserWineListResult()
    class Failure(val apiFailure: ApiFailure) : UntrackedUserWineListResult()
    class Error(val e: Throwable?) : UntrackedUserWineListResult()
    override fun toString(): String {
        return "UntrackedUserWineListResult " +
                when(this) {
                    is Success -> "Success ${list.size}"
                    is Failure ->"Failure $apiFailure"
                    is Error ->"Error $e"
                }
    }
}

sealed class UntrackedUserWineResult {
    class Success(val userWine: UntrackedUserWineItemById) : UntrackedUserWineResult()
    class Failure(val apiFailure: ApiFailure) : UntrackedUserWineResult()
    class Error(val e: Throwable?) : UntrackedUserWineResult()

    override fun toString(): String {
        return "UntrackedUserWineResult " +
                when (this) {
                    is Success -> "Success $userWine"
                    is Failure -> "Failure $apiFailure"
                    is Error -> "Error $e"
                }
    }
}

sealed class UntrackedWineResult {
        class Success(val wine: UntrackedWineItem) : UntrackedWineResult()
        class Failure(val apiFailure: ApiFailure) : UntrackedWineResult()
        class Error(val e: Throwable?) : UntrackedWineResult()
        override fun toString(): String {
            return "UntrackedWineResult " +
                    when(this) {
                        is Success -> "Success $wine"
                        is Failure ->"Failure $apiFailure"
                        is Error ->"Error $e"
                    }
        }
}

sealed class VersionResult {
    class Success(val response: VersionResponse) : VersionResult()
    class Failure(val apiFailure: ApiFailure) : VersionResult()
    class Error(val e: Throwable?) : VersionResult()

    override fun toString(): String {
        return "VersionResult " +
                when (this) {
                    is Success -> "Success $response"
                    is Failure -> "Failure $apiFailure"
                    is Error -> "Error $e"
                }
    }
}
