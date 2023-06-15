package com.example.medsafecycle

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,
)

data class Data(

	@field:SerializedName("token")
	val token: String? = null
)
