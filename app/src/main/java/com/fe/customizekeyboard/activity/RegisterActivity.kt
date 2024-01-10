package com.fe.customizekeyboard.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class RegisterActivity:AppCompatActivity (), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {
    private  lateinit var binding:ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.emailEt.onFocusChangeListener = this
        binding.passwordEt.onFocusChangeListener = this
        binding.confirmPasswordEt.onFocusChangeListener = this

//        back button
        binding.backRegisterBtn.setOnClickListener {
            onBackPressed()
        }

        binding.loginViewBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signupBtnEt.setOnClickListener {
            val emailEt = binding.emailEt.text.toString()
            val passwordEt = binding.passwordEt.text.toString()
            val confirmPasswordEt = binding.confirmPasswordEt.text.toString()

            if (emailEt.isNotEmpty() && passwordEt.isNotEmpty() && confirmPasswordEt.isNotEmpty()) {
                if (passwordEt == confirmPasswordEt) {

                    firebaseAuth.createUserWithEmailAndPassword(emailEt, passwordEt).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            Toast.makeText(this, "Account created.", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }


//        google
//        val googleBtnRegister = findViewById<Button>(R.id.googleBtnRegister)
//        googleBtnRegister.setOnClickListener {
//            signIn()
//        }

        binding.googleBtnRegister.setOnClickListener {
//            val googleBtnRegister = findViewById<Button>(R.id.googleBtnRegister)
            val intent = Intent(this, ProfileActivity::class.java)
            signIn(intent)
            finish()
        }
    }

//    google

    private fun signIn(intent: Intent) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
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

    //    Call this function to check the validity of the confirm password field's input.
    private fun validateConfirmPassword ():Boolean {
        var errorMessage: String? = null
        val value = binding.confirmPasswordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirm password is required"
        } else if (value.length < 8){
            errorMessage = "Confirm password must be 8 character long"
        }

        if (errorMessage != null) {
            binding.confirmPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    //    Call this function to specifically check if the password and confirm password fields match.
    private fun validatePasswordAndConfirmPassword ():Boolean {
        var errorMessage: String? = null
        val password = binding.passwordEt.text.toString()
        val confirmPassword = binding.confirmPasswordEt.text.toString()
        if (password != confirmPassword) {
            errorMessage = "Confirm password doesn't match with password"
        }
        if (errorMessage != null) {
            binding.confirmPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    //    This method is present but empty, suggesting it's not currently used for any click events.
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
                R.id.confirmPasswordEt -> {
                    if (hasFocus) {
                        if (binding.confirmPasswordTil.isErrorEnabled) {
                            binding.confirmPasswordTil.isErrorEnabled = false
                        }
                    } else {
                        validateConfirmPassword()
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

