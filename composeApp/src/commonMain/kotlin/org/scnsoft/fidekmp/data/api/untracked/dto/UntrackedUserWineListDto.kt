package org.scnsoft.fidekmp.data.api.untracked.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UntrackedUserWineListDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Int,
    @SerialName("hydra:member")
    val wineList: List<UntrackedUserWineItem>,
    @SerialName("hydra:view")
    val hydraView: HydraView,
)

@Serializable
data class UntrackedUserWineItem(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val externalWine: ExternalUserWine,
    val vintage: Int,
    val qty: Int,
    val drunkQty: Int,
    val reviewsCount: Int,
)

@Serializable
data class ExternalUserWine(
    @SerialName("@id")
    val idUrl: String?,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val lwinId: Long,
    @SerialName("wineType")
    val wineType: String,
    @SerialName("type")
    val drinkType: String,
    @SerialName("name")
    val wineName: String,
    val color: String? = "White",
    val country: String?,
    val region: String?,
    val appellation: String?,
    val classification: String?,
    val producerName: String?,
)

@Serializable
data class HydraView(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
)
