package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedCellarWineItem
import org.scnsoft.fidekmp.ui.theme.burgundiColor
import org.scnsoft.fidekmp.ui.theme.listBackColor50
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle18
import org.scnsoft.fidekmp.ui.theme.textInfoStyle
import org.scnsoft.fidekmp.ui.theme.untrackedWinetype
import org.scnsoft.fidekmp.ui.theme.walletBackColor
import org.scnsoft.fidekmp.ui.utils.getBottleImageByColor
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CellarUntrackedWineList(
    clientList: List<UntrackedCellarWineItem>,
    onItemClick: ((UntrackedCellarWineItem) -> Unit)? = null
) {
    val state = rememberLazyGridState()//rememberLazyListState()
    val modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxSize()
//    LazyVerticalGrid(columns = GridCells.Fixed(2), state = rememberLazyGridState()){
//        items(data.size) { ind ->
//            BottleInfoCard(item = data[ind], Modifier)
//        }
//    }

    if (clientList.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            state = state
        ) {
            items(clientList.size) { ind ->
                CellarUntrackedWineInfoCard(clientList[ind], onItemClick = onItemClick)
            }
            if ((clientList.size % 2) == 1) item { Spacer(modifier = Modifier.height(190.dp)) }
            item { Spacer(modifier = Modifier.height(130.dp)) }
        }
        Spacer(modifier = Modifier.height(130.dp))
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
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
fun CellarUntrackedWineInfoCard(
    item: UntrackedCellarWineItem,
    backColor: Color = listBackColor50,
    onItemClick: ((UntrackedCellarWineItem) -> Unit)? = null
) {
    var isClicked by remember { mutableStateOf(false) }
    val clMb = Color(0x300A3F66)
    val clYel = Color(0x30FFFF00)
    val animatedColor by animateColorAsState(
        targetValue = if (isClicked) clMb else Color.Transparent,
//        animationSpec = tween(durationMillis = 300)
    )
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .padding(4.dp),
//        backgroundColor = animatedColor,
        elevation = 0.dp
    ) {
        LaunchedEffect(isClicked) {
            if (isClicked) {
                delay(300)
                isClicked = false
            }
        }
        val shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
        var drawRes = Res.drawable.bottle_wine
        drawRes = getBottleImageByColor(item.color)
        val painter = painterResource(drawRes)
        Box(
            modifier = Modifier
//                .padding(20.dp)
                .clickable {
                    isClicked = true
                    onItemClick?.invoke(item)
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .background(walletBackColor)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .align(Alignment.BottomCenter)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.wineName,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 64.dp),
                    color = primaryTextColor,
                    style = untrackedWinetype,
                    minLines = 2,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(2.dp))
                if (item.reviewsCount == 0) {
                    Text(
                        text = "${item.vintage}",
                        color = burgundiColor,
                        style = textInfoStyle,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Row(modifier = Modifier.fillMaxWidth().padding(start = 4.dp, end = 4.dp, bottom = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = " ${item.vintage}",
                            color = burgundiColor,
                            style = textInfoStyle,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "${stringResource(Res.string.reviews)}(${item.reviewsCount}) ",
                            color = burgundiColor,
                            style = textInfoStyle,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
            Image(
                painter = painter,
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.TopCenter)
                    .size(40.dp, 130.dp)
            )
            Box(modifier = Modifier.fillMaxSize().background(animatedColor))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun PreviewUntrackedSelectWineListView() {
    val l1 = mutableListOf<UntrackedCellarWineItem>()
    repeat(5) { ind ->
        l1 += UntrackedCellarWineItem(
            id = ind,
            wineName = "Wine Name $ind",
            vintage = 2018 + ind,
            color = "White",
            reviewsCount = if (ind % 2 == 0) 0 else ind
        )
    }
    Surface(color = Color.White) {
        CellarUntrackedWineList(l1, null)
    }
}
