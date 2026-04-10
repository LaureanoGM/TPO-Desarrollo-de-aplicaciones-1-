package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val allMovies: Flow<List<Movie>> = repository.allMovies

    fun insert(movie: Movie) = viewModelScope.launch {
        repository.addMovie(movie)
    }

    fun update(movie: Movie) = viewModelScope.launch {
        repository.updateMovie(movie)
    }

    fun delete(movie: Movie) = viewModelScope.launch {
        repository.deleteMovie(movie)
    }

    fun refresh() = viewModelScope.launch {
        repository.refreshMovies()
    }
}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
