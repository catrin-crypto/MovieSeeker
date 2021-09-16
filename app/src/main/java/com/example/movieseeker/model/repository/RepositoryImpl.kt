package com.example.movieseeker.model.repository

import android.os.Build
import androidx.annotation.RequiresApi
import app.moviebase.tmdb.image.TmdbImage
import app.moviebase.tmdb.image.TmdbImageType
import app.moviebase.tmdb.image.TmdbImageUrlBuilder
import com.example.movieseeker.R
import com.example.movieseeker.model.database.Database
import com.example.movieseeker.model.database.HistoryEntity
import com.example.movieseeker.model.entities.Movie
import com.example.movieseeker.model.entities.getRussianMovies
import com.example.movieseeker.model.entities.getWorldMovies
import com.example.movieseeker.model.rest.MovieRepo


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

    override fun getAllHistory(): List<Movie> =
        convertHistoryEntityToMovie(Database.db.historyDao().all())


    override fun saveEntity(movie: Movie) {
        Database.db.historyDao().insert(convertMovieToEntity(movie))
    }

    private fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<Movie> =
        entityList.map {
            Movie(it.id,it.language,it.name,it.creationDate,it.rating,it.picture)
        }


    private fun convertMovieToEntity(movie: Movie): HistoryEntity =
        HistoryEntity(movie.id,movie.language,movie.name,movie.creationDate?: "",movie.rating,movie.picture
        )

}