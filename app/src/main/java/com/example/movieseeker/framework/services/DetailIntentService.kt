package com.example.movieseeker.framework.services

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movieseeker.framework.ui.details.DETAILS_INTENT_FILTER
import com.example.movieseeker.framework.ui.details.DETAILS_LOAD_RESULT_EXTRA
import com.example.movieseeker.model.entities.Movie
import com.example.movieseeker.model.repository.RepositoryImpl

const val MOVIE_ID_EXTRA = "MovieID"
const val MOVIE_LANGUAGE_EXTRA = "MovieLanguage"

const val ACTION_FETCH_DETAILS = "com.example.movieseeker.framework.services.action.ACTION_FETCH_DETAILS"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.

 */
class DetailIntentService : IntentService("DetailIntentService") {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FETCH_DETAILS -> {
                try {
                        requestMovieDetail(intent.getIntExtra(MOVIE_ID_EXTRA,-1),
                            intent?.getStringExtra(MOVIE_LANGUAGE_EXTRA) ?: "")
                } catch (e: Exception) {
                    StringBuilder().apply {
                        append("DetailsIntentService\n")
                        append("Failed to interpret ID or language from intent: " + e.localizedMessage)
                        toString().also {
                            Toast.makeText(baseContext, it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
        @RequiresApi(Build.VERSION_CODES.O)
       private fun requestMovieDetail(id: Int, language: String) {
            val rep  = RepositoryImpl();
            val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                rep.getMovieFromServer(id,language)
            } else {
                TODO("VERSION.SDK_INT < O")
            };
            onResponse(movie)
        }

    private fun onResponse(movie: Movie) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, movie)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }


    companion object {

        @JvmStatic
        fun startActionDetailRequest(context: Context, id: Int, language: String) {
            val intent = Intent(context, DetailIntentService::class.java).apply {
                 action = ACTION_FETCH_DETAILS
                putExtra(MOVIE_ID_EXTRA, id)
                putExtra(MOVIE_LANGUAGE_EXTRA, language)
            }
            context.startService(intent)
        }
    }
}