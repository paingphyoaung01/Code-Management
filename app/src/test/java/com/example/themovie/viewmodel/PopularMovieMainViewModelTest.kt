package com.example.themovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.themovie.repository.PopularMovieRepository
import com.example.themovie.repository.PopularMovieRepositoryTest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMovieMainViewModelTest @Inject constructor(
    repository: PopularMovieRepositoryTest
) : ViewModel() {

    val popularMovie = repository.getPopularMovie().asLiveData()
}