package com.example.themovie

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.themovie.adapter.PopularMovieAdapter
import com.example.themovie.databinding.ActivityMainBinding
import com.example.themovie.util.Resource
import com.example.themovie.viewmodel.PopularMovieViewModel
import com.example.themovie.viewmodel.UpcomingMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.Serializable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val upcomingMovieViewModel: UpcomingMovieViewModel by viewModels()
    private val popularMovieViewModel: PopularMovieViewModel by viewModels()


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUpcomingMovie()
        getPopularMovie()
    }

    private fun getUpcomingMovie() {

        binding.apply {

            lifecycleScope.launchWhenStarted {

                upcomingMovieViewModel.upcomingMovie.collect {

                    val result = it ?: return@collect

                    val upcomingMovieList = ArrayList<SlideModel>()

                    swipeRefresh.isRefreshing = result is Resource.Loading
                    progressBar.isVisible = result is Resource.Error

                    for (i in result.data!!) {
                        upcomingMovieList.add(
                            SlideModel(
                                "https://image.tmdb.org/t/p/original" + i.backdrop_path,
                                i.title
                            )
                        )
                    }

                    upcomingImageSlider.apply {
                        setImageList(upcomingMovieList, ScaleTypes.FIT)
                        setItemClickListener(object : ItemClickListener {
                            override fun onItemSelected(position: Int) {

                                Intent(this@MainActivity, MovieDetail::class.java).apply {
                                    putExtra("upcomingData", result.data[position] as Serializable)
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    this@MainActivity.startActivity(this)
                                }
                            }

                        })
                    }

                }
            }

            swipeRefresh.setOnRefreshListener {
                upcomingMovieViewModel.onManualRefresh()
                popularMovieViewModel.onManualRefresh()
            }

        }
    }

    private fun getPopularMovie() {

        binding.apply {

            this@MainActivity.lifecycleScope.launchWhenStarted {

                popularMovieViewModel.popularMovie.collect{

                    val result = it ?: return@collect

                    swipeRefresh.isRefreshing = result is Resource.Loading

                    progressBar.isVisible = it is Resource.Error

                    val popularMovieAdapter = PopularMovieAdapter(
                        onItemClick = {
                            Intent(this@MainActivity, MovieDetail::class.java).apply {
                                putExtra("popularData", it as Serializable)
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                this@MainActivity.startActivity(this)
                            }
                        }
                    )

                    binding.popularRecycler.apply {
                        adapter = popularMovieAdapter
                        layoutManager =
                            LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )

                    }

                    popularMovieAdapter.submitList(it.data) {
                        if (popularMovieViewModel.pendingScrollToTopAfterRefresh) {
                            popularRecycler.scrollToPosition(0)
                            popularMovieViewModel.pendingScrollToTopAfterRefresh = false
                        }
                    }

                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        upcomingMovieViewModel.onStart()
        popularMovieViewModel.onStart()
    }


}