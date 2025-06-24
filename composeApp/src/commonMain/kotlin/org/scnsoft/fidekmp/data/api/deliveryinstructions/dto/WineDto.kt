package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.scnsoft.fidekmp.utils.currencyToSymbol

@Serializable
data class WineDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val winesTotalItems: Int,
    @SerialName("hydra:member")
    val wines: List<WineItem>,
    @SerialName("hydra:search")
    val hydraSearch: WineSearch,
)

@Serializable
data class WineItem(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val wineType: String,
    val name: String,
    val country: WineCountry,
    val region: WineRegion,
    val appellation: WineAppellation,
    val classification: String?,
    val color: String,
    val agriculture: String?,
    val vintage: Int,
    val producer: DptOrganization,
//    val winemaker: String?,
    val prices: List<WinePrice>?,
//    val champagneType: String?,
    val isPrimeurse: Boolean,
    val grapeVarieties: String,
    val alcohol: Double,
//    @SerialName("certification")
//    val certificationSt: String?,
    val certification: List<String>?,
    val sulfites: Boolean,
    val maxQty: Int,
    val allergens: String?,
    val nftId: String?,
    val bottleAmount: Double?,
    val stripeProductId: String,
//    royaltyRates: List<>
    val description: List<WineDescription>?,
    @SerialName("inStock")
    val stockQty : Int,
    val originalPrice: Double,
    val passportPrice: Double,
    val images: List<WineImage>?,
    @SerialName("domaine")
    val domaine: DigitalPasswordTransferDomain,
    val royaltyRates: List<WineRoyaltyRate>?,
    val reviews: List<WineReview>?,
    val packageList : Map<String, String>?,
    val bottleTypeName: Map<String, String>?,
    val bottleVolume: Double?, // for liverables only
    ) {
    val domain: String get() = domaine.name
    val imageUrl: String? get() = if (!images.isNullOrEmpty()) images.first().contentUrl else null
    fun getPackageList(lang: String): List<String>  {
        if (packageList != null && packageList.isNotEmpty()) {
            if (packageList?.containsKey(lang) == true) return packageList[lang]?.split(", ") ?: listOf()
            else return packageList["en"]?.split(", ") ?: listOf()
        }
        return listOf()
    }
    fun getBottleTypeName(lang: String): String { return bottleTypeName?.get(lang) ?: "" }
//    val certification: String get() = if (!certificationL.isNullOrEmpty()) certificationL[0] else certificationSt ?: ""
}

@Serializable
data class WineReview(
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

@Serializable
data class WineRoyaltyRate(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val rateLevel: Int,
    val percentage: Int
)

@Serializable
data class WineImage(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val dimensions: List<Int>,
    val contentUrl: String,
)

@Serializable
data class WineCountry(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
){
    override fun toString(): String {
        return name
    }
}

@Serializable
data class WineRegion(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
){
    override fun toString(): String {
        return name
    }
}

@Serializable
data class WineAppellation(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
){
    override fun toString(): String {
        return name
    }
}

@Serializable
data class WinePrice(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val priceType: String,
    val value: Double,
    val currency: String,
) {
    override fun toString(): String {
        return "$priceType:${currencyToSymbol(currency)}$value"
    }
//    fun toPriceWithSymbol() = PriceWithSymbol(value, currencyToSymbol(currency))
}

@Serializable
data class WineSearch(
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:template")
    val hydraTemplate: String,
    @SerialName("hydra:variableRepresentation")
    val hydraVariableRepresentation: String,
    @SerialName("hydra:mapping")
    val hydraMapping: List<WineMapping>,
)

@Serializable
data class WineMapping(
    @SerialName("@type")
    val type: String,
    val variable: String,
    val property: String,
    val required: Boolean,
)

@Serializable
data class WineDescription(
    val language: String,    // "EN"
    val text: String,
)
/*
{
"@context":"\/api\/v1\/wineyard\/contexts\/Wine",
"@id":"\/api\/v1\/wineyard\/wines",
"@type":"hydra:Collection",
"hydra:totalItems":0,
"hydra:member":[],
"hydra:search":{"@type":"hydra:IriTemplate","hydra:template":"\/api\/v1\/wineyard\/wines{?primeurse}","hydra:variableRepresentation":"BasicRepresentation","hydra:mapping":[{"@type":"IriTemplateMapping","variable":"primeurse","property":"primeurse","required":false}]}}

 {
   "@context":"\/api\/v1\/wineyard\/contexts\/Wine",
   "@id":"\/api\/v1\/wineyard\/wines",
   "@type":"hydra:Collection",
   "hydra:totalItems":9,
   "hydra:member":[
      {
         "@id":"\/api\/v1\/wineyard\/wines\/2",
         "@type":"Wine",
         "id":2,
         "wineType":"Fizzy",
         "name":"Saint-Estèphe",
         "imageUrl":"\/path\/image.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/3",
            "@type":"Geo\/Appellation",
            "name":"Crémant d’Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2019,
         "producer":"Winery Y",
         "winemaker":"John Doe",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/2",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":42,
               "currency":"EUR"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":false,
         "grapeVarieties":"Cabernet Sauvignon\/Merlot",
         "alcohol":12.5,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":10000,
         "allergens":"None",
         "nftId":"89496109379098398331435327135176210190776126940094689984518844693875020280629",
         "bottleAmount":10000,
         "stripeProductId":"prod_P6feF5Fcmg4JpA",
         "domaine":"Château Phélan Ségur"
      },
      {
         "@id":"\/api\/v1\/wineyard\/wines\/3",
         "@type":"Wine",
         "id":3,
         "wineType":"Fizzy",
         "name":"Expert Fizz",
         "imageUrl":"\/path\/image.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "name":"Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2017,
         "producer":"Winery X",
         "winemaker":"John Doe",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/3",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":18.95,
               "currency":"USD"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":false,
         "grapeVarieties":"Cabernet Sauvignon, Merlot",
         "alcohol":12.5,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":1000,
         "allergens":"None",
         "nftId":"60559346974169494097452418470406107384780248184421105795254982939188000112120",
         "bottleAmount":10000,
         "stripeProductId":"prod_P3lvdL9KRc5bGx"
      },
      {
         "@id":"\/api\/v1\/wineyard\/wines\/4",
         "@type":"Wine",
         "id":4,
         "wineType":"Fizzy",
         "name":"Another wine",
         "imageUrl":"\/path\/image.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "name":"Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2019,
         "producer":"Winery X",
         "winemaker":"John Doe",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/4",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":18.95,
               "currency":"USD"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":true,
         "grapeVarieties":"Cabernet Sauvignon, Merlot",
         "alcohol":12.5,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":1000,
         "allergens":"None",
         "nftId":"84002027592759814198718671992970298715767988485308836759913134139596764060265",
         "stripeProductId":"prod_P5fkcYqD7kABd6"
      },
      {
         "@id":"\/api\/v1\/wineyard\/wines\/5",
         "@type":"Wine",
         "id":5,
         "wineType":"Fizzy",
         "name":"Another wine",
         "imageUrl":"\/path\/image.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "name":"Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2019,
         "producer":"Winery X",
         "winemaker":"John Doe",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/5",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":18.95,
               "currency":"USD"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":true,
         "grapeVarieties":"Cabernet Sauvignon, Merlot",
         "alcohol":12.5,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":1000,
         "allergens":"None",
         "nftId":"9198974329676027699062332603522874341900199160648543551100383782601570082403",
         "stripeProductId":"prod_P5fkcYqD7kABd6"
      },
      {
         "@id":"\/api\/v1\/wineyard\/wines\/6",
         "@type":"Wine",
         "id":6,
         "wineType":"Fizzy",
         "name":"Chateau Mystique",
         "imageUrl":"\/wine_image.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "name":"Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2019,
         "producer":"Vignoble Fantastique",
         "winemaker":"Vincent Artisan",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/6",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":29.99,
               "currency":"USD"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":true,
         "grapeVarieties":"Merlot, Cabernet Sauvignon",
         "alcohol":13,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":1000,
         "allergens":"None",
         "nftId":"87315814271774687371513822770276538005091347131857945407199368450274963107684",
         "stripeProductId":"prod_P7oYotXQlmFAwR",
         "domaine":"Domaine de lEclat"
      },
      {
         "@id":"\/api\/v1\/wineyard\/wines\/7",
         "@type":"Wine",
         "id":7,
         "wineType":"Fizzy",
         "name":"Sparkling Rouge",
         "imageUrl":"https:\/\/example.com\/sparkling_rouge.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "name":"Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2019,
         "producer":"Vineyard Elegance",
         "winemaker":"Winemaster Harmony",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/7",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":34.99,
               "currency":"USD"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":true,
         "grapeVarieties":"Cabernet Franc, Syrah",
         "alcohol":13,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":1000,
         "allergens":"None",
         "nftId":"65651588920373420038357580766741366230958009557992920924741669734854354888579",
         "stripeProductId":"prod_P7obZdo4eVVjgA",
         "domaine":"Estate Splendor"
      },
      {
         "@id":"\/api\/v1\/wineyard\/wines\/8",
         "@type":"Wine",
         "id":8,
         "wineType":"Fizzy",
         "name":"Bubbly Euphoria",
         "imageUrl":"https:\/\/example.com\/bubbly_euphoria.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "name":"Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2019,
         "producer":"Vine Artistry",
         "winemaker":"Craftsman Vineyards",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/8",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":39.99,
               "currency":"USD"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":true,
         "grapeVarieties":"Pinot Noir, Grenache",
         "alcohol":13,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":1000,
         "allergens":"None",
         "nftId":"12867260669130697002983429769627894479043623027181388332870697028660098169975",
         "stripeProductId":"prod_P7ohDCZaBplDvO",
         "domaine":"Vineyard Radiance"
      },
      {
         "@id":"\/api\/v1\/wineyard\/wines\/9",
         "@type":"Wine",
         "id":9,
         "wineType":"Fizzy",
         "name":"Effervescent Elegance",
         "imageUrl":"https:\/\/example.com\/effervescent_elegance.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "name":"Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2020,
         "producer":"Artisanal Vineyards",
         "winemaker":"Master Vintner Craft",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/9",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":45.99,
               "currency":"USD"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":false,
         "grapeVarieties":"Merlot, Cabernet Franc",
         "alcohol":13,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":1000,
         "allergens":"None",
         "nftId":"82169822556193673686083857639058970668197942440153199249214878337281430204748",
         "bottleAmount":0.75,
         "stripeProductId":"prod_P7ojGHKsxRN6rf",
         "domaine":"Estate Opulence"
      },
      {
         "@id":"\/api\/v1\/wineyard\/wines\/10",
         "@type":"Wine",
         "id":10,
         "wineType":"Fizzy",
         "name":"Glamourous Bubbles",
         "imageUrl":"https:\/\/example.com\/glamourous_bubbles.jpg",
         "country":{
            "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
            "@type":"Geo\/Country",
            "name":"France"
         },
         "region":{
            "@id":"\/api\/v1\/wineyard\/geo\/regions\/4",
            "@type":"Geo\/Region",
            "name":"Champagne"
         },
         "appellation":{
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "name":"Alsace"
         },
         "classification":"Grand cru",
         "color":"Red",
         "agriculture":"Organic",
         "vintage":2013,
         "producer":"Vineyard Radiance",
         "winemaker":"Craftsman Vintages",
         "prices":[
            {
               "@id":"\/api\/v1\/wineyard\/wine\/prices\/10",
               "@type":"Wine\/Price",
               "priceType":"distributor",
               "value":49.99,
               "currency":"USD"
            }
         ],
         "champagneType":"Blanc de blanc",
         "isPrimeurse":false,
         "grapeVarieties":"Syrah, Grenache",
         "alcohol":13,
         "certification":"AOC",
         "sulfites":true,
         "maxQty":1000,
         "allergens":"None",
         "nftId":"89225183701638234794155170861169877692167986027917545852347123383855308520673",
         "bottleAmount":0.75,
         "stripeProductId":"prod_P7okSm6Uz7x2PA",
         "domaine":"Estate Splendor"
      }
   ],
   "hydra:search":{
      "@type":"hydra:IriTemplate",
      "hydra:template":"\/api\/v1\/wineyard\/wines{?primeurse}",
      "hydra:variableRepresentation":"BasicRepresentation",
      "hydra:mapping":[
         {
            "@type":"IriTemplateMapping",
            "variable":"primeurse",
            "property":"primeurse",
            "required":false
         }
      ]
   }
}

 */