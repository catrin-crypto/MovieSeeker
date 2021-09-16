package com.example.movieseeker.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.*
import androidx.fragment.app.Fragment
import com.example.movieseeker.BuildConfig
import com.example.movieseeker.R
import com.example.movieseeker.databinding.MainActivityBinding
import com.example.movieseeker.framework.ui.contacts.ContactsFragment
import com.example.movieseeker.framework.ui.history.HistoryFragment
import com.example.movieseeker.framework.ui.main.MainFragment
import com.example.movieseeker.framework.ui.maps.MapsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textActivity.text = BuildConfig.BUILD_TYPE
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        val string = intent.extras?.getString("custom", "no data") ?: "no data"
        makeText(this, string, LENGTH_SHORT).show()
    }

override fun onCreateOptionsMenu(menu: Menu?) : Boolean{
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {

            R.id.menu_history -> {
                openFragment(HistoryFragment.newInstance())
                true
            }
            R.id.menu_contacts -> {
                openFragment(ContactsFragment.newInstance())
                true
            }
            R.id.menu_google_maps -> {
                openFragment(MapsFragment.newInstance())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}