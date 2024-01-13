package com.fe.customizekeyboard.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
// import androidx.navigation.fragment.findNavController
import com.fe.customizekeyboard.activity.RegisterActivity
import com.fe.customizekeyboard.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.profileBackBtn.setOnClickListener {
            // Navigate back to the previous destination
//            findNavController().popBackStack()
        }

        binding.gotoLogin.setOnClickListener {
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}
