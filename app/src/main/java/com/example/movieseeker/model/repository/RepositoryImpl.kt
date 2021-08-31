package com.example.movieseeker.model.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import app.moviebase.tmdb.Tmdb3
import app.moviebase.tmdb.image.TmdbImage
import app.moviebase.tmdb.image.TmdbImageType
import app.moviebase.tmdb.image.TmdbImageUrlBuilder
import app.moviebase.tmdb.model.AppendResponse
import app.moviebase.tmdb.model.TmdbMovieDetail
import com.example.movieseeker.R
import com.example.movieseeker.model.entities.Movie
import com.example.movieseeker.model.entities.getRussianMovies
import com.example.movieseeker.model.entities.getWorldMovies
import com.example.movieseeker.model.rest.MovieRepo
import kotlinx.coroutines.runBlocking


class RepositoryImpl : Repository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getMovieFromServer(id: Int, language: String): Movie {
         val dto = MovieRepo.api.getMovie(id, "fde6ee8a014ddd05450b99c820be25a8", language)
             .execute()
             .body()
//        var movieDetail: TmdbMovieDetail? = null
//        runBlocking {
//            try {
//                val tmdb = Tmdb3("fde6ee8a014ddd05450b99c820be25a8")
//                movieDetail = tmdb.movies.getDetails(
//                    movieId = id,
//                    language = language,
//                    appendResponses = listOf(AppendResponse.IMAGES)
//                )
//            } catch (e: Exception) {
//                println("Exception: " + e.message!!)
//            }
//        }
//        movieDetail?.let {
//            return Movie(
//                id = it.id,
//                rating = it.voteAverage,
//                name = it.title,
//                picture = it.posterImage?.let{
//                    TmdbImageUrlBuilder.build(
//                    image = it,
//                    width = R.dimen.image_width,
//                    height =  R.dimen.image_height)
//                },
//                creationDate = it.releaseDate?.toString()
//            )
//         }
        dto?.let{
            return Movie(
                id = it.id,
                rating = it.voteAverage,
                name = it.title,
                picture = it.posterPath?.let {
                    TmdbImageUrlBuilder.build(
                        image = TmdbImage(it,TmdbImageType.POSTER),
                        width = R.dimen.image_width,
                        height = R.dimen.image_height
                    )
                },
                creationDate = it.releaseDate ?: ""
            )
        }
        return Movie()
    }

    override fun getMovieFromLocalStorageWorld() = getWorldMovies()

    override fun getMovieFromLocalStorageRus() = getRussianMovies()

}