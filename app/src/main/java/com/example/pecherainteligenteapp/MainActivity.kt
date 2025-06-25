package com.example.pecherainteligenteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pecherainteligenteapp.data.network.RetrofitClient
import com.example.pecherainteligenteapp.data.repository.AuthRepositoryImpl
import com.example.pecherainteligenteapp.ui.theme.MyAppTheme
import com.example.pecherainteligenteapp.ui.theme.AppNavigation
import com.example.pecherainteligenteapp.viewmodel.AuthViewModel
import com.example.pecherainteligenteapp.viewmodel.AuthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancia del repositorio y factory
        val repository = AuthRepositoryImpl(RetrofitClient.apiService)
        val viewModelFactory = AuthViewModelFactory(repository)

        setContent {
            MyAppTheme {
                // Usar viewModel con factory correctamente
                val authViewModel: AuthViewModel = viewModel(factory = viewModelFactory)
                AppNavigation(authViewModel) // <- pásalo explícitamente
            }
        }
    }
}
