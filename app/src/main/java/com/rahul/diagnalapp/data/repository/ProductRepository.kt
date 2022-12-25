package com.rahul.diagnalapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rahul.diagnalapp.data.Utils.Constants
import com.rahul.diagnalapp.data.models.MovieResponse
import com.rahul.diagnalapp.data.retrofit.MovieApi
import javax.inject.Inject

class ProductRepository @Inject constructor(private val movieApi: MovieApi) {
    private val _movies = MutableLiveData<MovieResponse>()
    val movies: LiveData<MovieResponse>
        get() = _movies

    suspend fun getAllMovie(pageNumber: Int) {
        val result = movieApi.getMovieList(Constants.API_KEY, Constants.LANGUAGE, pageNumber)
        if (result.isSuccessful && result.body() != null) {
            _movies.postValue(result.body())
        }
    }
}