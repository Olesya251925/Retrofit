package com.example.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyApiService {
    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @GET("character")
    fun getCharacters(): Call<List<CharacterResponse>>
}