//package com.fe.customizekeyboard.activity_model
//
//
//import android.app.Application
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.fe.customizekeyboard.data.User
//import com.fe.customizekeyboard.data.ValidateEmailBody
//import com.fe.customizekeyboard.repository.AuthRespository
//
//class RegisterViewModel(val authRespository: AuthRespository, val application: Application):ViewModel() {
//    private val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
//    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
//    private var isUnique: MutableLiveData<Boolean> = MutableLiveData<Boolean>() .apply { value = false }
//    private var user: MutableLiveData<User> = MutableLiveData()
//
//    fun getIsLoading(): LiveData<Boolean> = isLoading
//    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
//    fun getIsUnique(): LiveData<Boolean> = isUnique
//    fun getUser(): LiveData<User> = user
//
//    fun validateEmailAddress(body: ValidateEmailBody) {
//
//    }
//}