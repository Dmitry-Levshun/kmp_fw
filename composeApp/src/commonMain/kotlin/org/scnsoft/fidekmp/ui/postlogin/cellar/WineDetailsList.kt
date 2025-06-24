package org.scnsoft.fidekmp.ui.postlogin.cellar

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.domain.model.PackageItem
import org.scnsoft.fidekmp.domain.model.WineDetailInfo
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle18
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle

@Composable
fun WineDetailsList(
    detailList: List<WineDetailInfo>,
    onItemClick: ((PackageItem) -> Unit)? = null
) {
    val lazyListState = rememberLazyListState()
    if (detailList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            state = lazyListState
        ) {
            items(items = detailList) { cl ->
                if (!cl.datailParams.isNullOrEmpty()) DetailInfoCard(
                    info = cl,
                    onItemClick = onItemClick
                )
            }
            item { Spacer(modifier = Modifier.height(96.dp)) }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
//                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(Res.string.list_is_empty),
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
fun DetailInfoCard(info: WineDetailInfo, onItemClick: ((PackageItem) -> Unit)? = null) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(modifier = Modifier
                .clickable { expanded = expanded.not() }) {
                Text(
                    text = info.detailName,
                    style = textHeaderStyle
                )
                Image(
                    if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(primaryColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(24.dp),

                    alignment = Alignment.CenterEnd)

            }
            if (expanded) Column {
//                info.datailParams.forEach{item ->
                if (info.detailName.contains(stringResource(Res.string.scores))) {
                    DetailItemScoresCard(info.datailParams)
                } else DetailItemInfoCard(info.datailParams)
                Spacer(modifier = Modifier.height(4.dp))
            }

        }
    }
}

@Composable
fun DetailItemInfoCard(params: List<Pair<String, String?>>?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        if (params.isNullOrEmpty()) {
            Text(text = "Coming soon")
            return@Card
        }
        Column {
            params.forEach {
                val label = it.first
                val value = it.second
                if (!value.isNullOrBlank()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = label, modifier = Modifier.fillMaxWidth(0.3f))
                        Text(text = value, modifier = Modifier.fillMaxWidth(0.6f), textAlign = TextAlign.End)
                    }
                } else {
//                    HtmlText(html = label)
                    Text(text = label, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
                    Spacer(modifier = Modifier.height(32.dp))
                }

            }
        }
    }
}

@Composable
fun DetailItemScoresCard(params: List<Pair<String, String?>>?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        if (params.isNullOrEmpty()) {
            Text(text = "Coming soon")
            return@Card
        }
        Column {
            params.forEachIndexed cont@{ index, it ->
                if (index % 4 != 0) return@cont
                Column {
                    val revName = if (index + 1 < params.size) params[index + 1].second else null
                    val revScore = if (index + 1 < params.size)  params[index + 2].second else null
                    val revDesc = if (index < params.size) params[index].second else null
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (!revName.isNullOrBlank()) Text(text = revName, color = primaryColor)
                        if (!revScore.isNullOrBlank()) Text(text = revScore, color = primaryColor)
                    }
                    if (!revDesc.isNullOrBlank()) Text(text = revDesc, fontStyle = FontStyle.Italic)
                } //column
                Spacer(modifier = Modifier.height(8.dp))
            } // params
        } //column
    } // card
}

private fun fakePairs(): List<Pair<String, String>> {
    val l = mutableListOf<Pair<String, String>>()
    repeat(5) { ind -> l += Pair("Name$ind", "Value$ind") }
    l += Pair("certification", listOf("Biodynamie","Nature","Agriculture biologique","HVE level 3","HVE level 2","HVE level 1").toString())
    return l
}

@Preview
@Composable
private fun PreviewWineDetailsList() {
    val desc =
        "Traditionally, Bordeaux estates were named after their owners or founders. But history’s path to the ownership of Chateau Leoville Poyferre is a long and winding road shared with Chateau Leoville Las Cases starting all the way back to 1638.\\r\\n\\r\\nJean de Moytie a member of the Bordeaux Parliament owned a Bordeaux vineyard. Moytie called it Mont-Moytie. "
    val scores =
        listOf(Pair("description", desc), Pair("reviewer", "reviewer1"), Pair("score", "SCORE1"))
    val list = listOf(
        WineDetailInfo("Wine Details", fakePairs()),
        WineDetailInfo("Scores", scores),
        WineDetailInfo(
            "Description",
            listOf(Pair(stringResource(Res.string.wine_detail_description), null))
        ),
    )
    WineDetailsList(list)
}
