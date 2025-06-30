package com.example.pecherainteligenteapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pecherainteligenteapp.data.model.DeviceRequest
import com.example.pecherainteligenteapp.data.network.RetrofitClient
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterDeviceScreen(
    navController: NavController,
    petId: Long,
    authViewModel: AuthViewModel
) {
    var deviceId by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }
    var responseMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val apiService = RetrofitClient.apiService
    val userId = authViewModel.loggedInUserId ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "🔗 Registrar Dispositivo para Mascota ID: $petId", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = deviceId,
            onValueChange = { deviceId = it },
            label = { Text("ID del dispositivo (ej: MAC o Serial)") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isActive,
                onCheckedChange = { isActive = it }
            )
            Text("Activo")
        }

        Button(onClick = {
            scope.launch {
                try {
                    val deviceRequest = DeviceRequest(
                        petId = petId,
                        deviceId = deviceId,
                        isActive = isActive
                    )

                    val response = apiService.createDevice(deviceRequest)

                    if (response.isSuccessful) {
                        responseMessage = "✅ Dispositivo vinculado correctamente"

                        navController.navigate("sensor/$userId/$petId")
                    } else {
                        responseMessage = "❌ Error: ${response.code()}"
                    }
                } catch (e: Exception) {
                    responseMessage = "❌ Error: ${e.localizedMessage}"
                }
            }
        }) {
            Text("Registrar Dispositivo")
        }

        if (responseMessage.isNotEmpty()) {
            Text(responseMessage)
        }
    }
}
