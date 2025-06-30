package com.example.pecherainteligenteapp.data.network

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.util.CoilUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-API-KEY", "uiksafasf1290") // ← tu clave API
            .build()
        return chain.proceed(request)
    }
}

fun provideImageLoader(context: Context): ImageLoader {
    val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .build()

    return ImageLoader.Builder(context)
        .okHttpClient(client)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizeBytes(50L * 1024 * 1024) // Ejemplo: 50 MB
                .build()
        }
        .build()
}