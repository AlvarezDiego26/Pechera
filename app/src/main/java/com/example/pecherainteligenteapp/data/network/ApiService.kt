package com.example.pecherainteligenteapp.data.network

import com.example.pecherainteligenteapp.data.model.LoginRequest
import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*
import retrofit2.Response


interface ApiService {
    @POST("api/users")
    suspend fun registerUser(@Body user: UserRegisterDTO): UserResponse

    @POST("/api/users/login")
    suspend fun login(@Body request: LoginRequest): Response<UserResponse>

    @GET("api/users/{id}")
    suspend fun getUser(@Path("id") id: Long): UserResponse

    @DELETE("api/users/{id}")
    suspend fun deleteUser(@Path("id") id: Long): Void

    @PUT("api/users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: UserRegisterDTO): UserResponse

    @GET("api/users/password-reset/request")
    suspend fun requestPasswordReset(@Query("email") email: String): Void

    @GET("api/users/confirm/{token}")
    suspend fun confirmAccount(@Path("token") token: String): UserResponse

}
