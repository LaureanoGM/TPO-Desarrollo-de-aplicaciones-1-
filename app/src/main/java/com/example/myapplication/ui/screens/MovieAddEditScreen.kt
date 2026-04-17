package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Movie
import com.example.myapplication.ui.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAddEditScreen(
    movieId: Int,
    viewModel: MovieViewModel,
    onNavigateBack: () -> Unit
) {
    val movies by viewModel.allMovies.collectAsState(initial = emptyList())
    val existingMovie = movies.find { it.id == movieId }

    var title by rememberSaveable(movieId) { mutableStateOf("") }
    var director by rememberSaveable(movieId) { mutableStateOf("") }
    var genre by rememberSaveable(movieId) { mutableStateOf("") }
    var releaseYear by rememberSaveable(movieId) { mutableStateOf("") }
    var rating by rememberSaveable(movieId) { mutableStateOf("") }

    var titleError by remember { mutableStateOf<String?>(null) }
    var directorError by remember { mutableStateOf<String?>(null) }
    var genreError by remember { mutableStateOf<String?>(null) }
    var releaseYearError by remember { mutableStateOf<String?>(null) }
    var ratingError by remember { mutableStateOf<String?>(null) }

    var loadedMovie by remember(movieId) { mutableStateOf(false) }

    LaunchedEffect(existingMovie, movieId) {
        if (movieId != 0 && existingMovie != null && !loadedMovie) {
            title = existingMovie.title
            director = existingMovie.director
            genre = existingMovie.genre
            releaseYear = existingMovie.releaseYear.toString()
            rating = existingMovie.rating.toString()
            loadedMovie = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (movieId == 0) "Agregar Película" else "Editar Película")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = null
                },
                label = { Text("Título") },
                isError = titleError != null,
                modifier = Modifier.fillMaxWidth()
            )
            titleError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = director,
                onValueChange = {
                    director = it
                    directorError = null
                },
                label = { Text("Director") },
                isError = directorError != null,
                modifier = Modifier.fillMaxWidth()
            )
            directorError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = genre,
                onValueChange = {
                    genre = it
                    genreError = null
                },
                label = { Text("Género") },
                isError = genreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            genreError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    TextField(
                        value = releaseYear,
                        onValueChange = {
                            releaseYear = it
                            releaseYearError = null
                        },
                        label = { Text("Año") },
                        isError = releaseYearError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    releaseYearError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    TextField(
                        value = rating,
                        onValueChange = {
                            rating = it
                            ratingError = null
                        },
                        label = { Text("Rating (0-10)") },
                        isError = ratingError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    ratingError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    titleError = if (title.isBlank()) "El título es obligatorio" else null
                    directorError = if (director.isBlank()) "El director es obligatorio" else null
                    genreError = if (genre.isBlank()) "El género es obligatorio" else null

                    val releaseYearInt = releaseYear.toIntOrNull()
                    releaseYearError = if (releaseYearInt == null) {
                        "Ingresá un año válido"
                    } else {
                        null
                    }

                    val ratingDouble = rating.toDoubleOrNull()
                    ratingError = when {
                        ratingDouble == null -> "Ingresá un rating válido"
                        ratingDouble < 0.0 || ratingDouble > 10.0 -> "Debe estar entre 0 y 10"
                        else -> null
                    }

                    val hasErrors = listOf(
                        titleError,
                        directorError,
                        genreError,
                        releaseYearError,
                        ratingError
                    ).any { it != null }

                    if (!hasErrors) {
                        val movie = Movie(
                            id = if (movieId == 0) 0 else movieId,
                            title = title.trim(),
                            director = director.trim(),
                            genre = genre.trim(),
                            releaseYear = releaseYearInt ?: 0,
                            rating = ratingDouble ?: 0.0
                        )

                        if (movieId == 0) {
                            viewModel.insert(movie)
                        } else {
                            viewModel.update(movie)
                        }

                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
