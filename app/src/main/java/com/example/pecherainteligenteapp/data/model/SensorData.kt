package com.example.pecherainteligenteapp.data.model

data class SensorData(
    val ecg_raw: Int = 0,
    val temperatura_celsius: Float? = null,
    val movimiento: String = "Desconocido"
)
