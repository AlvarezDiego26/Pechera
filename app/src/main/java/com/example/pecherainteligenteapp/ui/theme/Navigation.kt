package com.example.pecherainteligenteapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.pecherainteligenteapp.ui.theme.screens.PetSensorScreen
import com.example.pecherainteligenteapp.ui.theme.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register") {
        composable("register") {
            RegisterScreen(onSuccess = { navController.navigate("sensor") })
        }
        composable("sensor") {
            PetSensorScreen()
        }
    }
}