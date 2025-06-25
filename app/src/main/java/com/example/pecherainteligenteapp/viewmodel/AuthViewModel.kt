package com.example.pecherainteligenteapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.data.model.UserResponse
import com.example.pecherainteligenteapp.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {
    fun register(
        user: UserRegisterDTO,
        onSuccess: (UserResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        RetrofitClient.apiService.registerUser(user)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { onSuccess(it) }
                    } else {
                        onError("❌ Error en el registro: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    onError("❌ Fallo de red: ${t.message}")
                }
            })
    }
}
