package com.example.movieseeker.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class MovieDTO (
    val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    val title: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("poster_path") val posterPath: String?,
)