package com.rahul.diagnalapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rahul.diagnalapp.data.models.paging.Movie
import com.rahul.diagnalapp.data.models.paging.NETWORK_PAGE_SIZE
import com.rahul.diagnalapp.data.retrofit.RetrofitService
import com.rahul.dignaltask.data.network.repository.MoviePagingSource

class PagingRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(retrofitService)
            }, initialKey = 1
        ).liveData
    }
}