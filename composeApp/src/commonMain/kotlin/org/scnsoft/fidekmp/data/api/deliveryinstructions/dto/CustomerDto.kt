package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerDto (
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val customerTotalItems: Long,
    @SerialName("hydra:member")
    val customerItems: List<CustomerItemDto>,
)

@Serializable
data class CustomerItemDto(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
val type: String,
    @SerialName("id")
    val id: String,
    val contactType: String,
    val companyName: String?,
    val name: String?,
    val domainName: String?,
    val producer: String?,
    val email: String,
    val phone: String,
    val city: String?,
    val country: String?,
    val address: String?,
    val walletValue: Double?,
    val boughtTotalPrice: Double?,
    val soldTotalPrice: Double?,
    val firstPurchaseDate: String,
    val lastPurchaseDate: String,
)