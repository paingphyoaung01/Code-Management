package com.example.themovie.repository

import android.util.Log
import android.widget.Toast
import androidx.room.withTransaction
import com.example.themovie.api.UpcomingMovieApi
import com.example.themovie.database.TheMovieDatabase
import com.example.themovie.util.networkBoundResource
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class UpcomingRepository @Inject constructor(
    private val api : UpcomingMovieApi,
    private val db : TheMovieDatabase
) {
    private val upcomingMovieDao = db.upcomingMovieDao()

    fun getUpcomingMovie(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ) = networkBoundResource(
        query = {
            upcomingMovieDao.getAllUpcomingMovie()
        },
        fetch = {
            delay(2000)
            api.getUpcomingMovie(1)
        },
        saveFetchResult = {
            db.withTransaction {
                upcomingMovieDao.deleteAllUpcomingMovie()
                upcomingMovieDao.insertUpcomingMovie(it.results)
            }
        },
        shouldFetch = { it ->
            if (forceRefresh) {
                true
            } else {
                val sortedMovieId = it.sortedBy { it ->
                    it.id
                }
                val oldestMovieId = sortedMovieId.firstOrNull()?.id
                val needsRefresh = oldestMovieId == null || oldestMovieId != it[0].id
                needsRefresh
            }
        },
        onFetchSuccess = onFetchSuccess,
        onFetchFailed = { t ->
            if (t !is HttpException && t !is IOException) {
                throw t
            }
            onFetchFailed(t)
        }
    )
}