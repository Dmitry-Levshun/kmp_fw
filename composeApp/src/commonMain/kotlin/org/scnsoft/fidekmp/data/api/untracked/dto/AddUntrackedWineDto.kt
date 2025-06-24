package org.scnsoft.fidekmp.data.api.untracked.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddUntrackedWineRequest (
    val vintage: Int,  // 2021
    val bottleVolume: Double, // 0.75
    val externalWine: String, // "/api/v1/wineyard/wines/external/176000",
    val qty: Int,
    val price: Double,
    val vendorName: String,
    val dateOfPurchase: String,
)

/*
{
"vintage": 2021,
 "bottleVolume": 1.5,
 "externalWine": "/api/v1/wineyard/wines/external/176000",
 "qty": 1,
 "price": 54,
 "vendorName": "Vendor2",
 "dateOfPurchase": "2024-08-24"
 }
 */