//package com.fe.customizekeyboard.utils
//
//import com.fe.customizekeyboard.data.UniqueEmailValidateResponse
//import com.fe.customizekeyboard.data.ValidateEmailBody
//import com.google.android.gms.common.api.Response
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//interface APIConsumer {
//    @POST("users/validate-unique-email")
//
//    suspend fun validateEmailAddress(@Body body: ValidateEmailBody) : Response<UniqueEmailValidateResponse>
//}