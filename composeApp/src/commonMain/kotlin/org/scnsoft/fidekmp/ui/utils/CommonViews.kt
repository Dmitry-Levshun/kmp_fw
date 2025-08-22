package org.scnsoft.fidekmp.ui.utils

//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.TopAppBarColors
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import org.scnsoft.fidekmp.ui.theme.listBackColor35
import org.scnsoft.fidekmp.ui.theme.nfcDefaultBackColor
import org.scnsoft.fidekmp.ui.theme.priceGreen
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.seccondaryButtonColor
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle10
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle12
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle14
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle16
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle18
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle48
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.theme.mainBlue
import org.scnsoft.fidekmp.ui.theme.textInfoStyle14

//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomToolbarWithBackArrow(title: String, navController: NavHostController, onBackClick: (() -> Unit)? = null, backGroundColor: Color = primaryColor) {
//    CenterAlignedTopAppBar(
    TopAppBar(
        title = { Text(text = title, style = textHeaderStyle, color = Color.White) },
        backgroundColor = backGroundColor,
        contentColor = Color.White,
//        colors = appBarColors(),
//        windowInsets = TopAppBarDefaults.windowInsets,
        navigationIcon = {
            IconButton(onClick = {
                val backList = navController.currentBackStack.value.map{it.destination.route}//  backStackList()
                Napier.d("CustomToolbarWithBackArrow back:$backList")
                onBackClick?.invoke()
                navController.popBackStack()
            }
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "back",//stringResource(id = R.string.back),
                    tint = Color.White
                )
            }
        }
    )
    BackHandler(enabled = true) {
        Napier.d("BackHandler BackPress ")
        onBackClick?.invoke()
        navController.popBackStack()
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColors() = topAppBarColors(
    containerColor = primaryColor,
    scrolledContainerColor = primaryColor,
    navigationIconContentColor= Color.White,
    titleContentColor = Color.White,
    actionIconContentColor = Color.White
)
*/

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomToolbarWithOutBackArrow(title: String, navController: NavHostController) {
    TopAppBar(
                title = { Text(text = title, style = textHeaderStyle, color = Color.White) },
        backgroundColor = primaryColor,
        contentColor = Color.White,
//                colors = appBarColors(),
//                windowInsets = TopAppBarDefaults.windowInsets,
    )
    BackHandler(enabled = true) {
        Napier.d("CustomToolbarWithOutBackArrow BackHandler BackPress ")
    }
}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomToolbar(title: String, navController: NavHostController, onSearchClick: (() -> Unit)? = null,  onFilterClick: (() -> Unit)? = null, backGroundColor: Color = primaryColor) {
    val accRes = painterResource(Res.drawable.ic_account_face)
    val scanRes = painterResource(Res.drawable.ic_scan_barcode)
    val searchRes = painterResource(Res.drawable.ic_magnifying_glass)
    val filterRes = painterResource(Res.drawable.ic_filter_edit)
    TopAppBar(
        title = { Text(text = title, style = textHeaderStyle) },
        backgroundColor = backGroundColor,
        contentColor = Color.White,
        navigationIcon = {
            IconButton(onClick = { /*navController.navigate(AppScreens.Account.route)*/ } ) {
                Icon(accRes, contentDescription = "navigation drawer")
            }
        },
        actions = {
            IconButton(onClick = { onFilterClick?.invoke()/*SearchToolBar("Searh", {}, {}, {}) */} ) {
                Icon(filterRes, contentDescription = "navigation drawer")
            }
            IconButton(onClick = { /*if (BuildConfig.DEBUG) navController.navigate(AppScreens.MapViewScreen.route) else*/ onSearchClick?.invoke() } ) {
                Icon(searchRes, contentDescription = "navigation drawer")
            }
            IconButton(onClick = { /*navController.navigate(AppScreens.QrCodeScreen.route)*/ } ) {
                Icon(scanRes, contentDescription = "navigation drawer")
            }

        }
    )
    BackHandler(enabled = true) {
        Napier.d("Main BackHandler BackPress ")
    }

}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchToolBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    backGroundColor: Color = primaryColor
//    onSearchClick: (String) -> Unit
) {
    var content by remember {
        mutableStateOf(text)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backGroundColor)

    ) {
        TextField(value = content,
            onValueChange = { content = it
                Napier.d(" onValueChange tx:$it")

                onTextChange.invoke(it)},
            colors = TextFieldDefaults.textFieldColors(
                //containerColor = Color.Transparent,
                textColor = Color.White,
                cursorColor = backGroundColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            placeholder = { Text(stringResource(Res.string.search), color = Color.White) },
            singleLine = true, keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//            keyboardActions = KeyboardActions(onSearch = { onSearchClick(text) }),
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    tint = Color.White,
                    contentDescription = stringResource(Res.string.search)
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        content =""
                        onTextChange("")
                    } else {
                        Napier.d("Search onCloseClick ")
                        onTextChange("")
                        onCloseClick()
                    }

                }) {
                    Icon(
                        Icons.Filled.Close,
                        tint = Color.White,
                        contentDescription = stringResource(Res.string.settings_description)
                    )
                }
/*
                Icon(
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                Timber.d("Search onCloseClick ")
                                onCloseClick()
                            }
                        },
                        tint = Color.White,
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close"
                    )

 */
            }
        )
    }
    BackHandler(enabled = true) {
        Napier.d("Seach BackHandler BackPress ")
        onCloseClick()
    }

}
@Composable
fun WideToolBar(title: String,navController: NavHostController) {
    Column(Modifier.fillMaxWidth()) {
        Box (modifier = Modifier.fillMaxWidth()){
            IconButton(modifier = Modifier.align(Alignment.TopStart),
                onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ExitToApp, contentDescription = "navigation drawer")
            }
            Row(Modifier.align(Alignment.TopEnd)) {
                Text("john Door", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
                IconButton(
                    onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "navigation drawer")
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(modifier = Modifier.align(Alignment.BottomStart),
                onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.Settings, contentDescription = "navigation drawer")
            }
            Text(title, Modifier.align(Alignment.BottomCenter))
            IconButton(modifier = Modifier.align(Alignment.BottomEnd),
                onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.AccountCircle, contentDescription = "navigation drawer")
            }

        }
    }
}

@Composable
fun showToast(message: String, isLong: Boolean = false) {
    val toaster = rememberToasterState()
    toaster.show(message, duration = if (isLong) ToasterDefaults.DurationLong else ToasterDefaults.DurationShort)
    Toaster(
        state = toaster,
        alignment = Alignment.BottomCenter
    )
}

@Composable
fun RoundButtonView(
    textId: StringResource? = null,
    text: String = "",
    color: Color = primaryColor,
    fontSize: TextUnit = 16.sp,
    inUpperCase: Boolean = true,
    modifier: Modifier,
    onClick: () -> Unit,
    enabled: Boolean,
) {
    val buttText = if (textId != null) stringResource(textId) else text
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(if (fontSize >= 14.sp) 48.dp else 32.dp)
//            .clip(CircleShape)
            .testTag("signInButton"),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(50)
    ) {
            Text(text = if(inUpperCase) buttText.uppercase() else buttText,
                style = textExplanationStyle, fontSize = fontSize, color = Color.White)
    }
}

@Composable
fun RoundOkButton(
    textId: StringResource? = Res.string.OK,
    text: String = "",
    color: Color = primaryColor,
    fontSize: TextUnit = 14.sp,
    inUpperCase: Boolean = true,
    modifier: Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    val buttText = if (textId != null) stringResource(textId) else text
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .fillMaxWidth(0.4f)
            .height(if (fontSize >= 16.sp) 48.dp else 40.dp)
            .testTag("signInButton"),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(50)
    ) {
        Text(text = if(inUpperCase) buttText.uppercase() else buttText,
            style = textExplanationStyle, fontSize = fontSize, color = Color.White)
    }
}

@Composable
fun CustomTabs(headerList: List<String>, fontSize: TextUnit = 14.sp, tabColor: Color = primaryColor, startInd: Int = 0, isHigh: Boolean = false, onClick: ((Int) -> Unit)? = null) {
    var selectedIndex = startInd//by remember { mutableStateOf(startInd) }
    val height = if (isHigh) 44.dp else 32.dp
    TabRow(selectedTabIndex = selectedIndex,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(50))
            .padding(1.dp)
            .height(height)
            .border(1.dp, tabColor, RoundedCornerShape(50)),

        indicator = { tabPositions: List<TabPosition> ->
            Box {}
        }
    ) {
        headerList.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            val textColor = if (selected) Color.White else tabColor
            Tab(
                modifier = if (selected) Modifier
                    .clip(RoundedCornerShape(50))
                    .background(tabColor)
                else Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White),
                selected = selected,
                onClick = {
                    selectedIndex = index
                    onClick?.invoke(selectedIndex)
                    Napier.d("CustomTabs onClick $selectedIndex")
                          },
                text = { Text(text = text, color = textColor, style = textExplanationStyle, fontSize = fontSize) }
            )
        }
    }
}

@Composable
fun ProcessInventoryCasesPanelView(
    contentBottles: String, contentCases: String,
    undoBtnClick: (() -> Unit),
    textColor: Color = primaryTextColor,
    backGroundColor: Color = Color.Red//Color.Transparent,
) {
    val headerCases = stringResource(Res.string.cases_created)
    val headerBottles = stringResource(Res.string.bottles_scanned)
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(backGroundColor, shape = RoundedCornerShape(8.dp))
        .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(Res.drawable.ic_undo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .padding(bottom = 10.dp, end = 8.dp)
                .clickable { undoBtnClick.invoke() },

//                        .clip(RoundedCornerShape(50)),
        )
        Column {
            Text(
                "$headerBottles $contentBottles",
                color = textColor,
                style = textHeaderStyle18,
                textAlign = TextAlign.Center
            )
            if (contentCases.isNotBlank()) {
                Text(
                    "$headerCases $contentCases",
                    color = textColor,
                    style = textHeaderStyle18,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
@Composable
fun InventoryScanResultPanelView(
    contentBottles: String, contentCases: String,
    contentBottlesInCases: String, contentSingleBottles: String,
    textColor: Color = primaryTextColor,
    backGroundColor: Color = Color.Red//Color.Transparent,
) {
    val headerBottles = stringResource(Res.string.bottles_scanned)
    val headerBottlesInCase = stringResource(Res.string.bottles_in_cases)
    val headerCasesCreated = stringResource(Res.string.cases_created)
    val headerSingleBottles = stringResource(Res.string.single_bottles)
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(backGroundColor, shape = RoundedCornerShape(8.dp))
        .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Column {
            Text(
                "$headerBottles $contentBottles",
                color = textColor,
                style = textHeaderStyle18,
                textAlign = TextAlign.Center
            )
            if (contentCases.isNotBlank()) {
                Text(
                    "$headerCasesCreated $contentCases",
                    color = textColor,
                    style = textHeaderStyle18,
                    textAlign = TextAlign.Center
                )
                Text(
                    "$headerBottlesInCase $contentBottlesInCases",
                    color = textColor,
                    style = textHeaderStyle18,
                    textAlign = TextAlign.Center
                )
                Text(
                    "$headerSingleBottles $contentSingleBottles",
                    color = textColor,
                    style = textHeaderStyle18,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}

@Composable
fun ProcessCasesPanelView(
    header: String = stringResource(Res.string.cases_scanned), contentText: String, additionalText: String ="",
    undoBtnClick: (() -> Unit)? = null,
    textColor: Color = primaryTextColor,
    backGroundColor: Color = Color.Red//Color.Transparent,
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(backGroundColor, shape = RoundedCornerShape(8.dp))
        .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(header, color = textColor, style = textHeaderStyle18, textAlign = TextAlign.Center)
        if (undoBtnClick == null) {
            Text(contentText, color = textColor, style = textHeaderStyle48, textAlign = TextAlign.Center)
        } else {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(Res.drawable.ic_undo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(bottom = 10.dp, end = 8.dp)
                        .clickable { undoBtnClick.invoke() },

//                        .clip(RoundedCornerShape(50)),
                )
                Text(contentText, color = textColor, style = textHeaderStyle48, textAlign = TextAlign.Center)
            }
        }
        if (additionalText.isNotEmpty()) Text(additionalText, color = textColor, style = textHeaderStyle18, textAlign = TextAlign.Center)
    }
}

@Composable
fun ProcessPanelView(
    header: String = stringResource(Res.string.bottles_scanned), contentText: String, additionalText: String ="",
    undoBtnClick: (() -> Unit)? = null,
    textColor: Color = primaryTextColor,
    backGroundColor: Color = Color.Transparent,
    modifier: Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .background(backGroundColor, shape = RoundedCornerShape(8.dp))
        .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Text(header, color = textColor, style = textHeaderStyle16, textAlign = TextAlign.Center)
        if (undoBtnClick == null) {
            Text(contentText, color = textColor, style = textHeaderStyle48, textAlign = TextAlign.Center)
        } else {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(Res.drawable.ic_undo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(bottom = 8.dp, end = 8.dp)
                        .clickable { undoBtnClick.invoke() },
//                        .clip(RoundedCornerShape(50)),
                )
                Text(contentText, color = textColor, style = textHeaderStyle48, textAlign = TextAlign.Center)
            }
        }
        if (additionalText.isNotEmpty()) Text(additionalText, color = textColor, style = textHeaderStyle16, textAlign = TextAlign.Center)
    }
}

@Composable
fun ProgressPanelView(nsProgress: Int, pendProgress: Int, syncProgress: Int,
    modifier: Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(listBackColor35, shape = RoundedCornerShape(8.dp))
        .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        ProgressPanelItem(nsProgress, Res.string.not_scanned, Color(0xFFD4CFCF))
        ProgressPanelItem(pendProgress, Res.string.pending, Color(0xFFFE9827))
        ProgressPanelItem(syncProgress, Res.string.sync, Color(0xFF33B958))

    }
}
@Composable
fun ProgressPanelItem(progress: Int, txtId: StringResource, color: Color
) {
    Box(Modifier.size(125.dp, 70.dp)) {
        Text(
            stringResource(txtId),
            modifier = Modifier.align(Alignment.TopCenter),
            color = primaryTextColor,
            style = textExplanationStyle14,
            textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            CircularProgressIndicator(
                progress = progress/100f,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                color = color
            )
            Text(
                "$progress%",
                modifier = Modifier.align(Alignment.Center),
                color = primaryTextColor,
                style = textExplanationStyle10,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun CaseCompleted() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(nfcDefaultBackColor, shape = RoundedCornerShape(8.dp))
        .height(50.dp)
        .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(Res.drawable.bottle_box),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(40.dp)
                .padding(bottom = 8.dp, end = 8.dp)
        )
        Text(stringResource(Res.string.case_completed), color = Color.White, style = textHeaderStyle18, textAlign = TextAlign.Center)
    }

}
@Composable
fun TextWithIcon(text: String, iconId: DrawableResource, modifier: Modifier) {
    val myId = "inlineContent"
    val text = buildAnnotatedString {
        append("$text Where do you like to go?")
        // Append a placeholder string "[icon]" and attach an annotation "inlineContent" on it.
        appendInlineContent(myId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            // This tells the [CoreText] to replace the placeholder string "[icon]" by
            // the composable given in the [InlineTextContent] object.
            myId,
            InlineTextContent(
                // Placeholder tells text layout the expected size and vertical alignment of
                // children composable.
                Placeholder(
                    width = 14.sp,
                    height = 14.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                // This Icon will fill maximum size, which is specified by the [Placeholder]
                // above. Notice the width and height in [Placeholder] are specified in TextUnit,
                // and are converted into pixel by text layout.
                val icon = painterResource(iconId)
                Icon(icon, "",tint = Color.Red)
            }
        )
    )

    Text(text = text,
        modifier = modifier.fillMaxWidth(),
        inlineContent = inlineContent,
        color = Color.Black,//seccondaryButtonColor,
        style = textExplanationStyle12,)
}

@Composable
fun TextWithIcon1(text: String, iconId: DrawableResource, modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            color = seccondaryButtonColor,
            style = textExplanationStyle12,
        )
        Spacer(modifier = Modifier.width(2.dp))
        Image(painter = painterResource(iconId),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically))
    }
}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier, fontSize: Int = 14, isAlignCenter: Boolean = false) {
/*
    val text = HtmlCompat.fromHtml(spannableString, HtmlCompat.FROM_HTML_MODE_COMPACT)
    AnnotatedString.Companion.fromHtml
    AnnotatedString.fromHtml(html)
    Text(AnnotatedString.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT),)
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = {
            if (isAlignCenter) it.textAlignment = View.TEXT_ALIGNMENT_CENTER
            it.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize.toFloat());
            it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )

 */
//    Text(text = AnnotatedString.fromHtml(html))
}

@Composable
fun loadWebUrl( url: String) {
    Napier.d("loadWebUrl $url")
//    val webViewState = rememberWebViewState(url)
    Column(Modifier.fillMaxSize()) {
        val webViewState = rememberWebViewState(url)
        val text = webViewState.let {
            "${it.pageTitle ?: ""} ${it.loadingState} ${it.lastLoadedUrl ?: ""}"
        }
        Text(text)
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize()
        )
    }}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.TextFieldShape,//  filledShape,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    height: Dp = 48.dp
) {
// If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        MaterialTheme.colors.onSurface
    }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colors.primary,
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        (BasicTextField(
            value = value,
            modifier = modifier.height(height),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            decorationBox = @Composable { innerTextField ->
                // places leading icon, text field with label and placeholder, trailing icon
/*                TextFieldDefaults.TextFieldDecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
                    label = label,
//                    leadingIcon = leadingIcon,
//                    trailingIcon = trailingIcon,
//                    supportingText = supportingText,
//                    shape = shape,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    contentPadding = PaddingValues(0.dp)
//                            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
//                            start = 8.dp, end = 8.dp
//                ),
                )
*/
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    leadingIcon?.invoke()
                    Row(modifier = Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.Center) {
                        innerTextField.invoke()
                    }
                    trailingIcon?.invoke()
                }

            }
        ))

    }
}

@Preview //(name = "RoundButtonView")
@Composable
private fun RoundButtonPreview() {
    Surface(contentColor = Color.White) {
        Column {
            CaseCompleted()
            InventoryScanResultPanelView(
                contentBottles= "14", contentCases = "1",
                contentBottlesInCases = "12", contentSingleBottles = "2")

            ProcessInventoryCasesPanelView("12","1", {})
            RoundButtonView(Res.string.send, modifier = Modifier, onClick = {}, enabled = true)
            val list = listOf(stringResource(Res.string.client), stringResource(Res.string.package_text))
            CustomTabs(list)
            ProcessCasesPanelView(
                contentText = "12 | 30",
                backGroundColor = Color.White,
                )
            ProcessPanelView(
                contentText = "10 | 360",
                backGroundColor = Color.White,
                modifier = Modifier
            )
            ProgressPanelView(67, 54, 97, modifier = Modifier)
            TextWithIcon1("buttlsc 5|", Res.drawable.pregnant, Modifier)
            CustomTextField("123.45", {}, height = 24.dp,
                modifier = Modifier
                    .width(100.dp)
                    .padding(horizontal = 4.dp)
                    .background(priceGreen, shape = RoundedCornerShape(50))
                    .testTag("price"),
                 leadingIcon = {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .width(12.dp),

                    ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_minus),
                        contentDescription = null
                    )
                }
            }, trailingIcon =  {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .width(12.dp),

                ) {
                    Image(
//                                modifier = Modifier
//                                    .width(12.dp),
//                                    ),
                        painter = painterResource(Res.drawable.ic_plus),
                        contentDescription = null
                    )
                }
            })
//            SearchToolBar("Searh", {}, {}, {})
            HtmlText(html = stringResource(Res.string.email_resend_html))
        }
    }
}

@Composable
fun ShowNftN(nftId: String, backColor: Color = Color.Transparent, isFideWine: Boolean = true){
    val clipboard = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
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
        .clickable { clipboard.setText(buildAnnotatedString {  append(nftId) }) } ,
//        .background(Color.White, shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick =  { clipboard.setText(buildAnnotatedString {  append(nftId) })  },
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

