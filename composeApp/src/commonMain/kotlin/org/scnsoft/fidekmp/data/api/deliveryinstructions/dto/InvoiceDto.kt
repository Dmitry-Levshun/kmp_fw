package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceDto(
    @SerialName("@context")
    val context: String,
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("hydra:totalItems")
    val hydraTotalItems: Long,
    @SerialName("hydra:member")
    val hydraMember: List<InvoiceItem>,
)

@Serializable
data class InvoiceItem(
    @SerialName("@id")
    val idUrl: String,
    @SerialName("@type")
    val type: String,
    @SerialName("id")
    val id: Long,
    val orderLink: String,
    val externalInvoiceId: String,
    val externalInvoiceUrl: String?,
)

/*
{"@context":"\/api\/v1\/wineyard\/contexts\/Invoice","@id":"\/api\/v1\/wineyard\/invoices","@type":"hydra:Collection","hydra:totalItems":19,"hydra:member":[{"@id":"\/api\/v1\/wineyard\/invoices\/1","@type":"Invoice","id":1,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","externalInvoiceId":"in_1OISSdFSrxJ11FojEPl6Dqwl","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmZvUzg4MWhVY2I4SkV4dGl3dWJweGtVNzl6d2Y3LDkxOTYyMDE20200Obc1Ttgh\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/2","@type":"Invoice","id":2,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","externalInvoiceId":"in_1OISnhFSrxJ11FojL0ftnGr9","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmdBemhOVVpFV3NPOUxGSVl4VHpMMWlOUG02NVZvLDkxOTYzMTM50200YiC6BkNz\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/3","@type":"Invoice","id":3,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","externalInvoiceId":"in_1OISSaFSrxJ11FojmGlo4hrD","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmZvdlBGdDVUV2k1Q1B5WXllaVR0amVmTDVPcmhQLDkxOTY1MTUw0200TS6zYoXh\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/4","@type":"Invoice","id":4,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","externalInvoiceId":"in_1OISSdFSrxJ11FojEPl6Dqwl"},{"@id":"\/api\/v1\/wineyard\/invoices\/5","@type":"Invoice","id":5,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","externalInvoiceId":"in_1OISnhFSrxJ11FojL0ftnGr9"},{"@id":"\/api\/v1\/wineyard\/invoices\/6","@type":"Invoice","id":6,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","externalInvoiceId":"in_1OIUm9FSrxJ11FojAJT3cKTl","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmlDUWVldWxubndIb3JETWV4ZkRUOXRZU3kyaXRvLDkxOTcwNDU00200owlIiO4z\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/7","@type":"Invoice","id":7,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","externalInvoiceId":"in_1OIUp6FSrxJ11FojafrJvuTc","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmlGOTBEWEU3cmZIYnAzYUQxSVBSa0JNSXBiczEwLDkxOTcwNjM40200tNYDYbXE\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/8","@type":"Invoice","id":8,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","externalInvoiceId":"in_1OISSdFSrxJ11FojEPl6Dqwl"},{"@id":"\/api\/v1\/wineyard\/invoices\/9","@type":"Invoice","id":9,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","externalInvoiceId":"in_1OIVOjFSrxJ11Fojfpmp7TkS","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmlxSVR5bUhqSWZ0enZjcHQxUktSTlBRcmxBOXZ4LDkxOTcyODQ30200wLQ354hf\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/10","@type":"Invoice","id":10,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","externalInvoiceId":"in_1OISnhFSrxJ11FojL0ftnGr9"},{"@id":"\/api\/v1\/wineyard\/invoices\/11","@type":"Invoice","id":11,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","externalInvoiceId":"in_1OIWCVFSrxJ11Foj3J73rKgP","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmpmUFY3eTEwT0xoU0VnQUlITnNpYkMzRWE0V1lPLDkxOTc1OTMy0200yeTdHpZV\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/12","@type":"Invoice","id":12,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","externalInvoiceId":"in_1OIWDAFSrxJ11FojnTEvlGBM","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmpnYkxIdHVsUWJ6SjB4QVRnNWZWa21LTmNXQWRVLDkxOTc1OTcz0200gutlk60O\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/13","@type":"Invoice","id":13,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","externalInvoiceId":"in_1OIWDWFSrxJ11FojY2lgRtYy","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmpnTk83THREMjRUVXVzUHozZ2pOUTdnVE5WVmR0LDkxOTc1OTk10200SweXowEH\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/14","@type":"Invoice","id":14,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","externalInvoiceId":"in_1OJI6EFSrxJ11FojkHGz1rYi","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hBRkpWMzh1cXA3UkxEMUplSDQ2VWpOVlZnUnZhLDkyMTYwMDU102007QLxH6f4\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/15","@type":"Invoice","id":15,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","externalInvoiceId":"in_1OJI6ZFSrxJ11FojTJxXRAkz","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hBME9ZVHFKdTVBVDNzS0M0NndtU3JrNnpoSnFiLDkyMTYwMDc20200Uw4GRtg0\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/16","@type":"Invoice","id":16,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6","externalInvoiceId":"in_1OJI75FSrxJ11FojQoh40Qhx","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hCRTJna2NHZkRwOVNteU5ua09WblRTQ3BqOU5PLDkyMTYwMTA402005YeesGK6\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/17","@type":"Invoice","id":17,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","externalInvoiceId":"in_1OJIipFSrxJ11FojWPVkKPrv","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hvaXdUOUlzcG1CZW1UZVl6eWhoNXJrNkRZd1ByLDkyMTYyNDQ402002CZZoGXS\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/18","@type":"Invoice","id":18,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5","externalInvoiceId":"in_1OJIjKFSrxJ11FojmeuyR2Z9","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hvRG5jQXVUd0EyWHRqNEFFdGtLQlVPNHpLQW0xLDkyMTYyNDc50200OteaxFtD\/pdf?s=ap"},{"@id":"\/api\/v1\/wineyard\/invoices\/19","@type":"Invoice","id":19,"orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4","externalInvoiceId":"in_1OJJDdFSrxJ11FojWgiCrlH7","externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1lLdFBEUWEyZG5EZWFQbzkzSUdIbjBra3pmNURqLDkyMTY0MzU50200DuU29Wrr\/pdf?s=ap"}]}

{
    "@context":"\/api\/v1\/wineyard\/contexts\/Invoice",
    "@id":"\/api\/v1\/wineyard\/invoices",
    "@type":"hydra:Collection",
    "hydra:totalItems":19,
    "hydra:member":[
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/1",
            "@type":"Invoice",
            "id":1,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
            "externalInvoiceId":"in_1OISSdFSrxJ11FojEPl6Dqwl",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmZvUzg4MWhVY2I4SkV4dGl3dWJweGtVNzl6d2Y3LDkxOTYyMDE20200Obc1Ttgh\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/2",
            "@type":"Invoice",
            "id":2,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
            "externalInvoiceId":"in_1OISnhFSrxJ11FojL0ftnGr9",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmdBemhOVVpFV3NPOUxGSVl4VHpMMWlOUG02NVZvLDkxOTYzMTM50200YiC6BkNz\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/3",
            "@type":"Invoice",
            "id":3,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
            "externalInvoiceId":"in_1OISSaFSrxJ11FojmGlo4hrD",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmZvdlBGdDVUV2k1Q1B5WXllaVR0amVmTDVPcmhQLDkxOTY1MTUw0200TS6zYoXh\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/4",
            "@type":"Invoice",
            "id":4,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
            "externalInvoiceId":"in_1OISSdFSrxJ11FojEPl6Dqwl"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/5",
            "@type":"Invoice",
            "id":5,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
            "externalInvoiceId":"in_1OISnhFSrxJ11FojL0ftnGr9"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/6",
            "@type":"Invoice",
            "id":6,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
            "externalInvoiceId":"in_1OIUm9FSrxJ11FojAJT3cKTl",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmlDUWVldWxubndIb3JETWV4ZkRUOXRZU3kyaXRvLDkxOTcwNDU00200owlIiO4z\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/7",
            "@type":"Invoice",
            "id":7,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
            "externalInvoiceId":"in_1OIUp6FSrxJ11FojafrJvuTc",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmlGOTBEWEU3cmZIYnAzYUQxSVBSa0JNSXBiczEwLDkxOTcwNjM40200tNYDYbXE\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/8",
            "@type":"Invoice",
            "id":8,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
            "externalInvoiceId":"in_1OISSdFSrxJ11FojEPl6Dqwl"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/9",
            "@type":"Invoice",
            "id":9,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
            "externalInvoiceId":"in_1OIVOjFSrxJ11Fojfpmp7TkS",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmlxSVR5bUhqSWZ0enZjcHQxUktSTlBRcmxBOXZ4LDkxOTcyODQ30200wLQ354hf\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/10",
            "@type":"Invoice",
            "id":10,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
            "externalInvoiceId":"in_1OISnhFSrxJ11FojL0ftnGr9"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/11",
            "@type":"Invoice",
            "id":11,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
            "externalInvoiceId":"in_1OIWCVFSrxJ11Foj3J73rKgP",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmpmUFY3eTEwT0xoU0VnQUlITnNpYkMzRWE0V1lPLDkxOTc1OTMy0200yeTdHpZV\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/12",
            "@type":"Invoice",
            "id":12,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
            "externalInvoiceId":"in_1OIWDAFSrxJ11FojnTEvlGBM",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmpnYkxIdHVsUWJ6SjB4QVRnNWZWa21LTmNXQWRVLDkxOTc1OTcz0200gutlk60O\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/13",
            "@type":"Invoice",
            "id":13,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
            "externalInvoiceId":"in_1OIWDWFSrxJ11FojY2lgRtYy",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QNmpnTk83THREMjRUVXVzUHozZ2pOUTdnVE5WVmR0LDkxOTc1OTk10200SweXowEH\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/14",
            "@type":"Invoice",
            "id":14,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
            "externalInvoiceId":"in_1OJI6EFSrxJ11FojkHGz1rYi",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hBRkpWMzh1cXA3UkxEMUplSDQ2VWpOVlZnUnZhLDkyMTYwMDU102007QLxH6f4\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/15",
            "@type":"Invoice",
            "id":15,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
            "externalInvoiceId":"in_1OJI6ZFSrxJ11FojTJxXRAkz",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hBME9ZVHFKdTVBVDNzS0M0NndtU3JrNnpoSnFiLDkyMTYwMDc20200Uw4GRtg0\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/16",
            "@type":"Invoice",
            "id":16,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/6",
            "externalInvoiceId":"in_1OJI75FSrxJ11FojQoh40Qhx",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hCRTJna2NHZkRwOVNteU5ua09WblRTQ3BqOU5PLDkyMTYwMTA402005YeesGK6\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/17",
            "@type":"Invoice",
            "id":17,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
            "externalInvoiceId":"in_1OJIipFSrxJ11FojWPVkKPrv",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hvaXdUOUlzcG1CZW1UZVl6eWhoNXJrNkRZd1ByLDkyMTYyNDQ402002CZZoGXS\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/18",
            "@type":"Invoice",
            "id":18,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/5",
            "externalInvoiceId":"in_1OJIjKFSrxJ11FojmeuyR2Z9",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1hvRG5jQXVUd0EyWHRqNEFFdGtLQlVPNHpLQW0xLDkyMTYyNDc50200OteaxFtD\/pdf?s=ap"
        },
        {
            "@id":"\/api\/v1\/wineyard\/invoices\/19",
            "@type":"Invoice",
            "id":19,
            "orderLink":"\/api\/v1\/wineyard\/digital-passport-transfers\/4",
            "externalInvoiceId":"in_1OJJDdFSrxJ11FojWgiCrlH7",
            "externalInvoiceUrl":"https:\/\/pay.stripe.com\/invoice\/acct_1O34gCFSrxJ11Foj\/test_YWNjdF8xTzM0Z0NGU3J4SjExRm9qLF9QN1lLdFBEUWEyZG5EZWFQbzkzSUdIbjBra3pmNURqLDkyMTY0MzU50200DuU29Wrr\/pdf?s=ap"
        }
    ]
}
 */