package com.example.pecherainteligenteapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pecherainteligenteapp.data.network.provideImageLoader
import com.example.pecherainteligenteapp.ui.theme.components.ECGChart
import com.example.pecherainteligenteapp.viewmodel.PetViewModel
import com.example.pecherainteligenteapp.viewmodel.SensorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetSensorScreen(
    navController: NavController,
    userId: Long? = null,
    petId: Long? = null,
    deviceId: Long? = null,
    petViewModel: PetViewModel = viewModel(),
    sensorViewModel: SensorViewModel = viewModel()
) {
    val petData by petViewModel.petData.collectAsState()
    val sensorData by sensorViewModel.sensorData.collectAsState()
    val context = LocalContext.current
    val imageLoader = remember { provideImageLoader(context) }

    LaunchedEffect(petId) {
        petId?.let { petViewModel.getPetById(it) }
    }

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
            petData?.let { pet ->
                Text("Nombre: ${pet.name}", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                val imageUrl = "http://192.168.1.3:8080/api/pets/photo/${pet.photo}"

                Image(
                    painter = rememberAsyncImagePainter(
                        model = imageUrl,
                        imageLoader = imageLoader
                    ),
                    contentDescription = "Foto de la mascota",
                    modifier = Modifier
                        .size(160.dp)
                        .padding(8.dp)
                )
            }

            sensorData?.let { data ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE))
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("🧠 ECG", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Text("Valor: ${data.ecgRaw} unidades")
                        ECGChart(ecgValue = data.ecgRaw)
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("🌡️ Temperatura", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = data.temperatura?.let { "$it °C" } ?: "Sensor no detectado"
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("🏃 Movimiento", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Text("Estado: ${data.movimiento}")
                    }
                }
            } ?: run {
                Text("Esperando datos del sensor...")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { sensorViewModel.startWebSocket() },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("📡 Conectar WebSocket")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (userId != null && petId != null) {
                Button(
                    onClick = {
                        navController.navigate("select_pet/$userId") {
                            popUpTo("sensor/$userId/$petId") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBBDEFB))
                ) {
                    Text("🔙 Cambiar Mascota")
                }
            }
        }
    }
}
