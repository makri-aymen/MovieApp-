package com.example.movie.Retrofit

import com.example.movie_test.Models.DataHolder
import com.example.movie_test.Models.MovieDetailsDataHolder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestInterface {

    @GET("trending/all/week")
    fun getMovieList(@Query("api_key") api_key: String
                     ,@Query("media_type") media_type: String
                     ,@Query("time_window") time_window: String) : Call<DataHolder>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int,@Query("api_key") api_key: String) : Call<MovieDetailsDataHolder>

}