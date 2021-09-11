package com.example.movieseeker.model.repository

import com.example.movieseeker.model.entities.Movie

interface Repository {
    fun getMovieFromServer(id : Int, language : String): Movie
    fun getMovieFromLocalStorageWorld(): List<Movie>
    fun getMovieFromLocalStorageRus(): List<Movie>
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
}