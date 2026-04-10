package com.example.myapplication.data.remote

import com.example.myapplication.model.Movie
import com.example.myapplication.model.Product
import retrofit2.http.*

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("movies")
    suspend fun getMovies(): List<Movie>

    @POST("movies")
    suspend fun createMovie(@Body movie: Movie): Movie

    @PUT("movies/{id}")
    suspend fun updateMovie(@Path("id") id: Int, @Body movie: Movie): Movie

    @DELETE("movies/{id}")
    suspend fun deleteMovie(@Path("id") id: Int)
}
