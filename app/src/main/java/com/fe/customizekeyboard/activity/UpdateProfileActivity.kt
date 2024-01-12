package com.fe.customizekeyboard.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.fe.customizekeyboard.databinding.ActivityUpdateProfileBinding
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val updateButton: Button = binding.updateeButton

        binding.updateeButton.setOnClickListener {
            val updatedFullName = binding.fullnameTil.editText?.text.toString()
            val updatedEmail = binding.emailTil.editText?.text.toString()

            // Assuming you have a function to send the confirmation code
            sendConfirmationCode(updatedEmail, updatedFullName)
        }

        binding.backUpdateProfileBtn.setOnClickListener {
            // Instead of starting a new instance of UpdateProfileActivity, just finish the current one
            finish()
        }
    }

    private fun sendConfirmationCode(email: String, fullName: String) {
        // Generate a 6-digit code (you can implement your own logic here)
        val confirmationCode = generateSixDigitCode()

        // Send the code to the user's email (you need to implement this part)
        sendCodeToEmail(email, confirmationCode)

        // Navigate to the VerifyGmailActivity
//        val intent = Intent(this, VerifyGmailActivity::class.java)
        intent.putExtra("CONFIRMATION_CODE", confirmationCode)
        intent.putExtra("FULL_NAME", fullName)
        intent.putExtra("EMAIL", email)
        startActivity(intent)
    }

    // Implement your own logic for generating a 6-digit code
    private fun generateSixDigitCode(): String {
        return (100000..999999).random().toString()
    }

    // Implement your own logic for sending the code to the user's email
    private fun sendCodeToEmail(email: String, code: String) {
        // Initialize Firebase Auth
        val auth = FirebaseAuth.getInstance()

        // Create the ActionCodeSettings
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://your-app-url.com/verify") // Set your app's URL for handling the verification
            .setHandleCodeInApp(true)
            .setAndroidPackageName(
                "com.your.package.name",
                true, /* installIfNotAvailable */
                "12" /* minimumVersion */
            )
            .build()

        // Send verification email
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Verification email sent successfully
                    // You can show a message to the user or handle it accordingly
                } else {
                    // Handle the error
                    val exception = task.exception
                    // You can log the exception or show an error message to the user
                }
            }
    }
}
