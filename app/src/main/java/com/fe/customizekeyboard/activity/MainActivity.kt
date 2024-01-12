package com.fe.customizekeyboard.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fe.customizekeyboard.fragment.FavoriteFragment
import com.fe.customizekeyboard.fragment.HomeFragment
import com.fe.customizekeyboard.fragment.ProductFragment
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.databinding.ActivityMainBinding
import com.fe.customizekeyboard.fragment.ProfileFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var userLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Check if the user is logged in
        checkUserLoggedIn()

        // Show the initial fragment
        showFragment(HomeFragment())

        // Set up BottomNavigationView listener
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mnuHome -> showFragment(HomeFragment())
                R.id.mnuProduct -> showFragment(ProductFragment())
                R.id.mnuProfile -> {
                    if (userLoggedIn) {
                        showProfileActivity()
                    } else {
                        showFragment(ProfileFragment())
                    }
                }
                R.id.mnuFavorite -> {
                    if (userLoggedIn) {
                        showFragment(FavoriteFragment())
                    } else {
                        showWelcomeCreateAccountActivity()
                    }
                }
                else -> {

                }
            }

            true
        }
    }

    // Function to check if the user is logged in
    private fun checkUserLoggedIn() {
        val currentUser = firebaseAuth.currentUser
        userLoggedIn = currentUser != null
    }

    // Navigate to RegisterActivity for non-logged-in users
    private fun showWelcomeCreateAccountActivity() {
        val intent = Intent(this, WelcomeCreateAccountActivity::class.java)
        startActivity(intent)
    }

    // Navigate to ProfileActivity for non-logged-in users
    private fun showProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Function to show fragments
    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.lytFragment, fragment)
        fragmentTransaction.commit()
    }

}
