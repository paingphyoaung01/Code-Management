package com.example.themovie.viewmodel

import com.example.themovie.api.PopularMovieApi
import com.example.themovie.database.TheMovieDatabase
import com.example.themovie.repository.PopularMovieRepositoryTest
import com.example.themovie.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking


import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PopularMovieViewModelTest @Inject constructor(
    private val popularMovieApi : PopularMovieApi,
    private val db : TheMovieDatabase
) {

    private lateinit var viewModel: PopularMovieMainViewModelTest


    @Before
    fun setUp() {
        viewModel = PopularMovieMainViewModelTest(PopularMovieRepositoryTest(popularMovieApi, db))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `popular movie list, return success`() = runBlocking {

        viewModel.popularMovie

//        assertThat(value.value!!.data).isEqualTo(Resource.Success)
    }


}