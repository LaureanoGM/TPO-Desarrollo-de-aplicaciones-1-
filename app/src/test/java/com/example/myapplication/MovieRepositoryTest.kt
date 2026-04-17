package com.example.myapplication

import com.example.myapplication.data.local.MovieDao
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.model.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MovieRepositoryTest {

    @Mock
    private lateinit var movieDao: MovieDao

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = MovieRepository(movieDao, apiService)
    }

    @Test
    fun refreshMovies_insertaPeliculasRemotas() = runBlocking {
        val movies = listOf(
            Movie(
                id = 1,
                title = "Matrix",
                director = "Wachowski",
                genre = "Sci-Fi",
                releaseYear = 1999,
                rating = 9.0
            )
        )

        `when`(apiService.getMovies()).thenReturn(movies)

        repository.refreshMovies()

        verify(movieDao).clearAll()
        verify(movieDao).insertMovie(movies[0])
    }
}