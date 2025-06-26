package com.example.pecherainteligenteapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pecherainteligenteapp.data.model.UserRegisterDTO
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(),
    onSuccess: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val celesteClaro = Color(0xFF73C9C4)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Registro", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("Ciudad") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val user = UserRegisterDTO(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        password = password,
                        phone = phone,
                        city = city,
                        username = username
                    )

                    viewModel.register(
                        user,
                        onSuccess = {
                            message = "✅ Registro exitoso"
                            onSuccess()
                        },
                        onError = {
                            message = "❌ Error: $it"
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = celesteClaro,
                    contentColor = Color.White
                )
            ) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(message, color = Color.Red)
        }
    }
}
