package com.example.movie.View.MovieList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.Utils.Constants
import com.example.movie.Retrofit.RequestInterface
import com.example.movie.Utils.ViewEnums
import com.example.movie_test.Models.DataHolder
import com.example.movie_test.Models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieListViewModel : ViewModel() {

    val viewState: MutableLiveData<ViewEnums> by lazy {
        MutableLiveData<ViewEnums>()
    }

    val data: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    fun fetchData(token: String){
        try {
            viewState.value = ViewEnums.LoadingLayout
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RequestInterface::class.java)

            val retrofidData = retrofitBuilder.getMovieList(token,"all", "week")

            retrofidData.enqueue(object : Callback<DataHolder?> {
                override fun onResponse(call: Call<DataHolder?>, response: Response<DataHolder?>) {
                    if(response.body()!=null){
                        val myResponseBody = response.body()!!.results
                        data.value  = myResponseBody
                        if(myResponseBody.isEmpty()){
                            viewState.value = ViewEnums.EmptyLayout
                        }else{
                            viewState.value = ViewEnums.MainLayout
                        }
                    }
                }

                override fun onFailure(call: Call<DataHolder?>, t: Throwable) {
                    viewState.value = ViewEnums.ErrorLayout
                }
            })
        } catch (e: Exception){
            viewState.value = ViewEnums.ErrorLayout
        }
    }
}