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

            val ecg = json.getInt("ecg")
            val acX = json.getInt("AcX")
            val acY = json.getInt("AcY")
            val acZ = json.getInt("AcZ")
            val gyX = json.getInt("GyX")
            val gyY = json.getInt("GyY")
            val gyZ = json.getInt("GyZ")

            _sensorData.value = SensorData(ecg, acX, acY, acZ, gyX, gyY, gyZ)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
