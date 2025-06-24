package com.example.pecherainteligenteapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pecherainteligenteapp.viewmodel.SensorViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetSensorScreen() {
    val viewModel: SensorViewModel = viewModel()
    val data = viewModel.sensorData.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🐶 Pechera Inteligente", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🧠 ECG", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text("Valor: ${data.ecg_raw} bpm")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🌡️ Temperatura", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = if (data.temperatura_celsius != null)
                            "${data.temperatura_celsius} °C"
                        else
                            "Sensor no detectado"
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🏃 Movimiento", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text("Estado: ${data.movimiento}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.startWebSocket() },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("📡 Conectar WebSocket")
            }
        }
    }
}
