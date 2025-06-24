package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SyncInstructionsRequestDto(
    @SerialName("items")
    val deliveryInstructions : List<SyncDeliveryInstruction>
)

@Serializable
data class SyncDeliveryInstruction (
    val cases:  List<SyncCase>,
    val id: Int,
    val diId: Int,
    val dptId: Int,
    val tankId: String,
)

@Serializable
data class SyncCase (
    val bottles: List<SyncBottle> ,
    val id: String,
    val qrCode: String?,
)

@Serializable
data class SyncBottle (
    val bottleId: String,
    val linksToPhoto: List<String>?,
    val nfcCodes: List<String>? ,
    val qrCodes: List<String>?,
    val volume: String?
)