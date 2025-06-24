package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.scnsoft.fidekmp.data.api.StringOrListSerializer

@Serializable
data class DeliveryInstructionDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Long,
    @SerialName("hydra:member")
    val hydraMember: List<DeliveryInstructionHydraMember>,
)

@Serializable
data class DeliveryInstructionHydraMember(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val digitalPassportTransfer: String,
    val items: List<DeliveryInstructionItem>,
    val shippingInformation: ShippingInformation,
)

@Serializable
data class DeliveryInstructionItem(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id2: Long,
    val wine: DeliveryInstructionWine,
    val caseType: CaseType,
    val casesQty: Long,
    val pregnancyWarning: Boolean,
    val regie: String,
    val habillage: Habillage,
)

@Serializable
data class DeliveryInstructionWine(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id2: Long,
    val wineType: String,
    val name: String,
    val imageUrl: String,
    val country: String,
    val region: String,
    val appellation: String,
    val classification: String,
    val color: String,
    val agriculture: String,
    val producer: String,
    val champagneType: String,
    val grapeVarieties: String,
    val alcohol: Double,
    @SerialName("certification")
    @Serializable(with = StringOrListSerializer::class)
    val certification: List<String>?,
    val sulfites: Boolean,
    val allergens: String,
) {

}

@Serializable
data class CaseType(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id2: Long,
    val nameEn: String,
    val nameFr: String,
    val materialNameEn: String,
    val materialNameFr: String,
    val bottlesQtyInCase: Long,
    val bottleNameEn: String,
    val bottleNameFr: String,
    val totalAmountInLiters: Double,
    val bottleSizeLabelEn: String,
    val bottleSizeLabelFr: String,
    val litersPerCase: Double,
    @SerialName("unitEQVBottle")
    val unitEqvbottle: Double,
    @SerialName("totalEQVBottle")
    val totalEqvbottle: Long,
)

@Serializable
data class Habillage(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id2: Long,
    val name: String,
)

@Serializable
data class ShippingInformation(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Long,
    val typeOfDelivery: String,
    val deliveryFrom: LocalDate, //String,
    val deliveryTo: LocalDate, //String,
    val addressCountry: String,
    val addressLocality: String,
    val addressRegion: String,
    val postalCode: String,
    val streetAddress: String,
    val deliveryInstruction: String,
)
/*
{
    "@context":"\/api\/v1\/wineyard\/contexts\/DeliveryInstruction",
    "@id":"\/api\/v1\/wineyard\/delivery-instructions",
    "@type":"hydra:Collection",
    "hydra:totalItems":5,
    "hydra:member":[
        {
            "@id":"\/api\/v1\/wineyard\/delivery-instructions\/1",
            "@type":"DeliveryInstruction",
            "digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/1",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/1",
                    "@type":"DeliveryInstruction\/Item",
                    "id":1,
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
                        "allergens":"None"
                    },
                    "caseType":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3",
                        "@type":"DeliveryInstruction\/Item\/CaseType",
                        "id":3,
                        "nameEn":"12 Demie Wood",
                        "nameFr":"12 Demie CB",
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
                        "totalEQVBottle":6
                    },
                    "casesQty":10,
                    "pregnancyWarning":true,
                    "regie":"ACQ",
                    "habillage":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1",
                        "@type":"DeliveryInstruction\/Item\/Habillage",
                        "id":1,
                        "name":"English label"
                    }
                }
            ],
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
                "deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/1"
            }
        },
        {
            "@id":"\/api\/v1\/wineyard\/delivery-instructions\/2",
            "@type":"DeliveryInstruction",
            "digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/2",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/2",
                    "@type":"DeliveryInstruction\/Item",
                    "id":2,
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
                        "producer":"Winery X",
                        "champagneType":"Blanc de blanc",
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None"
                    },
                    "caseType":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3",
                        "@type":"DeliveryInstruction\/Item\/CaseType",
                        "id":3,
                        "nameEn":"12 Demie Wood",
                        "nameFr":"12 Demie CB",
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
                        "totalEQVBottle":6
                    },
                    "casesQty":10,
                    "pregnancyWarning":true,
                    "regie":"ACQ",
                    "habillage":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1",
                        "@type":"DeliveryInstruction\/Item\/Habillage",
                        "id":1,
                        "name":"English label"
                    }
                }
            ],
            "shippingInformation":{
                "@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/13",
                "@type":"https:\/\/schema.org\/PostalAddress",
                "id":13,
                "typeOfDelivery":"Delivery",
                "deliveryFrom":"2023-10-30T00:00:00+00:00",
                "deliveryTo":"2023-10-30T00:00:00+00:00",
                "addressCountry":"United States",
                "addressLocality":"New York",
                "addressRegion":"NY",
                "postalCode":"10001",
                "streetAddress":"123 Main Street",
                "deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/2"
            }
        },
        {
            "@id":"\/api\/v1\/wineyard\/delivery-instructions\/3",
            "@type":"DeliveryInstruction",
            "digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/3",
                    "@type":"DeliveryInstruction\/Item",
                    "id":3,
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
                        "producer":"Winery X",
                        "champagneType":"Blanc de blanc",
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None"
                    },
                    "caseType":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3",
                        "@type":"DeliveryInstruction\/Item\/CaseType",
                        "id":3,
                        "nameEn":"12 Demie Wood",
                        "nameFr":"12 Demie CB",
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
                        "totalEQVBottle":6
                    },
                    "casesQty":10,
                    "pregnancyWarning":true,
                    "regie":"ACQ",
                    "habillage":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1",
                        "@type":"DeliveryInstruction\/Item\/Habillage",
                        "id":1,
                        "name":"English label"
                    }
                }
            ],
            "shippingInformation":{
                "@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/5",
                "@type":"https:\/\/schema.org\/PostalAddress",
                "id":5,
                "typeOfDelivery":"Delivery",
                "deliveryFrom":"2023-10-30T00:00:00+00:00",
                "deliveryTo":"2023-10-30T00:00:00+00:00",
                "addressCountry":"United States",
                "addressLocality":"New York",
                "addressRegion":"NY",
                "postalCode":"10001",
                "streetAddress":"123 Main Street",
                "deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/3"
            }
        },
        {
            "@id":"\/api\/v1\/wineyard\/delivery-instructions\/4",
            "@type":"DeliveryInstruction",
            "digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/4",
                    "@type":"DeliveryInstruction\/Item",
                    "id":4,
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
                        "producer":"Winery X",
                        "champagneType":"Blanc de blanc",
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None"
                    },
                    "caseType":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3",
                        "@type":"DeliveryInstruction\/Item\/CaseType",
                        "id":3,
                        "nameEn":"12 Demie Wood",
                        "nameFr":"12 Demie CB",
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
                        "totalEQVBottle":6
                    },
                    "casesQty":10,
                    "pregnancyWarning":true,
                    "regie":"ACQ",
                    "habillage":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1",
                        "@type":"DeliveryInstruction\/Item\/Habillage",
                        "id":1,
                        "name":"English label"
                    }
                }
            ],
            "shippingInformation":{
                "@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/8",
                "@type":"https:\/\/schema.org\/PostalAddress",
                "id":8,
                "typeOfDelivery":"Delivery",
                "deliveryFrom":"2023-10-30T00:00:00+00:00",
                "deliveryTo":"2023-10-30T00:00:00+00:00",
                "addressCountry":"United States",
                "addressLocality":"New York",
                "addressRegion":"NY",
                "postalCode":"10001",
                "streetAddress":"123 Main Street",
                "deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/4"
            }
        },
        {
            "@id":"\/api\/v1\/wineyard\/delivery-instructions\/5",
            "@type":"DeliveryInstruction",
            "digitalPassportTransfer":"\/api\/v1\/wineyard\/digital-passport-transfers\/8",
            "items":[
                {
                    "@id":"\/api\/v1\/wineyard\/delivery-instruction\/items\/5",
                    "@type":"DeliveryInstruction\/Item",
                    "id":5,
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
                        "producer":"Winery X",
                        "champagneType":"Blanc de blanc",
                        "grapeVarieties":"Cabernet Sauvignon, Merlot",
                        "alcohol":12.5,
                        "certification":"AOC",
                        "sulfites":true,
                        "allergens":"None"
                    },
                    "caseType":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/case-types\/3",
                        "@type":"DeliveryInstruction\/Item\/CaseType",
                        "id":3,
                        "nameEn":"12 Demie Wood",
                        "nameFr":"12 Demie CB",
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
                        "totalEQVBottle":6
                    },
                    "casesQty":10,
                    "pregnancyWarning":true,
                    "regie":"ACQ",
                    "habillage":{
                        "@id":"\/api\/v1\/wineyard\/delivery-instruction\/item\/habillages\/1",
                        "@type":"DeliveryInstruction\/Item\/Habillage",
                        "id":1,
                        "name":"English label"
                    }
                }
            ],
            "shippingInformation":{
                "@id":"\/api\/v1\/wineyard\/delivery-instruction\/shipping-informations\/11",
                "@type":"https:\/\/schema.org\/PostalAddress",
                "id":11,
                "typeOfDelivery":"Delivery",
                "deliveryFrom":"2023-10-30T00:00:00+00:00",
                "deliveryTo":"2023-10-30T00:00:00+00:00",
                "addressCountry":"United States",
                "addressLocality":"New York",
                "addressRegion":"NY",
                "postalCode":"10001",
                "streetAddress":"123 Main Street",
                "deliveryInstruction":"\/api\/v1\/wineyard\/delivery-instructions\/5"
            }
        }
    ]
}
 */