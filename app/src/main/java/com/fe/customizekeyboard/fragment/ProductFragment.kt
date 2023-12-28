package com.fe.customizekeyboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fe.customizekeyboard.databinding.FragmentProductBinding

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductBinding.inflate(inflater , container , false)
        return binding.root ;



    }
}