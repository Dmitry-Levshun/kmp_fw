package org.scnsoft.fidekmp.ui.postlogin.notification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.scnsoft.fidekmp.ui.theme.Orange700
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.primaryTextColor50
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle12
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle14
import org.scnsoft.fidekmp.domain.model.NotificationItem
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.login.DropDownMenu
import org.scnsoft.fidekmp.utils.currentUtcDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationListView(notifications: List<NotificationItem>, pullRefreshState: PullRefreshState?, onItemClick: ((NotificationItem) -> Unit)? = null, onMenuClick: ((Int, NotificationItem)->Unit)? = null) {
    val lazyListState = rememberLazyListState()

    if (notifications.isNotEmpty()) {
        LazyColumn(
            modifier = if (pullRefreshState == null) Modifier.fillMaxWidth()
                else Modifier
                .fillMaxWidth()
                .pullRefresh(pullRefreshState),
//                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            state = lazyListState
        ) {
            items(items = notifications, key = { it.id }) { cl ->
                NotificationInfoCard(info = cl, onItemClick = onItemClick, onMenuClick)
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(Res.string.list_is_empty),
                style = textHeaderStyle,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center
            )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationInfoCard(info: NotificationItem, onItemClick: ((NotificationItem) -> Unit)? = null, onMenuClick: ((Int, NotificationItem)->Unit)? = null) {
    val onMenu = remember { mutableStateOf(false) }
    val menuItems =
        arrayOf(stringResource(Res.string.delete))

    Card(
        modifier = Modifier
            .padding(4.dp)
            .combinedClickable(
                onClick = { onItemClick?.invoke(info) },
                onLongClick = {
                    Napier.d("onLongClick")
                    onMenu.value = true
                }
            )

            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ){
            if (!info.isRead) Spacer(
                modifier = Modifier
                    .size(12.dp)
                    .background(Orange700, shape = RoundedCornerShape(50))
                    .align(Alignment.End)
            )

            Text(
                text = info.title,
                style = textHeaderStyle14,
                color = primaryTextColor, textAlign = TextAlign.Center,
            )

            Text(
                text = info.body,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = textExplanationStyle12,
                color = primaryTextColor, textAlign = TextAlign.Justify,
            )
            Text(
                text = info.date.toString(),
                style = textExplanationStyle12,
                color = primaryTextColor50, textAlign = TextAlign.Start,
            )
        }
        if (onMenu.value) DropDownMenu(onMenu, menuItems, onClick = { ind->
            onMenu.value = false
            onMenuClick?.invoke(ind, info)
        })

    }
}
private fun fakeNotification(): List<NotificationItem> {
    return listOf(NotificationItem(id = 1,
        title = "Delivery Instructions Received", body = "Wine preservation during shipping is an important topic for anyone in the wine supply chain who want to protect the quality and safety of their products during transit. Imagine receiving your shipment of wine, only to find the wine is undrinkable due to damage in transit, or less attractive at the point of sale due to label damage. This can leave you needing to juggle insurance claims, loss of sales, and out of stocks, all of which are damaging for brand integrity and the bottom line of your business.",
        from = "", role = "",isRead = false, date = currentUtcDateTime(), subTitle = null
    ))
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun PreviewNotificationListView() {
    Surface(color = Color.White) {
        NotificationListView(fakeNotification(), null)
    }
}
