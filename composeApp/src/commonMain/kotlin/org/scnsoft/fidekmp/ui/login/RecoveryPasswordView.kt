package org.scnsoft.fidekmp.ui.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.Dialog
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.scnsoft.fidekmp.ui.utils.showToast

@Composable
fun RecoveryPasswordScreen(token: String?,
    navController: NavHostController?,
    viewModel: UserLogin = koinViewModel<UserLoginViewModel>()
) {
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.recovery_password),
                navController = navController!!
            )
        },
        content = {
//            Surface(
//                color = Color.White,
//                modifier = Modifier
//                    .fillMaxSize()
//            ) {
            RecoveryPasswordView(token, navController, viewModel)
//            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun RecoveryPasswordView(token: String?, navController: NavHostController?,
    viewModel: UserLogin = koinViewModel<UserLoginViewModel>()
) {
    var fstStart by remember {mutableStateOf(false) }
    if (token.isNullOrBlank()) fstStart = true
    val newPasswordState by viewModel.newPasswordState.collectAsState()
    val cvonfirmPasswordState by viewModel.confirmPasswordState.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val newPasswordError by viewModel.newPasswordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val showSpinner by viewModel.awaitingLoginResponse.collectAsState()
    val enableInputs = !showSpinner && loginError == null
    val isPassConfirmed by viewModel.isPassConfirmed.collectAsState()

    val focusManager = LocalFocusManager.current
    val emailBringIntoViewRequester = BringIntoViewRequester()
    val passwordBringIntoViewRequester = BringIntoViewRequester()
    val newPasswordBringIntoViewRequester = BringIntoViewRequester()
    val confirmPasswordBringIntoViewRequester = BringIntoViewRequester()
    val mfaBringIntoViewRequester = BringIntoViewRequester()
    val scope = rememberCoroutineScope()
    LaunchedEffect(fstStart) {
        if (!fstStart) {
            viewModel.checkPassToken(token!!)
            fstStart = true
        }
    }

    LaunchedEffect(isPassConfirmed) {
        if (isPassConfirmed) {
            delay(2000)
            navController?.navigate(NavTarget.Login.label)
        }
    }
    showToast(stringResource(Res.string.forget_success), isLong = true)
//    WineAppTheme {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("changepass")
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            /*
                           BannerLogoView(
                               modifier = Modifier
                                   .align(Alignment.CenterHorizontally)
                                   .padding(top = 32.dp, bottom = 64.dp)
                           )

             */
            PasswordLoginView(
                Res.string.create_pass,
                Modifier.bringIntoViewOnFocus(scope, newPasswordBringIntoViewRequester),
                newPasswordState,
                viewModel::onNewPasswordTextValueChanged,
                viewModel::clickNewTogglePasswordVisibility,
                enabled = enableInputs
            )
            if (newPasswordError) Text(stringResource(Res.string.login_error_wrong_password))
            PasswordLoginView(
                Res.string.confirm_pass,
                Modifier.bringIntoViewOnFocus(scope, confirmPasswordBringIntoViewRequester),
                cvonfirmPasswordState,
                viewModel::onConfirmPasswordTextValueChanged,
                viewModel::clickConfirmTogglePasswordVisibility,
                enabled = enableInputs
            )
            if (confirmPasswordError) Text(stringResource(Res.string.login_error_password_mismatch))

            /*
                        MfaCodeView(
                            Modifier
                                .bringIntoViewOnFocus(scope, mfaBringIntoViewRequester),
                            mfaState,
                            viewModel::onMfaCodeValueChanged,
                            enabled = enableInputs
                        )

         */
//                SignInButtonView(
//                    R.string.change_pass,
//                    viewModel::clickChangePassword,
//                    enabled = enableInputs,
//                    focusManager = focusManager
//                )
            RoundButtonView(
                Res.string.confirm,
                modifier = Modifier
                    .padding(vertical = 24.dp),
                onClick = viewModel::clickRecoveryPassword,
                enabled = enableInputs
            )

        }
        if (showSpinner) {
            SpinnerOverlay()
        }
        loginError?.let { error ->
            Dialog(
                Modifier.testTag("loginError"),
                error.titleId,
                error.messageId,
                error.message,
                viewModel::consumeLoginError
            )
        }
    }
//    } // theme
}


@Preview
@Composable
private fun PreviewChangePasswortView() {
    RecoveryPasswordView(null, null, UserLoginPreview())
}