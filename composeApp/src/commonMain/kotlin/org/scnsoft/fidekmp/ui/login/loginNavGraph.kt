package org.scnsoft.fidekmp.ui.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.Uri
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedNavigator
import kotlin.io.encoding.ExperimentalEncodingApi

enum class NavTarget(val label: String) {
    Login("login"),
    ForgetPass("forgetpass"),
    LoginSupport("loginsupport"),
    Main("main"),
    Splash("splash"),
    LogView("logview"),
    SignUp("signup"),
    EmailConfirm("emailconfirm"),
    RecoveryPass("recoverypass"),
    NoLoginScanCode("nologinscancode"),
    NoLoginScanDetail("nologinscandetail"),
}

@Serializable
data class EmailConfirm(val email: String)

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
//    mainViewModel: MainViewModel
) {

    Napier.d(tag = "MAIN", message = "NavGraph start")
    val navController = rememberNavController()//rememberNavController()
    val viewModel: UserLoginViewModel = koinViewModel<UserLoginViewModel>()//(key = "LOGIN")
//    val viewModel: UserLoginViewModel = koinInject<UserLoginViewModel>(parameters = { parametersOf("Main") })

    val loginState by viewModel.loginState.collectAsState(false)
    val splashState by  remember { mutableStateOf(false) } //mainViewModel.splashState.collectAsState(true)
    val applink by remember { mutableStateOf(Uri(path = "")) }//mainViewModel.applink.collectAsState()
    var route by remember {
        mutableStateOf(NavTarget.Login.label)
    }
    Napier.d(tag = "MAIN", message = "NavGraph loginState:$loginState link:$applink, route:$route")
    LaunchedEffect(applink, loginState, splashState) {
        if (!loginState && !isUriEmpty(applink)) {
            route = getAppLinkRoute(applink)
            if (route.isNotBlank()) navController.navigate(route)
        } else if (splashState)  navController.navigate(NavTarget.Splash.label)
        else if (loginState) {
//            navController.popBackStack()
            navController.navigate(NavTarget.Main.label)
        } else {
//            navController.popBackStack()
            navController.navigate(route)
        }

    }
    LaunchedEffect(route, loginState) {
        if (route != NavTarget.Login.label && loginState) {
            delay(3000)
            route = NavTarget.Login.label
        }
    }
    /*
=    LaunchedEffect(splashState) {
        if (splashState)  navController.navigate(NavTarget.Splash.label)
    }

    LaunchedEffect(loginState, splashState, applink) {
        if (isUriEmpty(applink))
        if (!splashState)
        if (loginState) {
//            navController.popBackStack()
            navController.navigate(NavTarget.Main.label)
        } else {
//            navController.popBackStack()
            navController.navigate(NavTarget.Login.label)
        }
    }
*/
//    NavHost(navController = navController, startDestination = NavTarget.Splash.label) {
    NavHost(navController = navController, startDestination = NavTarget.Login.label) {

        composable(route = NavTarget.Login.label) {
            Napier.d(tag = "MAIN", message = "NavGraph route:LOGIN")
            BackHandler(true) {
                // Or do nothing
                Napier.i(tag ="LOG_TAG", message = "Clicked back")
            }
//            val showConfirmation:Boolean = it.arguments?.read { it. }   getBoolean("showConfirmation", false) ?: false
            UserLoginScreen(false, navController, viewModel)
        }
        composable(route = NavTarget.Login.label +"/{showConfirmation}") {
            Napier.d( "NavGraph route:LOGIN confirm")
            BackHandler(true) {
                // Or do nothing
                Napier.i("Clicked back")
            }
            UserLoginScreen(true, navController, viewModel)
        }

        composable(
            route = NavTarget.Main.label
        ) {
//            VpnStatusScreen(
//                vpnStatusViewModel,
//                goToSettings = { navController.navigate(NavTarget.Settings.label) })
            Napier.d("NavGraph route:MAIN")
//            mainViewModel.getCurrentProfile()
//            AppMainScreen(navController1 = navController, mainViewModel = mainViewModel)
            UntrackedNavigator()
        }
        composable(
            route = NavTarget.Splash.label
        ) {
            Napier.d( "NavGraph route:SPLASH")
            SplashView()
        }
        composable(route = NavTarget.ForgetPass.label) {
            Napier.d("NavGraph route:ForgetPass")
            ForgotPasswordScreen(navController, viewModel)
        }
        composable(route = NavTarget.RecoveryPass.label + "/{tokenArg}") { backStackEntry ->
//            navArgument("tokenArg") {
//                type = NavType.StringType
//                defaultValue = ""
//                nullable = true
//            }

            val token = backStackEntry.toRoute<String?>()
            Napier.d("NavGraph route:RecoveryPass ${token?.take(6)}")
//            route = NavTarget.Login.label
            RecoveryPasswordScreen(token, navController, viewModel)
        }

        composable(route = NavTarget.LoginSupport.label) {
            Napier.d("NavGraph route:LoginSupport")
            LoginSupportScreen(navController, viewModel)
        }
        composable(route = NavTarget.SignUp.label) {
            Napier.d("NavGraph route:SignUpView")
            SignUpView(navController, viewModel)
        }
        /*
        composable(route = NavTarget.NoLoginScanCode.label) {
            Timber.d("NavGraph route:SignUpView")
            NoLoginQrCodeScreen(navController, mainViewModel)
        }
        composable(route = NavTarget.NoLoginScanDetail.label) {
            Timber.d("NavGraph route:StandAloneScanResultBoxScreen")
            NoLoginScanResultBottleScreen(navController, mainViewModel)
        }
*/
        composable(route = NavTarget.EmailConfirm.label + "/{emailArg}") { backStackEntry ->
            Napier.d("NavGraph route:EmailConfirm be:$backStackEntry")
//            navArgument("emailArg") {
//                type = NavType.StringType
//                defaultValue = ""
//            }
//            var email = it.arguments?.getString("emailArg") ?: ""
            val email = backStackEntry.toRoute<String>()
            Napier.d("NavGraph route:SignUpView $email")
            EmailConfirmView(navController, email, viewModel)
        }
        composable<EmailConfirm> { backStackEntry ->
            val vals = backStackEntry.destination.arguments.values.toList()
            val keys = backStackEntry.destination.arguments.keys.toList()
            Napier.d("NavGraph route:EmailConfirm be:$backStackEntry keys:$keys vals:$vals")
//            navArgument("emailArg") {
//                type = NavType.StringType
//                defaultValue = ""
//            }
//            var email = it.arguments?.getString("emailArg") ?: ""
            val emailConfirm: EmailConfirm = backStackEntry.toRoute()
            val email = emailConfirm.email
            Napier.d("NavGraph route:SignUpView $emailConfirm  mail:$email")
            EmailConfirmView(navController, email, viewModel)
        }
    }

}

@OptIn(ExperimentalEncodingApi::class)
private fun getAppLinkRoute(uri: Uri): String {
    Napier.d("getAppLinkRoute NOT LOGIN $uri")
    var route = NavTarget.Login.label
    val  host = try {
         uri.path?.split("/")?.get(1)
    } catch (e: Exception) {
        Napier.e("error in getAppLinkRoute", throwable = e)
        return route
    }
    val queries = uri.query?.split('&')//.substringAfterLast('=', "")
    val key = queries?.get(0)?.substringAfterLast('=', "")
    Napier.d("getAppLinkRoute NOT LOGIN q:$key host:$host")
//http://ec2-15-237-3-118.eu-west-3.compute.amazonaws.com/b2b/password-recovery?token=U0wxendIWlYwOHBuMm1qZVVIMlYwWk1yS3pkOUN2QmQ&isconsumer=true
//    Android is always a sweet treat!
//    https://fidewineapp.com/password-recovery?token=U0wxendIWlYwOHBuMm1qZVVIMlYwWk1yS3pkOUN2QmQ&isconsumer=true
//    http://ec2-15-237-3-118.eu-west-3.compute.amazonaws.com/api/v1/auth/verify-email?email=dlevshun%40gmail.com&code=MFVjajN2dlM4Nm9aV2ZrZ2ZXWnJrT0FyWWV6S0pSdDg%3D
    val rt = when(host) {
//        fidewine://reset-password?key=bTF3SHMwWG1lRFpET1dPdXoxaERzWExHTjhlZ3FmaHk
        "password-recovery" -> { NavTarget.RecoveryPass.label+ "/$key" }
        "reset-password" -> { NavTarget.RecoveryPass.label+ "/$key" }
        "forget-password" -> { NavTarget.ForgetPass.label }
        "sign-up" -> { NavTarget.SignUp.label }
        "sign-in" -> { NavTarget.Login.label +"/true" }
        else -> ""
    }
    if (rt.isNotBlank()) route = rt
    Napier.d("getAppLinkRoute res:$rt ")
    return route
}

fun isUriEmpty(uri: Uri): Boolean = uri.path?.isBlank() ?: true

