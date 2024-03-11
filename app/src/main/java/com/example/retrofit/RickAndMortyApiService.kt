package com.example.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("character")
    fun getCharacters(): Call<CharacterResponse>
}