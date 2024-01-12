package com.fe.customizekeyboard.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.fe.customizekeyboard.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding = ActivityProfileBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.profileBackBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the WelcomeActivity to prevent going back to it
        }

        // Get the user information
        val user = firebaseAuth.currentUser

        if (user != null) {
            // Get and display user email
            val userEmail = user.email
            binding.showUserEmail.text = userEmail

            // Check if user has a display name (full name)
            if (user.displayName != null) {
                // If available, display full name
                binding.showFullName.text = user.displayName
            } else {
                // If not available, fetch full name from Firebase and update display name
                fetchAndDisplayFullName(user)
            }
            // Load the profile photo
            loadProfilePhoto(user)
        } else {
            // Handle the case where the user is not signed in
        }

        binding.signOut.setOnClickListener {
            showSignOutConfirmationDialog()
        }
    }


    private fun showSignOutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Sign Out")
        alertDialogBuilder.setMessage("Are you sure you want to sign out?")
        alertDialogBuilder.setPositiveButton("Sign Out") { _, _ ->
            signOut()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun signOut() {
        firebaseAuth.signOut()
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun fetchAndDisplayFullName(user: FirebaseUser) {
        // Here you can fetch the full name from Firebase and update the user's display name
        // For demonstration purposes, let's assume you have a field named "fullName" in your user database

        // Example: Fetch full name from Firebase and update the display name
        // Replace this with your actual implementation
        val fullNameFromFirebase = "So Phors" // Replace this with your Firebase query


        // Update the user's display name in Firebase
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(fullNameFromFirebase)
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Successfully updated the display name, now update the TextViews
                    binding.showFullName.text = fullNameFromFirebase
                }
            }
    }

    /**
     * private fun loadProfilePhoto(user: FirebaseUser) {
     */
    private fun loadProfilePhoto(user: FirebaseUser) {
        // Retrieve the user's photo URL
        val photoUrl = user.photoUrl

        // Check if the user has a profile photo
        if (photoUrl != null) {
            // Use Glide or Picasso to load the profile photo into an ImageView
            // Replace imageViewProfilePhoto with your actual ImageView ID
//            Glide.with(this)
//                .load(photoUrl)
//                .into(binding.imageViewProfilePhoto)
        } else {
            // Handle the case where the user does not have a profile photo
            // You can set a default image or hide the ImageView
        }
    }
    /**
     * Call the loadProfilePhoto function in onCreate:
     */

}
