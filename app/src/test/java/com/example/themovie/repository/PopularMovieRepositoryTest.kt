package com.example.themovie.repository

import androidx.room.withTransaction
import com.example.themovie.api.PopularMovieApi
import com.example.themovie.database.TheMovieDatabase
import com.example.themovie.util.networkBoundResource
import kotlinx.coroutines.delay
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import javax.inject.Inject

class PopularMovieRepositoryTest @Inject constructor(
    private val api : PopularMovieApi,
    private val db : TheMovieDatabase
) {
    private val popularMovieDao = db.popularMovieDao()

    fun getPopularMovie() = networkBoundResource(
        query = {
            popularMovieDao.getAllPopularMovie()
        },
        fetch = {
            delay(2000)
            api.getPopularMovie(1)
        },
        saveFetchResult = {
            db.withTransaction {
                popularMovieDao.deleteAllPopularMovie()
                popularMovieDao.insertPopularMovie(it.results)
            }
        }
    )
}