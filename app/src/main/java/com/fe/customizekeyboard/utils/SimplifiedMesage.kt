package com.fe.customizekeyboard.utils

import org.json.JSONException
import org.json.JSONObject

object SimplifiedMesage {
    fun get(stringMessage: String): HashMap<String, String> {
        val messages = HashMap<String, String> ()
        val jsonObject= JSONObject(stringMessage)

//        try {
//            val jsonMessage = jsonObject.getJSONObject(("message")
//            jsonMessage.keys().forEach{ messages [it] = jsonMessage.getString() }
//        } catch(e: JSONException) {
//            messages["message"] = jsonObject.getString("message")
//        }

        return messages
    }

}