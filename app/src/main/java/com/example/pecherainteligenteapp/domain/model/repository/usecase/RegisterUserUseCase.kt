package com.example.pecherainteligenteapp.domain.model.repository.usecase

import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.data.model.UserResponse
import com.example.pecherainteligenteapp.domain.model.repository.AuthRepository

class RegisterUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(user: UserRegisterDTO): Result<UserResponse> {
        return repository.registerUser(user)
    }
}
