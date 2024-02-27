package com.example.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CharacterAdapter(private val characters: List<Character>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_IMAGE = 1
        private const val VIEW_TYPE_NAME = 2
        private const val VIEW_TYPE_TYPE = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.character_image, parent, false)
                ImageViewHolder(view)
            }
            VIEW_TYPE_NAME -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.character_name, parent, false)
                NameViewHolder(view)
            }
            VIEW_TYPE_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.character_species, parent, false)
                TypeViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = characters[position]

        when (holder.itemViewType) {
            VIEW_TYPE_IMAGE -> {
                (holder as ImageViewHolder).bind(character.image)
            }
            VIEW_TYPE_NAME -> {
                (holder as NameViewHolder).bind(character.name)
            }
            VIEW_TYPE_TYPE -> {
                (holder as TypeViewHolder).bind(character.type)
            }
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun getItemViewType(position: Int): Int {
        val character = characters[position]
        return when {
            character.species == "Human" -> VIEW_TYPE_IMAGE
            character.species == "Alien" -> VIEW_TYPE_NAME
            else -> VIEW_TYPE_TYPE
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(image: String?) {
            if (!image.isNullOrEmpty()) {
                Picasso.get().load(image).into(imageView)
            }
        }
    }

    inner class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(name: String) {
            nameTextView.text = name
        }
    }

    inner class TypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeTextView: TextView = itemView.findViewById(R.id.speciesTextView)

        fun bind(type: String) {
            typeTextView.text = type
        }
    }
}

