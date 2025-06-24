package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class TransferHistoryItemDto(
    val buyerType: Int,
    val buyerTypeName: String,
    val sellerType: Int,
    val sellerTypeName: String,
    val transferDate: LocalDate,
    val country: String,
    val city: String,
    val longitude: Double,
    val latitude: Double,
    val distance: Double?,
)

/*
{
      "buyerType":2,
      "buyerTypeName":"Intermediate",
      "sellerType":2,
      "sellerTypeName":"Intermediate",
      "transferDate":"2024-08-17T00:00:00+00:00",
      "country":"US",
      "city":"New York",
      "longitude":-73.967596,
      "latitude":40.777048
   },
 */