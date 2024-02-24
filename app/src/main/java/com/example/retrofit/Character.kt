package com.example.retrofit

data class Character(
    val id: Int,
    val name: String,
    val species: String,
    val image: String
) {
    fun getType(): String {
        return when (species) {
            "human" -> "image"
            "alien" -> "name"
            else -> "Unknown species"
        }
    }
}
