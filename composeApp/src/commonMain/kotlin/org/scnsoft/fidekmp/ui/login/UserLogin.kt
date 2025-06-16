package org.scnsoft.fidekmp.ui.login

import kotlinx.coroutines.flow.StateFlow

interface UserLogin {
    val emailState: StateFlow<EmailState>
    val passwordState: StateFlow<PasswordState>
    val loginError: StateFlow<LoginError?>
    val mfaCodeTextField: StateFlow<String>
    val awaitingLoginResponse: StateFlow<Boolean>
    val newPasswordState: StateFlow<PasswordState>
    val confirmPasswordState: StateFlow<PasswordState>
    val passwordError: StateFlow<Boolean>
    val newPasswordError: StateFlow<Boolean>
    val confirmPasswordError: StateFlow<Boolean>
    val supportText: StateFlow<String>
    val countryList: StateFlow<List<String>>
    val phoneTextField: StateFlow<String>
    val isSignedUp: StateFlow<Boolean>
    val isPassConfirmed: StateFlow<Boolean>
    fun onEmailTextValueChanged(email: String)
    fun onSupportTextValueChanged(text: String)
    fun validateEmail()
    fun onPasswordTextValueChanged(password: String)
    fun onNewPasswordTextValueChanged(password: String)
    fun onConfirmPasswordTextValueChanged(password: String)
    fun checkConfirmPassword()
    fun onMfaCodeValueChanged(mfaCode: String)
    fun onPhoneValueChanged(phone: String)
    fun clickTogglePasswordVisibility()
    fun consumeLoginError()
    fun clickSignIn()
    fun clickChangePassword()
    fun clickNewTogglePasswordVisibility()
    fun clickConfirmTogglePasswordVisibility()
    fun clickSendSuppoort()
    fun getCountryList()
    fun setCreds(email: String)
    fun clickSignUp(countryVal: String)
    fun resendEmail()
    fun clickResetPassword()
    fun clickRecoveryPassword()
    fun checkPassToken(token: String)

}
