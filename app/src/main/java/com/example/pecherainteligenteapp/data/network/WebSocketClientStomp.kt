package com.example.pecherainteligenteapp.data.network

import android.util.Log
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import io.reactivex.disposables.Disposable

class WebSocketClientStomp(
    private val onMessageReceived: (String) -> Unit
) {
    private lateinit var stompClient: StompClient
    private var lifecycleDisposable: Disposable? = null
    private var topicDisposable: Disposable? = null

    fun connect(url: String = "ws://10.0.2.2:8080/ws")
    {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

        // Suscribirse al ciclo de vida del WebSocket
        lifecycleDisposable = stompClient.lifecycle().subscribe { event: LifecycleEvent ->
            when (event.type) {
                LifecycleEvent.Type.OPENED -> Log.d("STOMP", "✅ Conectado")
                LifecycleEvent.Type.CLOSED -> Log.d("STOMP", "🔌 Desconectado")
                LifecycleEvent.Type.ERROR -> Log.e("STOMP", "❌ Error", event.exception)
                else -> {}
            }
        }

        // Conexión
        stompClient.connect()

        // Suscribirse al tópico y mostrar en Logcat
        topicDisposable = stompClient.topic("/topic/sensores")
            .subscribe { topicMessage: StompMessage ->
                Log.d("STOMP_MSG", "📥 Mensaje recibido: ${topicMessage.payload}") // <-- AQUI
                onMessageReceived(topicMessage.payload)
            }
    }

    fun disconnect() {
        if (::stompClient.isInitialized) {
            stompClient.disconnect()
            lifecycleDisposable?.dispose()
            topicDisposable?.dispose()
        }
    }
}
