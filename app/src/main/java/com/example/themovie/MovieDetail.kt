package com.example.themovie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.themovie.databinding.ActivityMainBinding
import com.example.themovie.databinding.ActivityMovieDetailBinding
import com.example.themovie.model.PopularMovieResponse
import com.example.themovie.model.UpcomingMovieResponse

class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val upcomingMovieData =
                intent.getSerializableExtra("upcomingData") as UpcomingMovieResponse.UpcomingMovieResult
            binding.apply {
                Glide.with(this@MovieDetail)
                    .load("https://image.tmdb.org/t/p/original" + upcomingMovieData.backdrop_path)
                    .into(this.imgMoviePoster)
                this.txtMovieTitle.text = upcomingMovieData.original_title
                this.txtMovieRating.visibility = View.GONE
                this.txtMovieOverview.text = upcomingMovieData.overview
            }
        } catch (exception: Exception) {
            val popularMovieData =
                intent.getSerializableExtra("popularData") as PopularMovieResponse.PopularMovieResult

            binding.apply {
                Glide.with(this@MovieDetail)
                    .load("https://image.tmdb.org/t/p/original" + popularMovieData.backdrop_path)
                    .into(this.imgMoviePoster)
                this.txtMovieTitle.text = popularMovieData.original_title
                this.txtMovieRating.text = popularMovieData.vote_average.toString()
                this.txtMovieOverview.text = popularMovieData.overview
            }
        }


    }
}