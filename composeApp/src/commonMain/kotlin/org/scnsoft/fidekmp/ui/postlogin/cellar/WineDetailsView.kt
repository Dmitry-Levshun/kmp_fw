package org.scnsoft.fidekmp.ui.postlogin.cellar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineItem
import org.scnsoft.fidekmp.ui.theme.linkColor
import org.scnsoft.fidekmp.ui.theme.mainBlue
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.seccondaryButtonColor
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle14
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle16
import org.scnsoft.fidekmp.ui.theme.textInfoStyle14
import org.scnsoft.fidekmp.ui.theme.walletBackColor
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.BottomNavBar
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.getBottleImageByColor
import org.scnsoft.fidekmp.domain.model.*

@Composable
fun WineDetailsScreen(
    navController: NavHostController?,
    homeViewModel: ICellarModel,
) {
//    val wineDetailInfo by homeViewModel.wineDetailInfoState.collectAsState()
//    val wineDetailsPairs by homeViewModel.wineDetailsPairListState.collectAsState()
//    val nftId by homeViewModel.wineDetailsNftIdState.collectAsState()
//    val descriptionText by homeViewModel.wineDetailsDescriptionState.collectAsState()
    val profileInfoEx by homeViewModel.profileInfo.collectAsState()
    val profileInfo = profileInfoEx.profile
    val cellarWineDetails by homeViewModel.cellarWineDetails.collectAsState()
    val wineDetailInfo = cellarWineDetails.info
    val wineDetailsPairs = cellarWineDetails.wineDetailsPairs
    val descriptionText = cellarWineDetails.description
    val nftId = cellarWineDetails.nftId ?: ""
    val scores = cellarWineDetails.scoresPairs
    val wineItem = cellarWineDetails.wine
    Napier.d("WineDetailsScreen ${wineItem?.images}")
    Scaffold(
        topBar = {
        CustomToolbarWithBackArrow(
                title = wineDetailInfo.itemName,
                navController = navController!!
//                backGroundColor = seccondaryButtonColor
        )
        },
        bottomBar = { BottomNavBar(navController = navController, profileInfo.userType, profileInfo.permissions) },
        content = {
            WineDetailsView(navController, homeViewModel)

        },
        // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
        // how it should animate.

    )
}

private fun fakePairs(): List<Pair<String, String>> {
    val l = mutableListOf<Pair<String, String>>()
    repeat(15) { ind -> l += Pair("Name$ind", "Value$ind") }
    return l
}

@Composable
fun WineDetailsView(
    navController: NavHostController?, viewModel: ICellarModel
) {
    val cellarWineDetails by viewModel.cellarWineDetails.collectAsState()
    val wineDetailInfo = cellarWineDetails.info
    val wineDetailsPairs = cellarWineDetails.wineDetailsPairs
    val descriptionText = cellarWineDetails.description
    val nftId = cellarWineDetails.nftId ?: ""
    val scoresList = cellarWineDetails.scoresPairs
    val wineItem = cellarWineDetails.wine

    val desc = stringResource(Res.string.wine_detail_description)
    val premeur = stringResource(Res.string.en_primeur)
    val livrable = stringResource(Res.string.en_livrable)
    val wineDetails = stringResource(Res.string.wine_details)
    val scoresText = stringResource(Res.string.scores)
    val description = stringResource(Res.string.description)
    val royaltyRate = stringResource(Res.string.royalty_rate)
    val percents = stringResource(Res.string.percents)
//    val isShowTransfer = details.find { it.first.contains("stock", true)}?.second != "0"
//            || details.find { it.first.contains("originalPrice", true)}?.second != "0.0"
//    val isShowTransfer = wineItem?.stockQty != 0 && wineItem?.originalPrice != 0.0
    val isShowTransfer = false
    val wineDetailsList = remember {
        listOf(
            WineDetailInfo(wineDetails, wineDetailsPairs),
            WineDetailInfo(scoresText, scoresList),
            WineDetailInfo(
                description,
//                 null,
                listOf(Pair(descriptionText, null))
            ),
        )
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(walletBackColor)) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp))
//        .verticalScroll(rememberScrollState()))
    {
        if (nftId.isNotBlank()) ShowNftN(nftId = nftId)
        WineDetailBox(item = wineDetailInfo, isShowTransfer, wineItem, onClick = { item ->
            Napier.d("WineDetailBox onClick $item")
            val intStickStatus = viewModel?.prepareCreatePassportWines("", item) ?: ""
            val stickStatus = if (intStickStatus.contains("prime", true)) premeur else livrable
            navController?.navigate(AppScreens.CreatePassport.route + "/$stickStatus")
        })
        WineDetailsList(wineDetailsList)
    }
        /*
        RoundButtonView(
            text = stringResource(id = R.string.wine_has_been_drunk),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 60.dp)
                .align(androidx.compose.ui.Alignment.BottomCenter),
            enabled = true,
            onClick = {
                        navController?.navigate(AppScreens.RateScreen.route)
            })
         */
    }
}

@Composable
fun WineDetailBox(item : PackageItem,
    isShowTransfer: Boolean,
    wineItem: WineItem?,
    modifier: Modifier = Modifier,
    onClick: ((PackageItem)-> Unit)? = null
) {
    Box(
        modifier
            .height(270.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))) {
        if (isShowTransfer)
        Image(painter = painterResource(Res.drawable.ic_arrow_swap_horizontal),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clickable {
                    onClick?.invoke(item)
                })
        Napier.d("async ${wineItem?.imageUrl}")
        val painterAsync = rememberAsyncImagePainter(wineItem?.imageUrl)
//            ImageRequest.Builder(LocalContext.current).data(wineItem?.imageUrl).apply(block = fun ImageRequest.Builder.() {
//                placeholder(R.drawable.white_wine)
//                    .networkCachePolicy(CachePolicy.ENABLED)
//                    .diskCachePolicy(CachePolicy.ENABLED)
//                    .memoryCachePolicy(CachePolicy.ENABLED)
//            }).build()
//        )
//        Timber.d("async painter ${painterAsync} st: ${painterAsync.state}")
        val drawRes = getBottleImageByColor(item.color)
        val painter = if (painterAsync.state.value is AsyncImagePainter.State.Error || painterAsync.state.value is AsyncImagePainter.State.Empty) painterResource(drawRes) else painterAsync
        Image(painter = painter, //painterResource(R.drawable.big_bottle), contentDescription = null,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 20.dp)
                .size(180.dp, 130.dp)
                .align(Alignment.TopCenter))
        Column(modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(horizontal = 16.dp)) {
            Text(text = item.itemName,
                color = Color.Black,
                style = textHeaderStyle16)
            Text(
                text = item.domainName,
                color = seccondaryButtonColor,
                style = textExplanationStyle14,
            )
            Text(
                text = item.itemData,
                color = seccondaryButtonColor,
                style = textExplanationStyle14,
            )
        }
    }
}
    
@Composable
fun ShowNft(nftId: String, backColor: Color = Color.White) {
    val clipboard = LocalClipboardManager.current
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, bottom = 8.dp)
        .background(backColor, shape = RoundedCornerShape(8.dp))
        .testTag("nftIdField"),
    ) {
        TextField(
            value = nftId.take(9) + "..." + nftId.takeLast(9),
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
//                .padding(horizontal = 8.dp)
//                            .padding(top = 16.dp, bottom = 16.dp)
//                            .background(Color.White, shape = RoundedCornerShape(50))
                .testTag("walletField"),
            label = { Text(text = stringResource(Res.string.wallet_number)) },
//                leadingIcon = {
//                    IconButton(
//                        onClick = { openDialog.value = true },
//                        modifier = Modifier.testTag("passwordVisibilityToggle")
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_scan_barcode),
//                            colorFilter = ColorFilter.tint(primaryTextColor),
//                            contentDescription = null
//                        )
//                    }
//                },
            trailingIcon = {
                IconButton(
                    onClick = { clipboard.setText(buildAnnotatedString {  append(nftId) }) } ,
                    modifier = Modifier.testTag("passwordVisibilityToggle")
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_copy),
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                autoCorrect = false,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = linkColor,
                backgroundColor = Color.Transparent//Color.White
            ),
            readOnly = true
        )
    }
}
@Composable
fun ShowNftN(nftId: String, backColor: Color = Color.Transparent, isFideWine: Boolean = true){
    val clipboard = LocalClipboardManager.current
    Text(text = stringResource(if (isFideWine) Res.string.fidewine_wallet else Res.string.wallet_number),
        modifier = Modifier.padding(top = 8.dp),
        style = textInfoStyle14,
        color = primaryTextColor
    )
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(bottom = 8.dp)
        .background(backColor, shape = RoundedCornerShape(16.dp))
        .border(1.dp, mainBlue, RoundedCornerShape(16.dp))
        .clickable { clipboard.setText(buildAnnotatedString {  append(nftId) }) },
//        .background(Color.White, shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = { clipboard.setText(buildAnnotatedString {  append(nftId) }) },
            modifier = Modifier.testTag("passwordVisibilityToggle")
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_copy),
                contentDescription = null
            )
        }
        Text(text = nftId.take(9) + "..." + nftId.takeLast(9),
            modifier = Modifier.fillMaxWidth(0.85f),
            style = textInfoStyle14,
            color = primaryTextColor,
            textAlign = TextAlign.Center
        )

    }
    }


@Preview
@Composable
private fun PreviewWineDetailsView() {
    Surface(contentColor = Color.White) {
        WineDetailsView( null, CellarModelPreview())
    }
}