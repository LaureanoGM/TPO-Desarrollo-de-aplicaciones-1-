package com.example.myapplication.data.repository

import com.example.myapplication.data.local.MovieDao
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieDao: MovieDao,
    private val apiService: ApiService
) {
    val allMovies: Flow<List<Movie>> = movieDao.getAllMovies()

    suspend fun refreshMovies() {
        try {
            val movies = apiService.getMovies()
            movies.forEach { movieDao.insertMovie(it) }
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun addMovie(movie: Movie) {
        movieDao.insertMovie(movie)
        // Optionally sync with remote
        // apiService.createMovie(movie)
    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie)
        // apiService.updateMovie(movie.id, movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(movie)
        // apiService.deleteMovie(movie.id)
    }
}
