package com.example.tabbed_activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Movie(val name: String, val year: Int, val genre: String)

class MovieFragment : Fragment() {
    private val movies = listOf(
        Movie("Matrix", 1999, "Sci-Fi"),
        Movie("Gladiator", 2000, "Исторический боевик"),
        Movie("Interstellar", 2014, "Sci-Fi"),
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_movie, container, false)
        val rv = v.findViewById<RecyclerView>(R.id.movie_grid)
        rv.layoutManager = GridLayoutManager(context, 2)
        rv.adapter = MovieAdapter(movies)
        return v
    }

    inner class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
        override fun getItemCount() = movies.size
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val movie = movies[position]
            holder.title.text = movie.name + " (" + movie.year + ")"
            holder.subtitle.text = movie.genre
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(android.R.id.text1)
            val subtitle: TextView = view.findViewById(android.R.id.text2)
        }
    }
}