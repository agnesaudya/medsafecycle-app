package com.example.medsafecycle

import com.google.gson.annotations.SerializedName

data class LimbahResponse(

    @field:SerializedName("image_link")
    val imageLink: String?,

    @field:SerializedName("waste_information")
    val wasteInformation: WasteInformation?,

    @field:SerializedName("waste_type")
    val wasteType: String?,

    @field:SerializedName("waste_type_id")
    val wasteTypeId: String?
)

data class WasteInfo(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("extermination")
	val extermination: List<String>
)
