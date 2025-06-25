package com.example.pecherainteligenteapp.domain.model.repository

import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.data.model.UserResponse

interface AuthRepository {
    suspend fun registerUser(user: UserRegisterDTO): Result<UserResponse>
    suspend fun login(email: String, password: String): Result<UserResponse>
    suspend fun confirmAccount(token: String): UserResponse

}

