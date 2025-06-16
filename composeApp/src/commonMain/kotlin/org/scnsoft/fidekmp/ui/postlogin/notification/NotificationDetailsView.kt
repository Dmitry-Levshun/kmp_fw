package org.scnsoft.fidekmp.ui.postlogin.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.domain.model.NotificationItem
import org.scnsoft.fidekmp.ui.theme.*
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import org.scnsoft.fidekmp.utils.currentUtcDateTime

@Composable
fun NotificationDetailsScreen(
    navController: NavHostController?,
    viewModel: NotificationInterface?,//PrinterSettingsViewModel = hiltViewModel<PrinterSettingsViewModel>()
    notificationItem: NotificationItem
) {
    Napier.d("NotificationDetailsScreen")

    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.notifications),
                navController = navController!!
            )
        }
    ) {
        NotificationDetailsView(navController, notificationItem, viewModel)
    }
}

@Composable
fun NotificationDetailsView(navController: NavHostController?,
                     info: NotificationItem,
                     viewModel: NotificationInterface?
//                        viewModel: PrinterSettingsViewModel?
//                        viewModel: IPrinterSettingsViewModel = hiltViewModel<PrinterSettingsViewModel>()

) {
    Napier.d("NotificationDetailsView")

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = info.title,
                style = textHeaderStyle14,
                color = primaryTextColor, textAlign = TextAlign.Start,
            )
            Text(
                text = info.body,
//                overflow = TextOverflow.Ellipsis,
//                maxLines = 2,
                style = textExplanationStyle12,
                color = primaryTextColor, textAlign = TextAlign.Justify,
            )
            Row(Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = info.from,
                    style = textExplanationStyle12,
                    color = primaryTextColor, textAlign = TextAlign.Start,
                )
                Text(
                    text = info.date.toString(),
                    style = textExplanationStyle12,
                    color = primaryTextColor50, textAlign = TextAlign.End,
                )
            }
            Text(
                text = info.role,
                style = textInfoStyle,
                color = primaryTextColor, textAlign = TextAlign.Start,
            )
        }
        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(vertical = 24.dp)
        ) {
            RoundButtonView(
                Res.string.delete, modifier = Modifier
//                        .align(Alignment.BottomCenter)
                    .weight(1f)
                    .padding(/*vertical = 24.dp,*/ horizontal = 8.dp),
                onClick = {
                    viewModel?.deleteNotification(info.id)
                    navController?.popBackStack()
                }, enabled = true, color = nfcFailedBackColor
            )
            RoundButtonView(
                textId = if (info.url.isNullOrBlank()) Res.string.OK else Res.string.view,
                modifier = Modifier
//                        .align(Alignment.BottomCenter)
                    .weight(1f)
                    .padding(/*vertical = 24.dp,*/ horizontal = 8.dp),
                color = seccondaryButtonColor,
                onClick = {
                    Napier.d("View Click ")
                    if (info.url.isNullOrBlank()) navController?.popBackStack()
                    else {
                        val url =info.url!!
                        val link = if (url.contains("digital-passport-transfers")) "fidewine://dpt/"+ url.substringAfterLast('/', "")
                        else url
//                        viewModel?.setAppLink(Uri.parse(link))
                    }
                }, enabled = true)
        }
    }
//    if (isLoading) SpinnerOverlay()
}
private fun fakeNotification(): NotificationItem {
    return NotificationItem(id = 1,
        title = "Delivery Instructions Received", body = "Wine preservation during shipping is an important topic for anyone in the wine supply chain who want to protect the quality and safety of their products during transit. Imagine receiving your shipment of wine, only to find the wine is undrinkable due to damage in transit, or less attractive at the point of sale due to label damage. This can leave you needing to juggle insurance claims, loss of sales, and out of stocks, all of which are damaging for brand integrity and the bottom line of your business.",
        from = "Frank Giroud", role = "Distributor",isRead = true, date = currentUtcDateTime(), subTitle = null
    )
}

@Preview
@Composable
private fun PreviewNotificationDetailsView() {
    Surface (contentColor = Color.White) {
        NotificationDetailsView(null, fakeNotification(),null)// PrinterSettingsViewModelPreview())
    }
}