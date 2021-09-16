package com.example.movieseeker.framework.ui.main



import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.movieseeker.AppState
import com.example.movieseeker.R
import com.example.movieseeker.databinding.MainFragmentBinding
import com.example.movieseeker.framework.ui.details.MovieDetailsFragment
import com.example.movieseeker.framework.ui.adapters.ContactsFragmentAdapter
import com.example.movieseeker.framework.ui.adapters.MainFragmentAdapter
import com.example.movieseeker.framework.ui.showSnackBar
import com.example.movieseeker.model.entities.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val IS_RUS_SET  = "IS_RUS_SET"

class MainFragment : Fragment() {
    private val viewModel : MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: MainFragmentAdapter? = null
    private var isDataSetRus: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            if (it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_RUS_SET, false))
                isDataSetRus = true
            else
                isDataSetRus = false
        }
        with(binding){
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener{changeMovieDataSet()}
            viewModel.getLiveData().observe(viewLifecycleOwner,{ renderData(it)})
            if (isDataSetRus) {
                mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                viewModel.getMovieFromLocalSourceRus()
            }
            else {
                mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                viewModel.getMovieFromLocalSourceWorld()
            }
        }

    }

    override fun onDestroyView()  {
        super.onDestroyView()
        _binding = null
    }

    private fun changeMovieDataSet() = with(binding) {
        if (isDataSetRus) {
            viewModel.getMovieFromLocalSourceWorld()
            mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getMovieFromLocalSourceRus()
            mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus

        saveSetLanguage(isDataSetRus)
    }

    private fun saveSetLanguage(isSetWorld: Boolean) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(IS_RUS_SET, isSetWorld)
                apply()
            }
        }
    }


    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                adapter = MainFragmentAdapter(object: OnItemViewClickListener{
                    override fun onItemViewClick(movie: Movie){
                        val manager = activity?.supportFragmentManager
                        manager?.let{manager ->
                            val bundle = Bundle().apply{
                                putParcelable(MovieDetailsFragment.BUNDLE_EXTRA,movie)
                            }
                            manager.beginTransaction()
                                .add(R.id.container, MovieDetailsFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply{
                    setMovie(appState.movieData)
                }
                mainFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                main.showSnackBar(getString(R.string.snackbar_error),
                        getString(R.string.snackbar_reload),
                        { viewModel.getMovieFromLocalSourceRus() })
//               Snackbar
//                        .make(binding.mainFragmentFAB, R.string.snackbar_error, Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.snackbar_update) { viewModel.getMovieFromLocalSourceRus() }
//                        .show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }
    companion object {
        fun newInstance() = MainFragment()
    }


}

