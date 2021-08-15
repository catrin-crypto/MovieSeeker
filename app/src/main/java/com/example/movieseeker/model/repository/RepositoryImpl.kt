package com.example.movieseeker.model.repository

import com.example.movieseeker.model.entities.Movie
import com.example.movieseeker.model.entities.getRussianMovies
import com.example.movieseeker.model.entities.getWorldMovies

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie {
        return Movie()
        TODO("Not yet implemented")
    }

    override fun getMovieFromLocalStorageWorld(): List<Movie> {
        return getWorldMovies()
        TODO("Not yet implemented")
    }

    override fun getMovieFromLocalStorageRus(): List<Movie> {
        return getRussianMovies()
        TODO("Not yet implemented")
    }


}