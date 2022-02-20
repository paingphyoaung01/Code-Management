package com.example.themovie.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themovie.dao.PopularMovieDao
import com.example.themovie.dao.UpcomingMovieDao
import com.example.themovie.model.PopularMovieResponse
import com.example.themovie.model.UpcomingMovieResponse

@Database(entities = [UpcomingMovieResponse.UpcomingMovieResult::class, PopularMovieResponse.PopularMovieResult::class], version = 1)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun upcomingMovieDao(): UpcomingMovieDao

    abstract fun popularMovieDao() : PopularMovieDao
}