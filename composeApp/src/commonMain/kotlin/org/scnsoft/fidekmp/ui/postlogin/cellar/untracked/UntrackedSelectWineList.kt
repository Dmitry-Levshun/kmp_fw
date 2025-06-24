package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedCellarWineItem
import org.scnsoft.fidekmp.ui.theme.listBackColor50
import org.scnsoft.fidekmp.ui.theme.primaryTextColor
import org.scnsoft.fidekmp.ui.theme.searchBackColor
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle18
import org.scnsoft.fidekmp.ui.theme.textInfoStyle
import org.scnsoft.fidekmp.ui.theme.textInfoStyle14
import org.scnsoft.fidekmp.ui.utils.getBottleImageByColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UntrackedSelectWineListView(clientList: List<UntrackedCellarWineItem>, onItemClick: ((UntrackedCellarWineItem) -> Unit)? = null) {
    val lazyListState = rememberLazyListState()
    val modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxSize()

    if (clientList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            state = lazyListState
        ) {
            items(items = clientList, key = { it.id }) { cl ->
                if (cl.isVisible) UntrackedSelectWineInfoCard(cl, onItemClick = onItemClick)
            }
            item{ Spacer(modifier = Modifier.height(80.dp))}
        }
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
fun UntrackedSelectWineInfoCard(item: UntrackedCellarWineItem, backColor: Color = listBackColor50, onItemClick: ((UntrackedCellarWineItem) -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = backColor,
        elevation = 0.dp
    ) {
        var drawRes = Res.drawable.bottle_wine
        drawRes = getBottleImageByColor(item.color)
        val painter = painterResource(drawRes)
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
                        text = highlightedText(item.wineName, item.query),
                        style = textInfoStyle14,
                    )
                    Text(text = highlightedText(item.domainName, item.query),
                        style = textInfoStyle
                    )
                }

            }
        }
    }

}

fun highlightedText(fullText: String, query: String): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = primaryTextColor)){
            append(fullText)
        }
        val startIndex = fullText.indexOf(query, ignoreCase = true)
        if (startIndex != -1) {
            addStyle(
                style = SpanStyle(background = searchBackColor),
                start = startIndex,
                end = startIndex + query.length
            )
        }
    }
    return annotatedString
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun PreviewUntrackedSelectWineListView() {
    val l1 = mutableListOf<UntrackedCellarWineItem>()
    repeat(5) { ind -> l1 += UntrackedCellarWineItem( id= ind, wineName = "Wine Name $ind", vintage = 2018+ ind, color = "red", domainName = "domain Name $ind",  query = "nam")}
    Surface(color = Color.White) {
        UntrackedSelectWineListView(l1, null)
    }
}
