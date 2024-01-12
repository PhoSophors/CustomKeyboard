package com.fe.customizekeyboard.api.service

import com.fe.customizekeyboard.api.models.Keyboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET

interface ApiService {
    @GET("keyboards")
    fun loadData(): Call<List<Keyboard>>

}