package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.Serializable

@Serializable
data class DptCancelRequest(
    val abortOrder: Boolean
)
