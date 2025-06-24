package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrCodeResponseNoLogin(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val bottlingDate: LocalDate,
    val wine: NoLoginWine,
    val bottleVolume: Double,
    val bottleTypeName: NoLoginBottleTypeName,
    val owner: String,
)

@Serializable
data class NoLoginWine(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val wineType: String,
    val name: String,
    val images: List<WineImage>?,
    val country: String,
    val region: String,
    val appellation: String,
    val color: String,
    val vintage: Long,
    val isPrimeurse: Boolean,
    val grapeVarieties: String,
    val alcohol: Double,
    val producer: NoLoginProducer,
    val bottleVolume: Double,
    val domaine: NoLoginDomaine,
    val description: List<NoLoginDescription>,
    val reviews: List<WineReview>?,
) {
    val domain: String get() = domaine.name
    val producerName: String get() = producer.name
    val id: Int get() =  idUrl.substringAfterLast('/', "0").toInt()
    val imageUrl: String? get() = if (!images.isNullOrEmpty()) images.first().contentUrl else null
}

@Serializable
data class NoLoginProducer(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
)

@Serializable
data class NoLoginDomaine(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
)

@Serializable
data class NoLoginDescription(
    val language: String,
    val text: String,
)

@Serializable
data class NoLoginBottleTypeName(
    val en: String,
    val fr: String,
)
