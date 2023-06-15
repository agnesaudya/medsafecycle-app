package com.example.medsafecycle

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("waste_information")
	val wasteInformation: WasteInformation?,

	@field:SerializedName("message")
	val message: String
)

data class WasteInformation(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("extermination")
	val extermination: List<String>
)
