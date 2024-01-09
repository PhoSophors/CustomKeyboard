package com.fe.customizekeyboard.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import android.graphics.Color as AndroidColor

class RegisterActivity:AppCompatActivity (), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {
    private  lateinit var binding:ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.fullnameEt.onFocusChangeListener = this
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
            val fullnameEt = binding.fullnameEt.text.toString()
            val emailEt = binding.emailEt.text.toString()
            val passwordEt = binding.passwordEt.text.toString()
            val confirmPasswordEt = binding.confirmPasswordEt.text.toString()

            if (fullnameEt.isNotEmpty() && emailEt.isNotEmpty() && passwordEt.isNotEmpty() && confirmPasswordEt.isNotEmpty()) {
                if (passwordEt == confirmPasswordEt) {
                    firebaseAuth.createUserWithEmailAndPassword(emailEt, passwordEt)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, ProfileActivity::class.java)
                                showToast("Account created.", R.layout.custom_toast_success, R.id.toast_success)
                                startActivity(intent)
                                finish()
                            } else {
                                showToast("Email already login.", R.layout.custom_toast_error, R.id.toast_error)
                            }
                        }
                } else {
                    showToast("Password is not matching", R.layout.custom_toast_error,  R.id.toast_error)
                }
            } else {
                showToast("Empty Fields Are not Allowed !!", R.layout.custom_toast_error,  R.id.toast_error)
            }
        }

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }

        /***
         * register with google account
         */
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up Google Sign In button click listener
        binding.googleSignUpButton.setOnClickListener {
            signInWithGoogle()
        }

    }

    /**
     * toast set
     */

    private fun showToast(message: String, layoutId: Int, targetTextViewId: Int) {
        val toast = Toast(this)

        // Inflate the custom layout
        val inflater = layoutInflater
        val layout = inflater.inflate(layoutId, null)

        // Set the text content
        val textView = layout.findViewById<TextView>(targetTextViewId)
        textView.text = message

        // Set the custom view to the Toast
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT

        toast.show()
    }

    /**
     * register with google
     */

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

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
            Log.w(TAG, "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseAuth.getInstance().currentUser
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

    /**
     * create account from user
     * validate
     * 1. full name
     * 2. email
     * 3. password
     * 4. confirm password
     */
    private fun validateField(editText: EditText, textInputLayout: TextInputLayout, validationCondition: (String) -> Boolean, errorMessage: String): Boolean {
        val value = editText.text.toString()

        if (!validationCondition(value)) {
            textInputLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }
        textInputLayout.isErrorEnabled = false
        return true
    }

    private fun validateFullname(): Boolean {
        return validateField(
            binding.fullnameEt,
            binding.fullnameTil,
            { it.isNotEmpty() },
            "Fullname is required"
        )
    }

    private fun validateEmail(): Boolean {
        return validateField(
            binding.emailEt,
            binding.emailTil,
            { Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            "Email is required"
        )
    }

    private fun validatePassword(): Boolean {
        return validateField(
            binding.passwordEt,
            binding.passwordTil,
            { it.length >= 8 },
            "Password must be 8 characters long"
        )
    }

    private fun validateConfirmPassword(): Boolean {
        val password = binding.passwordEt.text.toString()
        val confirmPassword = binding.confirmPasswordEt.text.toString()

        return validateField(
            binding.confirmPasswordEt,
            binding.confirmPasswordTil,
            { confirmPassword.isNotEmpty() && confirmPassword == password },
            "Password is not matching!"
        )
    }


    //    This method is present but empty, suggesting it's not currently used for any click events.
    override fun onClick(view: View?) {
    }

    // This function is called when a view (like a text field) gains or loses focus.
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when(view.id) {
                R.id.fullnameEt -> {
                    if (hasFocus) {
                        if (binding.fullnameTil.isErrorEnabled) {
                            binding.fullnameTil.isErrorEnabled = false
                        }
                    }else {
                        validateFullname()
                    }
                }
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

