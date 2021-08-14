package com.example.movieseeker.model.repository

import com.example.movieseeker.model.entities.Movie

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie {
        return Movie()
        TODO("Not yet implemented")
    }

    override fun getMovieFromLocalStorage(): Movie {
       return Movie()
        TODO("Not yet implemented")
    }

}