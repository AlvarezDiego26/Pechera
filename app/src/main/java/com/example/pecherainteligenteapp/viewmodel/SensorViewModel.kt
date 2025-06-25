package com.example.pecherainteligenteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pecherainteligenteapp.data.model.SensorData
import com.example.pecherainteligenteapp.data.network.WebSocketClientStomp
import com.example.pecherainteligenteapp.data.repository.SensorRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SensorViewModel : ViewModel() {

    private val repository = SensorRepository()

    // Cliente WebSocket STOMP
    private val webSocketClient = WebSocketClientStomp { message ->
        viewModelScope.launch {
            repository.processMessage(message)
        }
    }

    val sensorData: StateFlow<SensorData> = repository.sensorData

    fun startWebSocket() {
        // Nota: Este es el endpoint real de STOMP (ver configuración backend)
        webSocketClient.connect("ws://192.168.1.3:8080/ws/websocket")
    }

    fun stopWebSocket() {
        webSocketClient.disconnect()
    }
}
