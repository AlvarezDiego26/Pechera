package com.example.pecherainteligenteapp.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel

@Composable
fun ConfirmAccountScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var confirmationCode by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Confirma tu cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmationCode,
            onValueChange = { confirmationCode = it },
            label = { Text("Código de confirmación") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.confirmAccount(
                    token = confirmationCode,
                    onSuccess = {
                        Toast.makeText(context, "Cuenta confirmada", Toast.LENGTH_SHORT).show()
                        navController.navigate("login") {
                            popUpTo("confirm") { inclusive = true }
                        }
                    },
                    onError = { errorMsg ->
                        Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar cuenta")
        }
    }
}