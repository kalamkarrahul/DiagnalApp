package com.rahul.diagnalapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahul.diagnalapp.R
import com.rahul.diagnalapp.data.models.paging.Movie
import com.rahul.diagnalapp.databinding.ItemVerticalLayoutBinding

class MoviePagerAdapter :
    PagingDataAdapter<Movie, MoviePagerAdapter.MovieViewHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.view.textViewTitle.text = movie.original_title
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w300" + movie.poster_path)
            .placeholder(R.drawable.placeholder_for_missing_posters)
            .into(holder.view.imageViewPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVerticalLayoutBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    class MovieViewHolder(val view: ItemVerticalLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {
        var width = view.root.width
        var height = width * 1.4946
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.original_title == newItem.original_title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}

