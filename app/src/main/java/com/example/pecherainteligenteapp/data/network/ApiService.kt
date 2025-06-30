package com.example.pecherainteligenteapp.data.network

import com.example.pecherainteligenteapp.data.model.LoginRequest
import com.example.pecherainteligenteapp.data.model.PetRequest
import com.example.pecherainteligenteapp.data.model.PetResponse
import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.data.model.UserResponse
import com.example.pecherainteligenteapp.data.model.DeviceRequest
import com.example.pecherainteligenteapp.data.model.Breed
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

    @POST("/api/pets")
    suspend fun createPet(@Body pet: PetRequest): Response<PetResponse>

    @POST("/api/devices")
    suspend fun createDevice(@Body request: DeviceRequest): Response<Any>

    @GET("/api/breeds")
    suspend fun getBreeds(): List<Breed>

    @GET("/api/pets/{id}")
    suspend fun getPetById(@Path("id") id: Long): Response<PetResponse>

    @GET("api/pets/owner/{ownerId}")
    suspend fun getPetsByOwnerId(@Path("ownerId") ownerId: Long): Response<List<PetResponse>>

    @DELETE("api/pets/{id}/")
    suspend fun deletePet(@Path("id") petId: Long): Response<Void>


}
