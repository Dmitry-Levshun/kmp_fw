package org.scnsoft.fidekmp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.data.api.untracked.UntrackedWineApi
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem

class WinesDataSource(
    private val wineApiService: UntrackedWineApi,
    private val query: String
): PagingSource<Int, UntrackedWineItem>() {
    override fun getRefreshKey(state: PagingState<Int, UntrackedWineItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UntrackedWineItem> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val name = query.ifEmpty { null }
            val response = wineApiService.getUntrackedWines(page = page, itemsPerPage = pageSize, name = name)
            if (response.isSuccess) {
//            val items = fakeNotificationList(page = page, itemsPerPage = 20) //response.articles,
                val dto = response.getOrNull() ?: return LoadResult.Error(RuntimeException("WinesDataSource No data"))
                val items = dto.wineList
                return LoadResult.Page(
                    data = items,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (items.isEmpty()) null else page.plus(1),
                )
            }
            return LoadResult.Error(RuntimeException("WinesDataSource No Data"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

class UserWinesDataSource(
    private val wineApiService: UntrackedWineApi,
    private val query: String
): PagingSource<Int, UntrackedUserWineItem>() {
     override fun getRefreshKey(state: PagingState<Int, UntrackedUserWineItem>): Int? {
        val st =  state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
//        val st = null
        Napier.d("UserWinesDataSource getRefreshKey st:$st")
        return st
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UntrackedUserWineItem> {
        return try {
            val page = params.key ?: 1
            val pageSize = 10 //params.loadSize
            Napier.d("UserWinesDataSource load pg:$page sz:$pageSize szl:${params.loadSize}")
            val name = query.ifEmpty { null }
            val response = wineApiService.getUntrackedUserWines(page = page, itemsPerPage = pageSize, name = name)
            if (response.isSuccess) {
//            val items = fakeNotificationList(page = page, itemsPerPage = 20) //response.articles,
                val dto = response.getOrNull() ?: return LoadResult.Error(RuntimeException("UserWinesDataSource No data"))
                val items = dto.wineList
                return LoadResult.Page(
                    data = items,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (items.isEmpty()) null else page.plus(1),
                )
            }
            return LoadResult.Error(RuntimeException("UserWinesDataSource No data"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}