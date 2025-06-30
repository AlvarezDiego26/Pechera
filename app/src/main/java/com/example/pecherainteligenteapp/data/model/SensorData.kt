package com.example.pecherainteligenteapp.data.model

import com.google.gson.annotations.SerializedName

data class SensorData(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("ecgRaw")
    val ecgRaw: Int = 0,

    @SerializedName("temperatura")
    val temperatura: Float? = null,

    @SerializedName("movimiento")
    val movimiento: String = "Desconocido",

    @SerializedName("fecha")
    val fecha: String? = null,

    @SerializedName("petId")
    val petId: Long? = null
)
