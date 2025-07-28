package org.scnsoft.fidekmp.data.api.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LoginResponseDto(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("tokenType") val tokenType: String,
    @SerialName("tokenID") val tokenID: String? = null,
    @SerialName("tokenId") val tokenId: String? = null,
    @SerialName("refreshToken") var refreshToken: String?
)
/*
access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
"refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJyZWZyZXNoX3Rva2VuIjp0cnVlfQ.zmG_1f-MjrRAuavUX1WUxjV_nxaruUlu6pXdprJZwx4",
"token_id": "d20477b6-02ec-4a9d-8674-cc34cf3ff72e",
"token_type": "bearer"

NEW
{"tokenID":"7b997449-e76a-4ea4-9ec4-7f6ef47ac640",
"accessToken":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cG4iOiJqZG9lIiwiZW1haWwiOiJ1c2VyMkBnbWFpbC5jb20iLCJ3YWxsZXRfYWRkcmVzcyI6IjB4NTRDMDg5N0ExRTI4MWIxMDdFZUUyNWQ0ZjhlRWU1RjZhZTEzZjlkOSIsIm9yZ19pZCI6IjQ3NWMzZjE1LTY3NTUtNGJhYy1iZDExLTFhOWMyNzE2YmNjNiIsImFjY291bnRfdHlwZSI6MSwidG9rZW5fdHlwZSI6ImFjY2VzcyIsInJvbGUiOnsiaWQiOjIsIm5hbWUiOiJVc2VyIn0sImRvbWFpbiI6W3siaWQiOiI1MWI4NzhhZC03MmRiLTQ5OGItOGFhYi0zMTNiZjYyYjA2YjMiLCJuYW1lIjoiRG9tYWluMiJ9XSwiaXNzIjoiYXV0aG4tc2VydmljZSIsInN1YiI6ImVmMTZjYTdkLTNjY2YtNDdkZC1iZTRhLTIxOGZhMDY1OTA3MSIsImF1ZCI6WyJhY2NvdW50Il0sImV4cCI6MTY5ODk1NjYwNCwibmJmIjoxNjk4OTQ1ODA0LCJpYXQiOjE2OTg5NDU4MDQsImp0aSI6IjdiOTk3NDQ5LWU3NmEtNGVhNC05ZWM0LTdmNmVmNDdhYzY0MCJ9.V1xJUEdd8lU3wo-hdSzrnBgdEA8mNSrrG0dFBgIz7mIwa7CrWaFbspMKbSl7aDOrwWYMLUB7FrZVlC5-4h-EUJitAT9U3ErmyDeF_F5-0vjWgJHW0sIR4DgaBI2e6BANLUOZDmI7MDTw1n9lOow8qR5kxTeThCFo8Xh2L2UaKfhC37rNPPmtqVCd17GrJs7Wlz6_FNoTVB0WDYzf6WdUwOMX_AQLxAPYnTlXN_FOY7Zqttwn3Q5fU6GDs_nOcSW6iEACM2-tCP9vShspRVPbjKsYZ-6sZ8C8faVnuEnNkrIyHiO20tF1NlLIRmADUbOa2DI3p6qCT1_MXmstLhoRJA",
"refreshToken":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cG4iOiJqZG9lIiwiZW1haWwiOiJ1c2VyMkBnbWFpbC5jb20iLCJhY2NvdW50X3R5cGUiOjEsInRva2VuX3R5cGUiOiJyZWZyZXNoIiwicm9sZSI6eyJpZCI6MCwibmFtZSI6IiJ9LCJpc3MiOiJhdXRobi1zZXJ2aWNlIiwic3ViIjoiZWYxNmNhN2QtM2NjZi00N2RkLWJlNGEtMjE4ZmEwNjU5MDcxIiwiZXhwIjoxNjk5MDMyMjA0LCJuYmYiOjE2OTg5NDU4MDQsImlhdCI6MTY5ODk0NTgwNCwianRpIjoiYzg0MDZhMGItMDM0Zi00NGZhLTgyZTctMDg0ODgyYmViNjQxIn0.dL6dQowSSxCailfZqDMcF_KTbUWZLCSmiePzKypzjYnJVo90iJR19YBFgsyyD1wpyGcCsQvArEDL4Z8oQY-4fga2JySTxXCj6TtPnrkZ6HMTh0zC8eEtSaQNxYkJQX7JCObD5jt4_4sMz8sxJPyFqsedJ2vEuvkYaOf3DJzchqsxAOPuCMwGbCaz6kvoJyuADIVan24TfGK-PntQ2Sng6b5MIbW19JyxzveoqpUzByhcumVs1iz6YOAP5epKbGQ8s5128axye7tdmKF82b1XyOp9TEPWtbFWf5gjIdGKwJ9Psrr-AvAI_vfibsmCNl18NErUPQH5qA3ea6Herahh1w",
"tokenType":"Bearer"}
 */

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class VerifyResponseDto(
    val email: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("token_id") val tokenId: String,
    @SerialName("user_id") val userId: String,
    @SerialName("user_name") val userName: String
)
/*
{
    "email": "jdoe@email.com",
    "full_name": "John Doe",
    "token_id": "d20477b6-02ec-4a9d-8674-cc34cf3ff72e",
    "user_id": "9a9e4bcb-dfbc-40d2-bfbe-b2ad3686de77",
    "user_name": "jdoe"
}
 */
@Serializable
data class ChangePasswordRequestDto(
    val currentPassword: String,
    val newPassword: String
)
@Serializable
data class SignUpRequestDto(
    val country: String,
    val language: String,
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val phone: String,
    val userType: Int,
    val companyCode: String? = null,
    val companyName: String? = null,
)

@Serializable
data class ForgetPasswordRequestDto(
    val email: String,
)
@Serializable
data class ResetPasswordRequestDto(
    val password: String,
    val token: String,
)
