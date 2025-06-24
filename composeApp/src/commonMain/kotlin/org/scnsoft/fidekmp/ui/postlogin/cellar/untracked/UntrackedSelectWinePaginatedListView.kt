package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem

@Composable
fun UntrackedSelectWinePaginatedListView(wineList: LazyPagingItems<UntrackedWineItem>, query: String, onItemClick: ((UntrackedCellarWineItem) -> Unit)? = null) {
    val lazyListState = rememberLazyListState()
    val modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxSize()
        LazyColumn(
            modifier = modifier,
            state = lazyListState
        ) {
            items(count = wineList.itemCount,
                key = wineList.itemKey{ it.id },
                contentType = wineList.itemContentType{"contentType"}
            ) { index ->
                val cl = wineList[index]
                if (cl != null) {
                    val wl = UntrackedCellarWineItem.fromUntrackedWineItem(cl, query)
                    if (wl.isVisible) UntrackedSelectWineInfoCard(wl, onItemClick = onItemClick)
                }
            }
            item{ Spacer(modifier = Modifier.height(80.dp)) }
        }
}
