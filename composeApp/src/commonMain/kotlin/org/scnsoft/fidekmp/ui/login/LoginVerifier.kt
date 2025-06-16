package org.scnsoft.fidekmp.ui.login

class LoginVerifier {

    companion object {
        const val MFA_CODE_LENGTH = 6
        val PASS_REGEX = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,}$")

    }
    fun verifyEmail(email: String): Boolean = (email.contains("@") && email.contains("."))

    fun verifyPassword(password: String) : Boolean = password.isNotEmpty() && password.matches(PASS_REGEX)

    fun verifyMfa(mfaCode: String) : Boolean = mfaCode.length == MFA_CODE_LENGTH
}
