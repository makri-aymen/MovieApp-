package com.example.movie.View.MovieDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.Utils.Constants
import com.example.movie.Retrofit.RequestInterface
import com.example.movie.Utils.ViewEnums
import com.example.movie_test.Models.MovieDetailsDataHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDetailsViewModel : ViewModel()  {

    var movie_id : Int = -1
    val viewState: MutableLiveData<ViewEnums> by lazy {
        MutableLiveData<ViewEnums>()
    }

    val data: MutableLiveData<MovieDetailsDataHolder> by lazy {
        MutableLiveData<MovieDetailsDataHolder>()
    }

    fun fetchData(token: String){
        try {
            viewState.value = ViewEnums.LoadingLayout
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RequestInterface::class.java)

            val retrofidData = retrofitBuilder.getMovieDetails(this.movie_id,token)

            retrofidData.enqueue(object : Callback<MovieDetailsDataHolder?> {
                override fun onResponse(call: Call<MovieDetailsDataHolder?>, response: Response<MovieDetailsDataHolder?>) {
                    if(response.body()!=null){
                        val myResponseBody = response.body()
                        data.value  = myResponseBody
                        viewState.value = ViewEnums.MainLayout
                    }else{
                        viewState.value = ViewEnums.EmptyLayout
                    }
                }

                override fun onFailure(call: Call<MovieDetailsDataHolder?>, t: Throwable) {
                    viewState.value = ViewEnums.ErrorLayout
                }
            })
        } catch (e: Exception){
            viewState.value = ViewEnums.ErrorLayout
        }
    }
}