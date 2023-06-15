package com.example.medsafecycle.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medsafecycle.HistoryResponseItem

@Dao
interface LimbahDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLimbah(story: List<HistoryResponseItem>)

    @Query("SELECT * FROM limbah")
    fun getAllLimbah(): PagingSource<Int, HistoryResponseItem>

    @Query("DELETE FROM limbah")
    suspend fun deleteAll()
}