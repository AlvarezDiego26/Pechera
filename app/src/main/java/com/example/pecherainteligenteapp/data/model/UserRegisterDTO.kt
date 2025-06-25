package com.example.pecherainteligenteapp.data.model

data class UserRegisterDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val city: String,
    val username: String
)