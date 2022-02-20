package com.example.themovie.repository

import androidx.room.withTransaction
import com.example.themovie.api.PopularMovieApi
import com.example.themovie.database.TheMovieDatabase
import com.example.themovie.model.PopularMovieResponse
import com.example.themovie.util.Resource
import com.example.themovie.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PopularMovieRepository @Inject constructor(
    private val api : PopularMovieApi,
    private val db : TheMovieDatabase
) {
    private val popularMovieDao = db.popularMovieDao()

    fun getPopularMovie(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<List<PopularMovieResponse.PopularMovieResult>>> = networkBoundResource(
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
        },
        shouldFetch = {
            if (forceRefresh) {
                true
            } else {
                val sortedMovieId = it.sortedBy {
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