package com.example.movie.View.MovieList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.Utils.SharedPreferenceUtils
import com.example.movie.Utils.ViewEnums
import com.example.movie.View.MovieDetails.MovieDetails
import com.example.movie.View.MovieList.RecyclerView.RecyclerAdapter
import com.example.movie_test.Models.Movie

class MovieList : AppCompatActivity() ,
    com.example.movie.View.MovieList.RecyclerView.OnItemClickListener {

    lateinit var viewModel: MovieListViewModel
    lateinit var RetryButton : Button
    lateinit var RetryButton2 : Button
    lateinit var LoadingLayout : LinearLayout
    lateinit var ErrorLayout : LinearLayout
    lateinit var EmptyLayout : LinearLayout
    lateinit var MyRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ///this token in real app should be saved when auth
        SharedPreferenceUtils.setToken("c9856d0cb57c3f14bf75bdc6c063b8f3", this)

        RetryButton = findViewById<Button>(R.id.TryAgain)
        RetryButton2 = findViewById<Button>(R.id.TryAgain2)

        LoadingLayout = findViewById<LinearLayout>(R.id.LoadingLayout)
        ErrorLayout = findViewById<LinearLayout>(R.id.ErrorLayout)
        EmptyLayout = findViewById<LinearLayout>(R.id.EmptyLayout)
        MyRecyclerView = findViewById<RecyclerView>(R.id.MyRecyclerView)

        changeView(ViewEnums.LoadingLayout)
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        viewModel.fetchData(SharedPreferenceUtils.getToken(this));
        viewModel.data.observe(this, Observer {
            MyRecyclerView.layoutManager = LinearLayoutManager(this)
            var adapter:RecyclerAdapter = RecyclerAdapter(viewModel.data.value,this)
            MyRecyclerView.adapter = adapter
        })
        viewModel.viewState.observe(this, Observer {
            changeView(viewModel.viewState.value)
        })
        RetryButton.setOnClickListener {
            viewModel.fetchData(SharedPreferenceUtils.getToken(this));
        }
        RetryButton2.setOnClickListener {
            viewModel.fetchData(SharedPreferenceUtils.getToken(this));
        }
    }

    fun changeView(data : ViewEnums?){
        when (data) {
            ViewEnums.EmptyLayout -> {
                LoadingLayout.visibility = View.GONE
                ErrorLayout.visibility = View.GONE
                EmptyLayout.visibility = View.VISIBLE
                MyRecyclerView.visibility = View.GONE
            }
            ViewEnums.ErrorLayout -> {
                LoadingLayout.visibility = View.GONE
                ErrorLayout.visibility = View.VISIBLE
                EmptyLayout.visibility = View.GONE
                MyRecyclerView.visibility = View.GONE
            }
            ViewEnums.LoadingLayout -> {
                LoadingLayout.visibility = View.VISIBLE
                ErrorLayout.visibility = View.GONE
                EmptyLayout.visibility = View.GONE
                MyRecyclerView.visibility = View.GONE
            }
            ViewEnums.MainLayout -> {
                LoadingLayout.visibility = View.GONE
                ErrorLayout.visibility = View.GONE
                EmptyLayout.visibility = View.GONE
                MyRecyclerView.visibility = View.VISIBLE
            }
            else -> {
                LoadingLayout.visibility = View.VISIBLE
                ErrorLayout.visibility = View.GONE
                EmptyLayout.visibility = View.GONE
                MyRecyclerView.visibility = View.GONE
            }
        }
    }

    override fun onItemClicked(movie: Movie) {
        val intent = Intent( this, MovieDetails::class.java)
        intent.putExtra("movie_id", movie.id.toString())
        startActivity(intent)
    }
}