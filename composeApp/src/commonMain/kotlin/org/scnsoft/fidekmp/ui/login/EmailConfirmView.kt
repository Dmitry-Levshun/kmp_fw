package org.scnsoft.fidekmp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.scnsoft.fidekmp.ui.theme.Gray16
import org.scnsoft.fidekmp.ui.theme.linkColor
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle14
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle16
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Duration.Companion.seconds
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmailConfirmView(navController: NavHostController?, email: String,
                     viewModel: UserLogin = koinViewModel<UserLoginViewModel>()
) {
    val url = stringResource(Res.string.app_url)

    var ticks by remember { mutableStateOf(0) }
    val emailState by remember {
        mutableStateOf(email)
    }

    LaunchedEffect(ticks) {
        if (ticks >0)
            while(true) {
                delay(1.seconds)
                ticks--
            }
    }

//    WineAppTheme {
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text(
//                    stringResource(id = R.string.app_name).uppercase(), modifier = Modifier
//                        .padding(top = 32.dp)
//                        .align(
//                            Alignment.CenterHorizontally
//                        ),
//                    color = Gray16, textAlign = TextAlign.Center, style = textGradientStyle
//                )
                Image(
                    painter = painterResource(Res.drawable.ic_logo_fide_new),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .size(190.dp, 40.dp)
                )

                Text(
                    stringResource(Res.string.email_confirmation),
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 40.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Gray16, style = textHeaderStyle
                )
                Image(
                    painter = painterResource(Res.drawable.ic_email),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
//                    colorFilter = ColorFilter.tint(
//                        colorResource(id = R.color.primaryColor),
//                    ),
                    modifier = Modifier
                        .size(60.dp)
//                        .clip(RoundedCornerShape(50)),
                )


                Spacer(modifier = Modifier.height(24.dp))
//                HtmlText(html = stringResource(id = R.string.email_text), modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = primaryTextColor)) {
                            append(stringResource(Res.string.email_text_start))
                        }
                        withStyle(style = SpanStyle(color = linkColor)) {
                            append(" ${emailState} ")
                        }
                        withStyle(style = SpanStyle(color = primaryTextColor)) {
                            append(stringResource(Res.string.email_text_finish))
                        }
                    },
                    modifier = Modifier.padding(vertical = 16.dp))
                Spacer(modifier = Modifier.height(24.dp))

//                HtmlText(html = stringResource(id = R.string.email_resend_html), modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .clickable(true) {
//                        viewModel.resendEmail()
//                    })
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = primaryTextColor)) {
                            append(stringResource(Res.string.email_resend_start))
                            append('\n')
                        }
                        withStyle(style = SpanStyle(color = linkColor)) {
                            if (ticks == 0) append(stringResource(Res.string.email_resend_end))
                            else append(stringResource(Res.string.email_resend_time) +": $ticks")
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable(ticks == 0) {
                            viewModel.resendEmail()
                            ticks = 60
                        },
                    style = textExplanationStyle16,
                    textAlign = TextAlign.Center
                )

/*
                val st = stringResource(
                    id = R.string.create_acc,
                    stringResource(id = R.string.fidewine_com)
                )
                val sts = stringResource(id = R.string.fidewine_com)
                val start = st.indexOf(sts, 0)
                val ast = buildAnnotatedString {
                    append(st)
                    addStyle(style = SpanStyle(color = linkColor), start, start + sts.length)
                }

                Text(
                    text = ast,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable(true) {
                            openWeb = true
                        },
                    textAlign = TextAlign.Center,
                    style = textExplanationStyle14
                )

 */

                Text(
                    text = stringResource(Res.string.help_support), color = linkColor,
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .clickable(true) {
                            navController?.navigate(NavTarget.LoginSupport.label)
                        },
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = primaryTextColor)) {
                            append(stringResource(Res.string.return_to))
                            append(" ")
                        }
                        withStyle(style = SpanStyle(color = linkColor)) {
                            append(stringResource(Res.string.sign_in))
                        }
                    },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .clickable(true) {
                            navController?.navigate(NavTarget.Login.label)
                        },
                    style = textExplanationStyle14,
                )
            }
            }
//        } // theme
}

@Composable
@Preview
fun DefaultSEmailConfirm() {
    EmailConfirmView(null, "user1@gmail.com", UserLoginPreview())
}