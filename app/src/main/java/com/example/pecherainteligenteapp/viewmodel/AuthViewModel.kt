package com.example.pecherainteligenteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.data.model.UserResponse
import com.example.pecherainteligenteapp.domain.model.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    fun register(
        user: UserRegisterDTO,
        onSuccess: (UserResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.registerUser(user)
            result
                .onSuccess { response -> onSuccess(response) }
                .onFailure { error -> onError(error.message ?: "Error desconocido") }
        }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: (UserResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            result
                .onSuccess { user -> onSuccess(user) }
                .onFailure { error -> onError(error.message ?: "Error desconocido") }
        }
    }
    fun confirmAccount(
        token: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.confirmAccount(token)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al confirmar la cuenta")
            }
        }
    }

}
