package org.scnsoft.fidekmp.ui.postlogin.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.ui.theme.linkColor
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.login.DropDownMenu
import org.scnsoft.fidekmp.utils.currentUtcDateTime
import org.scnsoft.fidekmp.utils.objectToJson

@Composable
fun NotificationScreen(
    navController: NavHostController?,
    viewModel: NotificationInterface
) {
    Napier.d("NotificationScreen")
    val notificationCount by viewModel.notificationCount.collectAsState()
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.notifications) +" ($notificationCount)",
                navController = navController!!
            )
        }
    ) {
        NotificationView(navController, viewModel)
    }
}

@OptIn(ExperimentalEncodingApi::class, ExperimentalMaterialApi::class)
@Composable
fun NotificationView(navController: NavHostController?,
                     viewModel: NotificationInterface
) {
    Napier.d("NotificationView")
    val notitications by viewModel.notifications.collectAsState()
    val isLoading by viewModel.isloadingState.collectAsState()

//    val notiticationsPaged = null //viewModel.getNotificationsPaged().collectAsLazyPagingItems()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = viewModel::getNotifications
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(all = 16.dp),
//            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.mark_all_as_read), color = linkColor,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.End)
                .clickable(true) {
                      viewModel.markAsReadAll()
//                    navController?.navigate(AppScreens.HabillageQrCodeScreen.route +"/NO")
                },
        )
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
//            modifier = Modifier.padding(vertical = 100.dp).align(Alignment.CenterHorizontally)
        )

        NotificationListView(notifications = notitications, pullRefreshState, onItemClick = { item ->
//        NotificationPagedListView(notiticationsPaged!! , onItemClick = { item ->
            Napier.d("NotificationPagedListView onItemClick $item")
            val param = Base64.UrlSafe.encode(objectToJson(item).toByteArray(Charsets.UTF_8))
            Napier.d("NotificationPagedListView param $param")
            viewModel.markAsRead(item.id)
//                navController?.navigate(AppScreens.PassportDetails.route + "/${objectToJsonEx(item)}")
            navController?.navigate(AppScreens.NotificationDetailsScreen.route + "/$param")
        }, onMenuClick = {ind, item ->
            Napier.d("NotificationPagedListView onMenuClick $item")
            if (ind == 0) viewModel.deleteNotification(item.id)
        })
    }
//    if (isLoading) SpinnerOverlay()
}

@Preview
@Composable
private fun PreviewNotificationView() {
    Surface (contentColor = Color.White) {
        NotificationView(null, NotificationPreview())// PrinterSettingsViewModelPreview())
    }
}