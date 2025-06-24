package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse (
    val version: String, //"1.2.14",
    val android: String, // "1.0.0",
    val ios : String, //"1.0.0"
)