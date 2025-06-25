package com.example.pecherainteligenteapp.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://vitalpaw.tecsup.xyz/"

    // Interceptor para logging (ver solicitudes/respuestas HTTP)
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cliente OkHttp con el interceptor de API key y logging
    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor()) // Interceptor con tu API KEY
        .addInterceptor(logging)             // Interceptor de logs
        .build()

    // Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client) // Usa el cliente configurado
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Servicio API
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
