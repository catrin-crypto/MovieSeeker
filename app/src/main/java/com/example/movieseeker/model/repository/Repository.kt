package com.example.movieseeker.model.repository

import com.example.movieseeker.model.entities.Movie

interface Repository {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorage(): Movie
}