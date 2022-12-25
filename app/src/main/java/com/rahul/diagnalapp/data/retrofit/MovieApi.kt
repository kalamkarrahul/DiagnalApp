package com.rahul.diagnalapp.data.retrofit

import com.rahul.diagnalapp.Utils.Constants
import com.rahul.diagnalapp.data.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET(Constants.ALL_MOVIE)
    suspend fun getMovieList(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>
}