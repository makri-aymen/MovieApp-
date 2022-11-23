package com.example.movie.View.MovieList.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie_test.Models.Movie
import com.squareup.picasso.Picasso

class RecyclerAdapter(var movies: List<Movie>?, val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var BaseUrl_Image : String = "https://image.tmdb.org/t/p/w500"
        val name: TextView = itemView.findViewById(R.id.MovieName);
        val date: TextView = itemView.findViewById(R.id.MovieDate);
        val image: ImageView = itemView.findViewById(R.id.MovieImage);


        fun bind(movie: Movie, clickListener: OnItemClickListener)
        {
            name.text = movie.title
            if(movie.release_date!=null)
                date.text = movie.release_date.substring(0,4)
            Picasso.get().load(BaseUrl_Image+movie.poster_path).into(image)

            itemView.setOnClickListener {
                clickListener.onItemClicked(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(movies!=null){
            val movie = movies!!.get(position)
            holder.bind(movie,itemClickListener)
        }
    }

    override fun getItemCount(): Int {
        if(movies!=null){
            return movies!!.size
        }else{
            return 0
        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(movie: Movie)
}