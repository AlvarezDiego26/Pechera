package com.example.pecherainteligenteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pecherainteligenteapp.data.model.Breed
import com.example.pecherainteligenteapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BreedViewModel : ViewModel() {
    private val _breeds = MutableStateFlow<List<Breed>>(emptyList())
    val breeds: StateFlow<List<Breed>> = _breeds

    init {
        fetchBreeds()
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.apiService.getBreeds()
                _breeds.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}