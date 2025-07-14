package org.scnsoft.fidekmp.domain.repository

import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.CustomerItemDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DigitalPassportTransfer
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptDeliveryInstructionItemBottleType
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptDeliveryInstructionItemCaseType
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.IntermediateSyncInstructionItem
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.InventoryRequestDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrCodeInfoRequest
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrCodeInstantSellingRequest
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.SyncInstructionRequestDtoNew
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineItem
import kotlinx.coroutines.flow.StateFlow
import org.scnsoft.fidekmp.domain.repository.AppellationResult
import org.scnsoft.fidekmp.domain.repository.BottleResult
import org.scnsoft.fidekmp.domain.repository.CaseResult
import org.scnsoft.fidekmp.domain.repository.CommonResult
import org.scnsoft.fidekmp.domain.repository.ContactResult
import org.scnsoft.fidekmp.domain.repository.CreateDigitalPassportTransferData
import org.scnsoft.fidekmp.domain.repository.CustomerResult
import org.scnsoft.fidekmp.domain.repository.DeliveryInstructionResult
import org.scnsoft.fidekmp.domain.repository.DigitalPassportTransferResult
import org.scnsoft.fidekmp.domain.repository.DptResult
import org.scnsoft.fidekmp.domain.repository.InvoiceResult
import org.scnsoft.fidekmp.domain.repository.OrganisationResult
import org.scnsoft.fidekmp.domain.repository.QrBoxCodeInfoResult
import org.scnsoft.fidekmp.domain.repository.QrCodeInfoResult
import org.scnsoft.fidekmp.domain.repository.QrCodeInfoResultNoLogin
import org.scnsoft.fidekmp.domain.repository.SyncResult
import org.scnsoft.fidekmp.domain.repository.TransferHistoryResult
import org.scnsoft.fidekmp.domain.repository.VersionResult
import org.scnsoft.fidekmp.domain.repository.WinesExtResult
import org.scnsoft.fidekmp.domain.repository.WinesResult

interface DeliveryInstructionsRepository {
    val digitalPassportTransfers: List<DigitalPassportTransfer>
    val wineList: List<WineItem>
    val customerList: StateFlow<List<CustomerItemDto>>
    val bottleList: StateFlow<List<DptDeliveryInstructionItemBottleType>>
    val caseList: StateFlow<List<DptDeliveryInstructionItemCaseType>>

    suspend fun syncInstructions(request: SyncInstructionRequestDtoNew?): SyncResult
    suspend fun syncInstructionsIntermediate(request: List<IntermediateSyncInstructionItem>): SyncResult
    suspend fun getWines(isRefresh: Boolean = false): WinesResult
    suspend fun getDigitalPassportTransfers(page: Int? = null, itemsPerPage: Int? = null, isRefresh: Boolean = false): DigitalPassportTransferResult
    suspend fun getDigitalPassportTransferById(id: Int): DigitalPassportTransferResult
    suspend fun getDeliveryInstructions(): DeliveryInstructionResult
    suspend fun getDigitalPassports()
    suspend fun getContacts(): ContactResult
    suspend fun getAppellations(): AppellationResult
    suspend fun getInvoices(): InvoiceResult
    suspend fun getOrganisations(): OrganisationResult

    suspend fun payDigitalPassport(id: String): DptResult
    suspend fun approveDigitalPassport(id: String): DptResult
    suspend fun cancelDigitalPassport(id: String, abortOrder: Boolean): DptResult
    suspend fun addDigitalPassportTransfers(createDigitalPassportTransferData: CreateDigitalPassportTransferData): DptResult
    suspend fun findCodeInfoScanMode(request: QrCodeInfoRequest): QrCodeInfoResult
    suspend fun findCodeInfoScanModeNoLogin(request: QrCodeInfoRequest): QrCodeInfoResultNoLogin
    suspend fun findCodeInfoWalletMode(request: QrCodeInfoRequest): QrCodeInfoResult
    suspend fun findCodeBoxInfo(caseId: String): QrBoxCodeInfoResult
    suspend fun logout()
    suspend fun getCustomerList(): CustomerResult
    suspend fun breakCase(caseId: String): CommonResult
    suspend fun getBottleList(): BottleResult
    suspend fun getCaseList(): CaseResult
    suspend fun getWine(wineId: Int): WinesResult
    suspend fun getWineExt(wineId: Int): WinesExtResult

    suspend fun getDigitalPassportTransferByToken(token: String): DigitalPassportTransferResult
    suspend fun syncInventory(request: InventoryRequestDto): SyncResult
    suspend fun syncInstantSelling(request: QrCodeInstantSellingRequest): SyncResult
    suspend fun getTransferHistory(transferId: Int): TransferHistoryResult
     suspend fun getVersion(): VersionResult
}

/*
getWines
{"@context":"\/api\/v1\/wineyard\/contexts\/Wine",
    "@id":"\/api\/v1\/wineyard\/wines",
    "@type":"hydra:Collection",
    "hydra:totalItems":0,
    "hydra:member":[],
    "hydra:search":{"@type":"hydra:IriTemplate","hydra:template":"\/api\/v1\/wineyard\/wines{?primeurse}",
    "hydra:variableRepresentation":"BasicRepresentation",
    "hydra:mapping":[{"@type":"IriTemplateMapping","variable":"primeurse","property":"primeurse","required":false}]}}

 */