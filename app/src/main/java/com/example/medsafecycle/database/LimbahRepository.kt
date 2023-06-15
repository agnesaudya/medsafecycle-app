package com.example.medsafecycle.database

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.config.ApiService
import com.example.medsafecycle.data.HistoryPagingSource

class LimbahRepository(private val limbahDatabase:LimbahDatabase, private val apiService: ApiService, private val token:String) {
    fun getHistory(): LiveData<PagingData<HistoryResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
//            remoteMediator = LimbahRemoteMediator(limbahDatabase, apiService, token),
            pagingSourceFactory = {
               HistoryPagingSource(apiService, token)
            }
        ).liveData
    }
}