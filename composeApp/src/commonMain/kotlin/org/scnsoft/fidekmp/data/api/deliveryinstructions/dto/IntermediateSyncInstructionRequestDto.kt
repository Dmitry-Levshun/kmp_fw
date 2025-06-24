package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.Serializable

@Serializable
data class IntermediateSyncInstructionRequestDto(
    val items: List<IntermediateSyncInstructionItem>
)

@Serializable
data class IntermediateSyncInstructionItem(
    val orderId: Int,
    val deliveryInstructionItemId: Int,
    val cases: List<String>?,
    val bottles: List<Int?>?,
)