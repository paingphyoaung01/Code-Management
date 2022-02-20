package com.example.themovie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themovie.model.PopularMovieResponse
import com.example.themovie.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovie(popularMovieEntity: List<PopularMovieResponse.PopularMovieResult>)

    @Query("DELETE FROM ${Constants.popularMovie}")
    suspend fun deleteAllPopularMovie()

    @Query("SELECT * FROM ${Constants.popularMovie}")
    fun getAllPopularMovie() : Flow<List<PopularMovieResponse.PopularMovieResult>>

}