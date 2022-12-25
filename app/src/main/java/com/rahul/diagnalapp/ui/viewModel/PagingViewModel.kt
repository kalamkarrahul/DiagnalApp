package com.rahul.diagnalapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rahul.diagnalapp.data.models.paging.Movie
import com.rahul.diagnalapp.data.repository.PagingRepository

class PagingViewModel constructor(private val repository: PagingRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    fun getMovieList(): LiveData<PagingData<Movie>> {
        return repository.getAllMovies()
            .cachedIn(viewModelScope)
    }
}