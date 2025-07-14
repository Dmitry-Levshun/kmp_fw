package org.scnsoft.fidekmp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.scnsoft.fidekmp.data.api.auth.dto.SignUpRequestDto
import org.scnsoft.fidekmp.domain.repository.ChangePasswordResult
import org.scnsoft.fidekmp.domain.repository.CountryListResult
import org.scnsoft.fidekmp.domain.repository.LoginRepository
import org.scnsoft.fidekmp.domain.repository.LoginResult
import org.scnsoft.fidekmp.domain.repository.SignApiResult
import org.scnsoft.fidekmp.getPlatform
import org.scnsoft.fidekmp.utils.getTickCount
import kotlin.time.ExperimentalTime

data class LoginError(
    val titleId: StringResource?,
    val messageId: StringResource,
    val message: String? = null
)

data class EmailState(
    val email: String,
    val isValid: Boolean,
)

data class PasswordState(
    val password: String,
    val isVisible: Boolean,
)

class UserLoginViewModel(
    //private val dispatcherProvider: DispatcherProvider,
    private val loginRepository: LoginRepository,
) : ViewModel(), UserLogin {
// покаработает пароль 12345678
// "email": "user1@gmail.com", "password": "12345678aA!"
// "email": "user2@gmail.com", "password":  TestPass123456!
// "francois-dupon-owner@vinexquisite.fr" "password": "TestPass123456!"
    private val dispatcherProvider = Dispatchers.IO
    private val loginVerifier = LoginVerifier()
    private val exceptionMapper = LoginExceptionMapper()
    private val defEmail = ""
    private val defPassw = ""
    private val defPhone = ""

    private val _emailState = MutableStateFlow(EmailState(defEmail, true))
    override val emailState: StateFlow<EmailState> get() = _emailState

    private val _passwordState = MutableStateFlow(PasswordState(defPassw, false))
    override val passwordState: StateFlow<PasswordState> get() = _passwordState

    private val _newPasswordState = MutableStateFlow(PasswordState(defPassw, false))
    override val newPasswordState: StateFlow<PasswordState> get() = _newPasswordState

    private val _confirmPasswordState = MutableStateFlow(PasswordState(defPassw, false))
    override val confirmPasswordState: StateFlow<PasswordState> get() = _confirmPasswordState

    private val _loginError = MutableStateFlow<LoginError?>(null)
    override val loginError: StateFlow<LoginError?> get() = _loginError

    private val _mfaCodeTextField = MutableStateFlow("")
    override val mfaCodeTextField: StateFlow<String> get() = _mfaCodeTextField

    private val _awaitingLoginResponse = MutableStateFlow(false)
    override val awaitingLoginResponse: StateFlow<Boolean> get() = _awaitingLoginResponse

    private val _passwordError = MutableStateFlow<Boolean>(false)
    override val passwordError: StateFlow<Boolean> get() = _passwordError
    private val _newPasswordError = MutableStateFlow<Boolean>(false)
    override val newPasswordError: StateFlow<Boolean> get() = _newPasswordError
    private val _confirmPasswordError = MutableStateFlow<Boolean>(false)
    override val confirmPasswordError: StateFlow<Boolean> get() = _confirmPasswordError

    override val supportText: StateFlow<String> get() = _supportText
    private val _supportText = MutableStateFlow<String>("")


    override val countryList: StateFlow<List<String>> = loginRepository.countryListFlow

    override val phoneTextField: StateFlow<String> get() = _phoneTextField
    private val _phoneTextField = MutableStateFlow<String>(defPhone)

    private val _isSignedUp = MutableStateFlow(false)
    override val isSignedUp: StateFlow<Boolean> get() = _isSignedUp

    private val _isPassConfirmed = MutableStateFlow(false)
    override val isPassConfirmed: StateFlow<Boolean> get() = _isPassConfirmed

    init {
        Napier.d("UserLoginViewModel init")
        getCountryList()
    }

    override fun setCreds(email: String) {
        val otherPassList = listOf("tsidorova@scnsoft.com", "sharsu714@gmail.com","usertest.tan@gmail.com","tan.forsender@gmail.com", "tan.forsender+winemaker@gmail.com", "tan.forsender+intermediate@gmail.com",
            "tan.forsender+restaurant@gmail.com", "sharsu714+consumer@gmail.com"
        )
        _emailState.value = EmailState(email, true)
        if (email in otherPassList) _passwordState.value = PasswordState("12345Qa@!", false)
        else _passwordState.value = PasswordState("TestPass123456!", false)
    }

    override fun onEmailTextValueChanged(email: String) {
        _emailState.value = emailState.value.copy(email = email)
        validateEmail()
    }
    override fun onSupportTextValueChanged(text: String) {
        _supportText.value = text
    }

    override fun validateEmail() {
        if (emailState.value.email.isNotEmpty()) {
            _emailState.value =
                emailState.value.copy(isValid = loginVerifier.verifyEmail(emailState.value.email))
        }
    }

    override fun onPasswordTextValueChanged(password: String) {
        if (password.length > 7) _passwordError.value = password.isBlank()//!loginVerifier.verifyPassword(password)
        _passwordState.value = passwordState.value.copy(password = password)
        Napier.d("onPasswordTextValueChanged ${_passwordError.value} $password")
    }
    override fun onNewPasswordTextValueChanged(password: String) {
        if (password.length > 7) _newPasswordError.value = !loginVerifier.verifyPassword(password)
        _newPasswordState.value = newPasswordState.value.copy(password = password)
    }
    override fun onConfirmPasswordTextValueChanged(password: String) {
        val newPassword = _newPasswordState.value.password.take(password.length)
        _confirmPasswordError.value = (newPassword != password)
        _confirmPasswordState.value = confirmPasswordState.value.copy(password = password)
    }
    override fun checkConfirmPassword() {
        val password = _newPasswordState.value.password
        val newPassword = _confirmPasswordState.value.password
        _confirmPasswordError.value = (newPassword != password)

    }
    override fun onMfaCodeValueChanged(mfaCode: String) {
        _mfaCodeTextField.value = mfaCode
    }
    override fun onPhoneValueChanged(phone: String) {
        Napier.d("onPhoneValueChanged $phone ")
        _phoneTextField.value = phone
    }

    override fun clickTogglePasswordVisibility() {
        val isVisible = !passwordState.value.isVisible
        _passwordState.value = passwordState.value.copy(isVisible = isVisible)
    }
    override fun clickNewTogglePasswordVisibility() {
        val isVisible = !newPasswordState.value.isVisible
        _newPasswordState.value = newPasswordState.value.copy(isVisible = isVisible)
    }
    override fun clickConfirmTogglePasswordVisibility() {
        val isVisible = !confirmPasswordState.value.isVisible
        _confirmPasswordState.value = confirmPasswordState.value.copy(isVisible = isVisible)
    }

    override fun consumeLoginError() {
        _loginError.value = null
    }

    override fun clickSignIn() {
        Napier.d("clickSignIn")
        val email = emailState.value.email
        val password = passwordState.value.password

        if (!loginVerifier.verifyEmail(email) || password.isBlank()) {
            _loginError.value = LoginError(null, Res.string.login_error_empty_fields)
            return
        }

        viewModelScope.launch(dispatcherProvider) {
            _awaitingLoginResponse.value = true

            val response = loginRepository.login(
                    email,
                    password
                )

            when (response) {
                is LoginResult.Success -> _loginError.value = null
/*
                is LoginResult.Failure -> {
                        Timber.d("clickSignIn Failure code:${response.apiFailure.errorCode} msg:${response.apiFailure.detail}")
                        _loginError.value = if (response.apiFailure.detail.isNullOrBlank()) {
                            LoginError(null, errorCodeMapper.map(response.apiFailure))
                        } else {
                            LoginError(null, 0, response.apiFailure.detail.capitalize())
                        }
                }*/
                is LoginResult.Error -> {
                    _loginError.value = LoginError(
                        Res.string.login_error_unable_to_connect,
                        exceptionMapper.map(response.e)
                    )
                }
                //else -> LoginError(null, Res.string.error_something_went_wrong)
            }
            _awaitingLoginResponse.value = false
        }
    }

    override fun clickResetPassword() {
        Napier.d("clickResetPassword")
        val email = emailState.value.email

        if (!loginVerifier.verifyEmail(email)) {
            _loginError.value = LoginError(null, Res.string.login_error_empty_email)
            return
        }

        viewModelScope.launch(dispatcherProvider) {
            _awaitingLoginResponse.value = true
            when (val response = loginRepository.forgetPassword(email)) {
                is SignApiResult.Success -> {
                    _loginError.value = null
                    _isSignedUp.value = true
                }
/*
                is SignApiResult.Failure -> {
                    _loginError.value = if (response.apiFailure.detail.isNullOrBlank()) {
                        LoginError(null, errorCodeMapper.map(response.apiFailure))
                    } else {
                        LoginError(null, 0, response.apiFailure.detail.capitalize())
                    }
                }*/
                is SignApiResult.Error -> {
                    _loginError.value = LoginError(
                        Res.string.login_error_unable_to_connect,
                        exceptionMapper.map(response.e)
                    )
                }
            }
            _awaitingLoginResponse.value = false
        }
    }

    override fun clickChangePassword() {
        Napier.d("clickChangePassword")
        val password = passwordState.value.password
        val newPassword = _newPasswordState.value.password
        val confirmPassword = confirmPasswordState.value.password

        if ( !loginVerifier.verifyPassword(newPassword)) {
            _loginError.value = LoginError(null, Res.string.login_error_empty_fields)
            return
        }
        if (newPassword != confirmPassword) {
            _loginError.value = LoginError(null, Res.string.login_error_password_mismatch)
        }
        viewModelScope.launch(dispatcherProvider) {
            _awaitingLoginResponse.value = true

            val response = loginRepository.changePassword(
                oldPass = password,
                newPass = newPassword
            )
            when (response) {
                is ChangePasswordResult.Success -> _loginError.value = null
/*
                is ChangePasswordResult.Failure -> {
//                    _loginError.value = LoginError(
//                        R.string.login_error_server_header,//null,
//                        R.string.error_something_went_wrong//errorCodeMapper.map(response.apiFailure)
//                    )
                    _loginError.value = if (response.apiFailure.detail.isNullOrBlank()) {
                        LoginError(null, errorCodeMapper.map(response.apiFailure))
                    } else {
                        LoginError(null, 0, response.apiFailure.detail.capitalize())
                    }

                }*/
                is ChangePasswordResult.Error -> {
                    _loginError.value = LoginError(
                        Res.string.login_error_server_header,//R.string.login_error_unable_to_connect,
                        Res.string.error_something_went_wrong//exceptionMapper.map(response.e)
                    )
                }
            }
            _awaitingLoginResponse.value = false
        }
    }
    override fun clickSendSuppoort() {
        val supportText = _supportText.value
//        context?.sendEmail(prepareEmailIntent(supportText))
//        sendEmail2(supportText, context!!)
    }
/*
    private fun sendEmail2(bodyText: String, context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:support-app@fidewine.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "user message")
        intent.putExtra(Intent.EXTRA_TEXT, bodyText)
        Timber.d("sendEmail2 ${intent.resolveActivity(context.packageManager)}")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
*/
    @OptIn(ExperimentalTime::class)
    override fun getCountryList() {
        Napier.d("getCountryList")
        if (countryList.value.isNotEmpty()) return
        viewModelScope.launch(dispatcherProvider) {
            _awaitingLoginResponse.value = true
            _loginError.value = null
            val tick = getTickCount()
            val response = loginRepository.getCountryList()
            Napier.d("getCountryList tick: ${getTickCount() - tick}")
            when (response) {
                is CountryListResult.Success -> {
                    Napier.d("getCountryList res: ${response.countries?.size}")
                }

                is CountryListResult.Error -> {
//                    _loginError.value = LoginError(
//                        R.string.login_error_server_header,//R.string.login_error_unable_to_connect,
//                        R.string.error_something_went_wrong//exceptionMapper.map(response.e)
//                    )
                }

            }
            _awaitingLoginResponse.value = false
        }
    }
    override fun clickSignUp(countryVal: String) {
        Napier.d("clickSignUp")
//Winemaker -1, Intermediate - 2, Wine shop - 3, Consumer - 4
        _isSignedUp.value = false
        val email = emailState.value.email
        val password = newPasswordState.value.password
        val confirmPassword = confirmPasswordState.value.password
        val phone = phoneTextField.value
        val country = countryVal
        val userTypeint = UserAccounts.CONSUMER.value// 4 // for Consumer
        val companyName = null
        _loginError.value = null
        if (!loginVerifier.verifyEmail(email)) _loginError.value = LoginError(Res.string.error_incorrect_params, Res.string.login_error_incorrect_email)
        if (!loginVerifier.verifyPassword(password)) _loginError.value = LoginError(Res.string.error_incorrect_params, Res.string.login_error_wrong_password)
        if (confirmPassword != password) _loginError.value = LoginError(Res.string.error_incorrect_params, Res.string.login_error_password_mismatch)
        if (phone.isBlank()) _loginError.value = LoginError(Res.string.error_incorrect_params, Res.string.login_error_incorrect_phone)
        if (country.isBlank()) _loginError.value = LoginError(Res.string.error_incorrect_params,  Res.string.login_error_incorrect_country)
        if (_loginError.value != null) return
        val language = getPlatform().language

        val request = SignUpRequestDto(country = country,
            email = email,
            password = password,
            passwordConfirm = confirmPassword,
            phone = phone,
            userType = userTypeint,
            companyName = companyName,
            companyCode = "FR",
            language = if (language != "fr") "en" else "fr"
        )

        viewModelScope.launch(dispatcherProvider) {
            _awaitingLoginResponse.value = true

            when (val response = loginRepository.signUp(request)) {
                is SignApiResult.Success -> {
                    _loginError.value = null
                    _isSignedUp.value = true
                }
/*
                is SignApiResult.Failure -> {
//                    _loginError.value = LoginError(R.string.error_server, 0,
//                        "${response.apiFailure.errorCode} ${response.apiFailure.detail}")
                    _loginError.value = if (response.apiFailure.detail.isNullOrBlank()) {
                        LoginError(null, errorCodeMapper.map(response.apiFailure))
                    } else {
                        LoginError(null, 0, response.apiFailure.detail.capitalize())
                    }
                }
 */
                is SignApiResult.Error -> {
                    _loginError.value = LoginError(
                        Res.string.login_error_unable_to_connect,
                        exceptionMapper.map(response.e)
                    )
                }
            }
            _awaitingLoginResponse.value = false
        }
    }
    override fun resendEmail() {
        val email = emailState.value.email
        Napier.d("resendEmail $email")
        viewModelScope.launch(dispatcherProvider) {
            _awaitingLoginResponse.value = true

            when (val response = loginRepository.resendEmail(email)) {
                is SignApiResult.Success -> {
                    _loginError.value = null
                }
/*
                is SignApiResult.Failure -> {
                    _loginError.value = LoginError(R.string.error_server, 0,
                        "${response.apiFailure.detail.capitalize()}")
                }

 */
                is SignApiResult.Error -> {
                    _loginError.value = LoginError(
                        Res.string.login_error_unable_to_connect,
                        exceptionMapper.map(response.e)
                    )
                }
            }
        }
    }
    
    var recoveryToken = ""
    override fun checkPassToken(token: String) {
        _isPassConfirmed.value = false
        Napier.d("checkPassToken $token")
        viewModelScope.launch(dispatcherProvider) {
            when (val response = loginRepository.checkPassToken(token)) {
                is SignApiResult.Success -> {
                    recoveryToken = token
                    _loginError.value = null
                }
/*
                is SignApiResult.Failure -> {
                    _loginError.value = LoginError(R.string.error_server, 0,
                        "${response.apiFailure.detail.capitalize()}")
                }

 */
                is SignApiResult.Error -> {
                    _loginError.value = LoginError(
                        Res.string.login_error_unable_to_connect,
                        exceptionMapper.map(response.e)
                    )
                }
            }
        }
    }

    override fun clickRecoveryPassword() {
        _isPassConfirmed.value = false
        Napier.d("clickRecoveryPassword")
        if (recoveryToken.isNullOrBlank()) {
            _loginError.value = LoginError(null, Res.string.error_server)
        }
        val newPassword = _newPasswordState.value.password
        val confirmPassword = confirmPasswordState.value.password

        if ( !loginVerifier.verifyPassword(newPassword)) {
            _loginError.value = LoginError(null, Res.string.login_error_empty_fields)
            return
        }
        if (newPassword != confirmPassword) {
            _loginError.value = LoginError(null, Res.string.login_error_password_mismatch)
        }
        viewModelScope.launch(dispatcherProvider) {
            _awaitingLoginResponse.value = true

            val response = loginRepository.resetPassword(
                newPassword = newPassword,
                token = recoveryToken
            )
            when (response) {
                is SignApiResult.Success -> {
                    _loginError.value = null
                    _isPassConfirmed.value = true
                }
/*
                is SignApiResult.Failure -> {
                    _loginError.value = LoginError(
                        R.string.login_error_server_header,//null,
                        R.string.error_something_went_wrong//errorCodeMapper.map(response.apiFailure)
                    )
                }

 */
                is SignApiResult.Error -> {
                    _loginError.value = LoginError(
                        Res.string.login_error_server_header,//R.string.login_error_unable_to_connect,
                        Res.string.error_something_went_wrong//exceptionMapper.map(response.e)
                    )
                }
            }
            _awaitingLoginResponse.value = false
        }
    }

}

enum class UserAccounts(val value: Int) {
    WINEMAKER(1),
    INTERMEDIATE(2),
    WINESHOP(3),
    CONSUMER(4)
}