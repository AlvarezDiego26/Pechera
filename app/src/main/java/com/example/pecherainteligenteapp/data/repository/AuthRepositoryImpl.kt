package com.example.pecherainteligenteapp.data.repository

import com.example.pecherainteligenteapp.data.model.LoginRequest
import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.data.model.UserResponse
import com.example.pecherainteligenteapp.data.network.ApiService
import com.example.pecherainteligenteapp.domain.model.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthRepositoryImpl(private val api: ApiService) : AuthRepository {
    override suspend fun registerUser(user: UserRegisterDTO): Result<UserResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.registerUser(user)
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(Exception("Error en el registro: ${e.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun login(email: String, password: String): Result<UserResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Credenciales inválidas o cuenta no confirmada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun confirmAccount(token: String): UserResponse {
        return api.confirmAccount(token)
    }



}
