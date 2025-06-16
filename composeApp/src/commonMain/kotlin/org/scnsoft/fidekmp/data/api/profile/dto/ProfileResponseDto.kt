package org.scnsoft.fidekmp.data.api.profile.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponseDto(
    val avatar: String,
    val company: String,
    val email: String,
    @SerialName("fullName")
    val fullName: String,
    val position: String,
    val role: RoleType,
    @SerialName("userName")
    val userName: String?,
    @SerialName("walletAddress")
    val walletAddress: String?,
    @SerialName("accountType")
    val accountType: UserType,
    val phone: String?,
    val billAddress: String?,
    val language: RoleType, // "language":{"id":2,"name":"French"}}
    val id: String,
    val deleteRequestedAt: String?,
    val permissions: List<String>  //["home_page","my_cellar","passports_transfer","create_passports_transfer","delivery_instructions","history","contacts","customers","account_settings","users_management","activity_logs","mobile_scanning","mobile_wallet"],
)

@Serializable
data class UserType(
    @SerialName("displayName")
    val displayName: String,
    val id: Int
)

@Serializable
data class RoleType(
    val name: String,
    val id: Int
)

/*
{"userName":"francois-dupon-owner","fullName":"Francois Dupon","email":"francois-dupon-owner@vinexquisite.fr","company":"Vin Exquisite","position":"","role":{"id":1,"name":"Owner"},"accountType":{"id":1,"displayName":"Winemaker"},"avatar":"","walletAddress":"0x1AebbE69459B80d4975259378577Bc01d2924Cf4","phone":"+33 6 12 34 56 78","billAddress":"56 Rue de la Dégustation, Paris, 75008, France","language":{"id":2,"name":"French"}}
{
    "id":"a8a5baef-600b-40ce-82ee-73b5a59c6037",
    "userName":"francois-dupon-owner",
    "fullName":"Francois Dupon",
    "email":"francois-dupon-owner@vinexquisite.fr",
    "company":"Vin Exquisite",
    "position":"",
    "role":{
        "id":1,
        "name":"Owner"
    },
    "accountType":{
        "id":1,
        "displayName":"Winemaker"
    },
    "avatar":"",
    "walletAddress":"0x1AebbE69459B80d4975259378577Bc01d2924Cf4",
    "phone":"+33 6 12 34 56 78",
    "billAddress":"56 Rue de la Dégustation, Paris, 75008, France",
    "language":{
        "id":2,
        "name":"French"
    }
}

{"userName":"jdoe",
"fullName":"Jane Doe",
"email":"user2@gmail.com",
"company":"SCN1",
"position":"sale",
"role":{"id":2,"name":"Employee"},
"accountType":{"id":1,"displayName":"Winemaker"},
"avatar":"http://link_to_avatar.net",
"walletAddress":"0x54C0897A1E281b107EeE25d4f8eEe5F6ae13f9d9",
"phone":"+33 12-00-0001",
"billAddress":"1 build, street, town, region, country"}
 */
/*

  "avatar": <string>",
  "company": "<string>",
  "email": "<string>",
  "full_name": "<string>",
  "position": "<string>",
  "role": "<string>",
  "user_name": "<string>",
  "user_type": {
    "display_name": " distributor",
    "id": 1
  }
  {"user_name":"jdoe","full_name":"Jane Doe","email":"user2@gmail.com","company":"SCN","position":"sale","role":"User","user_type":{"id":1,"display_name":"winemaker"},"avatar":"http://link_to_avatar.net"}
} */