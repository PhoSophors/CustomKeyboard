package com.fe.customizekeyboard.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fe.customizekeyboard.R
import com.fe.customizekeyboard.databinding.ActivityRegisterBinding


class RegisterActivity:AppCompatActivity (), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var binding: ActivityRegisterBinding
    private  lateinit var mBinding:ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater) ;
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root);

        mBinding.fullNameEt.onFocusChangeListener = this
        mBinding.emailEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.confirmPasswordEt.onFocusChangeListener = this
    }

//    Call this function to check the validity of the full name field's input.
    private fun validateFullName (): Boolean {
        var errorMessage: String? = null
        val value: String  = mBinding.fullNameEt.text.toString() // Gets the text entered in the full name field.
        if (value.isEmpty()) { // If empty, sets an error message
            errorMessage = "Full name is required"
        }

        if (errorMessage != null) { //If an error message was set, it's displayed.
            mBinding.fullNameTil.apply {// Uses a Kotlin apply block to configure the text input layout.
                isErrorEnabled = true // Enables error display for the layout.
                error = errorMessage // Sets the specific error message to display.
            }
        }
        return errorMessage == null //  Returns true if validation passed (no error), false if an error occurred.
}

//    Call this function to check the validity of the email field's input.
    private fun validateEmail (): Boolean {
        var errorMessage: String? = null
        val value = mBinding.emailEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage = "Email address is invalid"
        }

        if (errorMessage != null) {
            mBinding.emailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

//    Call this function to check the validity of the password field's input.
    private fun validatePassword ():Boolean {
        var errorMessage: String? = null
        val value = mBinding.passwordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 8){ //Checks if the password is shorter than 8 characters.
            errorMessage = "Password must be 8 character long"
        }

        if (errorMessage != null) {
            mBinding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

//    Call this function to check the validity of the confirm password field's input.
    private fun validateConfirmPassword ():Boolean {
        var errorMessage: String? = null
        val value = mBinding.confirmPasswordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirm password is required"
        } else if (value.length < 8){
            errorMessage = "Confirm password must be 6 character long"
        }

        if (errorMessage != null) {
            mBinding.confirmPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

//    Call this function to specifically check if the password and confirm password fields match.
    private fun validatePasswordAndConfirmPassword ():Boolean {
        var errorMessage: String? = null
        val password = mBinding.passwordEt.text.toString()
        val confirmPassword = mBinding.confirmPasswordEt.text.toString()
        if (password != confirmPassword) {
            errorMessage = "Confirm password doesn't match with password"
        }
        if (errorMessage != null) {
            mBinding.confirmPasswordTil.apply {
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
                R.id.fullNameEt -> {
                    if (hasFocus) {
                        if (mBinding.fullNameTil.isErrorEnabled) {
                            mBinding.fullNameTil.isErrorEnabled = false
                        }
                    }else {
                        validateFullName()
                    }
                }
                R.id.emailEt -> {
                    if (hasFocus) {
                        if (mBinding.emailTil.isErrorEnabled) {
                            mBinding.emailTil.isErrorEnabled = false
                        }
                    }else {
                        if (validateEmail()) {

                        }
                    }
                }
                R.id.passwordEt -> {
                    if (hasFocus) {
                        if (mBinding.passwordTil.isErrorEnabled) {
                            mBinding.passwordTil.isErrorEnabled = false
                        }
                    }else {
                      if (validatePassword() && mBinding.confirmPasswordEt.text!!.isEmpty() && validateConfirmPassword() &&
                              validatePasswordAndConfirmPassword()) {
                          if (mBinding.confirmPasswordTil.isErrorEnabled) {
                              mBinding.confirmPasswordTil.isCounterEnabled = false
                          }
                          mBinding.confirmPasswordTil.apply {
                              setStartIconDrawable(R.drawable.check_circle_24)
                              setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                          }
                      }
                    }
                }
                R.id.confirmPasswordEt -> {
                    if (hasFocus) {
                        if (mBinding.confirmPasswordTil.isErrorEnabled) {
                            mBinding.confirmPasswordTil.isErrorEnabled = false
                        }
                    }else {
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()){
                            if (mBinding.passwordTil.isErrorEnabled) {
                                mBinding.passwordTil.isCounterEnabled = false
                            }
                            mBinding.confirmPasswordTil.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
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