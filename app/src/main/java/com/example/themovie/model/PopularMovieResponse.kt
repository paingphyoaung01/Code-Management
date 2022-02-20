package com.example.themovie.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themovie.util.Constants
import java.io.Serializable

class PopularMovieResponse(
    val dates: UpcomingMovieDates,
    val page: Int,
    val results: List<PopularMovieResult>,
    val total_pages: Int,
    val total_results: Int,
) : Serializable{
    @Entity(tableName = Constants.popularMovie)
     class PopularMovieResult(
        @PrimaryKey
        val id: Int,
        val adult: Boolean,
        val backdrop_path: String,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    ) : Serializable

    data class UpcomingMovieDates(
        val maximum: String,
        val minimum: String
    )
}