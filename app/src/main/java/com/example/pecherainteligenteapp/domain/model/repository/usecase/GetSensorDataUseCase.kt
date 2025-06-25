package com.example.pecherainteligenteapp.domain.model.repository.usecase

import com.example.pecherainteligenteapp.data.model.SensorData
import com.example.pecherainteligenteapp.domain.model.repository.SensorRepository
import kotlinx.coroutines.flow.Flow

class GetSensorDataUseCase(private val repository: SensorRepository) {
    operator fun invoke(): Flow<SensorData> {
        return repository.getSensorData()
    }
}
