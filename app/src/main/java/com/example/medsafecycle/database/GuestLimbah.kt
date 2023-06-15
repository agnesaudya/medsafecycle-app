package com.example.medsafecycle.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GuestLimbah(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: String,
    
    var description: String = "",

    @TypeConverters(GuestLimbahTypeConverters::class)
    var extermination: List<String>,

    @ColumnInfo(name = "image_path")
    val imagePath: String,
) : Parcelable
