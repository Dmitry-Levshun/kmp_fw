package org.scnsoft.fidekmp.ui.postlogin.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.scnsoft.fidekmp.ui.utils.ShowNftN
import org.scnsoft.fidekmp.ui.theme.*
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import org.scnsoft.fidekmp.ui.utils.RoundOkButton
import org.jetbrains.compose.resources.stringArrayResource
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import org.jetbrains.compose.ui.tooling.preview.Preview

const val jessica = //"https://en.wikipedia.org/wiki/Jessica_Alba#/media/File:Jessica_Marie_Alba,_TechCrunch_Disrupt_NY_2016_-_Day_3_(cropped).jpg"
//    "https://images.app.goo.gl/LVLvDK1o1ynZymdD6"
    "https://www.wirewag.com/wp-content/uploads/2019/04/Jessica-Alba.jpg"
//    "http:/martypants.us/images/person4.png"
    @Composable
fun ProfileInfoScreen(
    navController: NavHostController?,
    viewModel: IProfileModel,
) {
    viewModel.resetUiResult()
    val profileInfo by viewModel.profileInfo.collectAsState()
    val personInfo by viewModel.personInfo.collectAsState()
    val isConsumer by viewModel.isConsumer.collectAsState()
    val notificationNotReadCount by viewModel.notificationNotReadCount.collectAsState()
    val notificationCount by viewModel.notificationCount.collectAsState()
    val uiResultState by viewModel.uiResult.collectAsState()
//    WineAppTheme() {
        Scaffold(
            topBar = {
                CustomToolbarWithBackArrow(
                    title = stringResource(Res.string.profile),
                    navController = navController!!
                )
            }
        ) {
            if (isConsumer) ProfileEditInfoView(
                navController = navController,
                viewModel = viewModel
            )
            else ProfileInfoView(navController,
                viewModel = viewModel)
        }
//    }
}
@Composable
private fun infoBox(infoText: String, contentText: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(top = 8.dp)
        .background(infoBackColor, shape = RoundedCornerShape(8.dp))) {
        Text(infoText, modifier = Modifier.padding(start = 16.dp), style = textInfoStyle, color = black60)
        Text(contentText, modifier = Modifier.padding(start = 16.dp), style = textInfoStyle16, color = primaryTextColor)
    }
}
@Composable
fun notificationBox(contentText: String, onClick: (() -> Unit)?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(top = 8.dp)
        .border(1.dp, mainBlue, RoundedCornerShape(16.dp))
        .clickable { onClick?.invoke() },
//        .background(Color.White, shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(Res.string.notifications),
            modifier = Modifier.padding(start = 16.dp),
            style = textInfoStyle14,
            color = primaryTextColor)
        if (contentText != "0") {
            Text(
                text = contentText,
                style = textExplanationStyle14,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .background(Orange700, shape = CircleShape)
                    .size(40.dp, 24.dp)
                    .wrapContentHeight(Alignment.CenterVertically),
                color = Color.White, textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ProfileInfoView(navController: NavHostController?, viewModel: IProfileModel
) {
//    val list = listOf(stringResource(id = R.string.scanning), stringResource(id = R.string.wallet))
    val accTypes = stringArrayResource(Res.array.acc_types)
    val roleTypes = stringArrayResource(Res.array.role_types)
    val profileInfo by viewModel.profileInfo.collectAsState()
    val notificationNotReadCount by viewModel.notificationNotReadCount.collectAsState()
    val notificationCount by viewModel.notificationCount.collectAsState()
    val openDeleteDialog = remember { mutableStateOf(false) }
    val context = LocalPlatformContext.current
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val logoutRes = painterResource(Res.drawable.ic_logout)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
//            Image(imageVector = Icons.Filled.AccountCircle, null, colorFilter = ColorFilter.tint(Color.Cyan),
//                modifier = Modifier.size(40.dp),  alignment = Alignment.CenterStart)
                Napier.d("async ${profileInfo.avatar}")
//                AsyncImagePainter(profileInfo.avatar, )
                    val painterAsync = rememberAsyncImagePainter(profileInfo.avatar)
//                        ImageRequest.Builder(context)
//                            .data(profileInfo.avatar).build())
//                            .target(onStart = { Image(painterResource(Res.drawable.avatar_icon),null)} )
//                            .data(profileInfo.avatar).apply(block = fun ImageRequest.Builder.() {
//                            placeholder(Image(painterResource(Res.drawable.avatar_icon)))
////                            .crossfade(true)
//                            .networkCachePolicy(CachePolicy.ENABLED)
//                            .diskCachePolicy(CachePolicy.ENABLED)
//                            .memoryCachePolicy(CachePolicy.ENABLED)
//                        }).build())
                    val painter = if (painterAsync.state.value is AsyncImagePainter.State.Error || painterAsync.state.value is AsyncImagePainter.State.Empty) painterResource(Res.drawable.avatar_icon) else painterAsync
                    Image(painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )

            Text(
                profileInfo.fullName,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable { viewModel?.getCurrentProfile() },
                style = textHeaderStyle,
                color = primaryTextColor,
                textAlign = TextAlign.Center,
            )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.clickable { viewModel.logout() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.logout() },) {
                    Icon(logoutRes, null, tint = linkColor)
                }
            }
        }

        if(NOTIFICATIONS_PERMISSION in profileInfo.permissions) notificationBox("$notificationNotReadCount", onClick= {
            if (notificationCount > 0) navController?.navigate(AppScreens.NotificationScreen.route)
        })
        /*
        OutlinedTextField(
            value = profileInfo.fullName,
            onValueChange = {},
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            label = { Text(text = stringResource(R.string.full_name)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            textStyle = ,
            readOnly = true
        )

         */
//        Text("Full Name")
//        Text(profileInfo.fullName)
        ShowNftN(profileInfo.userId)
        infoBox(infoText = stringResource(Res.string.full_name), contentText = profileInfo.fullName)
        infoBox(infoText = stringResource(Res.string.company), contentText = profileInfo.company)
//        Text("Company")
//        Text(profileInfo.company)
        infoBox(infoText = stringResource(Res.string.email), contentText = profileInfo.email)
//        Text("Email")
//        Text(profileInfo.email)
//        infoBox(infoText = stringResource(id = R.string.position), contentText = profileInfo.position)
        val role = if (profileInfo.roleId >= 0 && profileInfo.roleId < roleTypes.size) roleTypes[profileInfo.roleId] else profileInfo.role
        infoBox(infoText = stringResource(Res.string.role), contentText = role)

        if (profileInfo.accountType != null) {
            val accType = if (profileInfo.accountTypeId >= 0 && profileInfo.accountTypeId < accTypes.size) accTypes[profileInfo.accountTypeId] else profileInfo.accountType
            infoBox(infoText = stringResource(Res.string.account_type), accType)
        }

        if (profileInfo.deleteRequestedAt.isNullOrBlank()) {
            RoundButtonView(
                textId = Res.string.delete_account,
                modifier = Modifier
                    .padding(top = 24.dp),
                enabled = true,
                onClick = { openDeleteDialog.value = true })
        } else {

        }
        Text(
            text = stringResource(Res.string.change_pass),
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(true) {
                    navController?.navigate(AppScreens.ChangePasswordScreen.route)
                }, style = textExplanationStyle14, color = blueButtonColor)
        Text(
            text = stringResource(Res.string.help_support),
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(true) {
                    navController?.navigate(AppScreens.ProfileSupportScreen.route)
                }, style = textExplanationStyle14, color = blueButtonColor)
//        Text(
//            text = "V${BuildConfig.VERSION_NAME}.${BuildConfig.VERSION_CODE}.${BuildConfig.FLAVOR.uppercase()}",
//            modifier = Modifier
//                .padding(top = 8.dp)
//                .align(Alignment.End),
//                style = textExplanationStyle10, color = seccondaryButtonColor40)

    }
    if (openDeleteDialog.value) NotifyDeleteAccountDialog(openDialog = openDeleteDialog)

}
@Composable
fun NotifyDeleteAccountDialog(openDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = stringResource(Res.string.delete_account), style = textHeaderStyle16)
        },
        text = {
            Text(text = stringResource(Res.string.notify_delete_account),
                style = textExplanationStyle14,
            )
        },
        confirmButton = {
            RoundOkButton(
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 8.dp),
                onClick = { openDialog.value = false })
        },
    )
}

@Preview
@Composable
private fun PreviewProfileInfoView() {
    Surface (contentColor = Color.White) {
        ProfileInfoView(null, ProfileModelPreview())
    }
}