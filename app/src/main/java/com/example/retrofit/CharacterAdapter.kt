package com.example.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterAdapter(private val characterList: MutableList<Character>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_IMAGE = 1
    private val VIEW_TYPE_NAME = 2
    private val VIEW_TYPE_SPECIES = 3

    private val retrofit = Retrofit.Builder()
        .baseUrl(RickAndMortyApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val rickAndMortyApiService = retrofit.create(RickAndMortyApiService::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_IMAGE -> CharacterImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.character_image, parent, false)
            )
            VIEW_TYPE_NAME -> CharacterNameViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.character_name, parent, false)
            )
            VIEW_TYPE_SPECIES -> CharacterSpeciesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.character_species, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val characterType = characterList[position].getType()
        return when (characterType) {
            "image" -> VIEW_TYPE_IMAGE
            "name" -> VIEW_TYPE_NAME
            else -> VIEW_TYPE_SPECIES
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = characterList[position]
        when (holder) {
            is CharacterImageViewHolder -> {
                Picasso.get().load(character.image).into(holder.imageView)
            }
            is CharacterNameViewHolder -> {
                holder.nameTextView.text = character.name
            }
            is CharacterSpeciesViewHolder -> {
                holder.speciesTextView.text = character.species
            }
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    class CharacterImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    class CharacterNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    }

    class CharacterSpeciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val speciesTextView: TextView = itemView.findViewById(R.id.speciesTextView)
    }

    // Метод для загрузки данных с помощью Retrofit
    fun loadData() {
        val call = rickAndMortyApiService.getCharacters()

        call.enqueue(object : Callback<List<CharacterResponse>> {
            override fun onResponse(
                call: Call<List<CharacterResponse>>,
                response: Response<List<CharacterResponse>>
            ) {
                if (response.isSuccessful) {
                    val characters = response.body()
                    characters?.let {
                        // Очищаем старые данные
                        characterList.clear()
                        // Добавляем новые данные
                        for (characterResponse in characters) {
                            characterList.add(
                                Character(
                                    characterResponse.id,
                                    characterResponse.name,
                                    characterResponse.species,
                                    characterResponse.image
                                )
                            )
                        }
                        // Уведомляем адаптер об изменениях
                        notifyDataSetChanged()
                    }
                } else {
                    // Обработка неуспешного ответа (например, код ответа не 200)
                }
            }

            override fun onFailure(call: Call<List<CharacterResponse>>, t: Throwable) {
                // Обработка ошибки загрузки данных
            }
        })
    }
}