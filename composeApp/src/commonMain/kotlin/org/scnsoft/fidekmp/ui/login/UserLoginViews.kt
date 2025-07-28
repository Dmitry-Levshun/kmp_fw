@file:OptIn(ExperimentalFoundationApi::class)

package org.scnsoft.fidekmp.ui.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.scnsoft.fidekmp.ui.theme.Gray16
import org.scnsoft.fidekmp.ui.theme.GreyLight
import org.scnsoft.fidekmp.ui.theme.disabledGradientBrush
import org.scnsoft.fidekmp.ui.theme.gradientBrush
import org.scnsoft.fidekmp.ui.theme.linkColor
import org.scnsoft.fidekmp.ui.theme.mainBlue
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle14
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle16
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle16
import org.scnsoft.fidekmp.ui.utils.Dialog
import org.scnsoft.fidekmp.ui.utils.ForegroundContent
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.scnsoft.fidekmp.ui.utils.showToast

var shConfirm: Boolean? = null

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun UserLoginScreen(
    showConfirmation: Boolean, navController: NavHostController?,
    viewModel: UserLogin = koinViewModel<UserLoginViewModel>(key = "LOGIN")
) {
//    var viewModel = if (LocalInspectionMode.current) {
//        UserLoginPreview()
//    } else {
//        val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
//            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
//        }
//        LocalViewModelStoreOwner provides viewModelStoreOwner
//        hiltViewModel<UserLoginViewModel>(viewModelStoreOwner = viewModelStoreOwner)
//    }
    val scope = rememberCoroutineScope()
    val emailState by viewModel.emailState.collectAsState()
    val passwordState by viewModel.passwordState.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val showSpinner by viewModel.awaitingLoginResponse.collectAsState()
    val enableInputs = !showSpinner && loginError == null
    Napier.d("UserLoginScreen arg:$showConfirmation calc:$shConfirm")
    if (shConfirm == null && showConfirmation) shConfirm = showConfirmation
    if (shConfirm == true) {
        showToast(stringResource(Res.string.email_confirmed), true)
        shConfirm = false
    }
    val focusManager = LocalFocusManager.current
    val emailBringIntoViewRequester = BringIntoViewRequester()
    val passwordBringIntoViewRequester = BringIntoViewRequester()
    val mfaBringIntoViewRequester = BringIntoViewRequester()
    val userlist = arrayOf(
        "WM tsidorova@scnsoft.com",
        "Inter sharsu714@gmail.com",
        "WS usertest.tan@gmail.com",
        "WS2 tan.forsender@gmail.com",
        "WM tan.forsender+winemaker@gmail.com",
        "Inter tan.forsender+intermediate@gmail.com",
        "WS tan.forsender+restaurant@gmail.com",
        "Cust sharsu714+consumer@gmail.com",
        "Cust dlevshun@gmail.com",
    )
    val onMenu = remember { mutableStateOf(false) }
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val signUpAnnotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = primaryTextColor)) {
                append(stringResource(Res.string.not_have_account))
                append(" ")
            }
            withStyle(style = SpanStyle(color = linkColor)) {
                append(stringResource(Res.string.sign_up))
            }
        }


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
//    WineAppTheme {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("login")
    ) {
        /*
        LaunchedEffect(key1 = view) {
            view.viewTreeObserver.addOnGlobalLayoutListener {
                isKeyboardVisible = WindowInsetsCompat
                    .toWindowInsetsCompat(view.rootWindowInsets, view)
                    .isVisible(WindowInsetsCompat.Type.ime())
            }
        }
*/
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
//                Text(stringResource(id = R.string.app_name).uppercase(), modifier = Modifier.padding(top =32.dp).align(Alignment.CenterHorizontally), color = Gray16, textAlign = TextAlign.Center, style = textGradientStyle)
            Image(
                painter = painterResource(Res.drawable.ic_logo_fide_new),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .size(190.dp, 40.dp)
                    .align(Alignment.CenterHorizontally)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                stringResource(Res.string.habillage_processing),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { //if (BuildKonfig.FLAVOR != "prod")
                         onMenu.value = true },
                color = Gray16,
                textAlign = TextAlign.Center,
                style = textExplanationStyle16
            )
            if (onMenu.value) DropDownMenu(onMenu, userlist, onClick = { ind ->
                onMenu.value = false
                viewModel.setCreds(userlist[ind].split(" ")[1])
            })
            /*
            BannerLogoView(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp, bottom = 64.dp)
            )

*/
            Text(
                stringResource(Res.string.login_to_your_account),
                modifier = Modifier.padding(top = 40.dp, bottom = 8.dp),
                color = Gray16,
                style = textHeaderStyle16
            )
            EmailLoginView(
                Modifier.bringIntoViewOnFocus(scope, emailBringIntoViewRequester),
                emailState,
                viewModel::onEmailTextValueChanged,
                viewModel::validateEmail,
                enabled = enableInputs
            )
            PasswordLoginView(
                Res.string.password_placeholder,
                Modifier.bringIntoViewOnFocus(scope, passwordBringIntoViewRequester),
                passwordState,
                viewModel::onPasswordTextValueChanged,
                viewModel::clickTogglePasswordVisibility,
                enabled = enableInputs
            )
            if (passwordError) Text(
                stringResource(Res.string.login_error_wrong_password),
                color = Color.Red,
                style = MaterialTheme.typography.h6
            )

            Text(
                text = stringResource(Res.string.forgot_password_label),
                color = linkColor,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable(true) {
                        navController?.navigate(NavTarget.ForgetPass.label)
//                        navController?.navigate(EmailConfirm("a@b.com"))
                    },
                style = textExplanationStyle14
                )
//                SignInButtonView(
//                    R.string.sign_in,
//                    viewModel::clickSignIn,
//                    enabled = enableInputs,
//                    focusManager = focusManager
//                )
            RoundButtonView(
                Res.string.sign_in,
                modifier = Modifier
                    .padding(vertical = 20.dp),
                onClick = viewModel::clickSignIn,
                enabled = enableInputs
            )
            Text(signUpAnnotatedString,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable(true) {
                        navController?.navigate(NavTarget.SignUp.label)
                    },
                style = textExplanationStyle14
            )
        }
        if (!isKeyboardVisible) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 16.dp, horizontal = 16.dp),
            ) {

                OutlinedButton(shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = primaryTextColor,
                        backgroundColor = Color.White
                    ),
                    enabled = true,
                    border = BorderStroke(1.dp, color = mainBlue),
                    onClick = { navController?.navigate(NavTarget.NoLoginScanCode.label) }) {
                    Text(stringResource(Res.string.scan_bottle).uppercase())
                    Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing
                    Image(
                        painter = painterResource(Res.drawable.ic_scan_barcode),
                        colorFilter = ColorFilter.tint(primaryTextColor),
                        contentDescription = null
                    )
                }
                Text(
                    text = stringResource(Res.string.help_support), color = linkColor,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable(true) {
                            navController?.navigate(NavTarget.LoginSupport.label)
                        },
                    style = textExplanationStyle14
                    )
            }
        }

        if (showSpinner) {
            SpinnerOverlay()
        }

        loginError?.let { error ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                Dialog(
                    Modifier.testTag("loginError"),
                    error.titleId,
                    error.messageId,
                    error.message,
                    viewModel::consumeLoginError
                )
            }
        }
    }
//    } // theme
}

@Composable
fun EmailLoginView(
    modifier: Modifier = Modifier,
    emailState: EmailState,
    onEmailChanged: (String) -> Unit,
    validateEmail: () -> Unit,
    enabled: Boolean
) {
    val email = emailState.email
    val isValid = emailState.isValid
    /*
    Text(
        text = stringResource(R.string.email),
        modifier = Modifier.padding(top = 16.dp)
    )

     */
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChanged,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (!it.hasFocus) {
                    validateEmail()
                }
            }
            .testTag("emailField"),
        label = { Text(text = stringResource(Res.string.email_placeholder)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        isError = !isValid,
        enabled = enabled,
        supportingText = {
            if (!isValid) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.login_error_incorrect_email),
                    color = Color.Red
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(errorBorderColor = Color.Red, focusedBorderColor = mainBlue, unfocusedBorderColor = GreyLight),
        shape = RoundedCornerShape(8.dp)
    )
    /*
    if (!isValid) {
        LoginErrorMessage(R.string.invalid_email_message)
    }

     */
}

@Composable
fun LoginErrorMessage(
     message: StringResource
) {
    Text(
        text = stringResource(message),
        modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
        color = Color.Red
    )
}

@Composable
fun PasswordLoginView(
    placeholderTextId: StringResource,
    modifier: Modifier = Modifier,
    passwordState: PasswordState,
    onPasswordChanged: (String) -> Unit,
    onClickTogglePasswordVisibility: () -> Unit,
    enabled: Boolean,
    isError: Boolean = false
) {
    val password = passwordState.password
    val isVisible = passwordState.isVisible
    /*
        Text(
            text = stringResource(R.string.password),
            modifier = Modifier.padding(top = 16.dp)
        )

     */
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .testTag("passwordField"),
        visualTransformation =
        if (!isVisible) PasswordVisualTransformation()
        else VisualTransformation.None,
        label = { Text(text = stringResource(placeholderTextId)) },
        trailingIcon = {
            IconButton(
                onClick = onClickTogglePasswordVisibility,
                modifier = Modifier.testTag("passwordVisibilityToggle")
            ) {
                when (isVisible) {
                    true -> Image(
                        painter = painterResource(Res.drawable.ic_visibility),
                        colorFilter = ColorFilter.tint(primaryTextColor),
                        contentDescription = stringResource(Res.string.password_is_visible),
                    )

                    false -> Image(
                        painter = painterResource(Res.drawable.ic_visibility_off),
                        colorFilter = ColorFilter.tint(primaryTextColor),
                        contentDescription = stringResource(Res.string.password_is_not_visible),
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            autoCorrect = false,
            keyboardType = KeyboardType.Password
        ),
        enabled = enabled,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(errorBorderColor = Color.Red, focusedBorderColor = mainBlue, unfocusedBorderColor = GreyLight),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignInButtonView(
    textId: StringResource,
    onClickSignIn: () -> Unit,
    enabled: Boolean,
    focusManager: FocusManager
) {
    Button(
        onClick = {
            onClickSignIn()
            focusManager.clearFocus()
        },
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
//            .clip(CircleShape)
            .testTag("signInButton"),
        contentPadding = PaddingValues(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        shape = RoundedCornerShape(50)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(brush = if (enabled) gradientBrush else disabledGradientBrush),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = stringResource(textId).uppercase(),
                style = textExplanationStyle16,
                color = Color.White
            )
        }
    }
}

@Composable
fun SpinnerOverlay() {
    ForegroundContent(Color.Transparent) {
//        CircularProgressIndicator(
//            modifier = Modifier.testTag("loginSpinner"),
//            color = primaryColor
//        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = primaryColor)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bringIntoViewOnFocus(
    scope: CoroutineScope,
    bringIntoViewRequester: BringIntoViewRequester
) =
    this
        .then(
            onFocusChanged {
                if (it.hasFocus) {
                    scope.launch {
                        delay(200)
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }
        )
        .bringIntoViewRequester(bringIntoViewRequester)

@Composable
fun DropDownMenu(isExpanded: MutableState<Boolean>, accList: Array<String>, onClick: (Int)->Unit) {
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }
    Napier.d("DropDownMenu")
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        },
    ) {
        accList.forEachIndexed{index, s ->
            DropdownMenuItem( onClick = {
                selectedIndex = index
                onClick(index)
                isExpanded.value = false
            }) {
                Text(s)
            }

        }
    }
}

@Composable
@Preview
fun DefaultPreviewLogin() {
    UserLoginScreen(true, null, UserLoginPreview())
}

//@Composable
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//fun DarkThemePreviewLogin() {
//    UserLoginScreen(null, UserLoginPreview())
//}
