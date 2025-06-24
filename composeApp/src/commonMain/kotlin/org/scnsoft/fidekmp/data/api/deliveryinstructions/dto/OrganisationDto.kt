package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrganisationDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Long,
    @SerialName("hydra:member")
    val organisations: List<Organisation>,
)

@Serializable
data class Organisation(
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    val name: String,
    val extOrganizationId: String,
)

/*
{
    "@context":"\/api\/v1\/wineyard\/contexts\/Organization",
    "@id":"\/api\/v1\/wineyard\/organizations",
    "@type":"hydra:Collection",
    "hydra:totalItems":2,
    "hydra:member":[
    {
        "@id":"\/api\/v1\/wineyard\/organizations\/1",
        "@type":"Organization",
        "name":"Vin Exquisite",
        "extOrganizationId":"aa4befcb-c906-4727-9f54-24200a4c16f2"
    },
    {
        "@id":"\/api\/v1\/wineyard\/organizations\/2",
        "@type":"Organization",
        "name":"Les Vignobles Winery",
        "extOrganizationId":"0c855f32-f5a1-4eb0-ac7e-42dbfc9356a5"
    }
    ]
}

 */