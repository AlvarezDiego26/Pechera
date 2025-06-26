package com.example.pecherainteligenteapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pecherainteligenteapp.viewmodel.SensorViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pecherainteligenteapp.R
import com.example.pecherainteligenteapp.ui.theme.components.ECGChart


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetSensorScreen() {
    val viewModel: SensorViewModel = viewModel()
    val data = viewModel.sensorData.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo1_image),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(32.dp) // tamaño aumentado
                                    .align(Alignment.Start) // bien a la izquierda
                            )
                            Text(
                                text = "🐶 Pechera Inteligente",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                },
                title = { Text(" ") }, // No usar título para evitar conflicto
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp)) // baja el contenido para equilibrio visual

            // Card ECG
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
                    Text("Valor: ${data.ecg_raw} unidades")
                    ECGChart(ecgValue = data.ecg_raw)
                }
            }

            // Card Temperatura
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



