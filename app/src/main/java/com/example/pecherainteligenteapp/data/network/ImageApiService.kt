package com.example.pecherainteligenteapp.data.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ImageApiService {

    @Multipart
    @POST("api/pets/photo/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): Response<UploadResponse>

    @GET("/api/pets/photo/{filename}")
    suspend fun getImage(@Path("filename") filename: String): Response<ResponseBody>
}
