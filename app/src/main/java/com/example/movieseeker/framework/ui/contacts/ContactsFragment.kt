package com.example.movieseeker.framework.ui.contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.movieseeker.R
import com.example.movieseeker.databinding.FragmentContactsBinding
import com.example.movieseeker.framework.ui.adapters.ContactsFragmentAdapter
import com.example.movieseeker.framework.ui.adapters.MainFragmentAdapter
import com.example.movieseeker.framework.ui.details.MovieDetailsFragment
import com.example.movieseeker.framework.ui.main.MainFragment
import com.example.movieseeker.model.entities.Movie

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val contacts: MutableList<Pair<String, String>> =
        listOf<Pair<String, String>>().toMutableList()
    private var adapter: ContactsFragmentAdapter? = null
    private val permissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            getContacts()
        } else {
            Toast.makeText(
                context,
                getString(R.string.ask_permission),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        adapter = ContactsFragmentAdapter(object : ContactsFragment.OnItemViewClickListener {
            override fun onItemViewClick(contact: Pair<String, String>) {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + contact.second)
                startActivity(dialIntent)
            }
        }).apply {
            setContacts(contacts)
        }
        with(binding) {
            contactsFragmentRecyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun checkPermission() {
        context?.let { notNullContext ->
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    notNullContext,
                    Manifest.permission.READ_CONTACTS
                ) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }


    private fun getContacts() {
        context?.let {
            val projection = arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
            )

            val selection = String.format("%s > 0", ContactsContract.Contacts.HAS_PHONE_NUMBER)
            val cursorWithContacts: Cursor? = it.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                selection,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name =
                            cursor.getString(
                                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                            )

                        val hasPhoneNumber =
                            cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        // val contacts = MyContacts()
                        val contactId: String =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Data._ID))
                        val phoneNumbers = ArrayList<String>()
                        if (hasPhoneNumber > 0) {
                            // contacts.contactName = name
                            val phoneCursor = it.contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                arrayOf(contactId), null
                            )

                            phoneCursor!!.moveToFirst()
                            while (!phoneCursor.isAfterLast) {
                                val phoneNumber =
                                    phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                                        .replace(" ", "")
                                phoneNumbers.add(phoneNumber)
                                phoneCursor.moveToNext()
                            }
                            phoneCursor.close()
                        }
                        //addView("$name : $phoneNumbers")
                        contacts.add(Pair(name ?: "", phoneNumbers.toString()))
                    }
                }
            }
            cursorWithContacts?.close()
        }
    }

    private fun addView(textToShow: String) = with(binding) {
        contactsFragmentRecyclerView.addView(AppCompatTextView(requireContext()).apply {
            text = textToShow
            textSize = resources.getDimension(R.dimen.movie_details_textsize)
        })
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(contact: Pair<String, String>)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}