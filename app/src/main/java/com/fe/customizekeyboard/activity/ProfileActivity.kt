package com.fe.customizekeyboard.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.databinding.ActivityProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding = ActivityProfileBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


//        back button
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        firebaseAuth = FirebaseAuth.getInstance()

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()

//        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signOut.setOnClickListener {
            // signout acc
            firebaseAuth.signOut()

            //start another activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

//        val textView = findViewById<TextView>(R.id.name)

//        val firebaseAuth = FirebaseAuth.getInstance()
//        val user = firebaseAuth.currentUser
//
//        if (user != null) {
//            val userName = user.displayName
//            textView.text = "Welcome, " + userName
//        } else {
//            // Handle the case where the user is not signed in
//        }


//        val textView = findViewById<TextView>(R.id.Email)
//
//        val firebaseAuth = FirebaseAuth.getInstance()
//        val user = firebaseAuth.currentUser
//
//        if (user != null) {
//            val ShowEmail = user.Email
//            textView.text = ShowEmail
//        } else {
//            // Handle the case where the user is not signed in
//        }


    }
}