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

    suspend fun getMovieById(id: Int): Movie? {
        return movieDao.getMovieById(id)
    }

    suspend fun refreshMovies() {
        try {
            val remoteMovies = apiService.getMovies()
            movieDao.deleteAllMovies()
            remoteMovies.forEach { movieDao.insertMovie(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun addMovie(movie: Movie) {
        try {
            val createdMovie = apiService.createMovie(movie.copy(id = 0))
            movieDao.insertMovie(createdMovie)
        } catch (e: Exception) {
            e.printStackTrace()
            movieDao.insertMovie(movie)
        }
    }

    suspend fun updateMovie(movie: Movie) {
        try {
            val updatedMovie = apiService.updateMovie(movie.id, movie)
            movieDao.insertMovie(updatedMovie)
        } catch (e: Exception) {
            e.printStackTrace()
            movieDao.updateMovie(movie)
        }
    }

    suspend fun deleteMovie(movie: Movie) {
        try {
            if (movie.id != 0) {
                apiService.deleteMovie(movie.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            movieDao.deleteMovie(movie)
        }
    }
}