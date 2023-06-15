package com.example.medsafecycle.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: Long,
    val offset: Long,
    val prevKey: Long?,
    val nextKey: Long?
)