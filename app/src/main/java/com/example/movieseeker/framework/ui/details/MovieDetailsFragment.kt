package com.example.movieseeker.framework.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieseeker.AppState
import com.example.movieseeker.R
import com.example.movieseeker.databinding.FragmentMovieDetailsBinding
import com.example.movieseeker.model.entities.Movie
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun Float.format(digits: Int) = "%.${digits}f".format(this)

    private fun setData(movieData: Movie) = with(binding) {
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
                viewModel.liveDataToObserve.observe(viewLifecycleOwner, { appState ->
                    when (appState) {
                        is AppState.Error -> {
                            mainView.visibility = View.INVISIBLE
                            loadingLayout.visibility = View.GONE
                            errorTV.visibility = View.VISIBLE
                        }
                        AppState.Loading -> {
                            mainView.visibility = View.INVISIBLE
                            binding.loadingLayout.visibility = View.VISIBLE
                        }
                        is AppState.Success -> {
                            loadingLayout.visibility = View.GONE
                            mainView.visibility = View.VISIBLE
                            movieName.text = appState.movieData[0].name
                            appState.movieData[0].picture?.let{Picasso.get().load(it).into(moviePicture)}
                            movieRating.text = appState.movieData[0].rating.format(1)
                            creationYear.text = appState.movieData[0].creationDate
                         }
                    }
                })
                viewModel.loadData(it.id, it.language)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
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