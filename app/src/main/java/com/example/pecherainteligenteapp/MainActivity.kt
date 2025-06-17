package com.example.pecherainteligenteapp



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pecherainteligenteapp.ui.theme.screens.PetSensorScreen
import com.example.pecherainteligenteapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                PetSensorScreen()
            }
        }
    }
}
