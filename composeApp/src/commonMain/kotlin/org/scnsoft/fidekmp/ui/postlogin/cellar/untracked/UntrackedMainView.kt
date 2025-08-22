package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.scnsoft.fidekmp.ui.theme.GreyLight
import org.scnsoft.fidekmp.ui.theme.mainBlue
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.untrackedBackgroundColor
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.IUntrackedModel
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedModelPreview

@Composable
fun UntrackedMainView(navController: NavHostController?,
                     viewModel: IUntrackedModel
) {
    val untrackedWineList by viewModel.untrackedWineListState.collectAsState(initial = listOf())
    val untrackedSearchText by viewModel.untrackedSearchText.collectAsState()
    var wineName by remember { mutableStateOf(untrackedSearchText) }
    var isNavigated by remember { mutableStateOf(false) }
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(topStart = 32.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 0.dp)
    Napier.d("UntrackedMainView st:$untrackedSearchText $isNavigated $untrackedWineList")
    val isImeVisible = WindowInsets.ime
//    WindowInsetsHolder.current.ime.isVisible
//    WindowCompat.setDecorFitsSytemWindows(window, false)
//    LocalWindowInfo.current.
//    WindowInsets.isImeVisible
//    val view = LocalView.current
//    LaunchedEffect(key1 = view) {
//        view.viewTreeObserver.addOnGlobalLayoutListener {
//            isKeyboardVisible = WindowInsetsCompat
//                .toWindowInsetsCompat(view.rootWindowInsets, view)
//                .isVisible(WindowInsetsCompat.Type.ime())
//        }
//    }

//    WineAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(untrackedBackgroundColor),

//                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row( modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.Start,) {
            IconButton(onClick = {
                navController?.popBackStack()
            }
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "back",//stringResource(id = R.string.back),
                    tint = Color.White,
                    )
            }
        }

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
                    .padding(top = 84.dp)
                    .size(240.dp, 50.dp)
            )
            OutlinedTextField(
                value = wineName,
                onValueChange = {
                    wineName = it
                    viewModel.onUntrackMainWineSearch(wineName)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 56.dp)
                    .height(56.dp)
//                    .background(Color.White, shape = RoundedCornerShape(50))
                    .testTag("wineField"),
                    shape = RoundedCornerShape(50),
                label = { Text(text = stringResource(Res.string.find_wine)) },
                leadingIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.testTag("untrackedMain")
                    ) {
                        Image(
                            Icons.Rounded.Search,
                            colorFilter = ColorFilter.tint(primaryColor),
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text
                ),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = mainBlue, unfocusedBorderColor = GreyLight),
            )
            if (!isKeyboardVisible)
            Image(
                painter = painterResource(Res.drawable.wineyard),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(top = 96.dp, start = 100.dp, end = 0.dp)
                    .size(340.dp, 470.dp)
                    .clip(shape = shape)
                    .border(3.dp, primaryColor, shape = shape)
            )
            if (untrackedWineList.isNotEmpty() && untrackedSearchText.isNotEmpty() && !isNavigated) {
                Napier.d("to UntrackedSelectWineScreen")
                navController?.navigate(AppScreens.UntrackedSelectWineScreen.route)
                isNavigated = true
            }
        }
//        } // theme
}

@Composable
@Preview
private fun UntrackedMainPreview() {
    UntrackedMainView(null, UntrackedModelPreview())
}