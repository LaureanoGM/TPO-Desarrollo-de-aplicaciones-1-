package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.ui.navigation.Screen
import com.example.myapplication.ui.screens.AboutScreen
import com.example.myapplication.ui.screens.MovieAddEditScreen
import com.example.myapplication.ui.screens.MovieListScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.MovieViewModel
import com.example.myapplication.ui.viewmodel.MovieViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Database
        val database = AppDatabase.getDatabase(applicationContext)
        
        // Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/") // Cambia esto por tu IP de Insomnia (10.0.2.2 para emulador)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        // Repository
        val repository = MovieRepository(database.movieDao(), apiService)
        val viewModelFactory = MovieViewModelFactory(repository)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val movieViewModel: MovieViewModel = viewModel(factory = viewModelFactory)

                    NavHost(
                        navController = navController,
                        startDestination = Screen.MovieList.route
                    ) {
                        composable(Screen.MovieList.route) {
                            MovieListScreen(
                                viewModel = movieViewModel,
                                onAddMovie = {
                                    navController.navigate(Screen.MovieAddEdit.createRoute(0))
                                },
                                onEditMovie = { movie ->
                                    navController.navigate(Screen.MovieAddEdit.createRoute(movie.id))
                                },
                                onAbout = {
                                    navController.navigate(Screen.About.route)
                                }
                            )
                        }
                        composable(
                            route = Screen.MovieAddEdit.route,
                            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                            MovieAddEditScreen(
                                movieId = movieId,
                                viewModel = movieViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.About.route) {
                            AboutScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
