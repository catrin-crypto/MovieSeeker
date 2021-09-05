package com.example.movieseeker.model.rest

import com.example.movieseeker.model.rest.rest_entities.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface MovieApi {
    // Request{method=GET, url=https://api.themoviedb.org/3/movie/529106?api_key=fde6ee8a014ddd05450b99c820be25a8&language=ru, tags={class retrofit2.Invocation=com.example.movieseeker.model.rest.MovieApi.getMovie() [529106, fde6ee8a014ddd05450b99c820be25a8, ru]}}
    @GET("movie/{id}")
    fun getMovie(
        @Path("id") Id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Call<MovieDTO>
}