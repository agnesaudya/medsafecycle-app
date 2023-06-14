package com.example.medsafecycle

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("user_email")
	val userEmail: String?,

	@field:SerializedName("user_address")
	val userAddress: String?,

	@field:SerializedName("username")
	val username: String?
)
