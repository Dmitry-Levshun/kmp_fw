package org.scnsoft.fidekmp.data.api.deliveryinstructions

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.AppellationDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.BottleDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.BreakSealRequest
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.CaseDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.ContactDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.CreateDigitalPassportTransferRequestDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.CustomerDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DigitalPassportTransfer
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DigitalPassportTransferDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DptCancelRequest
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.IntermediateSyncInstructionRequestDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.InventoryRequestDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.InvoiceDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.OrCodeResponseNoLogin
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.OrganisationDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.PackageDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrBoxCodeInfoResponse
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrCodeBoxInfoRequest
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrCodeInfoRequest
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrCodeInfoResponse
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.QrCodeInstantSellingRequest
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.SyncInstructionRequestDtoNew
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.SyncInstructionResponseDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.TransferHistoryItemDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.VersionResponse
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineExtendedInfo
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineItem

interface DeliveryInstructionApi {
//    @POST("wineyard/habillage-processing")
    suspend fun syncInstructions(request: SyncInstructionRequestDtoNew): Result<SyncInstructionResponseDto>
//    @POST("wineyard/order-batch-process")
//    @POST("wineyard/order-processing")
    suspend fun syncInstructionsIntermediate(request: IntermediateSyncInstructionRequestDto): Result<SyncInstructionResponseDto>

//    @GET("wineyard/wines")
    suspend fun getWines(page: Int? = null, itemsPerPage: Int? = null, lastFetchTimestamp: Long? = null): Result<WineDto>

//    @GET("wineyard/wines/{id}")
    suspend fun getWine(id: Int): Result<WineItem>
//    @GET("wineyard/wines/{id}")
    suspend fun getWineExt(id: Int): Result<WineExtendedInfo>

//    @GET("wineyard/digital-passport-transfers/{id}")
    suspend fun getDigitalPassportTransferById(id: Int): Result<DigitalPassportTransfer>
//    @GET("wineyard/digital-passport-transfers/get-by-token/{token}")
    suspend fun getDigitalPassportTransferByToken(token: String): Result<DigitalPassportTransfer>
//    @GET("wineyard/digital-passport-transfers")
    suspend fun getDigitalPassportTransfers(page: Int? = null, itemsPerPage: Int? = null,
                                            lastFetchTimestamp: Long? = null,
                                            ordersGroup: String? = null): Result<DigitalPassportTransferDto>
        // `orders-group` possible values:
        //incoming
        //outcoming
        //transferred
        //canceled
//    @PATCH("wineyard/digital-passport-transfers/{id}/acknowledge")
    suspend fun acknowledge(id: String): Result<HttpResponse>

//    @PATCH("wineyard/digital-passport-transfers/{id}/cancel") // removed
    suspend fun cancel(id: String, request: DptCancelRequest): Result<DigitalPassportTransfer>

//    @PATCH("wineyard/digital-passport-transfers/{id}/pay")  // removed
    suspend fun pay(id: String): Result<DigitalPassportTransfer>

//    @PATCH("wineyard/digital-passport-transfers/{id}/approve")
    suspend fun approve(id: String): Result<DigitalPassportTransfer>

//    @PATCH("wineyard/digital-passport-transfers/{id}/close")
    suspend fun close(id: String): Result<DigitalPassportTransfer>

//    @PATCH("wineyard/digital-passport-transfers/{id}/reject-passport")
    suspend fun reject(id: String): Result<DigitalPassportTransfer>

//    @GET("wineyard/delivery-instructions")
    suspend fun getDeliveryInstructions(): Result<HttpResponse>//DeliveryInstructionDto>

//    @GET("wineyard/contacts")
    suspend fun getContacts(page: Int? = null, itemsPerPage: Int? = null,
                            lastFetchTimestamp: Long? = null): Result<ContactDto>

//    @GET("wineyard/digital-passports")
    suspend fun getDigitalPassports(): Result<HttpResponse>

//    @GET("wineyard/geo/appellations")
    suspend fun getAppellations(): Result<AppellationDto>

//    @GET("wineyard/invoices")
    suspend fun getInvoices(): Result<InvoiceDto>

//    @POST("wineyard/digital-passport-transfers")
    suspend fun addDigitalPassportTransfers(request: CreateDigitalPassportTransferRequestDto): Result<DigitalPassportTransfer>


//    @GET("wineyard/shippings")
    suspend fun getShippings(): Result<HttpResponse>

//    @GET("wineyard/transfers") // doesn't work
    suspend fun getTransfers(): Result<HttpResponse>

//    @GET("wineyard/vintage-list")
    suspend fun getVintageList(): Result<HttpResponse>

//    @GET("wineyard/organizations")
    suspend fun getOrganizations(): Result<OrganisationDto>

//    @GET("wineyard/delivery-instruction/item/case-types")
    suspend fun getPackageList(): Result<PackageDto>

//    @POST("wineyard/digital-passports/find-in-scanning-mode")
    suspend fun getQrCodeInfoScanMode(request: QrCodeInfoRequest): Result<QrCodeInfoResponse>

//    @POST("wineyard/digital-passports/find-in-scanning-mode")
    suspend fun getQrCodeInfoScanModeNoLogin(request: QrCodeInfoRequest): Result<OrCodeResponseNoLogin>

//    @POST("wineyard/digital-passports/find-in-wallet-mode")
    suspend fun getQrCodeInfoWalletMode(request: QrCodeInfoRequest): Result<QrCodeInfoResponse>

//    @POST("wineyard/packages/get-bottles-by-case")
    suspend fun getQrCodeBoxInfoInfo(request: QrCodeBoxInfoRequest): Result<QrBoxCodeInfoResponse>

//    @PATCH("wineyard/packages/break-the-seal/{id}")
    suspend fun breakCase(caseId: String, request: BreakSealRequest): Result<HttpResponse>

//    @GET("wineyard/digital-passports")   // doesn't work
//    suspend fun getDigitalPassport(): Result<HttpResponse>

//    @GET("wineyard/customers") // Intermediate
    suspend fun getCustomerList(contactType: String? = null, page: Int? = null, itemsPerPage: Int? = null, lastFetchTimestamp: Long? = null): Result<CustomerDto>

//    @GET("wineyard/delivery-instruction/item/bottle-types")
    suspend fun getBottleList(page: Int? = null, itemsPerPage: Int? = null, lastFetchTimestamp: Long? = null): Result<BottleDto>

//    @GET("wineyard/delivery-instruction/item/case-types")
    suspend fun getCaseList(page: Int? = null, itemsPerPage: Int? = null, lastFetchTimestamp: Long? = null): Result<CaseDto>

//    @POST("wineyard/inventory-processing")
    suspend fun syncInventory(request: InventoryRequestDto): Result<SyncInstructionResponseDto>

//    @POST("wineyard/digital-passport-transfers/instant-sell")
    suspend fun syncInstantSelling(request: QrCodeInstantSellingRequest): Result<SyncInstructionResponseDto>

//    @GET("wineyard/digital-passports/{id}/transfer-history")
    suspend fun getTransferHistory(id: Int): Result<List<TransferHistoryItemDto>>

//    @GET("wineyard/version")
    suspend fun getVersion(): Result<VersionResponse>

}

class DeliveryInstructionApiImpl(private val client: HttpClient): DeliveryInstructionApi {

    //    @POST("wineyard/habillage-processing")
    override suspend fun syncInstructions(request: SyncInstructionRequestDtoNew): Result<SyncInstructionResponseDto> = runCatching {
        client.post("wineyard/habillage-processing") {
            setBody(request)
        }.body()
    }
//    @POST("wineyard/order-processing")
    override suspend fun syncInstructionsIntermediate(request: IntermediateSyncInstructionRequestDto): Result<SyncInstructionResponseDto> = runCatching {
        client.post("wineyard/order-batch-processing") {
            setBody(request)
        }.body()
    }

    //    @GET("wineyard/wines")
    override suspend fun getWines(page: Int?, itemsPerPage: Int?, lastFetchTimestamp: Long?): Result<WineDto> = runCatching {
        client.get("wineyard/wines") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("lastFetchTimestamp", lastFetchTimestamp)
        }.body()
    }

    //    @GET("wineyard/wines/{id}")
    override suspend fun getWine(id: Int): Result<WineItem> = runCatching {
        client.get("wineyard/wines/$id").body()
    }
    //    @GET("wineyard/wines/{id}")
    override suspend fun getWineExt(id: Int): Result<WineExtendedInfo> = runCatching {
        client.get("wineyard/wines/$id").body()
    }

    //    @GET("wineyard/digital-passport-transfers/{id}")
    override suspend fun getDigitalPassportTransferById(id: Int): Result<DigitalPassportTransfer> = runCatching {
        client.get("wineyard/digital-passport-transfers/$id").body()
    }
    //    @GET("wineyard/digital-passport-transfers/get-by-token/{token}")
    override suspend fun getDigitalPassportTransferByToken(token: String): Result<DigitalPassportTransfer> = runCatching {
        client.get("wineyard/digital-passport-transfers/get-by-token/$token").body()
    }
    //    @GET("wineyard/digital-passport-transfers")
    override suspend fun getDigitalPassportTransfers(page: Int?, itemsPerPage: Int?, lastFetchTimestamp: Long?,
                                            ordersGroup: String?): Result<DigitalPassportTransferDto> = runCatching {
        client.get("wineyard/digital-passport-transfers") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("lastFetchTimestamp", lastFetchTimestamp)
            parameter("orders-group", ordersGroup)
        }.body()
    }
    // `orders-group` possible values:
    //incoming
    //outcoming
    //transferred
    //canceled
//    @PATCH("wineyard/digital-passport-transfers/{id}/acknowledge")
    override suspend fun acknowledge(id: String): Result<HttpResponse> = runCatching {
        client.patch("wineyard/digital-passport-transfers/$id/acknowledge")
    }

    //    @PATCH("wineyard/digital-passport-transfers/{id}/cancel") // removed
    override suspend fun cancel(id: String, request: DptCancelRequest): Result<DigitalPassportTransfer> = runCatching {
        client.patch("wineyard/digital-passport-transfers/$id/cancel") {
            setBody(request)
        }.body()
    }

    //    @PATCH("wineyard/digital-passport-transfers/{id}/pay")  // removed
    override suspend fun pay(id: String): Result<DigitalPassportTransfer> = runCatching {
        client.patch("wineyard/digital-passport-transfers/$id/pay").body()
    }

    //    @PATCH("wineyard/digital-passport-transfers/{id}/approve")
    override suspend fun approve(id: String): Result<DigitalPassportTransfer> = runCatching {
        client.patch("wineyard/digital-passport-transfers/$id/approve").body()
    }

    //    @PATCH("wineyard/digital-passport-transfers/{id}/close")
    override suspend fun close(id: String): Result<DigitalPassportTransfer> = runCatching {
        client.patch("wineyard/digital-passport-transfers/$id/close").body()
    }

    //    @PATCH("wineyard/digital-passport-transfers/{id}/reject-passport")
    override suspend fun reject(id: String): Result<DigitalPassportTransfer> = runCatching {
        client.patch("wineyard/digital-passport-transfers/$id/reject-passport").body()
    }

    //    @GET("wineyard/delivery-instructions")
    override suspend fun getDeliveryInstructions(): Result<HttpResponse> = runCatching {
        client.get("wineyard/delivery-instructions")
    }

    //    @GET("wineyard/contacts")
    override suspend fun getContacts(page: Int?, itemsPerPage: Int?,
                            lastFetchTimestamp: Long?): Result<ContactDto> = runCatching {
        client.get("wineyard/contacts") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("lastFetchTimestamp", lastFetchTimestamp)
        }.body()
    }

    //    @GET("wineyard/digital-passports")
    override suspend fun getDigitalPassports(): Result<HttpResponse> = runCatching {
        client.get("wineyard/digital-passports")
    }

    //    @GET("wineyard/geo/appellations")
    override suspend fun getAppellations(): Result<AppellationDto> = runCatching {
        client.get("wineyard/geo/appellations").body()
    }

    //    @GET("wineyard/invoices")
    override suspend fun getInvoices(): Result<InvoiceDto> = runCatching {
        client.get("wineyard/invoices").body()
    }

    //    @POST("wineyard/digital-passport-transfers")
    override suspend fun addDigitalPassportTransfers(request: CreateDigitalPassportTransferRequestDto): Result<DigitalPassportTransfer> = runCatching {
        client.post("wineyard/digital-passport-transfers") {
            setBody(request)
        }.body()
    }


    //    @GET("wineyard/shippings")
    override suspend fun getShippings(): Result<HttpResponse> = runCatching {
        client.get("wineyard/shippings")
    }

    //    @GET("wineyard/transfers") // doesn't work
    override suspend fun getTransfers(): Result<HttpResponse> = runCatching {
        client.get("wineyard/transfers")
    }

    //    @GET("wineyard/vintage-list")
    override suspend fun getVintageList(): Result<HttpResponse> = runCatching {
        client.get("wineyard/vintage-list")
    }

    //    @GET("wineyard/organizations")
    override suspend fun getOrganizations(): Result<OrganisationDto> = runCatching {
        client.get("wineyard/organizations").body()
    }

    //    @GET("wineyard/delivery-instruction/item/case-types")
    override suspend fun getPackageList(): Result<PackageDto> = runCatching {
        client.get("wineyard/delivery-instruction/item/case-types").body()
    }

    //    @POST("wineyard/digital-passports/find-in-scanning-mode")
    override suspend fun getQrCodeInfoScanMode(request: QrCodeInfoRequest): Result<QrCodeInfoResponse> = runCatching {
        client.post("wineyard/digital-passports/find-in-scanning-mode") {
            setBody(request)
        }.body()
    }

    //    @POST("wineyard/digital-passports/find-in-scanning-mode")
    override suspend fun getQrCodeInfoScanModeNoLogin(request: QrCodeInfoRequest): Result<OrCodeResponseNoLogin> = runCatching {
        client.post("wineyard/digital-passports/find-in-scanning-mode") {
            setBody(request)
        }.body()
    }

    //    @POST("wineyard/digital-passports/find-in-wallet-mode")
    override suspend fun getQrCodeInfoWalletMode(request: QrCodeInfoRequest): Result<QrCodeInfoResponse> = runCatching {
        client.post("wineyard/digital-passports/find-in-wallet-mode") {
            setBody(request)
        }.body()
    }

    //    @POST("wineyard/packages/get-bottles-by-case")
    override suspend fun getQrCodeBoxInfoInfo(request: QrCodeBoxInfoRequest): Result<QrBoxCodeInfoResponse> = runCatching {
        client.post("wineyard/packages/get-bottles-by-case") {
            setBody(request)
        }.body()
    }

    //    @PATCH("wineyard/packages/break-the-seal/{id}")
    override suspend fun breakCase(caseId: String, request: BreakSealRequest): Result<HttpResponse> = runCatching {
        client.patch("wineyard/packages/break-the-seal/$caseId") {
            setBody(request)
        }
    }

//    @GET("wineyard/digital-passports")   // doesn't work
//    suspend fun getDigitalPassport(): Result<HttpResponse>

    //    @GET("wineyard/customers") // Intermediate
    override suspend fun getCustomerList(contactType: String?, page: Int?, itemsPerPage: Int?, lastFetchTimestamp: Long?): Result<CustomerDto> = runCatching {
        client.get("wineyard/customers") {
            parameter("contactType", contactType)
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("lastFetchTimestamp", lastFetchTimestamp)
        }.body()
    }

    //    @GET("wineyard/delivery-instruction/item/bottle-types")
   override suspend fun getBottleList(page: Int?, itemsPerPage: Int?, lastFetchTimestamp: Long?): Result<BottleDto> = runCatching {
        client.get("wineyard/delivery-instruction/item/bottle-types") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("lastFetchTimestamp", lastFetchTimestamp)
        }.body()
    }

    //    @GET("wineyard/delivery-instruction/item/case-types")
    override suspend fun getCaseList(page: Int?, itemsPerPage: Int?, lastFetchTimestamp: Long?): Result<CaseDto> = runCatching {
        client.get("wineyard/delivery-instruction/item/case-types") {
            parameter("page", page)
            parameter("itemsPerPage", itemsPerPage)
            parameter("lastFetchTimestamp", lastFetchTimestamp)
        }.body()
    }

    //    @POST("wineyard/inventory-processing")
    override suspend fun syncInventory(request: InventoryRequestDto): Result<SyncInstructionResponseDto> = runCatching {
        client.post("wineyard/inventory-processing") {
            setBody(request)
        }.body()
    }

    //    @POST("wineyard/digital-passport-transfers/instant-sell")
    override suspend fun syncInstantSelling(request: QrCodeInstantSellingRequest): Result<SyncInstructionResponseDto> = runCatching {
        client.post("wineyard/digital-passport-transfers/instant-sell") {
            setBody(request)
        }.body()
    }

    //    @GET("wineyard/digital-passports/{id}/transfer-history")
    override suspend fun getTransferHistory(id: Int): Result<List<TransferHistoryItemDto>> = runCatching {
        client.get("wineyard/digital-passports/$id/transfer-history").body()
    }

    //    @GET("wineyard/version")
    override suspend fun getVersion(): Result<VersionResponse> = runCatching {
        client.get("wineyard/version").body()
    }

}
/*
{"@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassportTransfer",
"@id":"\/api\/v1\/wineyard\/digital-passport-transfers",
"@type":"hydra:Collection",
"hydra:totalItems":9,
"hydra:member":[
{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/1",
"@type":"DigitalPassportTransfer",
"id":1,
"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3",
"@type":"Organization",
"extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},
"sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
"sellerEmail":"francois-dupon-owner@vinexquisite.fr",
"buyerOrganization":{
"@id":"\/api\/v1\/wineyard\/organizations\/4",
"@type":"Organization",
"extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},
"buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
"buyerEmail":"sophie-lamber-user@lesvignobles.fr",
"deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
"status":"Paid",
"items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/1",
"@type":"DigitalPassportTransfer\/Item",
"id":1,
"qty":200,
"wine":{"@id":"\/api\/v1\/wineyard\/wines\/14",
"@type":"Wine","id":14,
"wineType":"Fizzy",
"name":"Expert Fizz",
"imageUrl":"\/path\/image.jpg",
"country":"\/api\/v1\/wineyard\/geo\/countries\/1",
"region":"\/api\/v1\/wineyard\/geo\/regions\/4",
"appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
"classification":"Grand cru"
,"color":"Red",
"agriculture":"Organic",
"vintage":2019,
"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":false,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"105406389563013744766365562059207685319364027738301577849808405617260447231127"},"price":"25.00"}],"invoiceUrl":"string","token":"95181efb09ddc479ef7f78c6132f2f492aafd3bca58808c65e3a3b5d68daf76c","sellerIsPayer":false,"description":"string","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"1","createdAt":"2023-11-23T15:57:34+00:00","updatedAt":"2023-11-23T15:58:09+00:00","isPresale":false},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/2","@type":"DigitalPassportTransfer","id":2,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/2","@type":"DigitalPassportTransfer\/Item","id":2,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/15","@type":"Wine","id":15,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"37030394076567731120697325186355463207983127696158393509650430628033960661874"},"price":"25.00"}],"invoiceUrl":"","token":"11eac4d73cc1546b4ab4f06169f82670564500b04a07b07b2a6b4edc20aac5f3","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"2","createdAt":"2023-11-24T10:49:13+00:00","updatedAt":"2023-11-24T10:49:30+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/3","@type":"DigitalPassportTransfer","id":3,"sellerOrganization":{"@
id":"\/api\/v1\/wineyard\/organizations\/3",
"@type":"Organization",
"extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},
"sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
"sellerEmail":"francois-dupon-owner@vinexquisite.fr",
"buyerOrganization":{
"@id":"\/api\/v1\/wineyard\/organizations\/4",
"@type":"Organization",
"extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},
"buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
"buyerEmail":"sophie-lamber-user@lesvignobles.fr",
"deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
"status":"Pending Payment",
"items":[
{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/3",
"@type":"DigitalPassportTransfer\/Item",
"id":3,
"qty":200,
"wine":{
"@id":"\/api\/v1\/wineyard\/wines\/16",
\"@type":"Wine",
"id":16,
"wineType":"Fizzy",
"name":"Expert Fizz",
"imageUrl":"\/path\/image.jpg",
"country":"\/api\/v1\/wineyard\/geo\/countries\/1",
"region":"\/api\/v1\/wineyard\/geo\/regions\/4",
"appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
"classification":"Grand cru"
,"color":"Red",
"agriculture":"Organic",
"vintage":2019,
"producer":"Winery X",
"winemaker":"John Doe",
"champagneType":"Blanc de blanc",
"isPrimeurse":true,
"grapeVarieties":"Cabernet Sauvignon, Merlot"
,"alcohol":12.5,
"certification":"AOC",
"sulfites":true,
"allergens":"None",
"nftId":"112928131092248927068419063200851447772005351561113284967211721292196041326920"},
"price":"25.00"}],
"invoiceUrl":"",
"token":"fd08706c18688f1987967b7b2e97c468316eb6ffad4bcc8f7c9dfbdc86bb0e14",
"sellerIsPayer":false,
"description":"",
"currency":"USD",
"selfProvideDeliveryInstruction":false,
"isViewed":false,"
vaultInvoiceId":"3",
"createdAt":"2023-11-24T10:49:35+00:00",
"updatedAt":"2023-11-24T10:49:35+00:00",
"isPresale":true},
{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
"@type":"DigitalPassportTransfer",
"id":4,
"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},
"sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
"sellerEmail":"francois-dupon-owner@vinexquisite.fr",
"buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},
"buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
"buyerEmail":"sophie-lamber-user@lesvignobles.fr",
"deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
"status":"Paid",
"items":[
{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/4",
"@type":"DigitalPassportTransfer\/Item",
"id":4,package com.fidewine.app.service.deliveryinstructions

import com.fidewine.app.service.deliveryinstructions.dto.SyncInstructionsRequestDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DeliveryInstructionService {
    @POST("sync/orders")
    suspend fun syncInstructions(@Body request: SyncInstructionsRequestDto): Response<ResponseBody>

    @GET("wineyard/wines")
    suspend fun getwines(): Response<ResponseBody>

    @GET("wineyard/digital-passport-transfers")
    suspend fun getDigitalPassportTransfers(): Response<ResponseBody>

    @GET("wineyard/delivery-instructions")
    suspend fun getDeliveryInstructions(): Response<ResponseBody>
}
/*
{"@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassportTransfer",
"@id":"\/api\/v1\/wineyard\/digital-passport-transfers",
"@type":"hydra:Collection",
"hydra:totalItems":9,
"hydra:member":[
{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/1",
"@type":"DigitalPassportTransfer",
"id":1,
"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3",
"@type":"Organization",
"extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},
"sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
"sellerEmail":"francois-dupon-owner@vinexquisite.fr",
"buyerOrganization":{
"@id":"\/api\/v1\/wineyard\/organizations\/4",
"@type":"Organization",
"extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},
"buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
"buyerEmail":"sophie-lamber-user@lesvignobles.fr",
"deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
"status":"Paid",
"items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/1",
"@type":"DigitalPassportTransfer\/Item",
"id":1,
"qty":200,
"wine":{"@id":"\/api\/v1\/wineyard\/wines\/14",
"@type":"Wine","id":14,
"wineType":"Fizzy",
"name":"Expert Fizz",
"imageUrl":"\/path\/image.jpg",
"country":"\/api\/v1\/wineyard\/geo\/countries\/1",
"region":"\/api\/v1\/wineyard\/geo\/regions\/4",
"appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
"classification":"Grand cru"
,"color":"Red",
"agriculture":"Organic",
"vintage":2019,
"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":false,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"105406389563013744766365562059207685319364027738301577849808405617260447231127"},"price":"25.00"}],"invoiceUrl":"string","token":"95181efb09ddc479ef7f78c6132f2f492aafd3bca58808c65e3a3b5d68daf76c","sellerIsPayer":false,"description":"string","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"1","createdAt":"2023-11-23T15:57:34+00:00","updatedAt":"2023-11-23T15:58:09+00:00","isPresale":false},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/2","@type":"DigitalPassportTransfer","id":2,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/2","@type":"DigitalPassportTransfer\/Item","id":2,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/15","@type":"Wine","id":15,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"37030394076567731120697325186355463207983127696158393509650430628033960661874"},"price":"25.00"}],"invoiceUrl":"","token":"11eac4d73cc1546b4ab4f06169f82670564500b04a07b07b2a6b4edc20aac5f3","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"2","createdAt":"2023-11-24T10:49:13+00:00","updatedAt":"2023-11-24T10:49:30+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/3","@type":"DigitalPassportTransfer","id":3,"sellerOrganization":{"@
id":"\/api\/v1\/wineyard\/organizations\/3",
"@type":"Organization",
"extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},
"sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
"sellerEmail":"francois-dupon-owner@vinexquisite.fr",
"buyerOrganization":{
"@id":"\/api\/v1\/wineyard\/organizations\/4",
"@type":"Organization",
"extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},
"buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
"buyerEmail":"sophie-lamber-user@lesvignobles.fr",
"deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
"status":"Pending Payment",
"items":[
{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/3",
"@type":"DigitalPassportTransfer\/Item",
"id":3,
"qty":200,
"wine":{
"@id":"\/api\/v1\/wineyard\/wines\/16",
\"@type":"Wine",
"id":16,
"wineType":"Fizzy",
"name":"Expert Fizz",
"imageUrl":"\/path\/image.jpg",
"country":"\/api\/v1\/wineyard\/geo\/countries\/1",
"region":"\/api\/v1\/wineyard\/geo\/regions\/4",
"appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
"classification":"Grand cru"
,"color":"Red",
"agriculture":"Organic",
"vintage":2019,
"producer":"Winery X",
"winemaker":"John Doe",
"champagneType":"Blanc de blanc",
"isPrimeurse":true,
"grapeVarieties":"Cabernet Sauvignon, Merlot"
,"alcohol":12.5,
"certification":"AOC",
"sulfites":true,
"allergens":"None",
"nftId":"112928131092248927068419063200851447772005351561113284967211721292196041326920"},
"price":"25.00"}],
"invoiceUrl":"",
"token":"fd08706c18688f1987967b7b2e97c468316eb6ffad4bcc8f7c9dfbdc86bb0e14",
"sellerIsPayer":false,
"description":"",
"currency":"USD",
"selfProvideDeliveryInstruction":false,
"isViewed":false,"
vaultInvoiceId":"3",
"createdAt":"2023-11-24T10:49:35+00:00",
"updatedAt":"2023-11-24T10:49:35+00:00",
"isPresale":true},
{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
"@type":"DigitalPassportTransfer",
"id":4,
"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},
"sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
"sellerEmail":"francois-dupon-owner@vinexquisite.fr",
"buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},
"buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
"buyerEmail":"sophie-lamber-user@lesvignobles.fr",
"deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
"status":"Paid",
"items":[
{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/4",
"@type":"DigitalPassportTransfer\/Item",
"id":4,
"qty":200,
"wine":{"@id":"\/api\/v1\/wineyard\/wines\/18",
"@type":"Wine",
"id":18,
"wineType":"Fizzy"
,"name":"Expert Fizz",
"imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"101093641801119338344520061423789308034636753980811449532935544422296691277908"},"price":"25.00"}],"invoiceUrl":"","token":"6c44055a1f86cd0cd31c76481ad48d528a55485ea3b72ccf7c728572efa867a3","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"4","createdAt":"2023-11-24T12:29:29+00:00","updatedAt":"2023-11-24T12:29:45+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","@type":"DigitalPassportTransfer","id":5,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/5","@type":"DigitalPassportTransfer\/Item","id":5,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/19","@type":"Wine","id":19,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"107156062744607798452426946043254814472167970429597812826128858155685914715434"},"price":"25.00"}],"invoiceUrl":"","token":"f48d5acebdd5de1129f1e3d064c2c5c8fa6ffccbca0b7e97d066a4617e4c7f07","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"5","createdAt":"2023-11-24T12:29:50+00:00","updatedAt":"2023-11-24T12:29:50+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","@type":"DigitalPassportTransfer","id":6,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/6","@type":"DigitalPassportTransfer\/Item","id":6,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/21","@type":"Wine","id":21,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"60529987602596009967768717865654227650848844083787904330061863577447154266135"},"price":"25.00"}],"invoiceUrl":"","token":"a2f34dcc1d711b55f7a50cccaf2e05df25eece21e1e981a26df7966984d3af91","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"6","createdAt":"2023-11-24T13:05:30+00:00","updatedAt":"2023-11-24T13:05:31+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/7","@type":"DigitalPassportTransfer","id":7,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/7","@type":"DigitalPassportTransfer\/Item","id":7,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/22","@type":"Wine","id":22,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"31694494055700966977994043768714847266827567639190274989353632663516697294758"},"price":"25.00"}],"invoiceUrl":"","token":"096b1a3a1838ca303dd72d0ab61869a284f8543947d7a1c08e22864b1f7cc3ab","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"7","createdAt":"2023-11-24T13:05:49+00:00","updatedAt":"2023-11-24T13:05:52+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/8","@type":"DigitalPassportTransfer","id":8,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/8","@type":"DigitalPassportTransfer\/Item","id":8,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/24","@type":"Wine","id":24,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"48575759204787227800045553296594020776266978749535980209436601161762957517543"},"price":"25.00"}],"invoiceUrl":"","token":"2f2c4dd2dd483fbfb5c65f5cecb7da501eb167f1937bfcd6933a321a2a0140f1","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"8","createdAt":"2023-11-26T17:18:04+00:00","updatedAt":"2023-11-26T17:18:04+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/9","@type":"DigitalPassportTransfer","id":9,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/9","@type":"DigitalPassportTransfer\/Item","id":9,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/25","@type":"Wine","id":25,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"58818477932642208965456358942774630121823253220540073130444209183836025084999"},"price":"25.00"}],"invoiceUrl":"","token":"96685a57d95b504cd80177690a14ed21552e64ff97954285d62a8aa82e4f9e5c","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"9","createdAt":"2023-11-26T17:18:22+00:00","updatedAt":"2023-11-26T17:18:26+00:00","isPresale":true}]}

 */
"qty":200,
"wine":{"@id":"\/api\/v1\/wineyard\/wines\/18",
"@type":"Wine",
"id":18,
"wineType":"Fizzy"
,"name":"Expert Fizz",
"imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"101093641801119338344520061423789308034636753980811449532935544422296691277908"},"price":"25.00"}],"invoiceUrl":"","token":"6c44055a1f86cd0cd31c76481ad48d528a55485ea3b72ccf7c728572efa867a3","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"4","createdAt":"2023-11-24T12:29:29+00:00","updatedAt":"2023-11-24T12:29:45+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","@type":"DigitalPassportTransfer","id":5,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/5","@type":"DigitalPassportTransfer\/Item","id":5,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/19","@type":"Wine","id":19,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"107156062744607798452426946043254814472167970429597812826128858155685914715434"},"price":"25.00"}],"invoiceUrl":"","token":"f48d5acebdd5de1129f1e3d064c2c5c8fa6ffccbca0b7e97d066a4617e4c7f07","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"5","createdAt":"2023-11-24T12:29:50+00:00","updatedAt":"2023-11-24T12:29:50+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","@type":"DigitalPassportTransfer","id":6,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/6","@type":"DigitalPassportTransfer\/Item","id":6,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/21","@type":"Wine","id":21,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"60529987602596009967768717865654227650848844083787904330061863577447154266135"},"price":"25.00"}],"invoiceUrl":"","token":"a2f34dcc1d711b55f7a50cccaf2e05df25eece21e1e981a26df7966984d3af91","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"6","createdAt":"2023-11-24T13:05:30+00:00","updatedAt":"2023-11-24T13:05:31+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/7","@type":"DigitalPassportTransfer","id":7,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/7","@type":"DigitalPassportTransfer\/Item","id":7,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/22","@type":"Wine","id":22,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"31694494055700966977994043768714847266827567639190274989353632663516697294758"},"price":"25.00"}],"invoiceUrl":"","token":"096b1a3a1838ca303dd72d0ab61869a284f8543947d7a1c08e22864b1f7cc3ab","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"7","createdAt":"2023-11-24T13:05:49+00:00","updatedAt":"2023-11-24T13:05:52+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/8","@type":"DigitalPassportTransfer","id":8,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/8","@type":"DigitalPassportTransfer\/Item","id":8,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/24","@type":"Wine","id":24,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"48575759204787227800045553296594020776266978749535980209436601161762957517543"},"price":"25.00"}],"invoiceUrl":"","token":"2f2c4dd2dd483fbfb5c65f5cecb7da501eb167f1937bfcd6933a321a2a0140f1","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"8","createdAt":"2023-11-26T17:18:04+00:00","updatedAt":"2023-11-26T17:18:04+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/9","@type":"DigitalPassportTransfer","id":9,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/9","@type":"DigitalPassportTransfer\/Item","id":9,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/25","@type":"Wine","id":25,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"58818477932642208965456358942774630121823253220540073130444209183836025084999"},"price":"25.00"}],"invoiceUrl":"","token":"96685a57d95b504cd80177690a14ed21552e64ff97954285d62a8aa82e4f9e5c","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"9","createdAt":"2023-11-26T17:18:22+00:00","updatedAt":"2023-11-26T17:18:26+00:00","isPresale":true}]}


{"@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassportTransfer","@id":"\/api\/v1\/wineyard\/digital-passport-transfers","@type":"hydra:Collection","hydra:totalItems":9,"hydra:member":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/1","@type":"DigitalPassportTransfer","id":1,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/1","@type":"DigitalPassportTransfer\/Item","id":1,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/14","@type":"Wine","id":14,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":false,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"105406389563013744766365562059207685319364027738301577849808405617260447231127"},"price":"25.00"}],"invoiceUrl":"string","token":"95181efb09ddc479ef7f78c6132f2f492aafd3bca58808c65e3a3b5d68daf76c","sellerIsPayer":false,"description":"string","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"1","createdAt":"2023-11-23T15:57:34+00:00","updatedAt":"2023-11-23T15:58:09+00:00","isPresale":false},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/2","@type":"DigitalPassportTransfer","id":2,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/2","@type":"DigitalPassportTransfer\/Item","id":2,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/15","@type":"Wine","id":15,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"37030394076567731120697325186355463207983127696158393509650430628033960661874"},"price":"25.00"}],"invoiceUrl":"","token":"11eac4d73cc1546b4ab4f06169f82670564500b04a07b07b2a6b4edc20aac5f3","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"2","createdAt":"2023-11-24T10:49:13+00:00","updatedAt":"2023-11-24T10:49:30+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/3","@type":"DigitalPassportTransfer","id":3,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/3","@type":"DigitalPassportTransfer\/Item","id":3,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/16","@type":"Wine","id":16,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"112928131092248927068419063200851447772005351561113284967211721292196041326920"},"price":"25.00"}],"invoiceUrl":"","token":"fd08706c18688f1987967b7b2e97c468316eb6ffad4bcc8f7c9dfbdc86bb0e14","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"3","createdAt":"2023-11-24T10:49:35+00:00","updatedAt":"2023-11-24T10:49:35+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","@type":"DigitalPassportTransfer","id":4,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/4","@type":"DigitalPassportTransfer\/Item","id":4,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/18","@type":"Wine","id":18,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"101093641801119338344520061423789308034636753980811449532935544422296691277908"},"price":"25.00"}],"invoiceUrl":"","token":"6c44055a1f86cd0cd31c76481ad48d528a55485ea3b72ccf7c728572efa867a3","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"4","createdAt":"2023-11-24T12:29:29+00:00","updatedAt":"2023-11-24T12:29:45+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","@type":"DigitalPassportTransfer","id":5,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/5","@type":"DigitalPassportTransfer\/Item","id":5,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/19","@type":"Wine","id":19,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"107156062744607798452426946043254814472167970429597812826128858155685914715434"},"price":"25.00"}],"invoiceUrl":"","token":"f48d5acebdd5de1129f1e3d064c2c5c8fa6ffccbca0b7e97d066a4617e4c7f07","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"5","createdAt":"2023-11-24T12:29:50+00:00","updatedAt":"2023-11-24T12:29:50+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","@type":"DigitalPassportTransfer","id":6,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/6","@type":"DigitalPassportTransfer\/Item","id":6,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/21","@type":"Wine","id":21,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"60529987602596009967768717865654227650848844083787904330061863577447154266135"},"price":"25.00"}],"invoiceUrl":"","token":"a2f34dcc1d711b55f7a50cccaf2e05df25eece21e1e981a26df7966984d3af91","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"6","createdAt":"2023-11-24T13:05:30+00:00","updatedAt":"2023-11-24T13:05:31+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/7","@type":"DigitalPassportTransfer","id":7,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/7","@type":"DigitalPassportTransfer\/Item","id":7,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/22","@type":"Wine","id":22,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"31694494055700966977994043768714847266827567639190274989353632663516697294758"},"price":"25.00"}],"invoiceUrl":"","token":"096b1a3a1838ca303dd72d0ab61869a284f8543947d7a1c08e22864b1f7cc3ab","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"7","createdAt":"2023-11-24T13:05:49+00:00","updatedAt":"2023-11-24T13:05:52+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/8","@type":"DigitalPassportTransfer","id":8,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/8","@type":"DigitalPassportTransfer\/Item","id":8,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/24","@type":"Wine","id":24,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"48575759204787227800045553296594020776266978749535980209436601161762957517543"},"price":"25.00"}],"invoiceUrl":"","token":"2f2c4dd2dd483fbfb5c65f5cecb7da501eb167f1937bfcd6933a321a2a0140f1","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":false,"vaultInvoiceId":"8","createdAt":"2023-11-26T17:18:04+00:00","updatedAt":"2023-11-26T17:18:04+00:00","isPresale":true},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/9","@type":"DigitalPassportTransfer","id":9,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/3","@type":"Organization","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":"\/api\/v1\/wineyard\/contacts\/2","sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/4","@type":"Organization","extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"},"buyerContact":"\/api\/v1\/wineyard\/contacts\/1","buyerEmail":"sophie-lamber-user@lesvignobles.fr","deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00","status":"Paid","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/9","@type":"DigitalPassportTransfer\/Item","id":9,"qty":200,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/25","@type":"Wine","id":25,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","vintage":2019,"producer":"Winery X","winemaker":"John Doe","champagneType":"Blanc de blanc","isPrimeurse":true,"grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None","nftId":"58818477932642208965456358942774630121823253220540073130444209183836025084999"},"price":"25.00"}],"invoiceUrl":"","token":"96685a57d95b504cd80177690a14ed21552e64ff97954285d62a8aa82e4f9e5c","sellerIsPayer":false,"description":"","currency":"USD","selfProvideDeliveryInstruction":false,"isViewed":true,"vaultInvoiceId":"9","createdAt":"2023-11-26T17:18:22+00:00","updatedAt":"2023-11-26T17:18:26+00:00","isPresale":true}]}
 */

/*
{"@context":"\/api\/v1\/wineyard\/contexts\/DeliveryInstruction",
"@id":"\/api\/v1\/wineyard\/delivery-instructions","@type":"hydra:Collection","hydra:totalItems":5,"hydra:member":[{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/1","@type":"DeliveryInstruction","digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/1","items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/1","@type":"DeliveryInstruction\/Item","id":1,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/14","@type":"Wine","id":14,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","producer":"Winery X","champagneType":"Blanc de blanc","grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None"},"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3","@type":"DeliveryInstruction\/Item\/CaseType","id":3,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","materialNameEn":"Wood","materialNameFr":"CB","bottlesQtyInCase":12,"bottleNameEn":"Demie","bottleNameFr":"Demie","totalAmountInLiters":0.375,"bottleSizeLabelEn":"Demie (375 ml)","bottleSizeLabelFr":"Demie (375 ml)","litersPerCase":4.5,"unitEQVBottle":0.5,"totalEQVBottle":6},"casesQty":10,"pregnancyWarning":true,"regie":"ACQ","habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"}}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/1","@type":"https:\/\/schema.org\/PostalAddress","id":1,"typeOfDelivery":"Delivery","deliveryFrom":"2023-10-30T00:00:00+00:00","deliveryTo":"2023-10-30T00:00:00+00:00","addressCountry":"United States","addressLocality":"New York","addressRegion":"NY","postalCode":"10001","streetAddress":"123 Main Street","deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/1"}},{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/2","@type":"DeliveryInstruction","digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/2","items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/2","@type":"DeliveryInstruction\/Item","id":2,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/15","@type":"Wine","id":15,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","producer":"Winery X","champagneType":"Blanc de blanc","grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None"},"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3","@type":"DeliveryInstruction\/Item\/CaseType","id":3,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","materialNameEn":"Wood","materialNameFr":"CB","bottlesQtyInCase":12,"bottleNameEn":"Demie","bottleNameFr":"Demie","totalAmountInLiters":0.375,"bottleSizeLabelEn":"Demie (375 ml)","bottleSizeLabelFr":"Demie (375 ml)","litersPerCase":4.5,"unitEQVBottle":0.5,"totalEQVBottle":6},"casesQty":10,"pregnancyWarning":true,"regie":"ACQ","habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"}}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/13","@type":"https:\/\/schema.org\/PostalAddress","id":13,"typeOfDelivery":"Delivery","deliveryFrom":"2023-10-30T00:00:00+00:00","deliveryTo":"2023-10-30T00:00:00+00:00","addressCountry":"United States","addressLocality":"New York","addressRegion":"NY"
,"postalCode":"10001","streetAddress":"123 Main Street","deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/2"}},{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/3","@type":"DeliveryInstruction","digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/3","@type":"DeliveryInstruction\/Item","id":3,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/18","@type":"Wine","id":18,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","producer":"Winery X","champagneType":"Blanc de blanc","grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None"},"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3","@type":"DeliveryInstruction\/Item\/CaseType","id":3,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","materialNameEn":"Wood","materialNameFr":"CB","bottlesQtyInCase":12,"bottleNameEn":"Demie","bottleNameFr":"Demie","totalAmountInLiters":0.375,"bottleSizeLabelEn":"Demie (375 ml)","bottleSizeLabelFr":"Demie (375 ml)","litersPerCase":4.5,"unitEQVBottle":0.5,"totalEQVBottle":6},"casesQty":10,"pregnancyWarning":true,"regie":"ACQ","habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"}}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/5","@type":"https:\/\/schema.org\/PostalAddress","id":5,"typeOfDelivery":"Delivery","deliveryFrom":"2023-10-30T00:00:00+00:00","deliveryTo":"2023-10-30T00:00:00+00:00","addressCountry":"United States","addressLocality":"New York","addressRegion":"NY","postalCode":"10001","streetAddress":"123 Main Street","deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/3"}},{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/4","@type":"DeliveryInstruction","digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/4","@type":"DeliveryInstruction\/Item","id":4,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/21","@type":"Wine","id":21,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","producer":"Winery X","champagneType":"Blanc de blanc","grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None"},"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3","@type":"DeliveryInstruction\/Item\/CaseType","id":3,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","materialNameEn":"Wood","materialNameFr":"CB","bottlesQtyInCase":12,"bottleNameEn":"Demie","bottleNameFr":"Demie","totalAmountInLiters":0.375,"bottleSizeLabelEn":"Demie (375 ml)","bottleSizeLabelFr":"Demie (375 ml)","litersPerCase":4.5,"unitEQVBottle":0.5,"totalEQVBottle":6},"casesQty":10,"pregnancyWarning":true,"regie":"ACQ","habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"}}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/8","@type":"https:\/\/schema.org\/PostalAddress","id":8,"typeOfDelivery":"Delivery","deliveryFrom":"2023-10-30T00:00:00+00:00","deliveryTo":"2023-10-30T00:00:00+00:00","addressCountry":"United States","addressLocality":"New York","addressRegion":"NY","postalCode":"10001","streetAddress":"123 Main Street","deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/4"}},{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/5","@type":"DeliveryInstruction","digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/8","items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/5","@type":"DeliveryInstruction\/Item","id":5,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/24","@type":"Wine","id":24,"wineType":"Fizzy","name":"Expert Fizz","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/4","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2","classification":"Grand cru","color":"Red","agriculture":"Organic","producer":"Winery X","champagneType":"Blanc de blanc","grapeVarieties":"Cabernet Sauvignon, Merlot","alcohol":12.5,"certification":"AOC","sulfites":true,"allergens":"None"},"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3","@type":"DeliveryInstruction\/Item\/CaseType","id":3,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","materialNameEn":"Wood","materialNameFr":"CB","bottlesQtyInCase":12,"bottleNameEn":"Demie","bottleNameFr":"Demie","totalAmountInLiters":0.375,"bottleSizeLabelEn":"Demie (375 ml)","bottleSizeLabelFr":"Demie (375 ml)","litersPerCase":4.5,"unitEQVBottle":0.5,"totalEQVBottle":6},"casesQty":10,"pregnancyWarning":true,"regie":"ACQ","habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"}}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/11","@type":"https:\/\/schema.org\/PostalAddress","id":11,"typeOfDelivery":"Delivery","deliveryFrom":"2023-10-30T00:00:00+00:00","deliveryTo":"2023-10-30T00:00:00+00:00","addressCountry":"United States","addressLocality":"New York","addressRegion":"NY","postalCode":"10001","streetAddress":"123 Main Street","deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/5"}}]}

 /1
 {"@context":"\/api\/v1\/wineyard\/contexts\/DeliveryInstruction",
 "@id":"\/api\/v1\/wineyard\/delivery-instructions\/1",
 "@type":"DeliveryInstruction",
 "digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/1",
 "items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/1",
        "@type":"DeliveryInstruction\/Item","id":1,
        "wine":{
            "@id":"\/api\/v1\/wineyard\/wines\/14",
            "@type":"Wine",
            "id":14,
            "wineType":"Fizzy",
            "name":"Expert Fizz",
            "imageUrl":"\/path\/image.jpg",
            "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "classification":"Grand cru",
            "color":"Red",
            "agriculture":"Organic",
            "producer":"Winery X",
            "champagneType":"Blanc de blanc",
            "grapeVarieties":"Cabernet Sauvignon, Merlot",
            "alcohol":12.5,
            "certification":"AOC",
            "sulfites":true,
            "allergens":"None"},
         "caseType":{
            "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3",
            "@type":"DeliveryInstruction\/Item\/CaseType","id":3,
            "nameEn":"12 Demie Wood","nameFr":"12 Demie CB",
            "materialNameEn":"Wood",
            "materialNameFr":"CB",
            "bottlesQtyInCase":12,
            "bottleNameEn":"Demie",
            "bottleNameFr":"Demie",
            "totalAmountInLiters":0.375,
            "bottleSizeLabelEn":"Demie (375 ml)",
            "bottleSizeLabelFr":"Demie (375 ml)",
            "litersPerCase":4.5,
            "unitEQVBottle":0.5,
            "totalEQVBottle":6},
         "casesQty":10,
         "pregnancyWarning":true,
         "regie":"ACQ",
         "habillage":{
            "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1",
            "@type":"DeliveryInstruction\/Item\/Habillage",
            "id":1,"name":"English label"}
         }],
         "shippingInformation":{
             "@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/1",
             "@type":"https:\/\/schema.org\/PostalAddress",
             "id":1,
             "typeOfDelivery":"Delivery",
             "deliveryFrom":"2023-10-30T00:00:00+00:00",
             "deliveryTo":"2023-10-30T00:00:00+00:00",
             "addressCountry":"United States",
             "addressLocality":"New York",
             "addressRegion":"NY",
             "postalCode":"10001",
             "streetAddress":"123 Main Street",
             "deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/1"}
 }

  {"@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassportTransfer","@id":"\/api\/v1\/wineyard\/digital-passport-transfers","@type":"hydra:Collection","hydra:totalItems":3,"hydra:member":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/399","@type":"DigitalPassportTransfer","id":399,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/1","@type":"Organization","name":"Vin Exquisite","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":{"@id":"\/api\/v1\/wineyard\/contacts\/3","@type":"Contact","id":3,"person":"francois-dupon-owner","email":"francois-dupon-owner@vinexquisite.fr","phone":"+33 6 78 90 12 34"},"sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerEmail":"tsidorova@scnsoft.com","deliveryInstructionsDueDate":"2024-02-14T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/492","@type":"DigitalPassportTransfer\/Item","id":492,"qty":25,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/211","@type":"Wine","id":211,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2021,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":true,"grapeVarieties":"67% Cabernet Sauvignon \/ 27% Merlot \/ 3% Petit Verdot \/ 3% Cabernet Franc","alcohol":14,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLNOashVh5YJF2"},"price":10,"fullPath":"492","sellLevel":1,"childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/53"],"digitalPassports":[]}],"paymentUrl":"https:\/\/invoice.stripe.com\/i\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDhjclFKSjJsZnE5NDU5WDNWeU1nM0kxaUR4d0RXLDk3MTQyNzg00200326x1lXT?s=ap","token":"43a2bbd2eda1c5a209570eb8d723a02271e5493322f6ed555393a2502fe7a0ad","sellerIsPayer":false,"currency":"EUR","selfProvideDeliveryInstruction":false,"isViewed":true,"deliveryInstruction":{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/399","@type":"DeliveryInstruction","id":399,"items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/63","@type":"DeliveryInstruction\/Item","id":63,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/211","@type":"Wine","id":211,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2021,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":true,"grapeVarieties":"67% Cabernet Sauvignon \/ 27% Merlot \/ 3% Petit Verdot \/ 3% Cabernet Franc","alcohol":14,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLNOashVh5YJF2"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/Item\/CaseType","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},"qty":2,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/Item\/Regie","id":1,"name":"ACQ"},"habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/2","@type":"DeliveryInstruction\/Item\/Habillage","id":2,"name":"French label"},"withCase":true,"bottlesQty":2,"bottleVolume":4.5},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/68","@type":"DeliveryInstruction\/Item","id":68,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/211","@type":"Wine","id":211,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2021,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":true,"grapeVarieties":"67% Cabernet Sauvignon \/ 27% Merlot \/ 3% Petit Verdot \/ 3% Cabernet Franc","alcohol":14,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLNOashVh5YJF2"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/Item\/CaseType","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},"qty":1,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/2","@type":"DeliveryInstruction\/Item\/Regie","id":2,"name":"CRD"},"withCase":true,"bottlesQty":1,"bottleVolume":4.5}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/90","@type":"https:\/\/schema.org\/PostalAddress"}},"stripeInvoiceId":"in_1OeCKnFSrxJ11FojddYYITDb","createdAt":"2024-01-30T08:06:20+00:00","updatedAt":"2024-01-30T08:06:24+00:00","invoiceDownloadUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDhjclFKSjJsZnE5NDU5WDNWeU1nM0kxaUR4d0RXLDk3MTQyNzg00200326x1lXT\/pdf?s=ap","isPresale":true,"isCancelled":false,"isIncoming":false},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/401","@type":"DigitalPassportTransfer","id":401,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/1","@type":"Organization","name":"Vin Exquisite","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":{"@id":"\/api\/v1\/wineyard\/contacts\/3","@type":"Contact","id":3,"person":"francois-dupon-owner","email":"francois-dupon-owner@vinexquisite.fr","phone":"+33 6 78 90 12 34"},"sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerEmail":"tsidorova@scnsoft.com","deliveryInstructionsDueDate":"2024-02-06T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/494","@type":"DigitalPassportTransfer\/Item","id":494,"qty":25,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/212","@type":"Wine","id":212,"wineType":"Still","name":"Château Moulin Riche","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2020,"producer":"Château Moulin Riche","winemaker":"Château Moulin Riche","isPrimeurse":false,"grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot","alcohol":13,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PM3tdbLZvO3NB0","domaine":"Château Moulin Riche"},"price":10,"fullPath":"494","sellLevel":1,"childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/55"],"digitalPassports":[]}],"paymentUrl":"https:\/\/invoice.stripe.com\/i\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDlJRXh6RVVBZElsQVZ3Tk9YWWdsS1RvNkt5RGI0LDk3MTQ1MzMw0200Ls4cthYa?s=ap","token":"6f4788b0cf16a99dff57a72c1f4200baf09f58a2f67472c86227fce986534b16","sellerIsPayer":false,"currency":"EUR","selfProvideDeliveryInstruction":false,"isViewed":true,"deliveryInstruction":{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/401","@type":"DeliveryInstruction","id":401,"items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/64","@type":"DeliveryInstruction\/Item","id":64,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/212","@type":"Wine","id":212,"wineType":"Still","name":"Château Moulin Riche","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2020,"producer":"Château Moulin Riche","winemaker":"Château Moulin Riche","isPrimeurse":false,"grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot","alcohol":13,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PM3tdbLZvO3NB0","domaine":"Château Moulin Riche"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4},"qty":6,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/Item\/Regie","id":1,"name":"ACQ"},"habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"},"reference":"1ghjghj","withCase":false,"bottlesQty":6,"bottleVolume":3}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/85","@type":"https:\/\/schema.org\/PostalAddress"}},"stripeInvoiceId":"in_1OeCzqFSrxJ11FojAHhaTxfP","createdAt":"2024-01-30T08:48:46+00:00","updatedAt":"2024-01-30T08:48:52+00:00","invoiceDownloadUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDlJRXh6RVVBZElsQVZ3Tk9YWWdsS1RvNkt5RGI0LDk3MTQ1MzMw0200Ls4cthYa\/pdf?s=ap","isPresale":false,"isCancelled":false,"isIncoming":false},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/402","@type":"DigitalPassportTransfer","id":402,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/1","@type":"Organization","name":"Vin Exquisite","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":{"@id":"\/api\/v1\/wineyard\/contacts\/3","@type":"Contact","id":3,"person":"francois-dupon-owner","email":"francois-dupon-owner@vinexquisite.fr","phone":"+33 6 78 90 12 34"},"sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerEmail":"tsidorova@scnsoft.com","deliveryInstructionsDueDate":"2024-02-06T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/495","@type":"DigitalPassportTransfer\/Item","id":495,"qty":10,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/210","@type":"Wine","id":210,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2014,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":false,"grapeVarieties":"60% Cabernet Sauvignon \/ 35% Merlot \/ 2% Petit Verdot \/ 3% Cabernet Franc","alcohol":13.5,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLje8hLWnziZ0W","domaine":"Château Léoville Poyferré"},"price":25,"fullPath":"495","sellLevel":1,"childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/56"],"digitalPassports":[]},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/496","@type":"DigitalPassportTransfer\/Item","id":496,"qty":10,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/212","@type":"Wine","id":212,"wineType":"Still","name":"Château Moulin Riche","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2020,"producer":"Château Moulin Riche","winemaker":"Château Moulin Riche","isPrimeurse":false,"grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot","alcohol":13,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PM3tdbLZvO3NB0","domaine":"Château Moulin Riche"},"price":74,"fullPath":"496","sellLevel":1,"childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/57"],"digitalPassports":[]}],"paymentUrl":"https:\/\/invoice.stripe.com\/i\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDlLa2JTS3BQa2RaSjl3akRtdkpkaXFBYVhzTVFhLDk3MTQ1NDgw0200VtDikcKl?s=ap","token":"05a982e9ba8e5c82174118dd57f7e0c521084b2eae8b0b528438afd8a7171ebd","sellerIsPayer":false,"currency":"EUR","selfProvideDeliveryInstruction":false,"isViewed":true,"deliveryInstruction":{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/402","@type":"DeliveryInstruction","id":402,"items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/65","@type":"DeliveryInstruction\/Item","id":65,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/210","@type":"Wine","id":210,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2014,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":false,"grapeVarieties":"60% Cabernet Sauvignon \/ 35% Merlot \/ 2% Petit Verdot \/ 3% Cabernet Franc","alcohol":13.5,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLje8hLWnziZ0W","domaine":"Château Léoville Poyferré"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/Item\/CaseType","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},"qty":2,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/Item\/Regie","id":1,"name":"ACQ"},"habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/2","@type":"DeliveryInstruction\/Item\/Habillage","id":2,"name":"French label"},"withCase":true,"bottlesQty":2,"bottleVolume":3},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/66","@type":"DeliveryInstruction\/Item","id":66,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/212","@type":"Wine","id":212,"wineType":"Still","name":"Château Moulin Riche","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2020,"producer":"Château Moulin Riche","winemaker":"Château Moulin Riche","isPrimeurse":false,"grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot","alcohol":13,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PM3tdbLZvO3NB0","domaine":"Château Moulin Riche"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/Item\/CaseType","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},"qty":1,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/Item\/Regie","id":1,"name":"ACQ"},"habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"},"withCase":true,"bottlesQty":12,"bottleVolume":0.5}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/87","@type":"https:\/\/schema.org\/PostalAddress"}},"stripeInvoiceId":"in_1OeD2FFSrxJ11FojfmefkflV","createdAt":"2024-01-30T08:51:15+00:00","updatedAt":"2024-01-30T08:51:21+00:00","invoiceDownloadUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDlLa2JTS3BQa2RaSjl3akRtdkpkaXFBYVhzTVFhLDk3MTQ1NDgw0200VtDikcKl\/pdf?s=ap","isPresale":false,"isCancelled":false,"isIncoming":false}]}
 */