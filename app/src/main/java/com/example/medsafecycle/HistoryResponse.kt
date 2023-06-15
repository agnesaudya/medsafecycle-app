package com.example.medsafecycle

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class HistoryResponse(
	@field:SerializedName("HistoryResponse")
	val historyResponse: List<HistoryResponseItem>?
)
@Entity(tableName = "limbah")
data class HistoryResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("image_link")
	val imageLink: String,


	@PrimaryKey
	@field:SerializedName("waste_id")
	val wasteId: Int,

	@field:SerializedName("waste_type")
	val wasteType: String
)
