package com.fe.customizekeyboard.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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

        // Get an instance of FirebaseAuth
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
            val displayName = user.displayName
            if (displayName != null) {
                // If available, display full name
                binding.showFullName.text = displayName
            } else {
                // If not available, fetch full name from intent
                val fullNameFromIntent = intent.getStringExtra("USER_NAME")
                if (fullNameFromIntent != null) {
                    binding.showFullName.text = fullNameFromIntent

                    // Update the user's display name in Firebase
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(fullNameFromIntent)
                        .build()

                    user.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Successfully updated the display name
                            }
                        }
                }
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


    // sign out function (when user click sign out button it alert Dialog Builder to confirm again)
    private fun showSignOutConfirmationDialog() {
        // Create an AlertDialog.Builder instance
        val alertDialogBuilder = AlertDialog.Builder(this)
        // Set the title of the dialog
        alertDialogBuilder.setTitle("Sign Out")
        // Set the message displayed in the dialog
        alertDialogBuilder.setMessage("Are you sure you want to sign out?")
        // Set the positive button with a click listener
        alertDialogBuilder.setPositiveButton("Sign Out") { _, _ ->
            // Execute the signOut function when the "Sign Out" button is clicked
            signOut()
        }

        // Set the negative button with a click listener
        alertDialogBuilder.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
            // Dismiss the dialog when the "Cancel" button is clicked
            dialog.dismiss()
        }

        // Create an AlertDialog based on the configured AlertDialog.Builder
        val alertDialog = alertDialogBuilder.create()
        // Show the created dialog
        alertDialog.show()
    }

    private fun signOut() {
        firebaseAuth.signOut()
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }


    /**
     * call the loadProfilePhoto function in onCreate:
     */
    private fun loadProfilePhoto(user: FirebaseUser) {
        // Retrieve the user's photo URL
        val photoUrl = user.photoUrl

        // Check if the user has a profile photo
        if (photoUrl != null) {
            // Use Glide or Picasso to load the profile photo into an ImageView
            // Replace imageViewProfilePhoto with your actual ImageView ID
            Glide.with(this)
                .load(photoUrl)
                .into(binding.imageViewProfilePhoto)
        } else {
            // Handle the case where the user does not have a profile photo
            // You can set a default image or hide the ImageView
        }
    }
}
