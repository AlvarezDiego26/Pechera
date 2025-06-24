package com.example.pecherainteligenteapp.data.repository

import com.example.pecherainteligenteapp.data.model.SensorData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

class SensorRepository {
    private val _sensorData = MutableStateFlow(SensorData())
    val sensorData: StateFlow<SensorData> = _sensorData

    fun processMessage(message: String) {
        try {
            val json = JSONObject(message)

            val ecg = json.optInt("ecg_raw", 0)
            val temp = json.optDouble("temperatura_celsius", Double.NaN).toFloat()
            val movimiento = json.optString("movimiento", "Desconocido")

            val sensorData = SensorData(
                ecg_raw = ecg,
                temperatura_celsius = if (!temp.isNaN()) temp else null,
                movimiento = movimiento
            )

            _sensorData.value = sensorData

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
