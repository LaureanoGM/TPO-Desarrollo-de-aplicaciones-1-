package com.example.myapplication.model

object MovieValidator {
    fun isValidTitle(title: String): Boolean = title.isNotBlank()
    
    fun isValidDirector(director: String): Boolean = director.isNotBlank()
    
    fun isValidGenre(genre: String): Boolean = genre.isNotBlank()
    
    fun isValidReleaseYear(year: Int): Boolean = year in 1888..2100
    
    fun isValidRating(rating: Double): Boolean = rating in 0.0..10.0
}
