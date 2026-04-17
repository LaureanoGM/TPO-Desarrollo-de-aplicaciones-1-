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
            val remoteMovies = apiService.getMovies()
            movieDao.clearAll()
            remoteMovies.forEach { movieDao.insertMovie(it) }
        } catch (_: Exception) {
            // Si falla internet, la app sigue funcionando con Room
        }
    }

    suspend fun addMovie(movie: Movie) {
        try {
            val createdMovie = apiService.createMovie(movie.copy(id = 0))
            movieDao.insertMovie(createdMovie)
        } catch (_: Exception) {
            movieDao.insertMovie(movie)
        }
    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie)
        try {
            apiService.updateMovie(movie.id, movie)
        } catch (_: Exception) {
        }
    }

    suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(movie)
        try {
            apiService.deleteMovie(movie.id)
        } catch (_: Exception) {
        }
    }
}