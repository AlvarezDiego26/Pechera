package com.example.pecherainteligenteapp.data.model

data class PetResponse(
    val id: Long,
    val name: String,
    val species: String,
    val breedId: Int,
    val birthDate: String,
    val ownerId: Long,
    val photo: String
)
