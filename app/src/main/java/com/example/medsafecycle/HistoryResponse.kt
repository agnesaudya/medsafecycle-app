package com.example.medsafecycle

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "limbah")
data class HistoryResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("image_link")
	val imageLink: String,


	@PrimaryKey
	@field:SerializedName("waste_id")
	val wasteId: Long,

	@field:SerializedName("waste_type")
	val wasteType: String
)
