package com.example.themovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovie.databinding.CustomMovieItemBinding
import com.example.themovie.model.PopularMovieResponse

class PopularMovieAdapter(private val onItemClick: (PopularMovieResponse.PopularMovieResult) -> Unit) :
    ListAdapter<PopularMovieResponse.PopularMovieResult, PopularMovieAdapter.PopularMovieViewHolder>(
        DifferCallback()
    ) {

    class PopularMovieViewHolder(
        private val binding: CustomMovieItemBinding,
        private val onItemClick: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popularMovieResult: PopularMovieResponse.PopularMovieResult) {
            binding.apply {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/original" + popularMovieResult.poster_path)
                    .into(imgMovie)

                txtTitle.text = popularMovieResult.title
                txtVote.text = popularMovieResult.vote_average.toString()
            }
        }

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClick(position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val binding =
            CustomMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularMovieViewHolder(binding,
            onItemClick = { position ->
                val popularMovie = getItem(position)
                if (popularMovie != null) {
                    onItemClick(popularMovie)
                }
            })
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class OnClickListener(val clickListener: (popularMovie: PopularMovieResponse.PopularMovieResult) -> Unit) {
        fun onClick(popularMovie: PopularMovieResponse.PopularMovieResult) =
            clickListener(popularMovie)
    }

    class DifferCallback : DiffUtil.ItemCallback<PopularMovieResponse.PopularMovieResult>() {
        override fun areItemsTheSame(
            oldItem: PopularMovieResponse.PopularMovieResult,
            newItem: PopularMovieResponse.PopularMovieResult
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopularMovieResponse.PopularMovieResult,
            newItem: PopularMovieResponse.PopularMovieResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}