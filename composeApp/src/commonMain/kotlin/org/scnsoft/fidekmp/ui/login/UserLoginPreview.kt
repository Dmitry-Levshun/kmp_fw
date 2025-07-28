package org.scnsoft.fidekmp.ui.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserLoginPreview : UserLogin {

    override val emailState: StateFlow<EmailState>
        get() = MutableStateFlow(EmailState("", false))

    override val passwordState: StateFlow<PasswordState>
        get() = MutableStateFlow(PasswordState("", false))

    override val loginError: StateFlow<LoginError?>
        get() = MutableStateFlow<LoginError?>(null)

    override val mfaCodeTextField: StateFlow<String>
        get() = MutableStateFlow("")

    override val awaitingLoginResponse: StateFlow<Boolean>
        get() = MutableStateFlow(false)
    override val newPasswordState: StateFlow<PasswordState>
        get() =  MutableStateFlow(PasswordState("", false))
    override val confirmPasswordState: StateFlow<PasswordState>
        get() =  MutableStateFlow(PasswordState("", false))
    override val passwordError: StateFlow<Boolean>
        get() = MutableStateFlow(false)
    override val newPasswordError: StateFlow<Boolean>
        get() = MutableStateFlow(true)
    override val confirmPasswordError: StateFlow<Boolean>
        get() = MutableStateFlow(false)
    override val supportText: StateFlow<String>
        get() = MutableStateFlow("")
    override val countryList: StateFlow<List<String>>
        get() = MutableStateFlow(listOf<String>())
    override val loginState: StateFlow<Boolean> get() = MutableStateFlow(false)

    override val phoneTextField: StateFlow<String>
        get() = MutableStateFlow("")
    override val isSignedUp: StateFlow<Boolean> get() = MutableStateFlow(false)
    override val isPassConfirmed: StateFlow<Boolean> get() = MutableStateFlow(false)
    override fun onEmailTextValueChanged(email: String) {
        // used by compose preview
    }
    override fun onSupportTextValueChanged(text: String) {
        // used by compose preview
    }

    override fun validateEmail() {
        // used by compose preview
    }
    override fun onPasswordTextValueChanged(password: String) {
        // used by compose preview
    }

    override fun onNewPasswordTextValueChanged(password: String) {
        // used by compose preview
    }

    override fun onConfirmPasswordTextValueChanged(password: String) {
        // used by compose preview
    }

    override fun onMfaCodeValueChanged(mfaCode: String) {
        // used by compose preview
    }
    override fun onPhoneValueChanged(phone: String) {
        // used by compose preview
    }
    override fun clickTogglePasswordVisibility() {
        // used by compose preview
    }
    override fun consumeLoginError() {
        // used by compose preview
    }
    override fun clickSignIn() {
        // used by compose preview
    }

    override fun clickChangePassword() {
        // used by compose preview
    }

    override fun clickNewTogglePasswordVisibility() {
        // used by compose preview
    }

    override fun clickConfirmTogglePasswordVisibility() {
        // used by compose preview
    }

    override fun clickSendSuppoort() {
        // used by compose preview
    }

    override fun getCountryList() {
        // used by compose preview
    }

    override fun setCreds(email: String) {
        // used by compose preview
    }
    override fun clickSignUp(countryVal: String) {
        // used by compose preview
    }
    override fun resendEmail() {
        // used by compose preview
    }
    override fun clickResetPassword() {
        // used by compose preview
    }
    override fun clickRecoveryPassword(){}
    override fun checkPassToken(token: String){}
    override fun checkConfirmPassword() {}
}
