package org.scnsoft.fidekmp.data.api.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryListDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val id: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Long,
    @SerialName("hydra:member")
    val hydraMembers: List<HydraMember>,
)

@Serializable
data class HydraMember(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Int,
    val name: String,
    val isoCode: String
)
/*
{
   "@context":"\/api\/v1\/wineyard\/contexts\/Geo\/Country",
   "@id":"\/api\/v1\/wineyard\/geo\/countries",
   "@type":"hydra:Collection",
   "hydra:totalItems":1,
   "hydra:member":[
      {
         "@id":"\/api\/v1\/wineyard\/geo\/countries\/1",
         "@type":"Geo\/Country",
         "id":1,
         "name":"France"
      }
   ]
}
 */