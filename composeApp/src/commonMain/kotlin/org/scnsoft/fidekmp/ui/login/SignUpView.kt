package org.scnsoft.fidekmp.ui.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.theme.Gray16
import org.scnsoft.fidekmp.ui.theme.GreyLight
import org.scnsoft.fidekmp.ui.theme.linkColor
import org.scnsoft.fidekmp.ui.theme.mainBlue
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.textCenterStyle
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle14
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle
import org.scnsoft.fidekmp.ui.utils.Dialog
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import org.scnsoft.fidekmp.ui.utils.loadWebUrl
import org.koin.compose.viewmodel.koinViewModel

//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun SignUpView(navController: NavHostController?,
                    viewModel: UserLogin = koinViewModel<UserLoginViewModel>()
) {
    val emailState by viewModel.emailState.collectAsState()
    val passwordState by viewModel.passwordState.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val showSpinner by viewModel.awaitingLoginResponse.collectAsState()
    val isSignedUp by viewModel.isSignedUp.collectAsState()
    val enableInputs = !showSpinner && loginError == null

    val focusManager = LocalFocusManager.current
    val emailBringIntoViewRequester = BringIntoViewRequester()
    val passwordBringIntoViewRequester = BringIntoViewRequester()
    val mfaBringIntoViewRequester = BringIntoViewRequester()
    val scope = rememberCoroutineScope()
    var accType by remember {
        mutableStateOf(-1)
    }
    var dropDownStartIndex by rememberSaveable() {
        mutableStateOf(3)
    }
    val isShowFields = remember {
        derivedStateOf{dropDownStartIndex == 3}
    }

    val url = stringResource(Res.string.app_url)
    var openWeb  by remember {
        mutableStateOf(false)
    }
    val uriHandler = LocalUriHandler.current

//    val navController = rememberAnimatedNavController()//rememberNavController()
    /*
        NavHost(navController = navController, startDestination = NavTarget.Login.label) {

            composable(route = NavTarget.Login.label) {
                Timber.d( "NavGraph route:LOGIN")
                UserLoginScreen(navController)
            }
            composable(route = NavTarget.ForgetPass.label) {
                Timber.d("NavGraph route:ForgetPass")
                ForgotPasswordScreen(navController, viewModel)
            }
        }

     */
    LaunchedEffect(isSignedUp) {
        if (isSignedUp) navController?.navigate(NavTarget.EmailConfirm.label + "/${emailState.email}")
    }
    LaunchedEffect(openWeb) {
        if (openWeb) {
            uriHandler.openUri(url)
//        loadWebUrl(url)
        }
        delay(500)
        openWeb = false
    }
     /*WineAppTheme {*/
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag("login")
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
//                Text(stringResource(id = R.string.app_name).uppercase(), modifier = Modifier
//                    .padding(top = 32.dp)
//                    .align(
//                        Alignment.CenterHorizontally
//                    ), color = Gray16, textAlign = TextAlign.Center, style = textGradientStyle
//                )
                Image(
                    painter = painterResource(Res.drawable.ic_logo_fide_new),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(190.dp, 40.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(stringResource(Res.string.register_your_account),
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 40.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Gray16, style = textHeaderStyle)

                DropDownList(Res.string.account_type, stringArrayResource(Res.array.acc_types).toTypedArray(), dropDownStartIndex) {
//                    isShowFields = it == 3
                    dropDownStartIndex = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (isShowFields.value) {
//                    viewModel.getCountryList()
                    ShowFields(viewModel)
                }
                else {
                    val urlFw = stringResource(Res.string.fidewine_com)
                    val st = stringResource(Res.string.create_acc, urlFw)
                    val sts = stringResource(Res.string.fidewine_com)
                    val start = st.indexOf(sts,0)
                    val ast = buildAnnotatedString {
                            append(st)
                            addStyle(style = SpanStyle(color = linkColor), start, start + sts.length)
                        }

                    Text(text = ast,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable(true) {
                                openWeb = true
                            },
                        textAlign = TextAlign.Center,
                        style = textExplanationStyle14
                    )

                }
                Text(
                    text = stringResource(Res.string.help_support), color = linkColor,
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable(true) {
                            navController?.navigate(NavTarget.LoginSupport.label)
                        },
                    )
                Text(
                    buildAnnotatedString {
                    withStyle(style = SpanStyle(color = primaryTextColor)) {
                        append(stringResource(Res.string.have_account))
                        append(" ")
                    }
                    withStyle(style = SpanStyle(color = linkColor)) {
                        append(stringResource(Res.string.sign_in))
                    }
                    },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable(true) {
                            navController?.navigate(NavTarget.Login.label)
                        },

                    style = textExplanationStyle14,
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
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowFields(viewModel: UserLogin = koinViewModel<UserLoginViewModel>()) {
    val emailState by viewModel.emailState.collectAsState()
    val passwordState by viewModel.passwordState.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val showSpinner by viewModel.awaitingLoginResponse.collectAsState()
    val enableInputs = !showSpinner && loginError == null
    val focusManager = LocalFocusManager.current
    val emailBringIntoViewRequester = BringIntoViewRequester()
    val passwordBringIntoViewRequester = BringIntoViewRequester()
    val scope = rememberCoroutineScope()
    val newPasswordError by viewModel.newPasswordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()
    val newPasswordBringIntoViewRequester = BringIntoViewRequester()
    val confirmPasswordBringIntoViewRequester = BringIntoViewRequester()
    val newPasswordState by viewModel.newPasswordState.collectAsState()
    val confirmPasswordState by viewModel.confirmPasswordState.collectAsState()
    val countryList by viewModel.countryList.collectAsState()

    val phone by viewModel.phoneTextField.collectAsState()

    var country by remember {
        mutableStateOf("")
    }
    var isConfirmed  by remember {
        mutableStateOf(false)
    }
    val uriHandler = LocalUriHandler.current
    val enableSignUp = !showSpinner && loginError == null && emailState.isValid && !newPasswordError && !confirmPasswordError && country.isNotBlank() && phone.isNotBlank() && isConfirmed

    EmailLoginView(
        Modifier.bringIntoViewOnFocus(scope, emailBringIntoViewRequester),
        emailState,
        viewModel::onEmailTextValueChanged,
        viewModel::validateEmail,
        enabled = enableInputs
    )
    PasswordLoginView(
        Res.string.create_new_password,
        Modifier.bringIntoViewOnFocus(scope, newPasswordBringIntoViewRequester),
        newPasswordState,
        viewModel::onNewPasswordTextValueChanged,
        viewModel::clickNewTogglePasswordVisibility,
        enabled = enableInputs,
        isError = newPasswordError
    )
    if (newPasswordError) Text(stringResource(Res.string.login_error_wrong_password), color = Color.Red, modifier = Modifier.padding(start = 16.dp) )
    PasswordLoginView(
        Res.string.repeat_new_password,
        Modifier.bringIntoViewOnFocus(scope, confirmPasswordBringIntoViewRequester)
            .onFocusChanged { if (!it.isFocused) viewModel.checkConfirmPassword() },
        confirmPasswordState,
        viewModel::onConfirmPasswordTextValueChanged,
        viewModel::clickConfirmTogglePasswordVisibility,
        enabled = enableInputs,
        isError = confirmPasswordError
    )
    if (confirmPasswordError) Text(stringResource(Res.string.login_error_password_mismatch), color = Color.Red, modifier = Modifier.padding(start = 16.dp))

    OutlinedTextField(
        value = phone,
        onValueChange = viewModel::onPhoneValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .testTag("phoneField"),
        label = { Text(text = stringResource(Res.string.phone_number)) },
//        trailingIcon = {
//            IconButton(
//                onClick = {},
//                modifier = Modifier.testTag("passwordVisibilityToggle")
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.ic_question_circle),
//                    contentDescription = null
//                )
//            }
//        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            autoCorrect = false,
            keyboardType = KeyboardType.Phone
        ),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = mainBlue, unfocusedBorderColor = GreyLight),
        shape = RoundedCornerShape(8.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))
    DropDownList(Res.string.country, countryList.toTypedArray(), onClick = { country = countryList[it] } )

//    SignInButtonView(
//        R.string.sign_up,
//        { viewModel.clickSignUp(country) },
//        enabled = enableInputs,
//        focusManager = focusManager
//    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isConfirmed,
            onCheckedChange = { check -> isConfirmed = check },
            colors = CheckboxDefaults.colors(checkedColor = primaryColor)
        )
        Spacer(modifier = Modifier.width(4.dp))
        val annotatedString =
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = primaryTextColor)) {
                    append(stringResource(Res.string.accept_policy) + " ")
                }
                pushStringAnnotation(tag = "policy", annotation = "https://prod.fidewine.com/b2b/privacy-policy")

                withStyle(style = SpanStyle(color = linkColor)) {
                    append(stringResource(Res.string.privacy_policy))
                }
                pop()
                withStyle(style = SpanStyle(color = primaryTextColor)) {
                    append(" " + stringResource(Res.string.and) + " ")
                }
                pushStringAnnotation(tag = "terms", annotation = "https://prod.fidewine.com/b2b/terms-of-use")
                withStyle(style = SpanStyle(color = linkColor)) {
                    append(stringResource(Res.string.terms_of_use))
                }
                pop()
            }
            ClickableText(text = annotatedString, style = textExplanationStyle14,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "policy", start = offset, end = offset).firstOrNull()?.let {
                    Napier.d("policy URL ${it.item}")
                        uriHandler.openUri(it.item)
                }
                annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset).firstOrNull()?.let {
                    Napier.d("terms URL ${it.item}")
                    uriHandler.openUri(it.item)
                }
            })
    }

    RoundButtonView(
        Res.string.sign_up,
        modifier = Modifier
            .padding(vertical = 24.dp),
        onClick = { viewModel.clickSignUp(country) },
        enabled = enableSignUp
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownList(labelId: StringResource,  accList: Array<String>, startIndex: Int = -1,  onClick: (Int)->Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var accType by remember {
        mutableStateOf(if (startIndex >= 0) accList[startIndex] else "")
    }
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
                isExpanded = !isExpanded
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = accType,
            onValueChange = {},
            readOnly = true,
            textStyle = textCenterStyle,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            label = {
                Text(text = stringResource(labelId))
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = mainBlue, unfocusedBorderColor = GreyLight),
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
        ) {
            accList.forEachIndexed{index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    onClick(index)
                    accType = s
                    isExpanded = false
                }) {
                    Text(s)
                }

            }
        }
    }
}

@Composable
@Preview
fun DefaultSignUpview() {
    SignUpView(null, UserLoginPreview())
}