package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.WinePropertyButtonBox
import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemReview
import org.scnsoft.fidekmp.ui.theme.untrackedBackgroundColor
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.IUntrackedModel
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedDetailWineItem
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedModelPreview
import org.scnsoft.fidekmp.utils.toSimpleMonthString
import org.jetbrains.compose.resources.stringResource
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UntrackedMyReviewsScreen(
    navController: NavHostController?,
    homeViewModel: IUntrackedModel,
) {
    Napier.d("UntrackedSelectWineScreen")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.my_reviews),
                navController = navController!!
//                backGroundColor = seccondaryButtonColor
            )
        },
//        bottomBar = { BottomNavBar(navController = navController, profileInfo.userType, profileInfo.permissions) },
        content = {
            UntrackedMyReviewsView(navController, homeViewModel)
        },
        // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
        // how it should animate.

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UntrackedMyReviewsView(navController: NavHostController?,
                           viewModel: IUntrackedModel
) {
    val wineList by viewModel.untrackedUserWineInfoState.collectAsState(listOf())
    val untrackWine = wineList.firstOrNull() ?: return
    val wine = UntrackedDetailWineItem.fromUntrackedUserWineItem(untrackWine)
    val reviews = untrackWine.reviews
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(untrackedBackgroundColor)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            UntrackedWineDetailBox(wine, fShowVintageImage = false)
        }
        ReviewInfoBox(reviews, onClick = {
            Napier.d("UntrackedMyReviewsView click $it")
            navController?.navigate(AppScreens.UntrackedReviewDetailsScreen(it))
        })
    }
}

@Composable
private fun ReviewInfoBox(reviews: List<UntrackedUserWineItemReview>, onClick: ((Int) -> Unit))
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        reviews.forEachIndexed { i, rev ->
            WinePropertyButtonBox(
                stringResource(Res.string.date_of_tasting),
                rev.dateOfTest.toSimpleMonthString(),
                Modifier.fillMaxWidth(), onClick = {onClick.invoke(i)}
            )
        }
    }
}


@Composable
@Preview
private fun UntrackedMyReviewsPreview() {
    UntrackedMyReviewsView(null, UntrackedModelPreview())
}