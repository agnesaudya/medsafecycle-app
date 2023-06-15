package com.example.medsafecycle.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.config.ApiService

class HistoryPagingSource (private val apiService: ApiService,private val token: String) : PagingSource<Int, HistoryResponseItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 0
        const val NETWORK_PAGE_SIZE = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryResponseItem> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val offset = if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_PAGE_INDEX
        return try {

            val responseData = apiService.getAllHistory(token,  params.loadSize, offset)
            val nextKey = if (responseData.isEmpty()) {
                null
            }else{
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HistoryResponseItem>): Int? {
        return null
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
    }

}