package com.example.pecherainteligenteapp.data.model

data class PetRequest(
    val name: String,
    val species: String,
    val breedId: Long,
    val birthDate: String,
    val ownerId: Long,
    val photo: String
)