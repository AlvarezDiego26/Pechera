package com.example.pecherainteligenteapp.data.model

data class Breed(
    val id: Long,
    val name: String,
    val species: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val maxHeartRate: Int,
    val minHeartRate: Int
)