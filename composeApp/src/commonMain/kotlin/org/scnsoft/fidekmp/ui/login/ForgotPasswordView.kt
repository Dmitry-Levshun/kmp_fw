package org.scnsoft.fidekmp.ui.login

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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.ui.theme.Gray16
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle14
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.Dialog
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.scnsoft.fidekmp.ui.utils.showToast

@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    viewModel: UserLogin = koinViewModel<UserLoginViewModel>(),
) {
    Napier.d("ForgotPasswordScreen")
//    val viewModel: UserLogin = koinViewModel<UserLoginViewModel>(key = "LOGIN")
//    val navigator = LocalNavigator.currentOrThrow
//    val viewModel: UserLogin = navigator.getNavigatorScreenModel<UserLoginViewModel>()
//    BackPressHandler(onBackPressed = {
//        Timber.d("BackHandler BackPress ")
//        navController.popBackStack() })
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.forget_password_title),
                navController = navController
            )
        },
        content = {
            ForgotPasswordView(viewModel, navController)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ForgotPasswordView(viewModel: UserLogin, navController: NavHostController?
) {
    val emailState by viewModel.emailState.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val isForgotPass by viewModel.isSignedUp.collectAsState()

    val showSpinner by viewModel.awaitingLoginResponse.collectAsState()
    val enableInputs = !showSpinner && loginError == null && !isForgotPass

    val focusManager = LocalFocusManager.current
    val emailBringIntoViewRequester = BringIntoViewRequester()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        /*
                       BannerLogoView(
                           modifier = Modifier
                               .align(Alignment.CenterHorizontally)
                               .padding(top = 32.dp, bottom = 64.dp)
                       )

         */
        LaunchedEffect(isForgotPass) {
            if (isForgotPass) {
                delay(2000)
                navController?.popBackStack()
            }
        }
        if (isForgotPass) {
            showToast(stringResource(Res.string.forget_success), isLong = true)
        }
        Text(stringResource(Res.string.reset_password), modifier = Modifier.padding(top = 32.dp, bottom = 8.dp), color = Gray16, style = textHeaderStyle)
        Text(stringResource(Res.string.reset_password_content), modifier = Modifier.padding(top = 16.dp, bottom = 32.dp), color = Gray16, style = textExplanationStyle14)
        EmailLoginView(
            Modifier.bringIntoViewOnFocus(scope, emailBringIntoViewRequester),
            emailState,
            viewModel::onEmailTextValueChanged,
            viewModel::validateEmail,
            enabled = enableInputs
        )

        RoundButtonView(
            Res.string.reset_password,
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = viewModel::clickResetPassword,
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

@Composable
@Preview
fun DefaultForgotPassword() {
    Surface(color = Color.White) {
        ForgotPasswordView(UserLoginPreview(), null)
    }
}