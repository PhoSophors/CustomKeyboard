package com.fe.customizekeyboard.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.databinding.ActivityLoginBinding
import com.fe.customizekeyboard.fragment.ProfileFragment
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity (), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener  {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        back button
        binding.backLoginBtn.setOnClickListener {
            onBackPressed()
        }

        binding.emailEt.onFocusChangeListener = this
        binding.passwordEt.onFocusChangeListener = this

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerViewBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.loingBtn.setOnClickListener {
            val emailEt = binding.emailEt.text.toString()
            val passwordEt = binding.passwordEt.text.toString()

            if (emailEt.isNotEmpty() && passwordEt.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(emailEt, passwordEt).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, ProfileActivity::class.java)
                        Toast.makeText(this, "Login success.", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }


    }


    //    Call this function to check the validity of the email field's input.
    private fun validateEmail (): Boolean {
        var errorMessage: String? = null
        val value = binding.emailEt.text.toString() // Gets the text entered in the full name field.
        if (value.isEmpty()) { // If empty, sets an error message
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage = "Email address is invalid"
        }

        if (errorMessage != null) { //If an error message was set, it's displayed.
            binding.emailTil.apply {// Uses a Kotlin apply block to configure the text input layout.
                isErrorEnabled = true  // Enables error display for the layout.
                error = errorMessage  // Sets the specific error message to display.
            }
        }
        return errorMessage == null //  Returns true if validation passed (no error), false if an error occurred.
    }

    //    Call this function to check the validity of the password field's input.
    private fun validatePassword ():Boolean {
        var errorMessage: String? = null
        val value = binding.passwordEt.text.toString()

        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 8){ //Checks if the password is shorter than 8 characters.
            errorMessage = "Password must be 8 character long"
        }

        if (errorMessage != null) {
            binding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    override fun onClick(view: View?) {
    }

    // This function is called when a view (like a text field) gains or loses focus.
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when(view.id) {
                R.id.emailEt -> {
                    if (hasFocus) {
                        if (binding.emailTil.isErrorEnabled) {
                            binding.emailTil.isErrorEnabled = false
                        }
                    }else {
                        validateEmail()
                    }
                }
                R.id.passwordEt -> {
                    if (hasFocus) {
                        if (binding.passwordTil.isErrorEnabled) {
                            binding.passwordTil.isErrorEnabled = false
                        }
                    }else {
                        validatePassword()
                    }
                }


            }
        }
    }
    //    This method is also present but always returns false, indicating it doesn't intercept any key events.
    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        return false
    }

}