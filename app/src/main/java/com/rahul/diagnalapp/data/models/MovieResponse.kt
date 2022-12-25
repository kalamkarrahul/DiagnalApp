package com.rahul.diagnalapp.data.models

data class MovieResponse(
    val page: Long,
    val results: List<Movie>,
    val totalResults: Long,
    val totalPages: Long
) {
}