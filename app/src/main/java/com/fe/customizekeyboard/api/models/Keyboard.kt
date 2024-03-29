package com.fe.customizekeyboard.api.models

data class Keyboard (
    val id: Int,
    val keyboard_name: String,
    val type: String,
    val image_url1: String,
    val image_url2: String,
    val description: String,
    val price: Double,
    val rate: Double
)

data class Keycaps (
    val id: Int,
    val keyboard_name: String,
    val type: String,
    val image_url1: String,
    val image_url2: String,
    val description: String,
    val price: Double,  // Assuming price is a Double for consistency
    val rate: Double    // Assuming rate is a Double for consistency
)




