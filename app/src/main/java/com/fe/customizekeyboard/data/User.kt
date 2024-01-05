package com.fe.customizekeyboard.data

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("_id") val id: String, val fullname : String, val emial: String)
