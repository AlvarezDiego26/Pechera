package com.example.pecherainteligenteapp.data.repository

import com.example.pecherainteligenteapp.data.model.PetRequest
import com.example.pecherainteligenteapp.data.model.PetResponse
import com.example.pecherainteligenteapp.data.network.RetrofitClient
import com.example.pecherainteligenteapp.data.network.RetrofitClient.apiService
import retrofit2.Response

class PetRepository {
    private val api = RetrofitClient.apiService

    suspend fun createPet(pet: PetRequest): Response<PetResponse> {
        return api.createPet(pet)
    }

    suspend fun getPetById(id: Long): Response<PetResponse> {
        return api.getPetById(id)
    }

    suspend fun getPetsByOwnerId(ownerId: Long): Response<List<PetResponse>> {
        return api.getPetsByOwnerId(ownerId)
    }

    suspend fun deletePetById(petId: Long): Response<Void> {
        return RetrofitClient.apiService.deletePet(petId)
    }

}
