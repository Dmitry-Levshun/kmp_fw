package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class QrCodeInfoRequest (
    val securityType: String,  // qr or nfc
    val value: String
)

@Serializable
data class QrCodeBoxInfoRequest (
    val caseId: String
)

@Serializable
data class QrCodeInfoWine(
    @SerialName("@id")  // "@id":"\/api\/v1\/wineyard\/wines\/212"
    val idUrl: String,
    @SerialName("@type")  // "@type":"Wine"
    val type: String,
    val wineType: String,       // "wineType":"Still"
    val name: String,           // "name":"Château Moulin Riche"
    @SerialName("country")
    val countryType: QrCodeInfoTypeName,
    @SerialName("region")
    val regionType: QrCodeInfoTypeName,
    @SerialName("appellation")
    val appellationType: QrCodeInfoTypeName,
    val classification: String,
    val color: String,
    val agriculture: String,
    val vintage: Int,
    val isPrimeurse: Boolean,
    val grapeVarieties: String,
    val alcohol: Double,
    val certification: List<String>,
    val allergens: String,
    @SerialName("producer")
    val producerType: QrCodeInfoTypeName,
    val bottleVolume: Double,
//    val description: List<String?>,
    val description: List<WineDescription>?,
    @SerialName("domaine")
    val domaine: QrCodeInfoTypeName,
    val reviews: List<WineReview>?,
    val images: List<WineImage>?,
    ) {
    val domain: String get() = domaine.name //domaine.name
    val country: String get() = countryType.name
    val region: String get()  = regionType.name
    val appellation: String get() = appellationType.name
    val producer: String get() = producerType.name
    val id: Int get() =  idUrl.substringAfterLast('/', "0").toInt()
    val imageUrl: String? get() = if (!images.isNullOrEmpty()) images.first().contentUrl else null
}

@Serializable
data class QrCodeInfoCountry(
    @SerialName("@id")  // "@id":"\/api\/v1\/wineyard\/geo\/countries\/1"
    val idUrl: String,
    @SerialName("@type")  // "@type":"Geo\/Country"
    val type: String,
    val name: String,           //"name":"France"
)

@Serializable
data class QrCodeInfoTypeName(
    @SerialName("@id")  // "@id":"\/api\/v1\/wineyard\/geo\/regions\/2"
    val idUrl: String,
    @SerialName("@type")  // "@type":"Geo\/Region"
    val type: String,
    val name: String,           // "name":"Bordeaux"
)

@Serializable
data class QrCodeInfoAppellation(
    @SerialName("@id")  // "@id":"\/api\/v1\/wineyard\/geo\/appellations\/4"
    val idUrl: String,
    @SerialName("@type")  // "@type":"Geo\/Appellation"
    val type: String,
    val name: String,           //"name":"Saint-Julien"
)

// -------
@Serializable
data class QrCodeInfoResponse(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("package")
    val packageInfo: QrCodeInfoPackage?,
    val salesOrder: QrCodeInfoSalesOrder?,
    @SerialName("id")
    val id: Int,
    val isSellable: Boolean,
    val owner: String?,
    val wine: QrCodeInfoWine,
    val platformSerialNumber: String?,
    val bottleVolume: Double?,
    val isSealBroken: Boolean?
)

@Serializable
data class QrCodeInfoPackage(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val caseId: String,
    val bottlingDate: LocalDate,
    val isSealBroken: Boolean,
)

@Serializable
data class QrCodeInfoSalesOrder(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val deliveryInstruction: QrCodeInfoDeliveryInstruction,
    val shippingInformation: QrCodeInfoShippingInformation?,
    val isIncoming: Boolean,
)

@Serializable
data class QrCodeInfoDeliveryInstruction(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
)

@Serializable
data class QrCodeInfoShippingInformation(
    @SerialName("@id")
    val idurl: String,
    @SerialName("@type")
    val type: String,
    val typeOfDelivery: String?,
    val addressCountry: String?,
    val addressLocality: String?,
    val addressRegion: String?,
    val postalCode: String?,
    val streetAddress: String?,
    val storagePartner: String?,
)

@Serializable
data class QrBoxCodeInfoResponse(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val caseId: String,
    val bottlingDate: LocalDate,
    val isSellable: Boolean?,
    val owner: String?,
    val digitalPassports: List<QrBoxCodeInfoDigitalPassport>,
    val isSealBroken: Boolean,
    val shippingInformation: QrCodeInfoShippingInformation?,
    val wine: QrBoxCodeInfoWine?,
    val caseTypeId: Int?,
)

@Serializable
data class QrBoxCodeInfoDigitalPassport(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val platformSerialNumber: String,
    val vendorSerialNumber: String,
    val bottleVolume: Double?,
)

@Serializable
data class QrBoxCodeInfoWine(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val name: String?,
    val vintage: Int?,
    val description: List<WineDescription>?,
    val reviews: List<WineReview>?,
    val images: List<WineImage>?,
) {
    val imageUrl: String? get() = if (!images.isNullOrEmpty()) images.first().contentUrl else null
}
@Serializable
data class BreakSealRequest(
    val isSealBroken: Boolean?
)

@Serializable
data class QrCodeInstantSellingRequest(
    val cases: List<String>?,
    val bottles: List<Int>?,
    val buyerEmail: String?,
    val buyerId: String?
)

//--------
/*

{"@context":"\/api\/v1\/wineyard\/contexts\/Package","@id":"\/api\/v1\/wineyard\/packages\/FDW_BX_2412661331","@type":"Package","id":298,"caseId":"FDW_BX_2412661331","bottlingDate":"2024-07-16T10:19:20+00:00","digitalPassports":[{"@id":"\/api\/v1\/wineyard\/digital-passports\/2346","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin2345678901","authDataSummary":[{"type":"qr","value":"TanWineVin2345678901"}],"id":2346,"platformSerialNumber":"3c3dc4ab2ae72efe8a6d59bea8b60280bf2515969f1fd5283d5b7dfee7c3fda2","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2336","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin","authDataSummary":[{"type":"qr","value":"TanWineVin"}],"id":2336,"platformSerialNumber":"d8d88acf9136de5cc5e872de23a534e8b09b6f5f88eb86408e2eeaf31eff1973","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2337","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin2","authDataSummary":[{"type":"qr","value":"TanWineVin2"}],"id":2337,"platformSerialNumber":"07bf974acb5ed7bb4bb01d93906389f43d126b1f270a62856d0f6a4e3797ac57","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2338","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin23","authDataSummary":[{"type":"qr","value":"TanWineVin23"}],"id":2338,"platformSerialNumber":"bf2fabbf0cd287b31c8f9f63b6827355e62a1578dc758664956e6205a78375ae","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2339","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin234","authDataSummary":[{"type":"qr","value":"TanWineVin234"}],"id":2339,"platformSerialNumber":"9989a9429d36f0171301bf0d4ae2d434253ba76b8d3bb749e2076412cdb24e02","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2344","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin23456789","authDataSummary":[{"type":"qr","value":"TanWineVin23456789"}],"id":2344,"platformSerialNumber":"a060fe3b6e0c2a9371a4e47153e48fd2fb3f6770322596c33d9bcd8bfbd6fe63","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2340","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin2345","authDataSummary":[{"type":"qr","value":"TanWineVin2345"}],"id":2340,"platformSerialNumber":"68c06872f1e370647c023a97d36e5b967158ce81cb683c975219d964735c1c8f","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2341","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin23456","authDataSummary":[{"type":"qr","value":"TanWineVin23456"}],"id":2341,"platformSerialNumber":"ade0a4e33950272440bebddc9ee8f3726b57782a6f965e8902d9f8679c100bd3","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2342","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin234567","authDataSummary":[{"type":"qr","value":"TanWineVin234567"}],"id":2342,"platformSerialNumber":"d2668fcecce8242ee59d2205871ae71fdb042bb4b20a309d0bff4cbefb9e228f","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2347","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin23456789011","authDataSummary":[{"type":"qr","value":"TanWineVin23456789011"}],"id":2347,"platformSerialNumber":"1f14dbb0b36ebc0f48ad74a21f018486f9861116536e90ed4ed9ee7b52292e88","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2345","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin234567890","authDataSummary":[{"type":"qr","value":"TanWineVin234567890"}],"id":2345,"platformSerialNumber":"a6b056ae0ef95c7fedac56afa35dd7c7fef44e1b4b05cca346bee5562ddd4eb9","bottleVolume":0.75},{"@id":"\/api\/v1\/wineyard\/digital-passports\/2343","@type":"DigitalPassport","vendorSerialNumber":"TanWineVin2345678","authDataSummary":[{"type":"qr","value":"TanWineVin2345678"}],"id":2343,"platformSerialNumber":"af94a7da3f22b69f49ed1326ec36a183b8c47093a42cb7a480e53004d0c9b48f","bottleVolume":0.75}],"isSealBroken":false,"shippingInformation":{"@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/299","@type":"https:\/\/schema.org\/PostalAddress","typeOfDelivery":"No Delivery","dateFrom":"2025-07-01T00:00:00+00:00","dateTo":"2027-07-31T00:00:00+00:00","storagePartner":"grtert"},"wine":{"@id":"\/api\/v1\/wineyard\/wines\/101","@type":"Wine","id":101,"name":"winTan3_Vint","vintage":2027}}

{
    "@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassport",
    "@id":"\/api\/v1\/wineyard\/digital-passports\/81",
    "@type":"DigitalPassport",
    "bottlingDate":"2024-01-31T09:34:24+00:00",
    "wine":{
        "@id":"\/api\/v1\/wineyard\/wines\/212",
        "@type":"Wine",
        "wineType":"Still",
        "name":"Château Moulin Riche",
        "imageUrl":"\/path\/image.jpg",
        "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
        },
        "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/2",
            "@type":"Geo\/Region",
            "name":"Bordeaux"
        },
        "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/4",
            "@type":"Geo\/Appellation",
            "name":"Saint-Julien"
        },
        "classification":"Deuxième grand cru",
        "color":"Red",
        "agriculture":"Sustainable",
        "vintage":2020,
        "producer":"Château Moulin Riche",
        "winemaker":"Château Moulin Riche",
        "grapeVarieties":"65% Cabernet Sauvignon \/ 20% Merlot \/ 15% Petit Verdot",
        "alcohol":13,
        "allergens":"Suflites",
        "domaine":"Château Moulin Riche"
    },
    "bottleVolume":0.5
}

//----------
{
    "@context":"\/api\/v1\/wineyard\/contexts\/DigitalPassport",
    "@id":"\/api\/v1\/wineyard\/digital-passports\/130",
    "@type":"DigitalPassport",
    "id":130,
    "wine":{
        "@id":"\/api\/v1\/wineyard\/wines\/213",
        "@type":"Wine",
        "wineType":"Still",
        "name":"Château Léoville Poyferré",
        "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
        },
        "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/2",
            "@type":"Geo\/Region",
            "name":"Bordeaux"
        },
        "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/4",
            "@type":"Geo\/Appellation",
            "name":"Saint-Julien"
        },
        "classification":"Deuxième grand cru",
        "color":"Red",
        "agriculture":"Sustainable",
        "vintage":2021,
        "producer":"Château Léoville Poyferré",
        "grapeVarieties":"67% Cabernet Sauvignon \/ 27% Merlot \/ 3% Petit Verdot \/ 3% Cabernet Franc",
        "alcohol":14,
        "certification":"Terra Vitis",
        "allergens":"Suflites",
        "domaine":"Château Léoville Poyferré",
        "description":[

        ]
    }
}
 */