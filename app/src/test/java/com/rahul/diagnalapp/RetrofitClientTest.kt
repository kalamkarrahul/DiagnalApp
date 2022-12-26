package com.rahul.diagnalapp

import com.rahul.diagnalapp.data.Utils.Constants
import com.rahul.diagnalapp.data.retrofit.MovieApi
import com.rahul.diagnalapp.di.NetworkModule
import org.junit.Test
import retrofit2.Retrofit

class RetrofitClientTest {
    @Test
    fun testRetrofitInstance() {
        //Get an instance of Retrofit
        val instance: Retrofit = NetworkModule().providesRetrofit()
        //Assert that, Retrofit's base url matches to our BASE_URL
        assert(instance.baseUrl().toUrl().toString() == Constants.BASE_URL)
    }

    @Test
    suspend fun testMovieService() {
        //Get an instance of Movie Service by providing the Retrofit instance
        val service = NetworkModule().providesMovieApi(NetworkModule().providesRetrofit())
        //Execute the API call
        val response = service.getMovieList(Constants.API_KEY, Constants.LANGUAGE, 1)
        //Check for error body
        val errorBody = response.errorBody()
        assert(errorBody == null)
        //Check for success body
        val responseWrapper = response.body()
        assert(responseWrapper != null)
        assert(response.code() == 200)
    }
}