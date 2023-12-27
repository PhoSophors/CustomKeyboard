package com.fe.customizekeyboard.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.databinding.ActivityRegisterBinding

class RegisterActivity:AppCompatActivity () {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater) ;
        setContentView(binding.root);

    }
    


}