package com.example.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterViewModel : ViewModel() {
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    init {
        liveData()
    }

    private fun  liveData() {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val service = retrofit.create(RickAndMortyApiService::class.java)
        val call = service.getCharacters()

        call.enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful) {
                    val characters = response.body()?.results ?: emptyList()

                    //Данные о персонажах помещаются в LiveData.
                    _characters.value = characters

                } else {
                    // Обработка ошибок
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                // Обработка ошибок
            }
        })
    }
}
