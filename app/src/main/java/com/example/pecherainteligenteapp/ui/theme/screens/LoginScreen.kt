package com.example.pecherainteligenteapp.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pecherainteligenteapp.R
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily


@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Color celeste claro del logo Vital Paw
    val celesteClaro = Color(0xFF73C9C4)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Imagen agrandada
            Image(
                painter = painterResource(id = R.drawable.logo1_image),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(300.dp)
            )

            Text(
                "Inicio de Sesión",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    authViewModel.login(
                        email,
                        password,
                        onSuccess = { user ->
                            Toast.makeText(
                                context,
                                "Bienvenido ${user.firstName}",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("sensor") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onError = { errorMsg ->
                            Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = celesteClaro,
                    contentColor = Color.White
                )
            ) {
                Text("Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿No tienes cuenta? Regístrate",
                modifier = Modifier
                    .clickable { navController.navigate("register") },
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}


