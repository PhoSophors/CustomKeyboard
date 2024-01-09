package com.fe.customizekeyboard.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.activity.MainActivity
import com.fe.customizekeyboard.databinding.ActivityWelcomeBinding
import com.fe.customizekeyboard.databinding.FragmentHomeBinding
import com.fe.customizekeyboard.databinding.FragmentProductBinding
import com.fe.customizekeyboard.databinding.FragmentProductDetailBinding
import kotlin.math.log

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding ;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater ,container ,false) ;
        return binding.root


        binding.cardProduct.setOnClickListener {
            // Open FragmentB when the button is clicked

        }



        return view

    }









}


