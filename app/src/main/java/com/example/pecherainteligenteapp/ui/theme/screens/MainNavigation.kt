package com.example.pecherainteligenteapp.ui.theme.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object PetSensor : Screen("sensor", "Sensor", Icons.Default.Home)
    object Usuario : Screen("usuario", "Usuario", Icons.Default.Person)
    object EditarPerfil : Screen("editar_perfil", "Editar", Icons.Default.Edit)
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.PetSensor.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.PetSensor.route) { PetSensorScreen() }
            composable(Screen.Usuario.route) { UsuarioScreen() }
            composable(Screen.EditarPerfil.route) { EditarPerfilScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.PetSensor,
        Screen.Usuario,
        Screen.EditarPerfil
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                },
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) }
            )
        }
    }
}