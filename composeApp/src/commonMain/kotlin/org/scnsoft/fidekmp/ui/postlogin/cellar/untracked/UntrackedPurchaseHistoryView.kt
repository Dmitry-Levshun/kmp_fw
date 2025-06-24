package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.utils.toSimpleMonthString
import org.scnsoft.fidekmp.utils.volumeDoubleToString
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItemPurchase
import org.scnsoft.fidekmp.ui.theme.*
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.IUntrackedModel
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedDetailWineItem
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedModelPreview
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.painterResource
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.domain.model.PriceWithSymbol

@Composable
fun UntrackedPurchaseHistoryScreen(
    navController: NavHostController?,
    homeViewModel: IUntrackedModel,
) {
    Napier.d("UntrackedSelectWineScreen")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.purchase_history),
                navController = navController!!
//                backGroundColor = seccondaryButtonColor
            )
        },
//        bottomBar = { BottomNavBar(navController = navController, profileInfo.userType, profileInfo.permissions) },
        content = {
            UntrackedPurchaseHistoryView(navController, homeViewModel)
        },
        // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
        // how it should animate.

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UntrackedPurchaseHistoryView(navController: NavHostController?,
                                 viewModel: IUntrackedModel
) {
    val wineList by viewModel.untrackedUserWineInfoState.collectAsState(listOf())
    val untrackWine = wineList.firstOrNull() ?: return
    val wine = UntrackedDetailWineItem.fromUntrackedUserWineItem(untrackWine)
    Napier.d("UntrackedPurchaseHestoryView $wine")
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
        PurcchaseHistoryInfoBox(untrackWine.purchases)
    }
}

@Composable
private fun PurcchaseHistoryInfoBox(purchases: List<UntrackedUserWineItemPurchase>,)
{

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        purchases.forEach {
            PurchaseHistoryExtendsBox(
                stringResource(Res.string.date_of_purchase),
                it.dateOfPurchase.toSimpleMonthString(),
                it,
                Modifier.fillMaxWidth()
            )
        }
    }
}
@Composable
fun PurchaseHistoryExtendsBox(header: String, text: String, purchase: UntrackedUserWineItemPurchase, modifier: Modifier,
                              onClick: (()-> Unit)? = null
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier
            .background(Color.White, RoundedCornerShape(16.dp)),
    ) {
        Text(
            text = header,
            color = burgundiColor,
            style = textInfoStyle16,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(modifier = Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = text,
                color = Color.Black,
                style = untrackedWinetype16,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Image(
                painter = painterResource(if (expanded) Res.drawable.ic_arrow_circle_up else Res.drawable.ic_arrow_circle_down),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.Bottom)
                    .clickable { expanded = !expanded },
                contentDescription = null
            )
        }
        if (expanded) {
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Column(modifier = Modifier.width(120.dp), ) {
                    Text(stringResource(Res.string.capacity), color = burgundiColor, textAlign = TextAlign.Center)
                    Text(text = volumeDoubleToString(purchase.bottleVolume), color = Color.Black, style = untrackedWinetype16)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(Res.string.vendor), color = burgundiColor, textAlign = TextAlign.Center)
                    Text(text = purchase?.vendorName ?: "", color = Color.Black, style = untrackedWinetype16)
                }
                Column(modifier = Modifier
                    .width(120.dp)
                    .padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(Res.string.unit_price), color = burgundiColor, textAlign = TextAlign.Center)
                    Text(text = PriceWithSymbol(purchase.price).toString(), color = Color.Black, style = untrackedWinetype16)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(Res.string.quantity), color = burgundiColor, textAlign = TextAlign.Center)
                        Text(
                            text = purchase.qty.toString(),
                            color = Color.Black,
                            style = untrackedWinetype16
                        )
                }
            }
        }
    }
}

@Composable
@Preview
private fun UUntrackedPurchaseHestoryPreview() {
    UntrackedPurchaseHistoryView(null, UntrackedModelPreview())
}