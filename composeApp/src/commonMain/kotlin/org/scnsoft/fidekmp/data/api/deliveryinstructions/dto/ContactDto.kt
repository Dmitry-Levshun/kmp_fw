package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ContactDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Long,
    @SerialName("hydra:member")
    val hydraMember: List<ContactItem>,
)

@Serializable
data class ContactItem(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Long,
    val person: String?,
    val email: String,
    val phone: String?,
    val billingAddress: String,
//    val outgoingTransfers: List<String?>,
//    val incomingTransfers: List<Any?>,
    val outgoingDigitalPassportTransfers: List<OutgoingDigitalPassportTransfer>,
    val incomingDigitalPassportTransfers: List<IncomingDigitalPassportTransfer>,
)

@Serializable
data class OutgoingDigitalPassportTransfer(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val transfers: List<String?>,
    val invoices: List<String>,
)

@Serializable
data class IncomingDigitalPassportTransfer(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    val transfers: List<String>,
    val invoices: List<String?>,
)

/*
{"@context":"\/api\/v1\/wineyard\/contexts\/Contact","@id":"\/api\/v1\/wineyard\/contacts","@type":"hydra:Collection","hydra:totalItems":2,"hydra:member":[{"@id":"\/api\/v1\/wineyard\/contacts\/1","@type":"Contact","id":1,"person":"Francois Dupon","email":"francois-dupon-user@vinexquisite.fr","phone":"+33 6 12 34 56 78","outgoingTransfers":[],"incomingTransfers":[],"outgoingDigitalPassportTransfers":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","@type":"DigitalPassportTransfer","transfers":[],"invoices":["\/api\/v1\/wineyard\/invoices\/3","\/api\/v1\/wineyard\/invoices\/6","\/api\/v1\/wineyard\/invoices\/11"]},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","@type":"DigitalPassportTransfer","transfers":[],"invoices":["\/api\/v1\/wineyard\/invoices\/1","\/api\/v1\/wineyard\/invoices\/4","\/api\/v1\/wineyard\/invoices\/7","\/api\/v1\/wineyard\/invoices\/8","\/api\/v1\/wineyard\/invoices\/12"]},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","@type":"DigitalPassportTransfer","transfers":[],"invoices":["\/api\/v1\/wineyard\/invoices\/2","\/api\/v1\/wineyard\/invoices\/5","\/api\/v1\/wineyard\/invoices\/9","\/api\/v1\/wineyard\/invoices\/10","\/api\/v1\/wineyard\/invoices\/13"]}],"incomingDigitalPassportTransfers":[]},{"@id":"\/api\/v1\/wineyard\/contacts\/2","@type":"Contact","id":2,"person":"Sophie Lamber","email":"ec4897551@gmail.com","phone":"+33 6 67 89 01 23","outgoingTransfers":[],"incomingTransfers":[],"outgoingDigitalPassportTransfers":[],"incomingDigitalPassportTransfers":[{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","@type":"DigitalPassportTransfer","transfers":[],"invoices":["\/api\/v1\/wineyard\/invoices\/3","\/api\/v1\/wineyard\/invoices\/6","\/api\/v1\/wineyard\/invoices\/11"]},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","@type":"DigitalPassportTransfer","transfers":[],"invoices":["\/api\/v1\/wineyard\/invoices\/1","\/api\/v1\/wineyard\/invoices\/4","\/api\/v1\/wineyard\/invoices\/7","\/api\/v1\/wineyard\/invoices\/8","\/api\/v1\/wineyard\/invoices\/12"]},{"@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","@type":"DigitalPassportTransfer","transfers":[],"invoices":["\/api\/v1\/wineyard\/invoices\/2","\/api\/v1\/wineyard\/invoices\/5","\/api\/v1\/wineyard\/invoices\/9","\/api\/v1\/wineyard\/invoices\/10","\/api\/v1\/wineyard\/invoices\/13"]}]}]}

{
   "@context":"\/api\/v1\/wineyard\/contexts\/Contact",
   "@id":"\/api\/v1\/wineyard\/contacts",
   "@type":"hydra:Collection",
   "hydra:totalItems":2,
   "hydra:member":[
      {
         "@id":"\/api\/v1\/wineyard\/contacts\/1",
         "@type":"Contact",
         "id":1,
         "person":"Francois Dupon",
         "email":"francois-dupon-user@vinexquisite.fr",
         "phone":"+33 6 12 34 56 78",
         "outgoingTransfers":[

         ],
         "incomingTransfers":[

         ],
         "outgoingDigitalPassportTransfers":[
            {
               "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
               "@type":"DigitalPassportTransfer",
               "transfers":[

               ],
               "invoices":[
                  "\/api\/v1\/wineyard\/invoices\/3",
                  "\/api\/v1\/wineyard\/invoices\/6",
                  "\/api\/v1\/wineyard\/invoices\/11"
               ]
            },
            {
               "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
               "@type":"DigitalPassportTransfer",
               "transfers":[

               ],
               "invoices":[
                  "\/api\/v1\/wineyard\/invoices\/1",
                  "\/api\/v1\/wineyard\/invoices\/4",
                  "\/api\/v1\/wineyard\/invoices\/7",
                  "\/api\/v1\/wineyard\/invoices\/8",
                  "\/api\/v1\/wineyard\/invoices\/12"
               ]
            },
            {
               "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
               "@type":"DigitalPassportTransfer",
               "transfers":[

               ],
               "invoices":[
                  "\/api\/v1\/wineyard\/invoices\/2",
                  "\/api\/v1\/wineyard\/invoices\/5",
                  "\/api\/v1\/wineyard\/invoices\/9",
                  "\/api\/v1\/wineyard\/invoices\/10",
                  "\/api\/v1\/wineyard\/invoices\/13"
               ]
            }
         ],
         "incomingDigitalPassportTransfers":[

         ]
      },
      {
         "@id":"\/api\/v1\/wineyard\/contacts\/2",
         "@type":"Contact",
         "id":2,
         "person":"Sophie Lamber",
         "email":"ec4897551@gmail.com",
         "phone":"+33 6 67 89 01 23",
         "outgoingTransfers":[

         ],
         "incomingTransfers":[

         ],
         "outgoingDigitalPassportTransfers":[

         ],
         "incomingDigitalPassportTransfers":[
            {
               "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
               "@type":"DigitalPassportTransfer",
               "transfers":[

               ],
               "invoices":[
                  "\/api\/v1\/wineyard\/invoices\/3",
                  "\/api\/v1\/wineyard\/invoices\/6",
                  "\/api\/v1\/wineyard\/invoices\/11"
               ]
            },
            {
               "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
               "@type":"DigitalPassportTransfer",
               "transfers":[

               ],
               "invoices":[
                  "\/api\/v1\/wineyard\/invoices\/1",
                  "\/api\/v1\/wineyard\/invoices\/4",
                  "\/api\/v1\/wineyard\/invoices\/7",
                  "\/api\/v1\/wineyard\/invoices\/8",
                  "\/api\/v1\/wineyard\/invoices\/12"
               ]
            },
            {
               "@id":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
               "@type":"DigitalPassportTransfer",
               "transfers":[

               ],
               "invoices":[
                  "\/api\/v1\/wineyard\/invoices\/2",
                  "\/api\/v1\/wineyard\/invoices\/5",
                  "\/api\/v1\/wineyard\/invoices\/9",
                  "\/api\/v1\/wineyard\/invoices\/10",
                  "\/api\/v1\/wineyard\/invoices\/13"
               ]
            }
         ]
      }
   ]
}

 */