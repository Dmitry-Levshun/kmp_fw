package org.scnsoft.fidekmp.ui.postlogin.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.scnsoft.fidekmp.ui.theme.*
import org.scnsoft.fidekmp.ui.utils.*
import org.jetbrains.compose.resources.stringResource
import org.scnsoft.fidekmp.domain.model.profile.ProfileInfo
import io.github.alexzhirkevich.qrose.ImageFormat
import io.github.alexzhirkevich.qrose.oned.BarcodeType
import io.github.alexzhirkevich.qrose.rememberQrCodePainter

@Composable
fun WalletScreen(
    navController: NavHostController?,
    viewModel: IProfileModel,
) {
    Napier.d("WalletScreen")
    val backColor = primaryColor
    val profileInfo by viewModel.profileInfo.collectAsState()

    Scaffold(
        topBar = {
            CustomToolbar(
                title = stringResource(Res.string.wallet),
                navController = navController!!,
                backGroundColor = backColor)} ,
        bottomBar = { BottomNavBar(navController = navController, profileInfo.userType, profileInfo.permissions) },
        content = {
            WalletMainView(profileInfo)
        },
        // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
        // how it should animate.

    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletMainView(profileInfo: ProfileInfo){
    var walletId by remember {
        mutableStateOf("0x54C0897A1E281b107EeE25d4f8eEe5F6ae13f9d9")
    }
    val openDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(walletBackColor)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            ShowNftN(profileInfo.userId)
                Text( modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp),
                    text = stringResource(Res.string.wallet_address), style = textHeaderStyle, color = primaryTextColor50, textAlign = TextAlign.Center)
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(250.dp),
//                    painter = painterResource(id =R.drawable.danger),
                    painter = rememberQrCodePainter(
                        data = profileInfo.userId
                    ),

                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


//        }

//        ClientInfoView(clientList = clientList)
    }
}

@Preview
@Composable
fun PreviewWalletMainView() {
    WalletMainView(ProfileInfo("avatar", "wine", "John Doe", "sale", "user",0, "email@gmail.com", "Winemaker", 0, "2233r5ddgfbh667uy", listOf("notifications")))
}
