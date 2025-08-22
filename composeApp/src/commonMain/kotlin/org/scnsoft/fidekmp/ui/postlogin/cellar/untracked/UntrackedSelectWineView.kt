package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.ui.theme.GreyLight
import org.scnsoft.fidekmp.ui.theme.mainBlue
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import kotlinx.coroutines.launch
import app.cash.paging.compose.collectAsLazyPagingItems
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.IUntrackedModel
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedModelPreview

const val EnableCostomWine = true

@Composable
fun UntrackedSelectWineScreen(
    navController: NavHostController?,
    homeViewModel: IUntrackedModel,
) {
    Napier.d("UntrackedSelectWineScreen")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.find_wine),
                navController = navController!!,
//                backGroundColor = seccondaryButtonColor
                onBackClick = { homeViewModel.onUntrackMainWineSearch(""); navController.popBackStack(); return@CustomToolbarWithBackArrow })
        },
//        bottomBar = { BottomNavBar(navController = navController, profileInfo.userType, profileInfo.permissions) },
        floatingActionButton = {
            if (EnableCostomWine) {
                OutlinedButton(shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = primaryColor
                    ),
                    onClick = {
                        homeViewModel.resetUiResult()
                        navController?.navigate(AppScreens.UntrackedAddCustomWineScreen.route) },
                    modifier = Modifier.padding(bottom = 56.dp)
                ) {
                    Text(stringResource(Res.string.add_custom_wine))
                }
            }
        },
        content = {
            UntrackedSelectWineView(navController, homeViewModel)
        },
        // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
        // how it should animate.

    )
}

@Composable
fun UntrackedSelectWineView(
    navController: NavHostController?,
    viewModel: IUntrackedModel
) {
    val untrackedWineList by viewModel.untrackedWineListState.collectAsState(initial = listOf())
    val untrackedPagedWineList = viewModel.untrackedWines.collectAsLazyPagingItems()
    val query by viewModel.untrackedSearchText.collectAsState()
//    val untrakedSelectedWines = untrackedWineList.map {UntrackedCellarWineItem.fromUntrackedWineItem(it, query)}
    var wineName by remember { mutableStateOf(query) }
    val scope = rememberCoroutineScope()
//    WineAppTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
//            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = wineName,
            onValueChange = {
                wineName = it
                viewModel.onUntrackWineSearch(wineName)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("wineField"),
            shape = RoundedCornerShape(50),
            label = { Text(text = stringResource(Res.string.find_wine)) },
            leadingIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier.testTag("passwordVisibilityToggle")
                ) {
                    Image(
                        Icons.Rounded.Search,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                autoCorrect = false,
                keyboardType = KeyboardType.Text
            ),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = mainBlue, unfocusedBorderColor = GreyLight),
        )
//        UntrackedSelectWineListView(untrakedSelectedWines, onItemClick = { item ->
        UntrackedSelectWinePaginatedListView(
            untrackedPagedWineList,
            query = query,
            onItemClick = { item ->
                Napier.d("Untracked wine click $item")
                scope.launch {
                    viewModel.getUntrackedWineById(item.id)
                    navController?.navigate(AppScreens.UntrackedAddWineScreen.route)
                }
            })
//        RoundButtonView(
//            R.string.add_to_my_cellar,
//            modifier = Modifier
//                .padding(vertical = 24.dp),
//            onClick = {
//                navController?.navigate(AppScreens.UntrackedAddCustomWineScreen.route)
//            },
//            enabled = true
//        )

    }
//        } // theme
}

@Composable
@Preview
private fun UntrackedSelectWinePreview() {
    UntrackedSelectWineView(null, UntrackedModelPreview())
}