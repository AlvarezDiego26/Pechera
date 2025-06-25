package com.example.pecherainteligenteapp.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.login(email, password,
                    onSuccess = { user ->
                        Toast.makeText(context, "Bienvenido ${user.firstName}", Toast.LENGTH_SHORT).show()
                        navController.navigate("sensor") {
                            popUpTo("login") { inclusive = true } // Elimina la pantalla de login del back stack
                        }
                    },
                    onError = { errorMsg ->
                        Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }


        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¿No tienes cuenta? Regístrate",
            modifier = Modifier.clickable { navController.navigate("register") },
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
