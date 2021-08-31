package com.example.movieseeker.framework.ui.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movieseeker.AppState
import com.example.movieseeker.R
import com.example.movieseeker.databinding.FragmentMovieDetailsBinding
import com.example.movieseeker.framework.services.ACTION_FETCH_DETAILS
import com.example.movieseeker.framework.services.DetailIntentService
import com.example.movieseeker.framework.services.MOVIE_ID_EXTRA
import com.example.movieseeker.framework.services.MOVIE_LANGUAGE_EXTRA
import com.example.movieseeker.model.entities.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

internal var _binding: FragmentMovieDetailsBinding? = null
internal val binding get() = _binding!!

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"


class MovieDetailsFragment : Fragment() {
    //private val viewModel: DetailsViewModel by viewModel()

    val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val movie = intent.getParcelableExtra<Movie>(DETAILS_LOAD_RESULT_EXTRA)
            movie?.let {
                    with(binding) {
                        loadingLayout.visibility = View.GONE
                        mainView.visibility = View.VISIBLE
                        movieName.text = movie.name
                        movieRating.text = movie.rating.format(1)
                        creationYear.text = movie.creationDate.toString()
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }

    }
    private fun initiateMovieRequest(id: Int,language: String) {
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        context?.let {
            it.startService(Intent(it, DetailIntentService::class.java).apply {
                action = ACTION_FETCH_DETAILS
                putExtra(MOVIE_ID_EXTRA,id)
                putExtra(MOVIE_LANGUAGE_EXTRA,language)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun Float.format(digits: Int) = "%.${digits}f".format(this)

    public fun setData(movieData: Movie) = with(binding) {
        movieName.text = movieData.name
        moviePicture.setImageResource(R.drawable.the_movie_db)
        movieRating.text = movieData.rating.format(1)
        creationYear.text = movieData.creationDate.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        movie?.let {
            with(binding) {
                setData(it)
                initiateMovieRequest(movie.id,movie.language)
                //MVVM is turned off in favour of service
//                viewModel.liveDataToObserve.observe(viewLifecycleOwner, { appState ->
//                    when (appState) {
//                        is AppState.Error -> {
//                            mainView.visibility = View.INVISIBLE
//                            loadingLayout.visibility = View.GONE
//                            errorTV.visibility = View.VISIBLE
//                        }
//                        AppState.Loading -> {
//                            mainView.visibility = View.INVISIBLE
//                            binding.loadingLayout.visibility = View.VISIBLE
//                        }
//                        is AppState.Success -> {
//                            loadingLayout.visibility = View.GONE
//                            mainView.visibility = View.VISIBLE
//                            movieName.text = appState.movieData[0].name
//                            movieRating.text = appState.movieData[0].rating.format(1)
//                         }
//                    }
//                })
//                viewModel.loadData(it.id, it.language)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.let {LocalBroadcastManager.getInstance(it)
            .unregisterReceiver(loadResultsReceiver)}
        _binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}