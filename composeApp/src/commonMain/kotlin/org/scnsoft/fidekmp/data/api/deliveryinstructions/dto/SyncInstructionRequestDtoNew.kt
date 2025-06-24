package org.scnsoft.fidekmp.data.api.deliveryinstructions.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.scnsoft.fidekmp.utils.getTickCount

@Serializable
data class SyncInstructionRequestDtoNew (
    @SerialName("items")
    val deliveryInstructions : List<SyncDeliveryInstructionItem>
)

@Serializable
data class SyncDeliveryInstructionItem (
        @SerialName("deliveryInstructionItemId")
        val deliveryItemId: Int,
        @SerialName("orderId")
        val dptId: Int,
        val bottles: List<SyncDeliveryInstructionItemBottle>
) {
    override fun equals(other: Any?): Boolean {
        if (other !is SyncDeliveryInstructionItem) return false
        if (other === this) return true
        return dptId == other.dptId && deliveryItemId == other.deliveryItemId
    }
    override fun hashCode(): Int {
        var result = dptId.hashCode()
        result = 31 * result + deliveryItemId.hashCode()
//        result = 31 * result + bottles.hashCode()
        return result
    }
}

@Serializable
data class  SyncDeliveryInstructionItemBottle(
    val vendorSerialNumber: String, //"E26P8DNNU", -- from QR code URL
    var caseId: String?,
    val tankId: String,
    val passportAuthData: List<SyncPassportAuthData>,
    val bottlingDate: Long,
) {
    companion object {
        fun Empty(): SyncDeliveryInstructionItemBottle =
            SyncDeliveryInstructionItemBottle( "", "", "", listOf(SyncPassportAuthData.Empty()), getTickCount())
    }
}

@Serializable
data class SyncPassportAuthData (
    val dataType: String, //"qr",  "nfc", "photo",
    val value: String   //"HTTP://MT1.FR/E26P8DNNU"
) {
    companion object {
        fun Empty(): SyncPassportAuthData = SyncPassportAuthData("", "")
    }

}

@Serializable
data class SyncInstructionResponseDto(
    val batchId: Int?,
    val deliveryInstructionItemId: Int,
    val status: String,
    val errorMessage: String?,
    val failedItems: List<FailedItem>?,
    @SerialName("failedDIItemCount")
    val failedDiItemCount: Int?,
)

@Serializable
data class FailedItem(
    val deliveryInstructionItemId: Int?,
    val errorMessage: String,
)
/*
{"status":"error",
"failedItems":[{
    "deliveryInstructionItemId":117,
    "errorMessage":"An exception occurred while executing a query: SQLSTATE[23505]: Unique violation: 7 ERROR:  duplicate key value violates unique constraint \u0022unique_dwp_auth_type_auth_value\u0022\nDETAIL:  Key (auth_type, auth_value)=(NFC, 579386F2500104E0) already exists."
    }],
    "batchId":210,"failedDIItemCount":1}
response:
{"batchId":3,"status":"error","errorMessage":"","failedItems":[{"deliveryInstructionItemId":null,"errorMessage":"orderId field does not exist in payload item"}],"failedDIItemCount":0}
{"batchId":4,"status":"error","errorMessage":"","failedItems":[{"orderId":377,"errorMessage":"deliveryInstructionItemId does not exist in payload item"}],"failedDIItemCount":0}
req:
{
  "items": [
    {
      "deliveryItemId": 49,
      "orderId": 320,
      "bottles": [
        {
          "vendorSerialNumber": "E26P8DNNU", -- from QR code URL
          "passportAuthData": [
            {
              "dataType": "qr",
              "value": "HTTP://MT1.FR/E26P8DNNU"
            },
            {
              "dataType": "nfc",
              "value": "nfccode"
            },
            {
              "dataType": "photo",
              "value": "http://linktophoto"
            }
          ],
          "caseId": "optional",
          "tankId": "tankId" -- TBD how to change it
        }
      ]
    }
  ]
}

     "deliveryInstuctionItemId": 49,
      "orderId": 320,
      "bottles": [
        {
          "vendorSerialNumber": "E26P8DNNU", -- from QR code URL
          "passportAuthData": [
            {
              "dataType": "qr",
              "value": "HTTP://MT1.FR/E26P8DNNU"
            },
            {
              "dataType": "nfc",
              "value": "nfccode"
            },
            {
              "dataType": "photo",
              "value": "http://linktophoto"
            }
          ],
          "caseId": "optional",
          "tankId": "tankId" -- TBD how to change it
            "bottlingDate": timeIntervalSecSince1970
        }
      ]
    }
  ]
}
 */
