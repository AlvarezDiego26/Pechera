package com.example.pecherainteligenteapp.domain.model.repository

import com.example.pecherainteligenteapp.data.model.SensorData
import kotlinx.coroutines.flow.Flow

interface SensorRepository {
    fun getSensorData(): Flow<SensorData>
}
