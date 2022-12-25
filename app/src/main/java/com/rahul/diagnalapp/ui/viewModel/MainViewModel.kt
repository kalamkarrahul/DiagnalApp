package com.rahul.diagnalapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.diagnalapp.data.models.MovieResponse
import com.rahul.diagnalapp.data.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val movies: LiveData<MovieResponse>
        get() = repository.movies

    fun getMovieList(pageNumber: Int) {
        viewModelScope.launch {
            repository.getAllMovie(pageNumber)
        }
    }
}