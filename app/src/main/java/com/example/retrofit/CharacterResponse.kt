package com.example.retrofit

data class CharacterResponse(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,

    val id: Int,
    val name: String,
    val species: String,
    val image: String
)
