package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.Serializable

@Serializable
data class InventoryRequestDto(
    val wineId: Int,
    val caseTypeId: Int,
    val bottles: List<InventoryBottle>,
)

@Serializable
data class InventoryBottle(
    val vendorSerialNumber: String,
    val passportAuthData: List<InventoryPassportAuthData>,
    var caseId: String?,
    val tankId: String? = null,
    val bottlingDate: Long?,
)

@Serializable
data class InventoryPassportAuthData(
    val dataType: String,
    val value: String,
)

/*
{
    "wineId":36,
    "caseTypeId":23,
    "bottles":[
        {
            "vendorSerialNumber":"E26P8DNNU",
            "passportAuthData":[
                {
                    "dataType":"qr",
                    "value":"HTTP://MT1.FR/E26P8DNNU"
                },
                {
                    "dataType":"nfc",
                    "value":"nfccode"
                },
                {
                    "dataType":"photo",
                    "value":"http://linktophoto"
                }
            ],
            "caseId":"optional",
            "tankId":"tankId",
            "bottlingDate":null
        },
        {
            "vendorSerialNumber":"E5P8DNNXU",
            "passportAuthData":[
                {
                    "dataType":"qr",
                    "value":"HTTP://MT1.FR/E5P8DNNXU"
                },
                {
                    "dataType":"nfc",
                    "value":"nfccode1"
                },
                {
                    "dataType":"photo",
                    "value":"http://linktophoto5"
                }
            ],
            "caseId":"optional",
            "tankId":"tankId",
            "bottlingDate":178784548552
        },
        {
            "vendorSerialNumber":"Q26P7DNTV",
            "passportAuthData":[
                {
                    "dataType":"qr",
                    "value":"HTTP://MT1.FR/Q26P7DNTV"
                },
                {
                    "dataType":"nfc",
                    "value":"nfccode2"
                },
                {
                    "dataType":"photo",
                    "value":"http://linktophoto4"
                }
            ],
            "caseId":"optional",
            "tankId":"tankId",
            "bottlingDate":178784548552
        },
        {
            "vendorSerialNumber":"SE6P8HNJI",
            "passportAuthData":[
                {
                    "dataType":"qr",
                    "value":"HTTP://MT1.FR/SE6P8HNJI"
                },
                {
                    "dataType":"nfc",
                    "value":"nfccode3"
                },
                {
                    "dataType":"photo",
                    "value":"http://linktophoto3"
                }
            ],
            "caseId":"optional",
            "tankId":"tankId",
            "bottlingDate":178784548552
        },
        {
            "vendorSerialNumber":"A56P8CNXT",
            "passportAuthData":[
                {
                    "dataType":"qr",
                    "value":"HTTP://MT1.FR/A56P8CNXT"
                },
                {
                    "dataType":"nfc",
                    "value":"nfccode4"
                },
                {
                    "dataType":"photo",
                    "value":"http://linktophoto2"
                }
            ],
            "caseId":"optional",
            "tankId":"tankId",
            "bottlingDate":178784548552
        },
        {
            "vendorSerialNumber":"Z19W8MNQ",
            "passportAuthData":[
                {
                    "dataType":"qr",
                    "value":"HTTP://MT1.FR/Z19W8MNQ"
                },
                {
                    "dataType":"nfc",
                    "value":"nfccode5"
                },
                {
                    "dataType":"photo",
                    "value":"http://linktophoto1"
                }
            ],
            "caseId":"optional",
            "tankId":"tankId",
            "bottlingDate":null
        }
    ]
}
 */