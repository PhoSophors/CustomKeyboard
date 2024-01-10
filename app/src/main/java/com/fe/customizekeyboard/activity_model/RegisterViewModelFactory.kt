//package com.fe.customizekeyboard.activity_model
//
//import android.app.Application
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.fe.customizekeyboard.repository.AuthRespository
//import java.security.InvalidParameterException
//
//class RegisterViewModelFactory(private val authRespository: AuthRespository, private val application: Application): ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
//            return RegisterViewModel(authRespository, application) as T
//        }
//
//        throw InvalidParameterException("Unable to contractor RegisterViewModel")
//    }
//}