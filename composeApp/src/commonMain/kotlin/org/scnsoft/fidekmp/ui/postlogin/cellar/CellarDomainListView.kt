package org.scnsoft.fidekmp.ui.postlogin.cellar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.domain.model.DomainInfo
import org.scnsoft.fidekmp.domain.model.PackageItem
import org.scnsoft.fidekmp.ui.theme.greyBlue
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle16
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle18
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle
import org.scnsoft.fidekmp.ui.theme.listBackColor50
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle12
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle14
import org.scnsoft.fidekmp.ui.utils.getBottleImageByColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CellarDomainListView(clientList: List<DomainInfo>, pullRefreshState: PullRefreshState?, onItemClick: ((PackageItem) -> Unit)? = null, onBoxClick: (()-> Unit)? = null ) {
    val lazyListState = rememberLazyListState()
    val mod = Modifier
        .padding(vertical = 4.dp)
        .fillMaxSize()
    val modifier = if (pullRefreshState == null) mod else mod.pullRefresh(pullRefreshState)

    if (clientList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            state = lazyListState
        ) {
            items(items = clientList, key = { it.id }) { cl ->
                if (cl.isVisible) DomainInfoCard(info = cl, onItemClick = onItemClick)
            }
            item{ Spacer(modifier = Modifier.height(80.dp))}
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable { onBoxClick?.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(Res.string.no_wine_in_cellar), //"List of Wines empty",
                style = textExplanationStyle18,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun DomainInfoCard(info: DomainInfo, onItemClick: ((PackageItem) -> Unit)? = null) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { expanded = expanded.not() }
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_client_ava),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(40.dp)
////                        .clip(RoundedCornerShape(50)),
//                )
                Text(
                    text = info.wines.size.toString(),
                    style = textExplanationStyle16,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .background(greyBlue, shape = CircleShape)
                        .size(40.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    color = Color.White, textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = info.domainName,
                        style = textHeaderStyle
                    )
                Image(
                    if(expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(primaryColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(24.dp),

                    alignment = Alignment.CenterEnd )

            }
            if (expanded) Column {
                Spacer(modifier = Modifier.height(8.dp))
                info.wines.forEach{item ->
                    if (item.isVisible) {
                        TextInfoCard(item, onItemClick = onItemClick)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

        }
    }
}

@Composable
fun TextInfoCard(item: PackageItem, backColor: Color = listBackColor50, onItemClick: ((PackageItem) -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = backColor,
        elevation = 0.dp
    ) {
        val painterAsync = rememberAsyncImagePainter(item.imageUrl)
//        Timber.d("async painter ${painterAsync} st: ${painterAsync.state}")
        val drawRes = getBottleImageByColor(item.color)
        val painter = if (painterAsync.state.value is AsyncImagePainter.State.Error || painterAsync.state.value is AsyncImagePainter.State.Empty) painterResource(drawRes) else painterAsync

        Column(
            modifier = Modifier
//                .padding(20.dp)
                .clickable {
                    onItemClick?.invoke(item)
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
//                    colorFilter = ColorFilter.tint(
//                        colorResource(id = R.color.primaryColor),
//                    ),
                    modifier = Modifier
                        .size(40.dp)
//                        .clip(RoundedCornerShape(50)),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "${item.itemName} | ${item.vintage}",
                        style = textHeaderStyle14,
                    )
                    Text(text = item.itemData,
                        style = textExplanationStyle12,
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                Image(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(primaryColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(24.dp),
                    alignment = Alignment.CenterEnd )


            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun PreviewCellarDomainListView() {
    val l = mutableListOf<DomainInfo>()
    val l1 = mutableListOf<PackageItem>()
    repeat(5) { ind -> l1 += PackageItem( "Wine Name $ind", "Max Qty: 1000 | Stock Qty: 900 | 13% alc.")}
    repeat(1) { ind -> l += DomainInfo(ind, "Domain Name $ind", l1)}
    Surface(color = Color.White) {
        CellarDomainListView(l, null)
    }
}
