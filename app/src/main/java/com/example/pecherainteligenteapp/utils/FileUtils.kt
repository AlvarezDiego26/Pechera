package com.example.pecherainteligenteapp.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    fun getMultipartFromUri(context: Context, uri: Uri, paramName: String): MultipartBody.Part? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
            val outputStream = FileOutputStream(tempFile)
            inputStream?.copyTo(outputStream)
            outputStream.close()
            inputStream?.close()

            val requestBody = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(paramName, tempFile.name, requestBody)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
