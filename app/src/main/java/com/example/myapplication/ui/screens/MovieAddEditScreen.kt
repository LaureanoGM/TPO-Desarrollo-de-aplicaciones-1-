package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    var title by remember { mutableStateOf(existingMovie?.title ?: "") }
    var director by remember { mutableStateOf(existingMovie?.director ?: "") }
    var genre by remember { mutableStateOf(existingMovie?.genre ?: "") }
    var releaseYear by remember { mutableStateOf(existingMovie?.releaseYear?.toString() ?: "") }
    var rating by remember { mutableStateOf(existingMovie?.rating?.toString() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (movieId == 0) "Agregar Película" else "Editar Película") })
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
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = director,
                onValueChange = { director = it },
                label = { Text("Director") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = genre,
                onValueChange = { genre = it },
                label = { Text("Género") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = releaseYear,
                    onValueChange = { releaseYear = it },
                    label = { Text("Año") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = rating,
                    onValueChange = { rating = it },
                    label = { Text("Rating (0-10)") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val movie = Movie(
                        id = if (movieId == 0) 0 else movieId,
                        title = title,
                        director = director,
                        genre = genre,
                        releaseYear = releaseYear.toIntOrNull() ?: 2024,
                        rating = rating.toDoubleOrNull() ?: 0.0
                    )
                    if (movieId == 0) {
                        viewModel.insert(movie)
                    } else {
                        viewModel.update(movie)
                    }
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
