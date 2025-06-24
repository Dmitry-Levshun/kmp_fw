package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import app.cash.paging.compose.itemKey

@Composable
fun UntrackedUserWinePaginatedListView(wineList: LazyPagingItems<UntrackedUserWineItem>, onItemClick: ((UntrackedCellarWineItem) -> Unit)? = null) {
    val lazyGridState = rememberLazyGridState()
    val modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxSize()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        state = lazyGridState,
    ) {
        items(count = wineList.itemCount,
              key = wineList.itemKey{ it.id }
        ) { index ->
            val cl = wineList[index]
            if (cl != null) {
                val wl = UntrackedCellarWineItem.fromUntrackedUserWineItem(cl)
                CellarUntrackedWineInfoCard(wl, onItemClick = onItemClick)
            }
        }
        if ((wineList.itemCount % 2) == 1) item { Spacer(modifier = Modifier.height(190.dp)) }
        item { Spacer(modifier = Modifier.height(130.dp)) }
    }

}
