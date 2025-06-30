package com.example.pecherainteligenteapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pecherainteligenteapp.ui.theme.screens.*
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel

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

        composable("create_pet") {
            CreatePetScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = "register_device/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.LongType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getLong("petId") ?: 0
            RegisterDeviceScreen(
                navController = navController,
                petId = petId,
                authViewModel = authViewModel
            )
        }


        // ✅ Ruta por userId y petId (Versión vieja)
        composable(
            route = "sensor/{userId}/{petId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.LongType },
                navArgument("petId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0
            val petId = backStackEntry.arguments?.getLong("petId") ?: 0
            PetSensorScreen(navController = navController, userId = userId, petId = petId, deviceId = null)
        }

        // ✅ Nueva Ruta solo por deviceId
        composable(
            route = "sensor/{deviceId}",
            arguments = listOf(
                navArgument("deviceId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getLong("deviceId") ?: 0
            PetSensorScreen(navController = navController, userId = null, petId = null, deviceId = deviceId)
        }

        composable(
            route = "select_pet/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0
            SelectPetScreen(userId = userId, navController = navController)
        }
    }
}
