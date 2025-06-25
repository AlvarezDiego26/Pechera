package com.example.pecherainteligenteapp.data.network

import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("api/users")
    fun registerUser(@Body user: UserRegisterDTO): Call<UserResponse>

    @GET("api/users/{id}")
    fun getUser(@Path("id") id: Long): Call<UserResponse>

    @DELETE("api/users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>

    @PUT("api/users/{id}")
    fun updateUser(@Path("id") id: Long, @Body user: UserRegisterDTO): Call<UserResponse>

    @GET("api/users/password-reset/request")
    fun requestPasswordReset(@Query("email") email: String): Call<Void>

    @POST("api/users/confirm/{token}")
    fun confirmAccount(@Path("token") token: String): Call<UserResponse>
}