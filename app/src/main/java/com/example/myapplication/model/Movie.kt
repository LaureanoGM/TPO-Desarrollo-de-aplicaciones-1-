package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val director: String,
    val genre: String,
    val releaseYear: Int,
    val rating: Double,
    val imageUrl: String = ""
)
