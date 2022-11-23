package com.example.movie.View.MovieDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movie.Utils.Constants
import com.example.movie.R
import com.example.movie.Utils.SharedPreferenceUtils
import com.example.movie.Utils.ViewEnums
import com.squareup.picasso.Picasso

class MovieDetails : AppCompatActivity() {

    lateinit var viewModel: MovieDetailsViewModel
    lateinit var RetryButton : Button
    lateinit var RetryButton2 : Button

    lateinit var LoadingLayout : LinearLayout
    lateinit var ErrorLayout : LinearLayout
    lateinit var EmptyLayout : LinearLayout
    lateinit var MainLayout : LinearLayout

    lateinit var MovieImage : ImageView
    lateinit var MovieName : TextView
    lateinit var MovieDate : TextView
    lateinit var MovieDescription : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)


        RetryButton = findViewById<Button>(R.id.TryAgain)
        RetryButton2 = findViewById<Button>(R.id.TryAgain2)

        LoadingLayout = findViewById<LinearLayout>(R.id.LoadingLayout)
        ErrorLayout = findViewById<LinearLayout>(R.id.ErrorLayout)
        EmptyLayout = findViewById<LinearLayout>(R.id.EmptyLayout)
        MainLayout = findViewById<LinearLayout>(R.id.MainLayout)

        MovieImage = findViewById<ImageView>(R.id.MovieImage)
        MovieName = findViewById<TextView>(R.id.MovieName)
        MovieDate = findViewById<TextView>(R.id.MovieDate)
        MovieDescription = findViewById<TextView>(R.id.MovieDescription)

        changeView(ViewEnums.LoadingLayout)
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        viewModel.movie_id = Integer.parseInt(intent.getSerializableExtra("movie_id").toString())
        viewModel.fetchData(SharedPreferenceUtils.getToken(this));

        viewModel.data.observe(this, Observer {
            var movieDetailsDataHolder = viewModel.data.value
            Picasso.get().load(Constants.BaseUrl_Image+movieDetailsDataHolder?.poster_path).into(MovieImage)

            MovieName.text = movieDetailsDataHolder?.title
            if(movieDetailsDataHolder?.release_date!=null)
                MovieDate.text = movieDetailsDataHolder?.release_date?.substring(0,4)
            MovieDescription.text = movieDetailsDataHolder?.overview
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
                MainLayout.visibility = View.GONE
            }
            ViewEnums.ErrorLayout -> {
                LoadingLayout.visibility = View.GONE
                ErrorLayout.visibility = View.VISIBLE
                EmptyLayout.visibility = View.GONE
                MainLayout.visibility = View.GONE
            }
            ViewEnums.LoadingLayout -> {
                LoadingLayout.visibility = View.VISIBLE
                ErrorLayout.visibility = View.GONE
                EmptyLayout.visibility = View.GONE
                MainLayout.visibility = View.GONE
            }
            ViewEnums.MainLayout -> {
                LoadingLayout.visibility = View.GONE
                ErrorLayout.visibility = View.GONE
                EmptyLayout.visibility = View.GONE
                MainLayout.visibility = View.VISIBLE
            }
            else -> {
                LoadingLayout.visibility = View.VISIBLE
                ErrorLayout.visibility = View.GONE
                EmptyLayout.visibility = View.GONE
                MainLayout.visibility = View.GONE
            }
        }
    }
}