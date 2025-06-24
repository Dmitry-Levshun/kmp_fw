package org.scnsoft.fidekmp.data.api.untracked.dto

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class UntrackedUserWineItemById(
    val id: Int,
    val userOwnerId: String,
    val externalWine: ExternalUserWine,
    val vintage: Int,
    val qty: Int,
    val drunkQty: Int,
    val purchases: List<UntrackedUserWineItemPurchase>,
    val reviews: List<UntrackedUserWineItemReview>,
    val volumesToBottles: List<VolumesToBottle>,
)

@Serializable
data class UntrackedUserWineItemPurchase(
    val id: Int,
    val dateOfPurchase: LocalDateTime,
    val vendorName: String?,
    val price: Double,
    val qty: Int,
    val bottleVolume: Double,
)

@Serializable
data class VolumesToBottle(
    val volume: Double,
    val qty: Int,
)

@Serializable
data class UntrackedUserWineItemReview(
    val id: Int,
    val dateOfTest: LocalDateTime,
    val locationOfTest: String,
    val rating: Int,
    val tastedWith: String,
    val course: String,
    val description: String?,
    val externalUserId: String,
    val drunkItems: List<UntrackedUserWineItemDrunkItem>,
)

@Serializable
data class UntrackedUserWineItemDrunkItem(
    val volume: Double,
    val qty: Int,
)