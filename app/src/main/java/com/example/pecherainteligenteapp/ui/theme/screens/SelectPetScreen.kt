package com.example.pecherainteligenteapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pecherainteligenteapp.viewmodel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPetScreen(
    userId: Long,
    navController: NavController,
    petViewModel: PetViewModel = viewModel()
) {
    val pets by petViewModel.petsByOwner.collectAsState()

    LaunchedEffect(userId) {
        petViewModel.getPetsByOwner(userId)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("🐾 Selecciona tu mascota", style = MaterialTheme.typography.titleLarge)

        pets.forEach { pet ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        navController.navigate("sensor/$userId/${pet.id}")
                    },
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = pet.name, fontWeight = FontWeight.Bold)
                        Text(text = pet.species)
                    }

                    if (pet.photo.isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter("https://vitalpaw.tecsup.xyz/public/images/${pet.photo}"),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            petViewModel.deletePet(pet.id) {
                                petViewModel.getPetsByOwner(userId)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.onError)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("create_pet") },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("➕ Registrar Nueva Mascota")
        }
    }
}
