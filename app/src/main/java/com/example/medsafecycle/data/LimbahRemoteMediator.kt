package com.example.medsafecycle.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.config.ApiService
import com.example.medsafecycle.database.LimbahDatabase
import com.example.medsafecycle.database.RemoteKeys

@OptIn(ExperimentalPagingApi::class)
class LimbahRemoteMediator(
    private val database: LimbahDatabase,
    private val apiService: ApiService,
    private val token: String
) : RemoteMediator<Int, HistoryResponseItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

        override suspend fun initialize(): InitializeAction {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HistoryResponseItem>
    ): MediatorResult {
        Log.d("test",state.pages.toString())
        Log.d("test",loadType.name.toString())
        val offset = when (loadType) {

            LoadType.REFRESH -> 0L
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.id ?: 0L
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val currentOffset = remoteKeys?.offset ?: 0L
                currentOffset + state.config.pageSize
            }
        }

        return try {
            val responseData = apiService.getAllHistory(token, state.config.pageSize, offset)

            val endOfPaginationReached = responseData.size <= state.config.pageSize
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.limbahDao().deleteAll()
                }
                val prevKey = if (offset == 0L) null else offset - state.config.pageSize
                val nextKey = if (endOfPaginationReached) null else offset + state.config.pageSize +1
                val keys = responseData.mapIndexed { index, item ->
                    Log.d("test",index.toString())
                    Log.d("test",item.wasteId.toString())
                    Log.d("test", (offset + index).toString())
                    RemoteKeys(item.wasteId, offset + index, prevKey, nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.limbahDao().insertLimbah(responseData)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            Log.d("test",exception.message.toString())
            MediatorResult.Error(exception)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, HistoryResponseItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.wasteId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, HistoryResponseItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.wasteId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, HistoryResponseItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.wasteId?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }

    }
}
//    @OptIn(ExperimentalPagingApi::class)
//    class LimbahRemoteMediator(
//        private val database: LimbahDatabase,
//        private val apiService: ApiService,
//        private val token: String
//    ) : RemoteMediator<Int, HistoryResponseItem>() {
//
//        private companion object {
//            const val INITIAL_PAGE_INDEX = 1
//        }
//
//    //    override suspend fun initialize(): InitializeAction {
//    //        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    //    }
//
//        override suspend fun load(
//            loadType: LoadType,
//            state: PagingState<Int, HistoryResponseItem>
//        ): MediatorResult {
//            Log.d("test",state.pages.toString())
//            Log.d("test",loadType.name.toString())
//            val page = when (loadType) {
//                LoadType.REFRESH -> {
//                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                    remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
//                }
//                LoadType.PREPEND -> {
//                    val remoteKeys = getRemoteKeyForFirstItem(state)
//                    val prevKey = remoteKeys?.prevKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                    prevKey
//                }
//                LoadType.APPEND -> {
//                    val remoteKeys = getRemoteKeyForLastItem(state)
//                    val nextKey = remoteKeys?.nextKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                    nextKey
//                }
//            }
//
//            return try {
//                val responseData = apiService.getAllHistory(token, state.config.pageSize, page)
//
//                val endOfPaginationReached = responseData.isEmpty()
//                database.withTransaction {
//                    if (loadType == LoadType.REFRESH) {
//                        database.remoteKeysDao().deleteRemoteKeys()
//                        database.limbahDao().deleteAll()
//                    }
//                    val prevKey = if (page == 1) null else page - 1
//                    val nextKey = if (endOfPaginationReached) null else page + 1
//                    val keys = responseData.map {
//                        Log.d("test",it.wasteId.toString())
//                        RemoteKeys(id = it.wasteId, prevKey = prevKey, nextKey = nextKey)
//                    }
//                    database.remoteKeysDao().insertAll(keys)
//                    database.limbahDao().insertLimbah(responseData)
//                }
//                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//            } catch (exception: Exception) {
//                Log.d("test",exception.message.toString())
//                MediatorResult.Error(exception)
//            }
//        }
//
//        private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, HistoryResponseItem>): RemoteKeys? {
//            return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
//                database.remoteKeysDao().getRemoteKeysId(data.wasteId)
//            }
//        }
//
//        private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, HistoryResponseItem>): RemoteKeys? {
//            return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
//                database.remoteKeysDao().getRemoteKeysId(data.wasteId)
//            }
//        }
//
//        private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, HistoryResponseItem>): RemoteKeys? {
//            return state.anchorPosition?.let { position ->
//                state.closestItemToPosition(position)?.wasteId?.let { id ->
//                    database.remoteKeysDao().getRemoteKeysId(id)
//                }
//            }
//
//        }
//    }
