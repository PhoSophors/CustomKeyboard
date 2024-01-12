package com.fe.customizekeyboard.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity:AppCompatActivity (), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener  {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        back button
        binding.backLoginBtn.setOnClickListener {
            onBackPressed()
        }

        // Set the onFocusChangeListener for the email EditText && password EditText
        binding.emailEt.onFocusChangeListener = this
        binding.passwordEt.onFocusChangeListener = this

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerViewBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
//        binding.forgotPasswordText.setOnClickListener {
//            val intent = Intent(this, ResetPasswordActivity::class.java)
//            startActivity(intent)
//            finish()  // Finish the current LoginActivity
//        }
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

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up Google Sign In button click listener
        binding.googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

    }


    /**
     * Login with google
     */
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account?.idToken)
        } catch (e: ApiException) {
            // Handle sign-in failure (e.g., no internet, account not found, etc.)
            Log.w(ContentValues.TAG, "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = FirebaseAuth.getInstance().currentUser
                    //  Navigate to the profile activity.
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //    Call this function to check the validity of the email field's input.
    private fun validateEmail(): Boolean {
        val email = binding.emailEt.text.toString().trim()

        return if (email.isEmpty()) {
            binding.emailTil.error = "Email is required"
            false
        } else {
            binding.emailTil.error = null
            true
        }
    }

    //    Call this function to check the validity of the password field's input.
    private fun validatePassword(): Boolean {
        val password = binding.passwordEt.text.toString()

        return if (password.isEmpty()) {
            binding.passwordTil.error = "Password is required"
            false
        } else if (password.length < 8) {
            binding.passwordTil.error = "Password must be 8 characters long"
            false
        } else {
            binding.passwordTil.error = null
            true
        }
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