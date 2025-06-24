package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import org.scnsoft.fidekmp.utils.toSimpleMonthString
import org.scnsoft.fidekmp.utils.volumeDoubleToString
import org.scnsoft.fidekmp.ui.theme.*
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.textInfoStyle
import org.scnsoft.fidekmp.ui.theme.untrackedBackgroundColor
import org.scnsoft.fidekmp.ui.theme.untrackedWinetype16
import org.scnsoft.fidekmp.ui.theme.untrackedWinetype32
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.getBottleImageByColor
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.AsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UntrackedReviewDetailsScreen(
    reviewId: Int,
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
            UntrackedReviewDetailsView(reviewId, navController, homeViewModel)
        },
    )
}

@Composable
fun UntrackedReviewDetailsView(reviewId: Int,
                               navController: NavHostController?,
                               viewModel: IUntrackedModel
) {
    val wineList by viewModel.untrackedUserWineInfoState.collectAsState(listOf())
    val untrackWine = wineList.firstOrNull() ?: return
    val wine = UntrackedDetailWineItem.fromUntrackedUserWineItem(untrackWine)
    if (reviewId < 0 || reviewId >= untrackWine.reviews.size) return
    val review = untrackWine.reviews[reviewId]

    val list = remember(review) {
        buildList {
            add(Pair(Res.string.date_of_tasting, review.dateOfTest.toSimpleMonthString()))
            add(Pair(Res.string.location_of_tasting, review.locationOfTest))
            add(Pair(Res.string.rating, "${review.rating}/100"))
            add(Pair(Res.string.tasting_with, review.tastedWith))
            add(Pair(Res.string.course, review.course))
            add(Pair(Res.string.review, review.description))

            review.drunkItems.filter { it.qty > 0 }.forEach {
                add(Pair(Res.string.capacity, volumeDoubleToString(it.volume)))
                add(Pair(Res.string.capacity, it.qty.toString()))
            }
        }
    }
    val shape = RoundedCornerShape(topStart = 32.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 0.dp)
    val painterAsync = rememberAsyncImagePainter(wine.imageUrl)
    val drawRes = getBottleImageByColor(wine.color)
    val painter = if (painterAsync.state.value is AsyncImagePainter.State.Error || painterAsync.state.value is AsyncImagePainter.State.Empty) painterResource(drawRes) else painterAsync

//    WineAppTheme {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(untrackedBackgroundColor),

//                .verticalScroll(rememberScrollState()),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier
            .offset(40.dp, 40.dp)
            .width(200.dp)
            )
        {
        Text(
            wine.wineName,
//            modifier = Modifier
//                .offset(40.dp, 40.dp)
//                .width(150.dp)
//                .height(150.dp),
            color = primaryColor,
            textAlign = TextAlign.Left, style = untrackedWinetype32,
        )
        Text(
//            modifier = Modifier
//                .offset(40.dp, 190.dp),
            text = "13% ${stringResource(Res.string.alc)}",
            color = primaryTextColor,
            style = untrackedWinetype16,
        )
        }

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
                .offset(32.dp, (230).dp)
                .background(Color.Companion.White, shape = shape)
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                list.forEach {
                    if (it.second != null && it.second!!.isNotBlank()) {
                    Text(stringResource(it.first), color = burgundiColor, style = textInfoStyle)
                    Row {
                    if (it.first == Res.string.rating) {
                        Image(modifier = Modifier.size(20.dp).padding(4.dp), painter = painterResource(Res.drawable.ic_star_fill), contentDescription = null)
                    }
                        Text(
                            it.second!!,
                            color = primaryTextColor,
                            style = untrackedWinetype16,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                }
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
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
@Preview
private fun UntrackedReviewDetailsPreview() {
    UntrackedReviewDetailsView(0,null, UntrackedModelPreview())
}