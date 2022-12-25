package com.rahul.diagnalapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahul.diagnalapp.R
import com.rahul.diagnalapp.Utils.DataChangeListener
import com.rahul.diagnalapp.data.models.Movie
import com.rahul.diagnalapp.databinding.ItemVerticalLayoutBinding

class MovieRecyclerAdapter(val dataChangeListener: DataChangeListener) :
    RecyclerView.Adapter<BindableViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding =
            ItemVerticalLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BindableViewHolder(binding)
    }

    var movieList = arrayListOf<Movie>()
    fun setList(movieList: List<Movie>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        with(holder) {
            with(movieList[position]) {
                binding.textViewTitle.text = this.title
                Glide.with(holder.itemView.context)
                    .load("https://image.tmdb.org/t/p/w300" + this.poster_path)
                    .placeholder(R.drawable.placeholder_for_missing_posters)
                    .into(binding.imageViewPoster)
            }
        }
        if ((position >= getItemCount() - 1))
            dataChangeListener.loadMore()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}

class BindableViewHolder(val binding: ItemVerticalLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
}