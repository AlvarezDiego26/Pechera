package com.example.pecherainteligenteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pecherainteligenteapp.data.model.PetRequest
import com.example.pecherainteligenteapp.data.model.PetResponse
import com.example.pecherainteligenteapp.data.repository.PetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class PetViewModel : ViewModel() {

    val repository = PetRepository()

    private val _petResponse = MutableStateFlow<Response<PetResponse>?>(null)
    val petResponse: StateFlow<Response<PetResponse>?> = _petResponse

    private val _petData = MutableStateFlow<PetResponse?>(null)
    val petData: StateFlow<PetResponse?> get() = _petData

    private val _petsByOwner = MutableStateFlow<List<PetResponse>>(emptyList())
    val petsByOwner: StateFlow<List<PetResponse>> get() = _petsByOwner

    fun createPet(pet: PetRequest) {
        viewModelScope.launch {
            val response = repository.createPet(pet)
            _petResponse.value = response
        }
    }

    fun getPetById(id: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getPetById(id)
                if (response.isSuccessful) {
                    _petData.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPetsByOwner(ownerId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getPetsByOwnerId(ownerId)
                if (response.isSuccessful) {
                    _petsByOwner.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deletePet(petId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.deletePetById(petId)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    // Aquí puedes poner un Log o un Toast si quieres
                    println("❌ Error al eliminar mascota: ${response.code()}")
                }
            } catch (e: Exception) {
                println("❌ Excepción al eliminar mascota: ${e.localizedMessage}")
            }
        }
    }

}
