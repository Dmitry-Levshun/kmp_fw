package org.scnsoft.fidekmp.data.api.untracked.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineReview

@Serializable
data class UntrackedWineListDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Int,
    @SerialName("hydra:member")
    val wineList: List<UntrackedWineItem>,
)

@Serializable
data class UntrackedWineItem(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val lwinId: Int,
    @SerialName("wineType")
    val wineType: String,
    @SerialName("type")
    val drinkType: String,
    @SerialName("name")
    val wineName: String,
    val country: String?,
    val region: String?,
    val color: String,
    val producerName: String?,
    val appellation: String?,
    val classification: String?,
    val reviews: List<WineReview?>,
    var query: String? = ""
) {
    fun contains(text: String): Boolean {
        return wineName.contains(text, true) || (producerName != null && producerName.contains(text, true))
    }
}

/*
{
    "@context":"\/api\/v1\/wineyard\/contexts\/ExternalWine",
    "@id":"\/api\/v1\/wineyard\/wines\/external",
    "@type":"hydra:Collection",
    "hydra:totalItems":176381,
    "hydra:member":[
        {
            "@id":"\/api\/v1\/wineyard\/wines\/external\/1",
            "@type":"ExternalWine",
            "id":1,
            "lwinId":1000001,
            "wineType":"Still",
            "type":"Wine",
            "name":"Lieu Dit Buehl Riesling",
            "country":"France",
            "region":"Alsace",
            "color":"White",
            "producerName":"Schieferkopf"
        },
        {
            "@id":"\/api\/v1\/wineyard\/wines\/external\/2",
            "@type":"ExternalWine",
            "id":2,
            "lwinId":1000014,
            "wineType":"Still",
            "type":"Wine",
            "name":"Riesling",
            "country":"France",
            "region":"Alsace",
            "appellation":"Kastelberg",
            "classification":"Grand Cru",
            "color":"White",
            "producerName":"Schieferkopf"
        }
    ]
}
 */