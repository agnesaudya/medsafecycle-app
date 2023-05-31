package com.example.medsafecycle.config

import com.example.medsafecycle.AuthResponse
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
//    @Multipart
//    @POST("stories")
//    fun uploadImage(
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//    ): Call<AddStoryResponse>
//
//
//    @GET("stories")
//    suspend fun getAllStories(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//        @Query("size") size: Int
//    ): StoriesResponse
//
//    @GET("stories")
//    fun getAllLocation(
//        @Query("location") location: Int,
//    ): Call<LocationResponse>
//
//
//    @GET("stories/{id}")
//    fun getStoryById(@Path("id") id: String
//    ): Call<DetailStoryResponse>
//

}