package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WineExtendedInfo(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val wineType: String,
    val name: String,
    val images: List<WineImage>?,
    val country: WineExtendedInfoCountry,
    val region: WineExtendedInfoRegion,
    val appellation: WineExtendedInfoAppellation,
    val classification: String,
    val color: String,
    val vintage: Int,
    val prices: List<WineExtendedInfoPrice>,
    val isPrimeurse: Boolean,
    val grapeVarieties: String,
    val alcohol: Double,
    val maxQty: Int,
    val producer: WineExtendedInfoProducer,
    val bottleVolume: Double,
    val stripeProductId: String,
    val domaine: WineExtendedInfoDomaine,
    val description: List<WineExtendedInfoDescription>,
    val passports: List<String>,
    val packages: List<WineExtendedInfoPackage>,
    val originalPrice: Double,
    val reviews: List<WineExtendedInfoReview>,
    val passportPrice: Double,
    val boughtQty: Int,
    val soldQty: Int,
    val inStock: Int,
)

@Serializable
data class WineExtendedInfoCountry(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
)

@Serializable
data class WineExtendedInfoRegion(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
)

@Serializable
data class WineExtendedInfoAppellation(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
)

@Serializable
data class WineExtendedInfoPrice(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val priceType: String,
    val value: Double,
    val currency: String,
)

@Serializable
data class WineExtendedInfoProducer(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val name: String,
    val extOrganizationId: String,
)

@Serializable
data class WineExtendedInfoDomaine(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val name: String,
//    val users: List<List<Any?>>,
    val producers: List<WineExtendedInfoProducer>,
    val extDomainId: String,
    val holding: WineExtendedInfoHolding,
)

@Serializable
data class WineExtendedInfoHolding(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val name: String,
    val extOrganizationId: String,
)

@Serializable
data class WineExtendedInfoDescription(
    val language: String,
    val text: String,
)

@Serializable
data class WineExtendedInfoPackage(
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
    val bottlesQty: Int,
    val bottleType: WineExtendedInfoBottleType,
    var lang: String = "en",
) {
    val name: String get() = if (lang == "fr") nameFr else nameEn
    val typeName: String get() = if (lang == "fr") typeNameFr else typeNameEn
}

@Serializable
data class WineExtendedInfoBottleType(
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
) {
    val name: String get() = if (lang == "fr") nameFr else nameEn
    val typeName: String get() = if (lang == "fr") typeNameFr else typeNameEn
}

@Serializable
data class WineExtendedInfoReview(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val reviewer: String,
    val score: String,
    val description: String,
)
/*
{"@context":"\/api\/v1\/wineyard\/contexts\/Wine","@id":"\/api\/v1\/wineyard\/wines\/67","@type":"Wine","id":67,"wineType":"Still","name":"wine Livrable","images":[],"country":{"@id":"\/api\/v1\/wineyard\/geo\/countries\/1","@type":"Geo\/Country","name":"France"},"region":{"@id":"\/api\/v1\/wineyard\/geo\/regions\/10","@type":"Geo\/Region","name":"South West France"},"appellation":{"@id":"\/api\/v1\/wineyard\/geo\/appellations\/12","@type":"Geo\/Appellation","name":"Alsace Sylvaner"},"color":"White","agriculture":"Sustainable","vintage":2025,"prices":[{"@id":"\/api\/v1\/wineyard\/wine\/prices\/232","@type":"Wine\/Price","priceType":"distributor","value":25,"currency":"EUR"},{"@id":"\/api\/v1\/wineyard\/wine\/prices\/233","@type":"Wine\/Price","priceType":"retailer","value":45,"currency":"EUR"},{"@id":"\/api\/v1\/wineyard\/wine\/prices\/234","@type":"Wine\/Price","priceType":"consumer","value":78,"currency":"EUR"}],"isPrimeurse":false,"grapeVarieties":"12","alcohol":45,"maxQty":500000,"producer":{"@id":"\/api\/v1\/wineyard\/organizations\/64","@type":"Organization","id":64,"name":"Prod2","extOrganizationId":"895a6f3d-79fa-4045-944d-706f53d96f62"},"bottleVolume":6,"stripeProductId":"prod_Q9WMAm6bEfKiM8","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/26","@type":"Domain","id":26,"name":"Domain de Tan3","users":[[]],"producers":[{"@id":"\/api\/v1\/wineyard\/organizations\/64","@type":"Organization","id":64,"name":"Prod2","extOrganizationId":"895a6f3d-79fa-4045-944d-706f53d96f62"}],"extDomainId":"ef89026b-8eec-4c46-8484-844d8eca167c","holding":{"@id":"\/api\/v1\/wineyard\/organizations\/35","@type":"Organization","id":35,"name":"TestTanka","extOrganizationId":"4cfa2aa1-37c7-43f2-8007-437387b10e80"}},"description":[{"language":"EN","text":"english 345"}],"passports":["\/api\/v1\/wineyard\/presale-passports\/901","\/api\/v1\/wineyard\/presale-passports\/904","\/api\/v1\/wineyard\/presale-passports\/916"],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/Item\/CaseType","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/Item\/CaseType","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/Item\/CaseType","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/Item\/BottleType","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/Item\/CaseType","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/Item\/BottleType","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/Item\/CaseType","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/Item\/CaseType","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/30","@type":"DeliveryInstruction\/Item\/CaseType","id":30,"nameEn":"Imperial Wood","nameFr":"Imperiale CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/8","@type":"DeliveryInstruction\/Item\/BottleType","id":8,"nameEn":"Imperial","nameFr":"Imperiale","typeNameEn":"Imperial (6 L)","typeNameFr":"Imperiale (6 l)","amountInLiters":6,"eqvBottle":8}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/31","@type":"DeliveryInstruction\/Item\/CaseType","id":31,"nameEn":"Salmanazar Wood","nameFr":"Salmanazar CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/9","@type":"DeliveryInstruction\/Item\/BottleType","id":9,"nameEn":"Salmanazar","nameFr":"Salmanazar","typeNameEn":"Salmanazar (9 L)","typeNameFr":"Salmanazar (9 l)","amountInLiters":9,"eqvBottle":12}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/Item\/CaseType","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/32","@type":"DeliveryInstruction\/Item\/CaseType","id":32,"nameEn":"Balthazar Wood","nameFr":"Balthazar CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":12,"eqvBottle":16,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/10","@type":"DeliveryInstruction\/Item\/BottleType","id":10,"nameEn":"Balthazar","nameFr":"Balthazar","typeNameEn":"Balthazar (12 L)","typeNameFr":"Balthazar (12 l)","amountInLiters":12,"eqvBottle":16}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/33","@type":"DeliveryInstruction\/Item\/CaseType","id":33,"nameEn":"Nebuchadnezzar Wood","nameFr":"Nabuchodonosor CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":15,"eqvBottle":20,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/11","@type":"DeliveryInstruction\/Item\/BottleType","id":11,"nameEn":"Nebuchadnezzar","nameFr":"Nabuchodonosor","typeNameEn":"Nebuchadnezzar (15 L)","typeNameFr":"Nabuchodonosor (15 l)","amountInLiters":15,"eqvBottle":20}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/Item\/CaseType","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/Item\/CaseType","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/20","@type":"DeliveryInstruction\/Item\/CaseType","id":20,"nameEn":"12 Jennie Carton","nameFr":"12 Pot CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/19","@type":"DeliveryInstruction\/Item\/CaseType","id":19,"nameEn":"12 Demie Wood","nameFr":"12 Demie CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/Item\/BottleType","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/18","@type":"DeliveryInstruction\/Item\/CaseType","id":18,"nameEn":"12 Demie Carton","nameFr":"12 Demie CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/Item\/BottleType","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/34","@type":"DeliveryInstruction\/Item\/CaseType","id":34,"nameEn":"Salomon Wood","nameFr":"Salomon CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":18,"eqvBottle":24,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/12","@type":"DeliveryInstruction\/Item\/BottleType","id":12,"nameEn":"Salomon","nameFr":"Salomon","typeNameEn":"Salomon (18 L)","typeNameFr":"Salomon (18 l)","amountInLiters":18,"eqvBottle":24}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/37","@type":"DeliveryInstruction\/Item\/CaseType","id":37,"nameEn":"Midas Wood","nameFr":"Midas CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":30,"eqvBottle":40,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/15","@type":"DeliveryInstruction\/Item\/BottleType","id":15,"nameEn":"Midas","nameFr":"Midas","typeNameEn":"Midas (30 L)","typeNameFr":"Midas (30 l)","amountInLiters":30,"eqvBottle":40}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/43","@type":"DeliveryInstruction\/Item\/CustomCaseType","id":43,"nameEn":"ert","nameFr":"ert fr","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":135,"eqvBottle":180,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/45","@type":"DeliveryInstruction\/Item\/CustomCaseType","id":45,"nameEn":"df","nameFr":"df","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":22.5,"eqvBottle":29.7,"bottlesQty":45,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/46","@type":"DeliveryInstruction\/Item\/CustomCaseType","id":46,"nameEn":"errr","nameFr":"erer","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":1.875,"eqvBottle":2.5,"bottlesQty":5,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/2","@type":"DeliveryInstruction\/Item\/BottleType","id":2,"nameEn":"Demie","nameFr":"Demie","typeNameEn":"Demie (375 ml)","typeNameFr":"Demie (375 ml)","amountInLiters":0.375,"eqvBottle":0.5}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/custom-case-types\/50","@type":"DeliveryInstruction\/Item\/CustomCaseType","id":50,"nameEn":"boxTan en","nameFr":"boxTan fr","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":153,"eqvBottle":204,"bottlesQty":34,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}}],"originalPrice":20,"reviews":[],"passportPrice":1,"bottleTypeName":{"en":"Imperial","fr":"Imperiale"},"boughtQty":50,"soldQty":0,"inStock":50}
{"@context":"\/api\/v1\/wineyard\/contexts\/Wine","@id":"\/api\/v1\/wineyard\/wines\/66","@type":"Wine","id":66,"wineType":"Still","name":"wineTan Test","images":[],"country":{"@id":"\/api\/v1\/wineyard\/geo\/countries\/1","@type":"Geo\/Country","name":"France"},"region":{"@id":"\/api\/v1\/wineyard\/geo\/regions\/12","@type":"Geo\/Region","name":"Bourgogne-Franche-Comté"},"appellation":{"@id":"\/api\/v1\/wineyard\/geo\/appellations\/219","@type":"Geo\/Appellation","name":"Anjou-Gamay"},"color":"Rose","vintage":2025,"prices":[{"@id":"\/api\/v1\/wineyard\/wine\/prices\/229","@type":"Wine\/Price","priceType":"distributor","value":45,"currency":"EUR"},{"@id":"\/api\/v1\/wineyard\/wine\/prices\/230","@type":"Wine\/Price","priceType":"retailer","value":67,"currency":"EUR"},{"@id":"\/api\/v1\/wineyard\/wine\/prices\/231","@type":"Wine\/Price","priceType":"consumer","value":89,"currency":"EUR"}],"isPrimeurse":false,"grapeVarieties":"34","alcohol":34,"maxQty":500000,"producer":{"@id":"\/api\/v1\/wineyard\/organizations\/64","@type":"Organization","id":64,"name":"Prod2","extOrganizationId":"895a6f3d-79fa-4045-944d-706f53d96f62"},"bottleVolume":4.5,"stripeProductId":"prod_Q9A802h3ybYSPC","domaine":{"@id":"\/api\/v1\/wineyard\/domains\/26","@type":"Domain","id":26,"name":"Domain de Tan3","users":[[]],"producers":[{"@id":"\/api\/v1\/wineyard\/organizations\/64","@type":"Organization","id":64,"name":"Prod2","extOrganizationId":"895a6f3d-79fa-4045-944d-706f53d96f62"}],"extDomainId":"ef89026b-8eec-4c46-8484-844d8eca167c","holding":{"@id":"\/api\/v1\/wineyard\/organizations\/35","@type":"Organization","id":35,"name":"TestTanka","extOrganizationId":"4cfa2aa1-37c7-43f2-8007-437387b10e80"}},"description":[{"language":"EN","text":"укук sdfsfd 345"}],"passports":["\/api\/v1\/wineyard\/presale-passports\/857","\/api\/v1\/wineyard\/presale-passports\/856","\/api\/v1\/wineyard\/presale-passports\/876","\/api\/v1\/wineyard\/presale-passports\/877","\/api\/v1\/wineyard\/presale-passports\/903"],"packages":[{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/24","@type":"DeliveryInstruction\/Item\/CaseType","id":24,"nameEn":"6 Standard Carton","nameFr":"6 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/25","@type":"DeliveryInstruction\/Item\/CaseType","id":25,"nameEn":"6 Standard Wood","nameFr":"6 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":6,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/26","@type":"DeliveryInstruction\/Item\/CaseType","id":26,"nameEn":"3 Magnum Carton","nameFr":"3 Magnum CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/5","@type":"DeliveryInstruction\/Item\/BottleType","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/27","@type":"DeliveryInstruction\/Item\/CaseType","id":27,"nameEn":"3 Magnum Wood","nameFr":"3 magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":3,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/
bottle-types\/5","@type":"DeliveryInstruction\/Item\/BottleType","id":5,"nameEn":"Magnum","nameFr":"Magnum","typeNameEn":"Magnum (1.5 L)","typeNameFr":"Magnum (1,5 l)","amountInLiters":1.5,"eqvBottle":2}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/28","@type":"DeliveryInstruction\/Item\/CaseType","id":28,"nameEn":"Dbl Magnum Wood","nameFr":"Dbl Magnum CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":3,"eqvBottle":4,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/6","@type":"DeliveryInstruction\/Item\/BottleType","id":6,"nameEn":"Dbl Magnum","nameFr":"Dbl Magnum","typeNameEn":"Dbl Magnum (3 L)","typeNameFr":"Dbl Magnum (3 l)","amountInLiters":3,"eqvBottle":4}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/23","@type":"DeliveryInstruction\/Item\/CaseType","id":23,"nameEn":"12 Standard Wood","nameFr":"12 Standard CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/29","@type":"DeliveryInstruction\/Item\/CaseType","id":29,"nameEn":"Jeroboam Wood","nameFr":"Jeroboam CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":4.5,"eqvBottle":6,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/7","@type":"DeliveryInstruction\/Item\/BottleType","id":7,"nameEn":"Jeroboam","nameFr":"Jeroboam","typeNameEn":"Jeroboam (4,5 L)","typeNameFr":"Jeroboam (4,5 l)","amountInLiters":4.5,"eqvBottle":6}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/22","@type":"DeliveryInstruction\/Item\/CaseType","id":22,"nameEn":"12 Standard Carton","nameFr":"12 Standard CA","typeNameEn":"Carton","typeNameFr":"CA","amountInLiters":9,"eqvBottle":12,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/4","@type":"DeliveryInstruction\/Item\/BottleType","id":4,"nameEn":"Standard","nameFr":"Standard","typeNameEn":"Standard (0,75 L)","typeNameFr":"Standard (0.75 l)","amountInLiters":0.75,"eqvBottle":1}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/21","@type":"DeliveryInstruction\/Item\/CaseType","id":21,"nameEn":"12 Jennie Wood","nameFr":"12 Pot CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":12,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/3","@type":"DeliveryInstruction\/Item\/BottleType","id":3,"nameEn":"Jennie","nameFr":"Pot","typeNameEn":"Jennie (0.5 L)","typeNameFr":"Pot (0,5 l)","amountInLiters":0.5,"eqvBottle":0.66}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/30","@type":"DeliveryInstruction\/Item\/CaseType","id":30,"nameEn":"Imperial Wood","nameFr":"Imperiale CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":6,"eqvBottle":8,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/8","@type":"DeliveryInstruction\/Item\/BottleType","id":8,"nameEn":"Imperial","nameFr":"Imperiale","typeNameEn":"Imperial (6 L)","typeNameFr":"Imperiale (6 l)","amountInLiters":6,"eqvBottle":8}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/32","@type":"DeliveryInstruction\/Item\/CaseType","id":32,"nameEn":"Balthazar Wood","nameFr":"Balthazar CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":12,"eqvBottle":16,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/10","@type":"DeliveryInstruction\/Item\/BottleType","id":10,"nameEn":"Balthazar","nameFr":"Balthazar","typeNameEn":"Balthazar (12 L)","typeNameFr":"Balthazar (12
l)","amountInLiters":12,"eqvBottle":16}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/34","@type":"DeliveryInstruction\/Item\/CaseType","id":34,"nameEn":"Salomon Wood","nameFr":"Salomon CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":18,"eqvBottle":24,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/12","@type":"DeliveryInstruction\/Item\/BottleType","id":12,"nameEn":"Salomon","nameFr":"Salomon","typeNameEn":"Salomon (18 L)","typeNameFr":"Salomon (18 l)","amountInLiters":18,"eqvBottle":24}},{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/35","@type":"DeliveryInstruction\/Item\/CaseType","id":35,"nameEn":"Sovereign Wood","nameFr":"Souverain CB","typeNameEn":"Wood","typeNameFr":"CB","amountInLiters":26.25,"eqvBottle":35,"bottlesQty":1,"bottleType":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/bottle-types\/13","@type":"DeliveryInstruction\/Item\/BottleType","id":13,"nameEn":"Sovereign","nameFr":"Souverain","typeNameEn":"Sovereign (26,25 L)","typeNameFr":"Souverain (26,25 l)","amountInLiters":26.25,"eqvBottle":35}}],"originalPrice":34,"reviews":[],"passportPrice":1.36,"bottleTypeName":{"en":"Jeroboam","fr":"Jeroboam"},"boughtQty":94,"soldQty":0,"inStock":94}

 */