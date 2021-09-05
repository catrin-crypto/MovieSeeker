package com.example.movieseeker.framework.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieseeker.databinding.ContactsFragmentRecyclerViewItemBinding
import com.example.movieseeker.framework.ui.contacts.ContactsFragment
import com.example.movieseeker.model.entities.Movie

class ContactsFragmentAdapter(private var itemClickListener: ContactsFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<ContactsFragmentAdapter.ContactsViewHolder>() {
    private var contactsData: List<Pair<String,String>> = listOf()
    private lateinit var binding: ContactsFragmentRecyclerViewItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setContacts(data: List<Pair<String,String>>) {
        contactsData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        binding = ContactsFragmentRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ContactsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) =
        holder.bind(contactsData[position])

    override fun getItemCount() = contactsData.size

    inner class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(contact: Pair<String,String>) = with(binding) {
            contactsFragmentRecyclerItemTextView.text = contact.first + " : " +contact.second
            root.setOnClickListener { itemClickListener.onItemViewClick(contact) }
        }
    }
}