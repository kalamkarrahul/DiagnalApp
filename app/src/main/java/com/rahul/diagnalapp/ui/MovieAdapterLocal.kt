package com.rahul.diagnalapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahul.diagnalapp.R
import com.rahul.diagnalapp.data.Utils.DataChangeListener
import com.rahul.diagnalapp.data.models.Movie
import com.rahul.diagnalapp.data.models.local.Content
import com.rahul.diagnalapp.databinding.ItemVerticalLayoutBinding

class MovieAdapterLocal(val dataChangeListener: DataChangeListener) :
    RecyclerView.Adapter<RecycleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val binding =
            ItemVerticalLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecycleViewHolder(binding)
    }

    var movieList = arrayListOf<Content>()
    fun setList(movieList: List<Content>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        with(holder) {
            with(movieList[position]) {
                binding.textViewTitle.text = this.name

                Glide.with(holder.itemView.context).load(
                    when (this.posterImage) {
                        "poster1.jpg" -> R.drawable.poster1
                        "poster2.jpg" -> R.drawable.poster2
                        "poster3.jpg" -> R.drawable.poster3
                        "poster4.jpg" -> R.drawable.poster4
                        "poster5.jpg" -> R.drawable.poster5
                        "poster6.jpg" -> R.drawable.poster6
                        "poster7.jpg" -> R.drawable.poster7
                        "poster8.jpg" -> R.drawable.poster8
                        "poster9.jpg" -> R.drawable.poster9
                        else -> {
                            R.drawable.placeholder_for_missing_posters
                        }
                    }
                )
                    .placeholder(R.drawable.placeholder_for_missing_posters)
                    .into(binding.imageViewPoster)
            }
            if ((position >= getItemCount() - 1)) dataChangeListener.loadMore()
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun switchImage(posterName: String): Int {
        var id = 0
        when (posterName) {
            "poster1" -> id = R.drawable.poster1
            "poster2" -> id = R.drawable.poster2
            "poster3" -> id = R.drawable.poster3
            "poster4" -> id = R.drawable.poster4
            "poster5" -> id = R.drawable.poster5
            "poster6" -> id = R.drawable.poster6
            "poster7" -> id = R.drawable.poster7
            "poster8" -> id = R.drawable.poster8
            "poster9" -> id = R.drawable.poster9
        }
        return id
    }
}


class RecycleViewHolder(val binding: ItemVerticalLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {}
