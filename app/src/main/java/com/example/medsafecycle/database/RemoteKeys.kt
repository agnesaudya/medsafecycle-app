package com.example.medsafecycle.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)