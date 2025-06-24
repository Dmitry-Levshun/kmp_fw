package org.scnsoft.fidekmp.data.api.untracked.dto

import kotlinx.serialization.Serializable

@Serializable
data class UntrackedWineReviewRequest(
    val userExternalWine: String,
    val drunkItems: List<UntrackedWineReviewRequestDrunkItem>,
    val course: String,
    val description: String? = null,
    val tastedWith: String? = null,
    val locationOfTest: String? = null,
    val rating: Int,
    val dateOfTest: String,
)

@Serializable
data class UntrackedWineReviewRequestDrunkItem(
    val volume: Double,
    val qty: Int,
)
