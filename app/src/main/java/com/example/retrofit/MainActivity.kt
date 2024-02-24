package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Создаем экземпляр адаптера и присваиваем его переменной characterAdapter
        characterAdapter = CharacterAdapter(mutableListOf())

        // Устанавливаем адаптер для RecyclerView
        recyclerView.adapter = characterAdapter

        // Загрузка данных при создании активности
        characterAdapter.loadData()
    }
}
