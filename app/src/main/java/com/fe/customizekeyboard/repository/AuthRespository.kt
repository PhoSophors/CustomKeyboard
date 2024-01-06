//package com.fe.customizekeyboard.repository
//
//import com.fe.customizekeyboard.data.RequestStatus
//import com.fe.customizekeyboard.data.UniqueEmailValidateResponse
//import com.fe.customizekeyboard.data.ValidateEmailBody
//import com.fe.customizekeyboard.utils.APIConsumer
//import com.fe.customizekeyboard.utils.SimplifiedMesage
//import kotlinx.coroutines.flow.flow
//
//class AuthRespository (val consumer: APIConsumer) {
//    fun validateEmailAddress(body: ValidateEmailBody) = flow {
//        emit(RequestStatus.Waiting)
//        val responce = consumer.validateEmailAddress(body)
//
//        if (responce.isSuccessful) {
//            emit((RequestStatus.Success(responce.body()!!)))
//        } else {
//            emit(RequestStatus.Error(SimplifiedMesage.get(responce.errorBody()!!.byteStream().reader().readText())))
//        }
//    }
//}