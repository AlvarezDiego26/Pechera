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
                    Text("Valor: ${data.ecg} bpm")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("📦 Acelerómetro", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text("AcX: ${data.AcX}")
                    Text("AcY: ${data.AcY}")
                    Text("AcZ: ${data.AcZ}")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("🎯 Giroscopio", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text("GyX: ${data.GyX}")
                    Text("GyY: ${data.GyY}")
                    Text("GyZ: ${data.GyZ}")
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
