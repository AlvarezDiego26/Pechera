package com.example.pecherainteligenteapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Registro", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Nombre") })
        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellido") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") })
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") })
        OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("Ciudad") })
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Usuario") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
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
        }) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(message, color = Color.Red)
    }
}
