package com.example.myapplication.ui.navigation

sealed class Screen(val route: String) {
    object MovieList : Screen("movie_list")
    object MovieAddEdit : Screen("movie_add_edit/{movieId}") {
        fun createRoute(movieId: Int) = "movie_add_edit/$movieId"
    }
    object About : Screen("about")
}
