package com.fe.customizekeyboard.api.service

import com.fe.customizekeyboard.api.models.Keyboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Get all keyboard
    @GET("keyboards")
    fun loadData(): Call<List<Keyboard>>

    //get keyboard by id
    @GET("keyboards/{keyboard_id}")
    suspend fun getProductDetails(@Path("productId") keyboard_id: Int): Keyboard

}