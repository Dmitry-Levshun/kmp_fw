package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppellationDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Long,
    @SerialName("hydra:member")
    val hydraMember: List<AppellationItem>,
)

@Serializable
data class AppellationItem(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Long,
    val name: String,
)

/*
{"@context":"\/api\/v1\/wineyard\/contexts\/Geo\/Appellation","@id":"\/api\/v1\/wineyard\/geo\/appellations","@type":"hydra:Collection","hydra:totalItems":3,"hydra:member":[{"@id":"\/api\/v1\/wineyard\/geo\/appellations\/1","@type":"Geo\/Appellation","id":1,"name":"Alsace grand cru"},{"@id":"\/api\/v1\/wineyard\/geo\/appellations\/2","@type":"Geo\/Appellation","id":2,"name":"Alsace"},{"@id":"\/api\/v1\/wineyard\/geo\/appellations\/3","@type":"Geo\/Appellation","id":3,"name":"Crémant d’Alsace"}]}

{
    "@context":"\/api\/v1\/wineyard\/contexts\/Geo\/Appellation",
    "@id":"\/api\/v1\/wineyard\/geo\/appellations",
    "@type":"hydra:Collection",
    "hydra:totalItems":3,
    "hydra:member":[
        {
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/1",
            "@type":"Geo\/Appellation",
            "id":1,
            "name":"Alsace grand cru"
        },
        {
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/2",
            "@type":"Geo\/Appellation",
            "id":2,
            "name":"Alsace"
        },
        {
            "@id":"\/api\/v1\/wineyard\/geo\/appellations\/3",
            "@type":"Geo\/Appellation",
            "id":3,
            "name":"Crémant d’Alsace"
        }
    ]
}
 */