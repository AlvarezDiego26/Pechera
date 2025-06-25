package com.example.pecherainteligenteapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-API-KEY", "uiksafasf1290") // ← tu clave API
            .build()
        return chain.proceed(request)
    }
}