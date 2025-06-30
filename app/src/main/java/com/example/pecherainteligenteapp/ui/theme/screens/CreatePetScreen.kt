package com.example.pecherainteligenteapp.ui.theme.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pecherainteligenteapp.data.model.Breed
import com.example.pecherainteligenteapp.data.model.PetRequest
import com.example.pecherainteligenteapp.data.network.RetrofitClientImages
import com.example.pecherainteligenteapp.utils.FileUtils
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel
import com.example.pecherainteligenteapp.viewmodel.BreedViewModel
import com.example.pecherainteligenteapp.viewmodel.PetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CreatePetScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    petViewModel: PetViewModel = viewModel(),
    breedViewModel: BreedViewModel = viewModel()
) {
    val context = LocalContext.current
    val breedList by breedViewModel.breeds.collectAsState()
    val ownerId = authViewModel.loggedInUserId ?: 0

    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var selectedBreed by remember { mutableStateOf<Breed?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    val petResponse = petViewModel.petResponse.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = species, onValueChange = { species = it }, label = { Text("Especie") })
        OutlinedTextField(value = birthDate, onValueChange = { birthDate = it }, label = { Text("Fecha de nacimiento (YYYY-MM-DD)") })

        Spacer(modifier = Modifier.height(8.dp))

        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = selectedBreed?.name ?: "",
                onValueChange = {},
                label = { Text("Selecciona Raza") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                breedList.forEach { breed ->
                    DropdownMenuItem(
                        text = { Text(breed.name) },
                        onClick = {
                            selectedBreed = breed
                            expanded = false
                        }
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expanded = true }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Seleccionar foto de galería")
        }

        selectedImageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (selectedBreed != null && selectedImageUri != null) {
                val part = FileUtils.getMultipartFromUri(context, selectedImageUri!!, "file")
                if (part != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = RetrofitClientImages.imageService.uploadImage(part)
                            if (response.isSuccessful) {
                                val filename = response.body()?.filename ?: ""

                                withContext(Dispatchers.Main) {
                                    petViewModel.createPet(
                                        PetRequest(
                                            name = name,
                                            species = species,
                                            breedId = selectedBreed!!.id,
                                            birthDate = birthDate,
                                            ownerId = ownerId,
                                            photo = filename
                                        )
                                    )
                                }
                            } else {
                                Log.e("UploadError", "Error al subir la imagen: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            Log.e("UploadError", "Error en la subida", e)
                        }
                    }
                } else {
                    Toast.makeText(context, "Error procesando imagen", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Completa todos los campos y selecciona una imagen", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Registrar Mascota")
        }

        petResponse.value?.let { response ->
            if (response.isSuccessful) {
                val petId = response.body()?.id ?: 0
                Text("✅ Mascota registrada. ID: $petId")

                LaunchedEffect(Unit) {
                    navController.navigate("register_device/$petId")
                }
            } else {
                Text("❌ Error al registrar: ${response.code()}")
            }
        }
    }
}
