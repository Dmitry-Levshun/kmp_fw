package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.scnsoft.fidekmp.ui.theme.burgundiColor
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.textInfoStyle
import org.scnsoft.fidekmp.ui.theme.textInfoStyle16
import org.scnsoft.fidekmp.ui.theme.untrackedBackgroundColor
import org.scnsoft.fidekmp.ui.theme.untrackedWinetype12
import org.scnsoft.fidekmp.ui.theme.untrackedWinetype16
import org.scnsoft.fidekmp.ui.theme.untrackedWinetype32
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import org.scnsoft.fidekmp.ui.utils.getBottleImageByColor
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.painterResource
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun UntrackedWineDetailsScreen(
    navController: NavHostController?,
    homeViewModel: IUntrackedModel,
) {
    Napier.d("UntrackedSelectWineScreen")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.wine_details),
                navController = navController!!
//                backGroundColor = seccondaryButtonColor
            )
        },
//        bottomBar = { BottomNavBar(navController = navController, profileInfo.userType, profileInfo.permissions) },
        content = {
            UntrackedWineDetailsView(navController, homeViewModel)
        },
    )
}

@Composable
fun UntrackedWineDetailsView(navController: NavHostController?,
                      viewModel: IUntrackedModel
) {
    val wineList by viewModel.untrackedUserWineInfoState.collectAsState(listOf())
    val untrackWine = if (LocalInspectionMode.current) untrackedUserWineItemById else wineList.firstOrNull() ?: return
    val wine = UntrackedDetailWineItem.fromUntrackedUserWineItem(untrackWine)
    val config = LocalWindowInfo.current.containerSize
    val screenHeight = config.height
    Napier.d("UntrackedWineDetailsView hdp:$screenHeight")
//    context.showToast(" hdp:$screenHeight hpx:$screenHeightM", true)

    val list = remember {
        listOf(
            Pair(Res.string.producer_name, wine.domainName),
            Pair(Res.string.country, wine.country),
            Pair(Res.string.appellation, wine.appellation),
            Pair(Res.string.type, wine.wineType),
//            Pair("Etc...", "Etc..."),
            Pair(Res.string.vintage, wine.vintage.toString()),
            Pair(Res.string.location, "${untrackWine.externalWine.region}" ),
            Pair(Res.string.color, wine.color),
//            Pair("Capacity", untrackWine.externalWine.),
            Pair(Res.string.quantity, untrackWine.qty.toString())
            )
    }
    val shape = RoundedCornerShape(topStart = 32.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 0.dp)
    val drawRes = getBottleImageByColor(wine.color)
    val painter = painterResource(drawRes)
//    WineAppTheme {
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
            .background(untrackedBackgroundColor),

//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
                Text(
//                    "Chateau Léoville Poyferré",
                    wine.wineName,
                    modifier = Modifier
                        .offset(40.dp, 25.dp)
                        .width(200.dp),
                    color = primaryColor,
                    textAlign = TextAlign.Left, style = untrackedWinetype32,
                )
//        Image(
//            painter = painter,
//            contentDescription = null,
//            contentScale = ContentScale.Fit,
//            alignment = Alignment.TopEnd,
//            modifier = Modifier
//                .offset(90.dp, 40.dp)
//                .size(100.dp, 320.dp)
//        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(top = 48.dp, start = 100.dp, end = 0.dp)
                .offset(32.dp, 200.dp)
//                .verticalScroll(rememberScrollState())
//                .weight(1f)
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 70.dp)

                .background(Color.White, shape = shape)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, )
                    .height(if (screenHeight < 650) 280.dp else if (screenHeight < 670) 300.dp else if (screenHeight < 750) 350.dp else if (screenHeight < 800) 400.dp else  if (screenHeight < 850) 470.dp else 500.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                list.forEach {
                    if (it.second.isNotBlank()) {
                        Text(
                            stringResource(it.first),
                            color = burgundiColor,
                            style = textInfoStyle,
                        )
                        Text(
                            it.second,
                            color = primaryTextColor,
                            style = untrackedWinetype12,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                if (untrackWine.reviews.isNotEmpty()) {
                    WinePropertyButtonBox(
                        stringResource(Res.string.my_reviews), "${untrackWine.reviews.size}",
                        Modifier.fillMaxWidth(0.9f),
                        onClick = {
                            navController?.navigate(AppScreens.UntrackedMyReviewsScreen.route)
                        })
                }
                WinePropertyButtonBox(
                    stringResource(Res.string.purchase_history),
                    "${untrackWine.purchases.size}",
                    Modifier.fillMaxWidth(0.9f),
                    onClick = {
                        navController?.navigate(AppScreens.UntrackedPurchaseHestoryScreen.route)
                    })
            }
        }
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.TopEnd,
            modifier = Modifier
                .offset(250.dp, 40.dp)
                .size(100.dp, 320.dp)
        )
//        Spacer(modifier = Modifier.height(24.dp))
        RoundButtonView(
            text = stringResource(Res.string.wine_has_been_drunk),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp)
                .align(androidx.compose.ui.Alignment.BottomCenter),
            enabled = untrackWine.qty > untrackWine.drunkQty,
            onClick = {
                navController?.navigate(AppScreens.UntrackedAddReviewScreen.route)
            })

    }
//        } // theme
}

@Composable
fun WinePropertyButtonBox(header: String, text: String, modifier: Modifier,
                            onClick: (()-> Unit)? = null
) {
    Box(
        modifier
            .height(56.dp)
            .background(Color.White, RoundedCornerShape(16.dp)),
    ) {
        Text(
            text = header,
            modifier = Modifier.align(Alignment.TopStart),
            color = burgundiColor,
            style = textInfoStyle16
        )
        Row(modifier = Modifier
            .align(Alignment.BottomStart)
            .fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = text,
            color = Color.Black,
            style = untrackedWinetype16
        )
        Image(
            painter = painterResource(Res.drawable.ic_arrow_circle_right),
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.Bottom)
                .clickable { onClick?.invoke() },
            contentDescription = null
        )
    }
    }
}

@Composable
@Preview
private fun UntrackedDetailsPreview() {
    UntrackedWineDetailsView(null, UntrackedModelPreview())
}