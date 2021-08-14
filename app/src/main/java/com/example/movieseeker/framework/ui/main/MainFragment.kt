package com.example.movieseeker.framework.ui.main


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.movieseeker.AppState
import com.example.movieseeker.R
import com.example.movieseeker.databinding.MainFragmentBinding
import com.example.movieseeker.model.entities.Movie
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val viewModel : MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true);
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = Observer<AppState> { renderData(it) }
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                progressBar.visibility = View.GONE
                movieGroup.visibility = View.VISIBLE
                setData(movieData)
            }
            is AppState.Loading -> {
                movieGroup.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                movieGroup.visibility = View.INVISIBLE
                Snackbar
                        .make(main, "Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") { viewModel.getMovie() }
                        .show()
            }
        }
    }

    fun Float.format(digits: Int) = "%.${digits}f".format(this)

    private fun setData(movieData: Movie) = with(binding) {
        movieName.text = movieData.name
        movieImage.setImageResource(R.drawable.the_movie_db)
        movieRating.text = movieData.rating.format(2)
        creationYear.text = movieData.creationDate.toString()
    }
    companion object {
        fun newInstance() = MainFragment()
    }


}

