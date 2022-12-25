package com.rahul.diagnalapp.data.models

data class Movie (
    val poster_path: String,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String,
    val genreIDS: List<Long>,
    val id: Long,
    val originalTitle: String,
    val title: String,
    val backdropPath: String,
    val popularity: Double,
    val voteCount: Long,
    val video: Boolean,
    val voteAverage: Double
)