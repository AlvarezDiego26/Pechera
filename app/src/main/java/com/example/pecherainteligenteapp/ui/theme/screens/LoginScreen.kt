package com.example.pecherainteligenteapp.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel
import com.example.pecherainteligenteapp.viewmodel.PetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    petViewModel: PetViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "🔑 Iniciar Sesión", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            authViewModel.login(
                email = email,
                password = password,
                onSuccess = { user ->
                    val userId = user.id

                    coroutineScope.launch {
                        try {
                            val response = petViewModel.repository.getPetsByOwnerId(userId)
                            if (response.isSuccessful) {
                                val pets = response.body() ?: emptyList()
                                if (pets.isNotEmpty()) {
                                    navController.navigate("select_pet/$userId")
                                } else {
                                    navController.navigate("create_pet")
                                }
                            } else {
                                Toast.makeText(context, "Error al verificar mascotas", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onError = { errorMsg ->
                    Toast.makeText(context, "Login fallido: $errorMsg", Toast.LENGTH_SHORT).show()
                }
            )
        }) {
            Text("Iniciar Sesión")
        }

        TextButton(onClick = { navController.navigate("register") }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}
