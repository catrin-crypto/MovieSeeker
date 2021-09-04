package com.example.movieseeker.framework.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.movieseeker.databinding.ItemHistoryListBinding
import com.example.movieseeker.model.entities.Movie

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {
    private var data: List<Movie> = arrayListOf()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(
            ItemHistoryListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(private val binding: ItemHistoryListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                recyclerViewItem.text =
                    String.format("%d %s %s %s %.2f %s", data.id,data.name, data.language, data.creationDate,data.rating, data.picture)
                root.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "on click: ${data.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}