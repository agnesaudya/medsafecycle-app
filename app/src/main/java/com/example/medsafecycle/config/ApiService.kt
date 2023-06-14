package com.example.medsafecycle.config

import com.example.medsafecycle.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    ): Call<AuthResponse>


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthResponse>


    //
//
    @Multipart
    @POST("upload")
    fun scanImage(
        @Part file: MultipartBody.Part,
    ): Call<UploadResponse>


    //
    @GET("history/{size}/{offset}")
    suspend fun getAllHistory(
        @Header("x-access-token") token: String,
        @Path("size") size: Number,
        @Path("offset") offset: Number
    ): HistoryResponse

    @GET("wastes/{waste_id}")
    fun getLimbah(@Path("waste_id") id: String): Call<LimbahResponse>

    @GET("myprofile}")
    fun getProfile(): Call<ProfileResponse>


}