package com.example.themovie.api

import com.example.themovie.model.PopularMovieResponse
import com.example.themovie.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovieApi {

    @GET("popular")
    suspend fun getPopularMovie(
        @Query("page")
        Page:Int,
        @Query("api_key")
        API_KEY: String = Constants.apiKey,
        @Query("language")
        Language: String = Constants.language,
    ) : PopularMovieResponse
}