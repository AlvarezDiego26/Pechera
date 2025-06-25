package com.example.pecherainteligenteapp.data.model

data class UserResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val city: String,
    val username: String,
    val isConfirmed: Boolean,
    val fcmToken: String?
)