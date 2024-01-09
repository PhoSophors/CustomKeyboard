package com.fe.customizekeyboard.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.fe.customizekeyboard.fragment.FavoriteFragment
import com.fe.customizekeyboard.fragment.HomeFragment
import com.fe.customizekeyboard.fragment.ProductFragment
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.databinding.ActivityMainBinding
import com.fe.customizekeyboard.fragment.ProfileFragment
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)



         // show fragment

        showFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.mnuHome -> showFragment(HomeFragment())
                R.id.mnuProduct-> showFragment(ProductFragment())
                R.id.mnuProfile -> {
                    showProfileActivity()
                }
                R.id.mnuFavorite ->{
                    showActivity()
                }

                else->{

                }
            }

            true
        }
    }

    // Draft register activity
    //Check existing account can add favorite product

    private fun showActivity(){
        val intent = Intent(this,RegisterActivity::class.java)

        // Start the SecondActivity using the intent
        startActivity(intent)
    }
    private fun showProfileActivity(){
        val intent = Intent(this,ProfileActivity::class.java)

        // Start the SecondActivity using the intent
        startActivity(intent)
        finish()
    }


    // Fun show fragment
    private fun showFragment(fragment: Fragment){

        // Fragment manager
        val fragmentManager  = supportFragmentManager
         //fragmemt transaction
        val fragmentTransaction =  fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.lytFragment ,fragment)
        fragmentTransaction.commit()

    }





}