package org.scnsoft.fidekmp.data.api.profile.dto

import kotlinx.serialization.Serializable

@Serializable
data class PersonInfoResponseDto(
    val person: PersonInfoDto,
)

@Serializable
data class PersonInfoDto(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val position: String,
    val roleId: Long,
    val accountType: PersonInfoAccountType,
    val avatar: String,
    val phone: String,
    val address: String,
    val twoFactor: Boolean,
    val languageId: Long,
    val zipCode: String,
    val deleteRequestedAt: String? = null
)

@Serializable
data class PersonInfoAccountType(
    val id: Long,
    val displayName: String,
)

@Serializable
data class PersonInfoRequestDto(
    var address: String? = null,
    var avatar: String? = null,
    var email: String? = null,
    var languageId: Int? = null,
    var name: String? = null,
    var phone: String? = null,
    var position: String? = null,
    var roleId: Long? = null,
    var surname: String? = null,
    var twoFactor: Boolean? = null,
    var zipCode: String? = null,
)
