package com.rahul.diagnalapp.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rahul.diagnalapp.data.models.MovieResponse
import com.rahul.diagnalapp.data.models.local.LocalResponse
import com.rahul.diagnalapp.data.repository.ProductRepository
import kotlinx.coroutines.launch
import java.io.IOException
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