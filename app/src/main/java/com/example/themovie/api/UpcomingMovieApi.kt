package com.example.themovie.api

import com.example.themovie.model.UpcomingMovieResponse
import com.example.themovie.util.Constants.Companion.apiKey
import com.example.themovie.util.Constants.Companion.language
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingMovieApi {

    @GET("upcoming")
    suspend fun getUpcomingMovie(
        @Query("page")
        Page:Int,
        @Query("api_key")
        API_KEY: String = apiKey,
        @Query("language")
        Language: String = language,
    ) : UpcomingMovieResponse

}