package com.example.contactstestapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.contactstestapp.databinding.ActivityMainBinding
import com.example.contactstestapp.ui.viewmodels.SharedViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val sharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedViewModel.navigateToFragment2.observe(this) { contactClicked ->
            if (contactClicked != null) {
                replaceFragment(Fragment2.newInstance(contactClicked))
                sharedViewModel.onNavigationHandled()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, Fragment1())
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(null)
            .commit()
    }
}