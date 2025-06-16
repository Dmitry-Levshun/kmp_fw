package org.scnsoft.fidekmp.ui.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.scnsoft.fidekmp.ui.theme.Gray16
import org.scnsoft.fidekmp.ui.theme.GreyLight
import org.scnsoft.fidekmp.ui.theme.mainBlue
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle16
import org.scnsoft.fidekmp.ui.theme.walletBackColor
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.RoundButtonView

@Composable
fun LoginSupportScreen(
    navController: NavHostController,
    viewModel: UserLogin = koinViewModel<UserLoginViewModel>(),
) {
//    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current){
//    }
//    LocalViewModelStoreOwner provides viewModelStoreOwner
//    val viewModel = hiltViewModel<UserLoginViewModel>(viewModelStoreOwner)
    Napier.d("LoginSupportScreen")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.support_title),
                navController = navController
            )
        },
        content = {
            LoginSupportView(viewModel)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginSupportView(viewModel: UserLogin
) {
    Napier.d("LoginSupportView")
    val emailState by viewModel.emailState.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val showSpinner by viewModel.awaitingLoginResponse.collectAsState()
    val enableInputs = !showSpinner && loginError == null

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
//        Text(stringResource(id = R.string.application_description), modifier = Modifier.padding(top = 40.dp, bottom = 8.dp), color = Gray16, style = textHeaderStyle)
//        Text(stringResource(id = R.string.application_description_content), modifier = Modifier.padding(top = 16.dp, bottom = 8.dp), color = Gray16, style = textExplanationStyle)
        Text(stringResource(Res.string.describe_issue), modifier = Modifier.padding(top = 40.dp, bottom = 8.dp), color = Gray16, style = textHeaderStyle16)
        SupportTextView(
            Modifier.bringIntoViewOnFocus(scope, emailBringIntoViewRequester),
            viewModel::onSupportTextValueChanged,
        )
        Spacer(modifier = Modifier.size(12.dp))
//        EmailLoginView(
//            Modifier.bringIntoViewOnFocus(scope, emailBringIntoViewRequester),
//            emailState,
//            viewModel::onEmailTextValueChanged,
//            viewModel::validateEmail,
//            enabled = enableInputs
//        )

//        SignInButtonView(
//            R.string.send,
//            { viewModel.clickSendSuppoort(context) },
//            enabled = enableInputs,
//            focusManager = focusManager
//        )
        RoundButtonView(
            Res.string.send,
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = { viewModel.clickSendSuppoort() },
            enabled = enableInputs
        )

    }
}

@Composable
fun SupportTextView(
    modifier: Modifier = Modifier,
    onSupportChanged: (String) -> Unit,
) {
    var supportText by remember {
        mutableStateOf("")
    }
    /*
    Text(
        text = stringResource(R.string.email),
        modifier = Modifier.padding(top = 16.dp)
    )

     */
    OutlinedTextField(
        value = supportText,
        onValueChange = { supportText = it 
            onSupportChanged.invoke(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .testTag("supporttextField"),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        maxLines = 3,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = mainBlue, unfocusedBorderColor = GreyLight),

    )
    /*
    if (!isValid) {
        LoginErrorMessage(R.string.invalid_email_message)
    }

     */
}


@Preview
@Composable
private fun PreviewLoginSupportView() {
    Surface(contentColor = Color.White) {
        LoginSupportView(UserLoginPreview())
    }
}