package com.example.movie_test.Models

data class DataHolder(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)