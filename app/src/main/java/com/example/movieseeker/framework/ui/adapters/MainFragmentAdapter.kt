package com.example.movieseeker.framework.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieseeker.databinding.MainFragmentRecyclerviewItemBinding
import com.example.movieseeker.framework.ui.main.MainFragment
import com.example.movieseeker.model.entities.Movie

class MainFragmentAdapter(private var itemClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var movieData: List<Movie> = listOf()
    private lateinit var binding: MainFragmentRecyclerviewItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = MainFragmentRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(movieData[position])

    override fun getItemCount() = movieData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) = with(binding) {
            mainFragmentRecyclerItemTextView.text = movie.name
            root.setOnClickListener { itemClickListener.onItemViewClick(movie) }
        }
    }
}