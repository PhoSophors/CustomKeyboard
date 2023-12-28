package com.fe.customizekeyboard.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.databinding.ActivityLoginBinding

class LoginActivity:AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root);
    }

}