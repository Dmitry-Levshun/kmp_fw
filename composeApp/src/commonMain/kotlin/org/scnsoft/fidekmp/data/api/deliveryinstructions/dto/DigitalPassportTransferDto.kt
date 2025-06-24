package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitalPassportTransferDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Int,
    @SerialName("hydra:member")
    val hydraMember: List<DigitalPassportTransfer>,
)

@Serializable
data class DigitalPassportTransfer(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val sellerOrganization: DptOrganization,
    val sellerContact: DptContact?,
    val sellerEmail: String,
    val buyerOrganization: DptOrganization?,
    val buyerContact: DptContact?,
    val buyerEmail: String,
    val deliveryInstructionsDueDate: LocalDate, //String,
    val status: String,
    val items: List<DigitalPassportTransferItem>,
    val invoiceUrl: String?,
    val paymentUrl: String?,
    val invoiceDownloadUrl: String?,
    val token: String,
    val sellerIsPayer: Boolean,
    val description: String,
    val currency: String,
    val selfProvideDeliveryInstruction: Boolean,
    val isViewed: Boolean,
    val deliveryInstruction: DptDeliveryInstruction,
    val stripeInvoiceId: String,
    val vaultInvoiceId: String,
    val createdAt: LocalDate, //String,
    val updatedAt: LocalDate, //String
    val isPresale: Boolean,
    val isIncoming: Boolean,
    val isCancelled: Boolean,
    val isPaid: Boolean,
    val isCompleted: Boolean,
    val isAborted: Boolean,
    val isBuyer: Boolean,
) {
    val statusEnum: DigitalPassportTransferStatus get() = DigitalPassportTransferStatus.fromValue(status)
    val isSubmitted: Boolean get() = (statusEnum in DigitalPassportTransferStatus.Submitted .. DigitalPassportTransferStatus.SubmittedNoTransfer)
    val isShowAble: Boolean get() = (statusEnum in DigitalPassportTransferStatus.Submitted .. DigitalPassportTransferStatus.CompletedNoTransfer)
}

@Serializable
enum class DigitalPassportTransferStatus(val value: String) {
    PendingApproval("Pending Approval"),
    PendingPayment("Pending Payment"),
    Paid("Paid"),
    Approved("Approved"),
    RejectedPassport("Rejected Passport"),
    Closed("Closed"),
    Submitted("Submitted"),
    SubmittedNoTransfer("Submitted no Transfer"),
    Completed("Completed"),
    CompletedNoTransfer("Completed no Transfer");
    companion object {
        fun fromValue(value: String): DigitalPassportTransferStatus = when (value) {
            "Pending Approval" -> PendingApproval
            "Pending Payment" -> PendingPayment
            "Paid" -> Paid
            "Approved" -> Approved
            "Rejected Passport" -> RejectedPassport
            "Closed" -> Closed
            "Submitted" -> Submitted
            "Submitted no Transfer" -> SubmittedNoTransfer
            "Completed" -> Completed
            "Completed no Transfer" -> CompletedNoTransfer

            "Soumis" -> Submitted
            "Fermé" -> Closed
            "Approuvé" -> Approved
            "Passeport rejeté" -> RejectedPassport
            "Payé" -> Paid
            "En attente de paiement" -> PendingPayment
            "En attente d'approbation" -> PendingApproval
            "Complété"  -> Completed
            "Soumis sans transfert" -> SubmittedNoTransfer
            "Complété sans transfert" -> CompletedNoTransfer
            else -> throw IllegalArgumentException("Invalid value: $value")
        }
    }
}

@Serializable
data class DptContact(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val id: Int,
    val person: String?,
    val email: String?,
    val phone: String?
)

@Serializable
data class DptOrganization(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val name: String,
    val address: String?,
    val extOrganizationId: String,
)

@Serializable
data class DigitalPassportTransferItem(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val qty: Int,
    val wine: DigitalPasswordTransferWine,
    val price: String,
    val passportPrice: Double,
    val nftId: String?,
    val parentNftId: String?,
)

@Serializable
data class DigitalPasswordTransferWine(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val wineType: String,
    val name: String,
    val country: String,
    val region: String,
    val appellation: String,
    val classification: String?,
    val color: String,
    val agriculture: String?,
    val vintage: Int,
    val producer: String,
    val champagneType: String,
    val isPrimeurse: Boolean,
    val grapeVarieties: String,
    val alcohol: Double,
//    @SerialName("certification")
//    val certificationSt: String?,
    @SerialName("certification")
    val certification: List<String>?,
    val sulfites: Boolean,
    val allergens: String?,
    val nftId: String,
    val bottleAmount: Int?,
    val images: List<WineImage>?,
//    val passportPrice: Double,
    val stripeProductId: String?,
    @SerialName("domaine")
    val domaine: DigitalPasswordTransferDomain,
    val packages: List<DptDeliveryInstructionItemCaseType>,
    val originalPrice: Double,
) {
    val domain: String get() = domaine.name
    val imageUrl: String? get() = if (!images.isNullOrEmpty()) images.first().contentUrl else null
//    val certification: String get() = if (!certificationL.isNullOrEmpty()) certificationL[0] else ""
}

@Serializable
data class DigitalPasswordTransferDomain(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val name: String
)

@Serializable
data class DptDeliveryInstruction(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val items: List<DptDeliveryInstructionItem>,
    val shippingInformation: DptDeliveryInstructionShippingInformation?,
)

@Serializable
data class DptDeliveryInstructionShippingInformation(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val typeOfDelivery: String?,
    val dateFrom: LocalDate?,
    val dateTo: LocalDate?,
    val storagePartner: String,
)

@Serializable
data class DptDeliveryInstructionItem(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val wine: DptDeliveryInstructionItemWine,
    @SerialName("type")
    val caseType: DptDeliveryInstructionItemCaseType?,
    val bottleType: DptDeliveryInstructionItemBottleType?,
    var qty: Int, // if withCase num of cases
    val pregnancyWarning: Boolean,
    val regie: DptDeliveryInstructionItemCaseTypeRegie?,
    val habillage: DptDeliveryInstructionItemHabillage?,
    val withCase : Boolean,
    val bottlesQty: Int,
    val isProcessed: Boolean,
    val comments: String?,
    val reference: String?,
    var dptId: Int = 0,
    var processedBottleQty: Int = 0
)

@Serializable
data class DptDeliveryInstructionItemWine(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val wineType: String,
    val name: String,
    val country: String,
    val region: String,
    val appellation: String,
    val classification: String?,
    val color: String,
    val agriculture: String?,
    val vintage: Int,
    val producer: String,
    val winemaker: String,
    val champagneType: String,
    val isPrimeurse: Boolean,
    val grapeVarieties: String,
    val alcohol: Double,
    @SerialName("certification")
    val certificationL: List<String>?,
    val sulfites: Boolean,
    val allergens: String?,
    val nftId: String,
    val bottleVolume: Double,
    val stripeProductId: String,
    val images: List<WineImage>?,
    @SerialName("domaine")
    val domaine: DigitalPasswordTransferDomain
) {
    val domain: String get() = domaine.name
    val imageUrl: String? get() = if (!images.isNullOrEmpty()) images.first().contentUrl else null
    val certification: String get() = if (!certificationL.isNullOrEmpty()) "${certificationL[0]}..." else ""

}

@Serializable
data class DptDeliveryInstructionItemCaseType(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val nameEn: String,
    val nameFr: String,
    @SerialName("typeNameEn")
    val materialNameEn: String,
    @SerialName("typeNameFr")
    val materialNameFr: String,
    val bottleNameEn: String,
    val bottleNameFr: String,
    val amountInLiters: Double,
    val bottleSizeLabelEn: String?,
    val bottleSizeLabelFr: String?,
    val litersPerCase: Double?,
    @SerialName("eqvBottle")
    val eqvBottle: Double,
    val bottlesQty: Int,
    val bottleType: DptDeliveryInstructionItemBottleType?,
    var lang: String = "en",
){
    val name: String get() = if (lang == "fr") nameFr else nameEn
    val materialName get() = if (lang == "fr") materialNameFr else materialNameEn
    val bottleSizeLabel get() = if (lang == "fr") bottleSizeLabelFr else bottleSizeLabelEn
}

@Serializable
data class DptDeliveryInstructionItemBottleType(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val nameEn: String,
    val nameFr: String,
    val typeNameEn: String,
    val typeNameFr: String,
    val amountInLiters: Double,
    val eqvBottle: Double,
    var lang: String = "en",
){
    val name: String get() = if (lang == "fr") nameFr else nameEn
    val typeName get() = if (lang == "fr") typeNameFr else typeNameEn
}

@Serializable
data class DptDeliveryInstructionItemCaseTypeRegie(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val name: String,
)

@Serializable
data class DptDeliveryInstructionItemHabillage(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val name: String?,
)

//-----------------------

@Serializable
data class CreateDigitalPassportTransferRequestDto(
    val deliveryInstructionsDueDate: String, //"2023-11-17T10:26:24.839Z",
    val domainName: String,
    val selfProvideDeliveryInstruction: Boolean,
    val organizationId: String?, //"/api/v1/wineyard/organizations/1",
    val recipientId: String?,  //"/api/v1/wineyard/organizations/2",
    val sellerIsPayeer: Boolean, //false,
    val isGift: Boolean, //false,
    val buyerEmail: String, //"fidewinebuyer@gmail.com",
    val items: List<CreateDigitalPassportTransferRequestWine>,
    val currency: String // "EUR"
)

@Serializable
data class CreateDigitalPassportTransferRequestWine(
    val qty: Int, //": 1,
    val wine: String, //"/api/v1/wineyard/wines/3",
    val price: String,  //"365"
    val passportPrice: Double
)

/*
26.07.24
{"@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassportTransfer","@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/631","@type":"DigitalPassportTransfer","id":631,"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/291","@type":"https:\/\/schema.org\/PostalAddress","id":291,"typeOfDelivery":"No Delivery","storagePartner":"liv"},"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/35","@type":"Organization","name":"TestTanka","extOrganizationId":"4cfa2aa1-37c7-43f2-8007-437387b10e80"},"sellerContact":{"@id":"\/api\/v1\/wineyard\/contacts\/27","@type":"Contact","id":27,"email":"tsidorova@scnsoft.com","phone":"345345353453","person":"tan Winemaker"},"sellerEmail":"tsidorova@scnsoft.com","buyerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/48","@type":"Organization","name":"testTestinka","extOrganizationId":"3f659b74-c4fb-42de-a9f6-59c8e1475bfd"},"buyerContact":{"@id":"\/api\/v1\/wineyard\/contacts\/45","@type":"Contact","id":45,"email":"sharsu714+3@gmail.com","phone":"456456456456","person":"er br"},"buyerEmail":"sharsu714+3@gmail.com","deliveryInstructionsDueDate":"2024-09-28T00:00:00+00:00","status":"Completed","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/838","@type":"DigitalPassportTransfer\/Item","id":838,"qty":33,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sans que son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"price":0,"fullPath":"838","sellLevel":1,"childItems":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/872","@type":"DigitalPassportTransfer\/Item","id":872,"qty":18,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sans que son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"price":0,"fullPath":"838\/872","sellLevel":2,"parent":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/838","childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/2395"],"digitalPassports":[],"passportPrice":3.36},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/876","@type":"DigitalPassportTransfer\/Item","id":876,"qty":15,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sans que son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"price":0,"fullPath":"838\/876","sellLevel":2,"parent":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/838","childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/2399"],"digitalPassports":[],"passportPrice":3.36}],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/2214"],"digitalPassports":[],"passportPrice":3.36}],"token":"a6da8dd1b002ca6ef806f3572d42b5e76e053c04a8dc1cea5e1222184d2a9701","sellerIsPayer":false,"currency":"EUR","isViewed":true,"deliveryInstruction":{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/631","@type":"DeliveryInstruction","id":631,"items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/888","@type":"DeliveryInstruction\/Item","id":888,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sans que son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},"qty":1,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/BottlesAndCases","id":1,"name":"ACQ"},"isProcessed":false,"withCase":true,"bottlesQty":12,"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},"bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/889","@type":"DeliveryInstruction\/Item","id":889,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sans que son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1},"qty":3,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"name":"CRD"},"isProcessed":false,"withCase":false,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1},"bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/828","@type":"DeliveryInstruction\/Item","id":828,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sans que son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},"qty":2,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/BottlesAndCases","id":1,"name":"ACQ"},"reference":"","comments":"","isProcessed":true,"withCase":true,"bottlesQty":12,"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},"bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/831","@type":"DeliveryInstruction\/Item","id":831,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sans que son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4},"qty":2,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"name":"CRD"},"reference":"","comments":"","isProcessed":true,"withCase":false,"bottlesQty":2,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4},"bottleVolume":3},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/830","@type":"DeliveryInstruction\/Item","id":830,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sansque son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1},"qty":1,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"name":"CRD"},"reference":"","comments":"","isProcessed":true,"withCase":false,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1},"bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/829","@type":"DeliveryInstruction\/Item","id":829,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/88","@type":"Wine","id":88,"wineType":"Still","name":"WineTan RoseAnj","images":[],"country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/16","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/1","color":"Rose","vintage":2025,"isPrimeurse":true,"grapeVarieties":"grape12, grape45, grapeqa","alcohol":58,"stripeProductId":"prod_QRtvWijcqwx75p","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/8","@type":"Domain","name":"Domain de VinTan"},"description":[{"language":"FR","text":"Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l\u0027imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n\u0027a pas fait que survivre cinq siècles, mais s\u0027est aussi adapté à la bureautique informatique, sans que son contenu n\u0027en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker."}],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/BottlesAndCases","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/BottlesAndCases","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/BottlesAndCases","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/BottlesAndCases","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/BottlesAndCases","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/BottlesAndCases","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/BottlesAndCases","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/BottlesAndCases","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/BottlesAndCases","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/BottlesAndCases","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/BottlesAndCases","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/BottlesAndCases","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/BottlesAndCases","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/BottlesAndCases","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/54","@type":"DeliveryInstruction\/BottlesAndCases","id":54,"nameEn":"CUSTOME TEST","nameFr":"CUSTOME TEST","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":33.75,"eqvBottle":45,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}}],"originalPrice":84},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},"qty":1,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/BottlesAndCases","id":1,"name":"ACQ"},"reference":"","comments":"","isProcessed":true,"withCase":true,"bottlesQty":12,"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/BottlesAndCases","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/BottlesAndCases","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},"bottleVolume":0.75}]},"createdAt":"2024-07-10T16:16:05+00:00","updatedAt":"2024-07-11T10:39:22+00:00","isPresale":true,"isPaid":false,"isCompleted":true,"isAborted":false,"isBuyer":false,"submitted":false,"isIncoming":false}

"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/1","@type":"https:\/\/schema.org\/PostalAddress"}},"vaultInvoiceId":"1","stripeInvoiceId":"in_1OL0ohFSrxJ11FojvIZVWMlu","createdAt":"2023-12-08T09:57:54+00:00","updatedAt":"2023-12-11T12:32:46+00:00","isPresale":false,"isCancelled":false,"isIncoming":false}
{
    "@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassportTransfer",
    "@id":"\/api\/v1\/wineyard\/digital-passport-transfers",
    "@type":"hydra:Collection",
    "hydra:totalItems":9,
    "hydra:member":[
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/1",
            "@type":"DigitalPassportTransfer",
            "id":1,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Paid",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/1",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":1,
                    "qty":200,
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
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":false,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"105406389563013744766365562059207685319364027738301577849808405617260447231127"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"string",
            "token":"95181efb09ddc479ef7f78c6132f2f492aafd3bca58808c65e3a3b5d68daf76c",
            "sellerIsPayer":false,
            "description":"string",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":false,
            "vaultInvoiceId":"1",
            "createdAt":"2023-11-23T15:57:34+00:00",
            "updatedAt":"2023-11-23T15:58:09+00:00",
            "isPresale":false
        },
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/2",
            "@type":"DigitalPassportTransfer",
            "id":2,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Paid",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/2",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":2,
                    "qty":200,
                    "wine":{
                        "@id":"\/api\/v1\/wineyard\/wines\/15",
                        "@type":"Wine",
                        "id":15,
                        "wineType":"Fizzy",
                        "name":"Expert Fizz",
                        "imageUrl":"\/path\/image.jpg",
                        "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
                        "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
                        "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
                        "classification":"Grand cru",
                        "color":"Red",
                        "agriculture":"Organic",
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":true,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"37030394076567731120697325186355463207983127696158393509650430628033960661874"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"",
            "token":"11eac4d73cc1546b4ab4f06169f82670564500b04a07b07b2a6b4edc20aac5f3",
            "sellerIsPayer":false,
            "description":"",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":true,
            "vaultInvoiceId":"2",
            "createdAt":"2023-11-24T10:49:13+00:00",
            "updatedAt":"2023-11-24T10:49:30+00:00",
            "isPresale":true
        },
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/3",
            "@type":"DigitalPassportTransfer",
            "id":3,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Pending Payment",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/3",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":3,
                    "qty":200,
                    "wine":{
                        "@id":"\/api\/v1\/wineyard\/wines\/16",
                        "@type":"Wine",
                        "id":16,
                        "wineType":"Fizzy",
                        "name":"Expert Fizz",
                        "imageUrl":"\/path\/image.jpg",
                        "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
                        "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
                        "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
                        "classification":"Grand cru",
                        "color":"Red",
                        "agriculture":"Organic",
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":true,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"112928131092248927068419063200851447772005351561113284967211721292196041326920"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"",
            "token":"fd08706c18688f1987967b7b2e97c468316eb6ffad4bcc8f7c9dfbdc86bb0e14",
            "sellerIsPayer":false,
            "description":"",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":false,
            "vaultInvoiceId":"3",
            "createdAt":"2023-11-24T10:49:35+00:00",
            "updatedAt":"2023-11-24T10:49:35+00:00",
            "isPresale":true
        },
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
            "@type":"DigitalPassportTransfer",
            "id":4,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Paid",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/4",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":4,
                    "qty":200,
                    "wine":{
                        "@id":"\/api\/v1\/wineyard\/wines\/18",
                        "@type":"Wine",
                        "id":18,
                        "wineType":"Fizzy",
                        "name":"Expert Fizz",
                        "imageUrl":"\/path\/image.jpg",
                        "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
                        "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
                        "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
                        "classification":"Grand cru",
                        "color":"Red",
                        "agriculture":"Organic",
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":true,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"101093641801119338344520061423789308034636753980811449532935544422296691277908"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"",
            "token":"6c44055a1f86cd0cd31c76481ad48d528a55485ea3b72ccf7c728572efa867a3",
            "sellerIsPayer":false,
            "description":"",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":true,
            "vaultInvoiceId":"4",
            "createdAt":"2023-11-24T12:29:29+00:00",
            "updatedAt":"2023-11-24T12:29:45+00:00",
            "isPresale":true
        },
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
            "@type":"DigitalPassportTransfer",
            "id":5,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Pending Payment",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/5",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":5,
                    "qty":200,
                    "wine":{
                        "@id":"\/api\/v1\/wineyard\/wines\/19",
                        "@type":"Wine",
                        "id":19,
                        "wineType":"Fizzy",
                        "name":"Expert Fizz",
                        "imageUrl":"\/path\/image.jpg",
                        "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
                        "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
                        "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
                        "classification":"Grand cru",
                        "color":"Red",
                        "agriculture":"Organic",
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":true,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"107156062744607798452426946043254814472167970429597812826128858155685914715434"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"",
            "token":"f48d5acebdd5de1129f1e3d064c2c5c8fa6ffccbca0b7e97d066a4617e4c7f07",
            "sellerIsPayer":false,
            "description":"",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":false,
            "vaultInvoiceId":"5",
            "createdAt":"2023-11-24T12:29:50+00:00",
            "updatedAt":"2023-11-24T12:29:50+00:00",
            "isPresale":true
        },
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
            "@type":"DigitalPassportTransfer",
            "id":6,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Paid",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/6",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":6,
                    "qty":200,
                    "wine":{
                        "@id":"\/api\/v1\/wineyard\/wines\/21",
                        "@type":"Wine",
                        "id":21,
                        "wineType":"Fizzy",
                        "name":"Expert Fizz",
                        "imageUrl":"\/path\/image.jpg",
                        "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
                        "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
                        "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
                        "classification":"Grand cru",
                        "color":"Red",
                        "agriculture":"Organic",
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":true,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"60529987602596009967768717865654227650848844083787904330061863577447154266135"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"",
            "token":"a2f34dcc1d711b55f7a50cccaf2e05df25eece21e1e981a26df7966984d3af91",
            "sellerIsPayer":false,
            "description":"",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":false,
            "vaultInvoiceId":"6",
            "createdAt":"2023-11-24T13:05:30+00:00",
            "updatedAt":"2023-11-24T13:05:31+00:00",
            "isPresale":true
        },
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/7",
            "@type":"DigitalPassportTransfer",
            "id":7,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Paid",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/7",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":7,
                    "qty":200,
                    "wine":{
                        "@id":"\/api\/v1\/wineyard\/wines\/22",
                        "@type":"Wine",
                        "id":22,
                        "wineType":"Fizzy",
                        "name":"Expert Fizz",
                        "imageUrl":"\/path\/image.jpg",
                        "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
                        "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
                        "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
                        "classification":"Grand cru",
                        "color":"Red",
                        "agriculture":"Organic",
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":true,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"31694494055700966977994043768714847266827567639190274989353632663516697294758"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"",
            "token":"096b1a3a1838ca303dd72d0ab61869a284f8543947d7a1c08e22864b1f7cc3ab",
            "sellerIsPayer":false,
            "description":"",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":true,
            "vaultInvoiceId":"7",
            "createdAt":"2023-11-24T13:05:49+00:00",
            "updatedAt":"2023-11-24T13:05:52+00:00",
            "isPresale":true
        },
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/8",
            "@type":"DigitalPassportTransfer",
            "id":8,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Paid",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/8",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":8,
                    "qty":200,
                    "wine":{
                        "@id":"\/api\/v1\/wineyard\/wines\/24",
                        "@type":"Wine",
                        "id":24,
                        "wineType":"Fizzy",
                        "name":"Expert Fizz",
                        "imageUrl":"\/path\/image.jpg",
                        "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
                        "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
                        "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
                        "classification":"Grand cru",
                        "color":"Red",
                        "agriculture":"Organic",
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":true,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"48575759204787227800045553296594020776266978749535980209436601161762957517543"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"",
            "token":"2f2c4dd2dd483fbfb5c65f5cecb7da501eb167f1937bfcd6933a321a2a0140f1",
            "sellerIsPayer":false,
            "description":"",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":false,
            "vaultInvoiceId":"8",
            "createdAt":"2023-11-26T17:18:04+00:00",
            "updatedAt":"2023-11-26T17:18:04+00:00",
            "isPresale":true
        },
        {
            "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/9",
            "@type":"DigitalPassportTransfer",
            "id":9,
            "sellerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/3",
                "@type":"Organization",
                "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
            },
            "sellerContact":"\/api\/v1\/wineyard\/contacts\/2",
            "sellerEmail":"francois-dupon-owner@vinexquisite.fr",
            "buyerOrganization":{
                "@id":"\/api\/v1\/wineyard\/organizations\/4",
                "@type":"Organization",
                "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
            },
            "buyerContact":"\/api\/v1\/wineyard\/contacts\/1",
            "buyerEmail":"sophie-lamber-user@lesvignobles.fr",
            "deliveryInstructionsDueDate":"2024-10-31T00:00:00+00:00",
            "status":"Paid",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/9",
                    "@type":"DigitalPassportTransfer\/Item",
                    "id":9,
                    "qty":200,
                    "wine":{
                        "@id":"\/api\/v1\/wineyard\/wines\/25",
                        "@type":"Wine",
                        "id":25,
                        "wineType":"Fizzy",
                        "name":"Expert Fizz",
                        "imageUrl":"\/path\/image.jpg",
                        "country":"\/api\/v1\/wineyard\/geo\/countries\/1",
                        "region":"\/api\/v1\/wineyard\/geo\/regions\/4",
                        "appellation":"\/api\/v1\/wineyard\/geo\/appellations\/2",
                        "classification":"Grand cru",
                        "color":"Red",
                        "agriculture":"Organic",
                        "vintage":2019,
                        "producer":"Winery X",
                        "winemaker":"John Doe",
                        "champagneType":"Blanc de blanc",
                        "isPrimeurse":true,
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None",
                        "nftId":"58818477932642208965456358942774630121823253220540073130444209183836025084999"
                    },
                    "price":"25.00"
                }
            ],
            "invoiceUrl":"",
            "token":"96685a57d95b504cd80177690a14ed21552e64ff97954285d62a8aa82e4f9e5c",
            "sellerIsPayer":false,
            "description":"",
            "currency":"USD",
            "selfProvideDeliveryInstruction":false,
            "isViewed":true,
            "vaultInvoiceId":"9",
            "createdAt":"2023-11-26T17:18:22+00:00",
            "updatedAt":"2023-11-26T17:18:26+00:00",
            "isPresale":true
        }
    ]
}

{"@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassportTransfer","@id":"\/api\/v1\/wineyard\/digital-passport-transfers","@type":"hydra:Collection","hydra:totalItems":3,"hydra:member":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/401","@type":"DigitalPassportTransfer","id":401,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/1","@type":"Organization","name":"Vin Exquisite","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":{"@id":"\/api\/v1\/wineyard\/contacts\/3","@type":"Contact","id":3,"person":"francois-dupon-owner","email":"francois-dupon-owner@vinexquisite.fr","phone":"+33 6 78 90 12 34"},"sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerEmail":"tsidorova@scnsoft.com","deliveryInstructionsDueDate":"2024-02-06T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/494","@type":"DigitalPassportTransfer\/Item","id":494,"qty":25,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/212","@type":"Wine","id":212,"wineType":"Still","name":"Château Moulin Riche","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2020,"producer":"Château Moulin Riche","winemaker":"Château Moulin Riche","isPrimeurse":false,"grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot","alcohol":13,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PM3tdbLZvO3NB0","domaine":"Château Moulin Riche"},"price":10,"fullPath":"494","sellLevel":1,"childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/55"],"digitalPassports":[]}],"paymentUrl":"https:\/\/invoice.stripe.com\/i\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDlJRXh6RVVBZElsQVZ3Tk9YWWdsS1RvNkt5RGI0LDk3MTQ1MzMw0200Ls4cthYa?s=ap","token":"6f4788b0cf16a99dff57a72c1f4200baf09f58a2f67472c86227fce986534b16","sellerIsPayer":false,"currency":"EUR","selfProvideDeliveryInstruction":false,"isViewed":true,"deliveryInstruction":{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/401","@type":"DeliveryInstruction","id":401,"items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/64","@type":"DeliveryInstruction\/Item","id":64,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/212","@type":"Wine","id":212,"wineType":"Still","name":"Château Moulin Riche","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2020,"producer":"Château Moulin Riche","winemaker":"Château Moulin Riche","isPrimeurse":false,"grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot","alcohol":13,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PM3tdbLZvO3NB0","domaine":"Château Moulin Riche"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4},"qty":6,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/Item\/Regie","id":1,"name":"ACQ"},"habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"},"reference":"1ghjghj","isProcessed":false,"withCase":false,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4},"bottleVolume":3}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/85","@type":"https:\/\/schema.org\/PostalAddress"}},"stripeInvoiceId":"in_1OeCzqFSrxJ11FojAHhaTxfP","createdAt":"2024-01-30T08:48:46+00:00","updatedAt":"2024-01-30T08:48:52+00:00","invoiceDownloadUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDlJRXh6RVVBZElsQVZ3Tk9YWWdsS1RvNkt5RGI0LDk3MTQ1MzMw0200Ls4cthYa\/pdf?s=ap","isPresale":false,"isCancelled":false,"isPaid":false,"isCompleted":false,"isAborted":false,"isIncoming":false},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/402","@type":"DigitalPassportTransfer","id":402,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/1","@type":"Organization","name":"Vin Exquisite","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":{"@id":"\/api\/v1\/wineyard\/contacts\/3","@type":"Contact","id":3,"person":"francois-dupon-owner","email":"francois-dupon-owner@vinexquisite.fr","phone":"+33 6 78 90 12 34"},"sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerEmail":"tsidorova@scnsoft.com","deliveryInstructionsDueDate":"2024-02-06T00:00:00+00:00","status":"Pending Payment","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/495","@type":"DigitalPassportTransfer\/Item","id":495,"qty":10,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/210","@type":"Wine","id":210,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2014,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":false,"grapeVarieties":"60% Cabernet Sauvignon \/ 35% Merlot \/ 2% Petit Verdot \/ 3% Cabernet Franc","alcohol":13.5,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLje8hLWnziZ0W","domaine":"Château Léoville Poyferré"},"price":25,"fullPath":"495","sellLevel":1,"childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/56"],"digitalPassports":[]},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/496","@type":"DigitalPassportTransfer\/Item","id":496,"qty":10,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/212","@type":"Wine","id":212,"wineType":"Still","name":"Château Moulin Riche","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2020,"producer":"Château Moulin Riche","winemaker":"Château Moulin Riche","isPrimeurse":false,"grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot","alcohol":13,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PM3tdbLZvO3NB0","domaine":"Château Moulin Riche"},"price":74,"fullPath":"496","sellLevel":1,"childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/57"],"digitalPassports":[]}],"paymentUrl":"https:\/\/invoice.stripe.com\/i\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDlLa2JTS3BQa2RaSjl3akRtdkpkaXFBYVhzTVFhLDk3MTQ1NDgw0200VtDikcKl?s=ap","token":"05a982e9ba8e5c82174118dd57f7e0c521084b2eae8b0b528438afd8a7171ebd","sellerIsPayer":false,"currency":"EUR","selfProvideDeliveryInstruction":false,"isViewed":true,"deliveryInstruction":{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/402","@type":"DeliveryInstruction","id":402,"items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/65","@type":"DeliveryInstruction\/Item","id":65,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/210","@type":"Wine","id":210,"wineType":"Still","name":"Château LéovillePoyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2014,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":false,"grapeVarieties":"60% Cabernet Sauvignon \/ 35% Merlot \/ 2% Petit Verdot \/ 3% Cabernet Franc","alcohol":13.5,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLje8hLWnziZ0W","domaine":"Château Léoville Poyferré"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/Item\/CaseType","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},"qty":2,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/Item\/Regie","id":1,"name":"ACQ"},"habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/2","@type":"DeliveryInstruction\/Item\/Habillage","id":2,"name":"French label"},"isProcessed":false,"withCase":true,"bottlesQty":2,"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/Item\/CaseType","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},"bottleVolume":3},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/66","@type":"DeliveryInstruction\/Item","id":66,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/212","@type":"Wine","id":212,"wineType":"Still","name":"Château Moulin Riche","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2020,"producer":"Château Moulin Riche","winemaker":"Château Moulin Riche","isPrimeurse":false,"grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot","alcohol":13,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PM3tdbLZvO3NB0","domaine":"Château Moulin Riche"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/Item\/CaseType","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},"qty":1,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/Item\/Regie","id":1,"name":"ACQ"},"habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1","@type":"DeliveryInstruction\/Item\/Habillage","id":1,"name":"English label"},"isProcessed":false,"withCase":true,"bottlesQty":12,"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/Item\/CaseType","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},"bottleVolume":0.5}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/87","@type":"https:\/\/schema.org\/PostalAddress"}},"stripeInvoiceId":"in_1OeD2FFSrxJ11FojfmefkflV","createdAt":"2024-01-30T08:51:15+00:00","updatedAt":"2024-01-30T08:51:21+00:00","invoiceDownloadUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDlLa2JTS3BQa2RaSjl3akRtdkpkaXFBYVhzTVFhLDk3MTQ1NDgw0200VtDikcKl\/pdf?s=ap","isPresale":false,"isCancelled":false,"isPaid":false,"isCompleted":false,"isAborted":false,"isIncoming":false},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/399","@type":"DigitalPassportTransfer","id":399,"sellerOrganization":{"@id":"\/api\/v1\/wineyard\/organizations\/1","@type":"Organization","name":"Vin Exquisite","extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"},"sellerContact":{"@id":"\/api\/v1\/wineyard\/contacts\/3","@type":"Contact","id":3,"person":"francois-dupon-owner","email":"francois-dupon-owner@vinexquisite.fr","phone":"+33 6 78 90 12 34"},"sellerEmail":"francois-dupon-owner@vinexquisite.fr","buyerEmail":"tsidorova@scnsoft.com","deliveryInstructionsDueDate":"2024-02-14T00:00:00+00:00","status":"Canceled","items":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfer\/items\/492","@type":"DigitalPassportTransfer\/Item","id":492,"qty":25,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/211","@type":"Wine","id":211,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2021,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":true,"grapeVarieties":"67% Cabernet Sauvignon \/ 27% Merlot \/ 3% Petit Verdot \/ 3% Cabernet Franc","alcohol":14,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLNOashVh5YJF2"},"price":10,"fullPath":"492","sellLevel":1,"childItems":[],"presalePassports":["\/api\/v1\/wineyard\/presale-passports\/53"],"digitalPassports":[]}],"paymentUrl":"https:\/\/invoice.stripe.com\/i\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDhjclFKSjJsZnE5NDU5WDNWeU1nM0kxaUR4d0RXLDk3MTQyNzg00200326x1lXT?s=ap","token":"43a2bbd2eda1c5a209570eb8d723a02271e5493322f6ed555393a2502fe7a0ad","sellerIsPayer":false,"currency":"EUR","selfProvideDeliveryInstruction":false,"isViewed":true,"deliveryInstruction":{"@id":"\/api\/v1\/wineyard\/delivery-instructions\/399","@type":"DeliveryInstruction","id":399,"items":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/63","@type":"DeliveryInstruction\/Item","id":63,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/211","@type":"Wine","id":211,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2021,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":true,"grapeVarieties":"67% Cabernet Sauvignon \/ 27% Merlot \/ 3% Petit Verdot \/ 3% Cabernet Franc","alcohol":14,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLNOashVh5YJF2"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/9","@type":"DeliveryInstruction\/Item\/CaseType","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},"qty":2,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/1","@type":"DeliveryInstruction\/Item\/Regie","id":1,"name":"ACQ"},"habillage":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/2","@type":"DeliveryInstruction\/Item\/Habillage","id":2,"name":"French label"},"isProcessed":false,"withCase":true,"bottlesQty":2,"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/Item\/CaseType","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},"bottleVolume":4.5},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/68","@type":"DeliveryInstruction\/Item","id":68,"wine":{"@id":"\/api\/v1\/wineyard\/wines\/211","@type":"Wine","id":211,"wineType":"Still","name":"Château Léoville Poyferré","imageUrl":"\/path\/image.jpg","country":"\/api\/v1\/wineyard\/geo\/countries\/1","region":"\/api\/v1\/wineyard\/geo\/regions\/2","appellation":"\/api\/v1\/wineyard\/geo\/appellations\/4","classification":"Deuxième grand cru","color":"Red","agriculture":"Sustainable","vintage":2021,"producer":"Château Léoville Poyferré","winemaker":"Château Léoville Poyferré","isPrimeurse":true,"grapeVarieties":"67% Cabernet Sauvignon \/ 27% Merlot \/ 3% Petit Verdot \/ 3% Cabernet Franc","alcohol":14,"allergens":"Suflites","bottleVolume":0.75,"stripeProductId":"prod_PLNOashVh5YJF2"},"type":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/Item\/CaseType","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},"qty":1,"pregnancyWarning":true,"regie":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/regies\/2","@type":"DeliveryInstruction\/Item\/Regie","id":2,"name":"CRD"},"isProcessed":false,"withCase":true,"bottlesQty":1,"caseType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/Item\/CaseType","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},"bottleVolume":4.5}],"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/90","@type":"https:\/\/schema.org\/PostalAddress"}},"stripeInvoiceId":"in_1OeCKnFSrxJ11FojddYYITDb","cancellationReason":"Reject DWP Purchase","createdAt":"2024-01-30T08:06:20+00:00","updatedAt":"2024-01-30T09:28:01+00:00","invoiceDownloadUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QVDhjclFKSjJsZnE5NDU5WDNWeU1nM0kxaUR4d0RXLDk3MTQyNzg00200326x1lXT\/pdf?s=ap","isPresale":true,"isCancelled":true,"isPaid":false,"isCompleted":false,"isAborted":false,"isIncoming":false}]}

ptDeliveryInstructionItem(
idUrl=/api/v1/wineyard/delivery-instruction/items/63,
type=DeliveryInstruction/Item, id=63,
wine=DptDeliveryInstructionItemWine(idUrl=/api/v1/wineyard/wines/211, type=Wine, id=211, wineType=Still, name=Château Léoville Poyferré, imageUrl=/path/image.jpg, country=/api/v1/wineyard/geo/countries/1, region=/api/v1/wineyard/geo/regions/2, appellation=/api/v1/wineyard/geo/appellations/4, classification=Deuxième grand cru, color=Red, agriculture=Sustainable, vintage=2021, producer=Château Léoville Poyferré, winemaker=Château Léoville Poyferré, champagneType=null, isPrimeurse=true, grapeVarieties=67% Cabernet Sauvignon / 27% Merlot / 3% Petit Verdot / 3% Cabernet Franc, alcohol=14.0, certification=null, sulfites=false, allergens=Suflites, nftId=null, bottleVolume=0.75, stripeProductId=prod_PLNOashVh5YJF2, domaine=null),
caseType=DptDeliveryInstructionItemCaseType(idUrl=/api/v1/wineyard/delivery-instruction/item/case-types/29, type=DeliveryInstruction/Item/CaseType, id=29, nameEn=Jeroboam Wood, nameFr=Jeroboam CB, materialNameEn=Wood, materialNameFr=CB, bottleNameEn=null, bottleNameFr=null,
        amountInLiters=4.5,
        bottleSizeLabelEn=null, bottleSizeLabelFr=null, litersPerCase=0.0, eqvBottle=6.0,
        bottlesQty=1,
        bottleType=DptDeliveryInstructionItemBottleType(idUrl=/api/v1/wineyard/delivery-instruction/item/bottle-types/7, type=DeliveryInstruction/Item/BottleType, id=7, nameEn=Jeroboam, nameFr=Jeroboam, typeNameEn=Jeroboam (4,5 L), typeNameFr=Jeroboam (4,5 l),
        amountInLiters=4.5, eqvBottle=6.0, lang=null), lang=en),
        bottleType=null, qty=2, pregnancyWarning=true, regie=DptDeliveryInstructionItemCaseTypeRegie(idUrl=/api/v1/wineyard/delivery-instruction/item/regies/1, type=
2024-01-31 18:54:05.057  8223-8223  MainViewModel com.fidewine.app.dev   D  DeliveryInstruction/Item/Regie, id=1, name=ACQ), habillage=DptDeliveryInstructionItemHabillage(idUrl=/api/v1/wineyard/delivery-instruction/item/habillages/2, type=DeliveryInstruction/Item/Habillage, id=2, name=French label), withCase=true, bottlesQty=2, isProcessed=false, comments=null, reference=null, dptId=399, processedBottleQty=0)
 */

/*
{"@context":"\/api\/v1\/wineyard\/contexts\/Wine","@id":"\/api\/v1\/wineyard\/wines\/64","@type":"Wine","id":64,"wineType":"Still","name":"winebumtan Beaujolais","images":[],"country":{"@id":"\/api\/v1\/wineyard\/geo\/countries\/1","@type":"Geo\/Country","name":"France"},"region":{"@id":"\/api\/v1\/wineyard\/geo\/regions\/12","@type":"Geo\/Region","name":"Bourgogne-Franche-Comté"},"appellation":{"@id":"\/api\/v1\/wineyard\/geo\/appellations\/15","@type":"Geo\/Appellation","name":"Beaujolais"},"classification":"Quatrième grand cru","color":"White","vintage":2026,"prices":[{"@id":"\/api\/v1\/wineyard\/wine\/prices\/223","@type":"Wine\/Price","priceType":"distributor","value":6786,"currency":"EUR"},{"@id":"\/api\/v1\/wineyard\/wine\/prices\/224","@type":"Wine\/Price","priceType":"retailer","value":8678,"currency":"EUR"},{"@id":"\/api\/v1\/wineyard\/wine\/prices\/225","@type":"Wine\/Price","priceType":"consumer","value":6786,"currency":"EUR"}],"isPrimeurse":true,"grapeVarieties":"we","alcohol":43,"maxQty":2333333,"producer":{"@id":"\/api\/v1\/wineyard\/organizations\/64","@type":"Organization","id":64,"name":"Prod2","extOrganizationId":"895a6f3d-79fa-4045-944d-706f53d96f62"},"stripeProductId":"prod_Q8oUlYBUnMWNKd","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/26","@type":"Domain","id":26,"name":"Domain de Tan3","users":[[]],"producers":[{"@id":"\/api\/v1\/wineyard\/organizations\/64","@type":"Organization","id":64,"name":"Prod2","extOrganizationId":"895a6f3d-79fa-4045-944d-706f53d96f62"}],"extDomainId":"ef89026b-8eec-4c46-8484-844d8eca167c","holding":{"@id":"\/api\/v1\/wineyard\/organizations\/35","@type":"Organization","id":35,"name":"TestTanka","extOrganizationId":"4cfa2aa1-37c7-43f2-8007-437387b10e80"}},"description":[{"language":"EN","text":"323 2323 dfdf 23 erwerwer"},{"language":"FR","text":"swerwer sdrfsdfsdf"}],"passports":["\/api\/v1\/wineyard\/presale-passports\/772","\/api\/v1\/wineyard\/presale-passports\/1311","\/api\/v1\/wineyard\/presale-passports\/1324"],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/Item\/CaseType","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/Item\/CaseType","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/20","@type":"DeliveryInstruction\/Item\/CaseType","id":20,"nameEn":"12 Jennie Carton","nameFr":"12 Pot CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/Item\/CaseType","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/Item\/BottleType","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/Item\/CaseType","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/Item\/BottleType","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/Item\/CaseType","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/Item\/CaseType","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/30","@type":"DeliveryInstruction\/Item\/CaseType","id":30,"nameEn":"Imperial Wood","nameFr":"Imperiale CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/8","@type":"DeliveryInstruction\/Item\/BottleType","id":8,"nameEn":"Imperial","nameFr":"Imperiale","typeNameEn":"Imperial (6 L)","typeNameFr":"Imperiale (6 l)","amountInLiters":6,"eqvBottle":8}}],"originalPrice":7686,"reviews":[{"@id":"\/api\/v1\/wineyard\/wine\/reviews\/30","@type":"Wine\/Review","id":30,"reviewer":"","score":"2323","description":""}],"passportPrice":307.44,"boughtQty":33,"soldQty":0,"inStock":33}
 */