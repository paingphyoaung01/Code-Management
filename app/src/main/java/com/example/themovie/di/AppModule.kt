package com.example.themovie.di

import android.app.Application
import androidx.room.Room
import com.example.themovie.api.PopularMovieApi
import com.example.themovie.api.UpcomingMovieApi
import com.example.themovie.database.TheMovieDatabase
import com.example.themovie.util.Constants.Companion.baseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUpcomingMovieApi(retrofit: Retrofit) : UpcomingMovieApi =
        retrofit.create(UpcomingMovieApi::class.java)

    @Provides
    @Singleton
    fun providePopularMovieApi(retrofit: Retrofit) : PopularMovieApi =
        retrofit.create(PopularMovieApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : TheMovieDatabase =
        Room.databaseBuilder(app, TheMovieDatabase::class.java, "THE_MOVIE_DATABASE")
            .build()

}