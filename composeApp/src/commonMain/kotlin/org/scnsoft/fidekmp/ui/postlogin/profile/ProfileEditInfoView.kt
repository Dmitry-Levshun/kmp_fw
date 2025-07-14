package org.scnsoft.fidekmp.ui.postlogin.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.Bitmap
import coil3.Uri
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.scnsoft.fidekmp.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import org.scnsoft.fidekmp.domain.model.profile.PersonInfo
import org.scnsoft.fidekmp.ui.utils.uiResultDialog
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun infoBox(infoText: String, contentText: String) {
//    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(top = 8.dp)
        .combinedClickable(
            onClick = { },
            onLongClick = {
                scope.launch {
                    clipboard.setText(buildAnnotatedString {  append(contentText) })  //setClipEntry(ClipEntry. withPlainText(contentText)) // copyToClipboard(contentText)
//                showToast("Copied")
                }
            })
        .background(infoBackColor, shape = RoundedCornerShape(8.dp))) {
        Text(infoText, modifier = Modifier.padding(start = 16.dp), style = textInfoStyle, color = black60)
        Text(contentText, modifier = Modifier.padding(start = 16.dp), style = textInfoStyle16, color = primaryTextColor)
    }
}
@Composable
private fun notificationBox1(contentText: String, onClick: (() -> Unit)?) {
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
            color = primaryTextColor,
            )
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
@Composable
fun textField(value: String, stId: StringResource, onValueChange: (String)-> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        label = { Text(text = stringResource(stId)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            autoCorrect = false,
            keyboardType = KeyboardType.Text
        ),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = mainBlue, unfocusedBorderColor = GreyLight),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun ProfileEditInfoView(navController: NavHostController?, viewModel: IProfileModel
) {
    val profileInfoEx by viewModel.profileInfo.collectAsState()
    val profileInfo = profileInfoEx.profile
    val personInfo by viewModel.personInfo.collectAsState()
    val isConsumer = profileInfoEx.isConsumer
    val notificationNotReadCount by viewModel.notificationNotReadCount.collectAsState()
    val notificationCount by viewModel.notificationCount.collectAsState()
    val uiResult by viewModel.uiResult.collectAsState()

    val firstName = remember { mutableStateOf(personInfo.name) }
    val lastName = remember { mutableStateOf(personInfo.surname) }
    val phone = remember { mutableStateOf(personInfo.phone) }
    val address = remember { mutableStateOf(personInfo.address) }
    val zipCode = remember { mutableStateOf(personInfo.zipCode) }
    val zipCodeError = remember { mutableStateOf(false) }
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val avatar = remember { mutableStateOf(personInfo.avatar) }

    val openResultDialog = remember { mutableStateOf(false) }
    openResultDialog.value = uiResult != null
    val openDeleteDialog = remember { mutableStateOf(false) }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    /*
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        Timber.d("ProfileEditInfoView uri:$imageUri")
    }
    val context = LocalContext.current
    LaunchedEffect(imageUri) {
        if (imageUri != null) avatar.value = saveFile(imageUri!!, personInfo.userid, context)
        Timber.d("ProfileEditInfoView avatar:${avatar.value}")
    }

     */
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
                Napier.d("async ${profileInfo.avatar} ld:${avatar.value}")
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
                        .clickable { }//launcher.launch("image/*") }
                        .clip(CircleShape)
                )
                Text(
                    "${personInfo.name} ${personInfo.surname}",
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
                modifier = Modifier.clickable { viewModel?.logout() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel?.logout() }) {
                    Icon(logoutRes, null, tint = linkColor)
                }
//                Text(stringResource(id = R.string.log_out), color = linkColor, style = textExplanationStyle)
            }
        }

//        infoBox(infoText = stringResource(id = R.string.full_name), contentText = profileInfo.fullName)
        if(NOTIFICATIONS_PERMISSION in profileInfo.permissions) {
            notificationBox("$notificationNotReadCount", onClick= {
                if (notificationCount > 0) navController?.navigate(AppScreens.NotificationScreen.route)
            })
        }
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
        infoBox(infoText = stringResource(Res.string.email), contentText = profileInfo.email)
//        infoBox(infoText = stringResource(id = R.string.first_name), contentText = profileInfo.name)
        textField(value = firstName.value, stId = Res.string.first_name, onValueChange = {firstName.value = it})

//        infoBox(infoText = stringResource(id = R.string.last_name), contentText = profileInfo.surname)
        textField(value = lastName.value, stId = Res.string.last_name, onValueChange = {lastName.value = it})
//        Text("Company")
//        Text(profileInfo.company)
//        Text("Email")
//        Text(profileInfo.email)
//        infoBox(infoText = stringResource(id = R.string.phone_number), contentText = profileInfo.phone)
        textField(value = phone.value, stId = Res.string.phone_number, onValueChange = {phone.value = it})
//        infoBox(infoText = stringResource(id = R.string.address), contentText = profileInfo.address)
        textField(value = address.value, stId = Res.string.address, onValueChange = {address.value = it})
//        infoBox(infoText = stringResource(id = R.string.zip_code), contentText = profileInfo.zipCode.toString())
        textField(value = zipCode.value.toString(), stId = Res.string.zip_code, onValueChange = {
            zipCode.value = it
        })
//        if (zipCodeError.value) Text(stringResource(id = R.string.error_incorrect_value), color = Color.Red, modifier = Modifier.padding(start = 16.dp) )
        Row {
            RoundButtonView(
                textId = Res.string.cancel,
                color = seccondaryButtonColor,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, end = 8.dp)
                    .weight(1f),
                enabled = true,
                onClick = { navController?.popBackStack() })
            RoundButtonView(
                textId = Res.string.save,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 8.dp)
                    .weight(1f),
                enabled = firstName.value != personInfo.name || lastName.value != personInfo.surname||
                            phone.value != personInfo.phone || address.value != personInfo.address ||
                            avatar.value != personInfo.avatar || zipCode.value != personInfo.zipCode,
                onClick = {
                    val saveInfo = PersonInfo(
                        email = personInfo.email,
                        avatar = avatar.value,
                        userid = personInfo.userid,
                        name = firstName.value,
                        surname = lastName.value,
                        phone = phone.value,
                        address = address.value,
                        zipCode = zipCode.value)
                    viewModel.setCurrentUserInfo(saveInfo) })
        }
        if (profileInfo.deleteRequestedAt.isNullOrBlank() &&  personInfo.deleteRequestedAt.isNullOrBlank()) {
            RoundButtonView(
                textId = Res.string.delete_account,
                modifier = Modifier
                    .padding(top = 24.dp),
                enabled = true,
                onClick = { openDeleteDialog.value = true })
        } else {
            val dateStr =  if (profileInfo.deleteRequestedAt.isNullOrBlank()) personInfo.deleteRequestedAt else profileInfo.deleteRequestedAt
            Text(
                text = stringResource(Res.string.account_in_deletion_state) + " $dateStr",
                style = textExplanationStyle16,
                color = Color.Red,
//                modifier = Modifier.align(Alignment.Center).padding(4.dp)
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color.Red, RoundedCornerShape(16.dp))
                    .height(50.dp)
            )

        }
        Text(
            text = stringResource(Res.string.change_pass),
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(true) {
                    navController?.navigate(AppScreens.ChangePasswordScreen.route)
                }, style = textExplanationStyle14,
            color = blueButtonColor)
        Text(
            text = stringResource(Res.string.help_support),
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(true) {
                    navController?.navigate(AppScreens.ProfileSupportScreen.route)
                }, style = textExplanationStyle14, color = blueButtonColor)
        if (openResultDialog.value) uiResultDialog(openDialog = openResultDialog, result = uiResult!!, msgId = Res.string.data_saved, onClick = {
            viewModel?.resetUiResult()
            navController?.navigate(AppScreens.HomeScreen.route)
        })
        if (openDeleteDialog.value) DeleteAccountDialog(openDialog = openDeleteDialog) {
            viewModel.deleteAccount()
        }

    }
}
@Composable
fun DeleteAccountDialog(openDialog: MutableState<Boolean>, onConfirm: (Boolean)->Unit) {
    var cancelChecked by remember {
        mutableStateOf(false)
    }
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = stringResource(Res.string.delete_account), style = textHeaderStyle14)
        },
        text = {
            Text(text = stringResource(Res.string.really_delete_account),
                 style = textExplanationStyle14,
                    )
               },
        confirmButton = {
            Row {
                RoundButtonView(
                    textId = Res.string.cancel,
                    color = seccondaryButtonColor,
                    modifier = Modifier
                        .padding(bottom = 16.dp, end = 8.dp)
                        .weight(1f),
                    enabled = true,
                    onClick = { openDialog.value = false })
                RoundButtonView(
                    textId = Res.string.confirm,
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 8.dp)
                        .weight(1f),
                    enabled = true,
                    onClick = { onConfirm.invoke(cancelChecked); openDialog.value = false })
            }
        },
    )
}


@Preview
@Composable
private fun PreviewProfileEditInfoView() {
    Surface (contentColor = Color.White) {
        ProfileEditInfoView(
            null, ProfileModelPreview())
    }
}