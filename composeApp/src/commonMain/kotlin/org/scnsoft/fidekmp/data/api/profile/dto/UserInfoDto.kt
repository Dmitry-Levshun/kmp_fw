package org.scnsoft.fidekmp.data.api.profile.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseDto(
    val person: UserInfoPerson,
    val company: UserInfoCompany,
)
@Serializable
data class UserInfoPerson(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val position: String,
    val roleId: Long,
    val accountType: UserInfoAccountType,
    val avatar: String,
    val phone: String,
    val address: String,
    val twoFactor: Boolean,
    val languageId: Long,
    val zipCode: Long,
)
@Serializable
data class UserInfoAccountType(
    val id: Long,
    val displayName: String,
)

@Serializable
data class UserInfoCompany(
    val id: String,
    val name: String,
    val billingAddress: String,
    val registrationCode: String,
    val domains: List<UserInfoDomain>,
    val organizationType: UserInfoOrganizationType,
    val holdingId: String?,
)

@Serializable
data class UserInfoDomain(
    val id: String,
    val name: String,
    val billingAddress: String,
    val registrationCode: String,
    val producers: List<UserInfoProducer>?,
)

@Serializable
data class UserInfoProducer(
    val id: String,
    val name: String,
    val zipCode: Long,
    val address: String,
    val registrationCode: String,
    val country: String,
    val region: String,
    val appellation: String,
)

@Serializable
data class UserInfoOrganizationType(
    val id: Long,
    val displayName: String,
)

/*
extend info
{
    "person":{
        "id":"a8a5baef-600b-40ce-82ee-73b5a59c6037",
        "name":"Francois",
        "surname":"Dupon",
        "email":"francois-dupon-owner@vinexquisite.fr",
        "position":"",
        "roleId":1,
        "accountType":{
            "id":1,
            "displayName":"Winemaker"
        },
        "avatar":"",
        "phone":"+33 6 12 34 56 78",
        "address":"56 Rue de la Dégustation, Paris, 75008, France",
        "twoFactor":false,
        "languageId":2,
        "zipCode":12
    },
    "company":{
        "id":"aa4befcb-c906-4727-9f54-24200a4c16f2",
        "name":"Vin Exquisite",
        "billingAddress":"56 Rue de la Dégustation, Paris, 75008, France",
        "registrationCode":"12345",
        "domains":[
            {
                "id":"65680cd5-61b9-4d82-a10a-3098e85b8a96",
                "name":"Domain 31",
                "billingAddress":"",
                "registrationCode":"",
                "producers":null
            },
            {
                "id":"e7f54f7e-5982-46fc-8705-023318566c14",
                "name":"Domain 41",
                "billingAddress":"",
                "registrationCode":"",
                "producers":null
            },
            {
                "id":"b58e09ae-15b3-492e-a5c7-209aeb656c01",
                "name":"Domain 51",
                "billingAddress":"",
                "registrationCode":"",
                "producers":null
            },
            {
                "id":"0afdd7cf-cb17-41cd-8cee-6ff5af67cb93",
                "name":"dom12",
                "billingAddress":"address12",
                "registrationCode":"12345",
                "producers":[
                    {
                        "id":"9f3ae565-b0e4-47bd-b112-0ae9c8026e6a",
                        "name":"Prod12",
                        "zipCode":12345,
                        "address":"address 123",
                        "registrationCode":"C1O2D3E4",
                        "country":"/api/v1/wineyard/geo/countries/1",
                        "region":"/api/v1/wineyard/geo/regions/13",
                        "appellation":"/api/v1/wineyard/geo/appellations/10"
                    }
                ]
            },
            {
                "id":"bfda7d5e-827e-4ab8-8272-100eefd8fc23",
                "name":"dom122",
                "billingAddress":"1213",
                "registrationCode":"1233",
                "producers":[
                    {
                        "id":"a6028d20-2fd3-4f23-8e48-0154869ff22e",
                        "name":"ыв",
                        "zipCode":33333,
                        "address":"вы",
                        "registrationCode":"3232",
                        "country":"/api/v1/wineyard/geo/countries/1",
                        "region":"/api/v1/wineyard/geo/regions/12",
                        "appellation":"/api/v1/wineyard/geo/appellations/15"
                    },
                    {
                        "id":"b2ce2418-1e3c-483b-ab5e-7c029137f370",
                        "name":"вывы",
                        "zipCode":2323,
                        "address":"323",
                        "registrationCode":"3232",
                        "country":"/api/v1/wineyard/geo/countries/1",
                        "region":"/api/v1/wineyard/geo/regions/12",
                        "appellation":"/api/v1/wineyard/geo/appellations/15"
                    },
                    {
                        "id":"d1bdd5e9-d80f-4643-abee-18044910e57e",
                        "name":"ertret",
                        "zipCode":5455,
                        "address":"45 dfgh 345",
                        "registrationCode":"ert345",
                        "country":"/api/v1/wineyard/geo/countries/1",
                        "region":"/api/v1/wineyard/geo/regions/15",
                        "appellation":"/api/v1/wineyard/geo/appellations/16"
                    }
                ]
            },
            {
                "id":"e3e07ff3-6f33-41bf-a66d-41bfa591e38c",
                "name":"4334",
                "billingAddress":"43",
                "registrationCode":"43",
                "producers":[
                    {
                        "id":"42d21af4-3c1d-4087-b2b3-533af4ff1df5",
                        "name":"45",
                        "zipCode":56666,
                        "address":"34534",
                        "registrationCode":"12345",
                        "country":"/api/v1/wineyard/geo/countries/1",
                        "region":"/api/v1/wineyard/geo/regions/12",
                        "appellation":"/api/v1/wineyard/geo/appellations/218"
                    }
                ]
            }
        ],
        "organizationType":{
            "id":1,
            "displayName":"Vineyard"
        },
        "holdingId":null
    }
}

person info
{
    "person":{
        "id":"c80fae7e-ccc4-428c-8aaa-b55824eca210",
        "name":"",
        "surname":"",
        "email":"ikaborda.ik+111@gmail.com",
        "position":"",
        "roleId":1,
        "accountType":{
            "id":4,
            "displayName":"Consumer"
        },
        "avatar":"",
        "phone":"33612345678",
        "address":"",
        "twoFactor":false,
        "languageId":2,
        "zipCode":0
    }
}
 */