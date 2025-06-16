package org.scnsoft.fidekmp.ui.postlogin.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.describe_your_issue
import fidekmp.composeapp.generated.resources.send
import fidekmp.composeapp.generated.resources.support_title
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.login.SupportTextView
import org.scnsoft.fidekmp.ui.theme.Gray16
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.RoundButtonView

@Composable
fun ProfileSupportScreen(
    navController: NavHostController,

) {
    Napier.d("ProfileSupportScreen")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.support_title),
                navController = navController
            )
        },
        content = {
            ProfileSupportView()
        }
    )
}

@Composable
fun ProfileSupportView(
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var supportText by remember {
        mutableStateOf<String>("")
    }
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
        Text(
            stringResource(Res.string.describe_your_issue),
            modifier = Modifier.padding(top = 40.dp, bottom = 8.dp),
            color = Gray16,
            style = textHeaderStyle
        )
        SupportTextView(
            Modifier
        )
        { supportText = it }//viewModel::onSupportTextValueChanged,

        Spacer(modifier = Modifier.height(50.dp))
//        SignInButtonView(
//            R.string.send,
//            {
//                context.sendEmail(prepareEmailIntent(supportText))
//            },
//            enabled = true,
//            focusManager = focusManager
//        )
        RoundButtonView(
            Res.string.send,
            modifier = Modifier
                .padding(vertical = 16.dp),
            onClick = { Napier.e("Text not send $supportText") },
            enabled = true,
        )

    }
}

@Preview
@Composable
private fun PreviewProfileSupportView() {
    Surface(contentColor = Color.White) {
        ProfileSupportView()
    }
}