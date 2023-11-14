package com.fe.customizekeyboard.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.databinding.ActivityWelcomeBinding
import kotlin.math.log

class WelcomeActivity:AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
//        setContentView(binding.root) ;
        val view = binding.root
        setContentView(view)

        binding.btnStart.setOnClickListener {
            // Create an Intent to start the SecondActivity
            val intent = Intent(this, MainActivity::class.java)

            // Start the SecondActivity using the intent
            startActivity(intent)

        }

        //show activity Main

    }

    // function show activity Home
//    fun showActivityMain() {
//        val intent = Intent(this , MainActivity::class.java)  ;
//        startActivity(intent) ;
//    }


}