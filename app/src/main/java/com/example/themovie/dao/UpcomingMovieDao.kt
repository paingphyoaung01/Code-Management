package com.example.themovie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themovie.model.UpcomingMovieResponse
import com.example.themovie.util.Constants.Companion.upcomingMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface UpcomingMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovie(upcomingMovieEntity: List<UpcomingMovieResponse.UpcomingMovieResult>)

    @Query("DELETE FROM $upcomingMovie")
    suspend fun deleteAllUpcomingMovie()

    @Query("SELECT * FROM $upcomingMovie")
    fun getAllUpcomingMovie() : Flow<List<UpcomingMovieResponse.UpcomingMovieResult>>

}