package com.example.movieseeker.model.repository

import com.example.movieseeker.model.entities.Movie
import com.example.movieseeker.model.entities.getRussianMovies
import com.example.movieseeker.model.entities.getWorldMovies

class RepositoryImpl : Repository {
    override fun getMovieFromServer() = Movie()

    override fun getMovieFromLocalStorageWorld() = getWorldMovies()

    override fun getMovieFromLocalStorageRus() = getRussianMovies()

}