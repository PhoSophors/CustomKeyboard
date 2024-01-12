package com.fe.customizekeyboard.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.databinding.ActivityLoginBinding
import com.fe.customizekeyboard.databinding.ActivityWelcomeCreateAccountBinding

class WelcomeCreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.gotoSignin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.gotoSignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}