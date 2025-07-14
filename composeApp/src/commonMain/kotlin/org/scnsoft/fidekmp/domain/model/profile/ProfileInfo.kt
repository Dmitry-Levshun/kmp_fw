package org.scnsoft.fidekmp.domain.model.profile

import kotlinx.serialization.Serializable
import org.scnsoft.fidekmp.data.api.profile.dto.PersonInfoDto
import org.scnsoft.fidekmp.data.api.profile.dto.ProfileResponseDto
import org.scnsoft.fidekmp.ui.postlogin.profile.BottomBarUserType

@Serializable
data class ProfileInfo(
    val avatar: String,
    val company: String,
    val fullName: String,
    val position: String,
    val role: String,
    val roleId: Int,
    val email: String,
    val accountType: String,
    val accountTypeId: Int,
    val userId: String,
    val permissions: List<String>,
    val deleteRequestedAt: String = "",
    var userType: BottomBarUserType = BottomBarUserType.WINEYARD
    ) {
    fun isNotEmpty(): Boolean = this.fullName.isNotEmpty() && this.email.isNotEmpty() && this.userId.isNotEmpty()
    companion object {
        fun Empty(): ProfileInfo = ProfileInfo("", "", "", "", "", 1, "", "", 1,"", listOf())

        fun profileFromDto(resp: ProfileResponseDto) =
            ProfileInfo(resp.avatar,
                resp.company,
                resp.fullName,
                resp.position,
                resp.role.name,
                resp.role.id - 1,
                resp.email,
                resp.accountType.displayName,
                resp.accountType.id - 1,
                resp.id,
                deleteRequestedAt = if (resp.deleteRequestedAt != null && resp.deleteRequestedAt != "null") resp.deleteRequestedAt else "",
                permissions = resp.permissions,
                )
    }
}

@Serializable
data class PersonInfo(
    val userid: String,
    val name: String,
    val surname: String,
    val email: String,
    val avatar: String,
    val phone: String,
    val address: String,
    val zipCode: String,
    val deleteRequestedAt: String = ""
) {
    companion object {
        fun Empty(): PersonInfo = PersonInfo("", "", "", "", "", "", "", "02-656")
        fun personFromDto(resp: PersonInfoDto) =
            PersonInfo(avatar = resp.avatar,
                name = resp.name,
                userid = resp.id,
                surname = resp.surname,
                email = resp.email,
                phone = resp.phone,
                address = resp.address,
                zipCode = resp.zipCode,
                deleteRequestedAt = if (resp.deleteRequestedAt != null && resp.deleteRequestedAt != "null") resp.deleteRequestedAt else "",
        )
    }
}

@Serializable
data class ProfileExtInfo(
    val profile: ProfileInfo,
    val isConsumer: Boolean,
    val isIntermediate: Boolean
)
