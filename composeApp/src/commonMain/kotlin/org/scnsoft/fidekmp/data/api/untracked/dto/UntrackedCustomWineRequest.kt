package org.scnsoft.fidekmp.data.api.untracked.dto

import kotlinx.serialization.Serializable

@Serializable
data class UntrackedCustomWineRequest(
    val vintage: Int,
    val bottleVolume: Double?,
    val wineType: String,
    val name: String,
    val producerName: String?,
    val qty: Int,
    val price: Double?,
    val vendorName: String?,
    val dateOfPurchase: String,
    val country: String?,
    val region: String?,
    val appellation: String?,
    val classification: String?,
    val color: String,
)
