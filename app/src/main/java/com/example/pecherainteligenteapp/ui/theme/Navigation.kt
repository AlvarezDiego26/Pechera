package com.example.pecherainteligenteapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pecherainteligenteapp.ui.theme.screens.ConfirmAccountScreen
import com.example.pecherainteligenteapp.ui.theme.screens.LoginScreen
import com.example.pecherainteligenteapp.ui.theme.screens.PetSensorScreen
import com.example.pecherainteligenteapp.ui.theme.screens.RegisterScreen
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel // ← Importa tu ViewModel

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onSuccess = {
                    navController.navigate("confirm") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }
        composable("confirm") {
            ConfirmAccountScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable("sensor") {
            PetSensorScreen()
        }
    }

}



