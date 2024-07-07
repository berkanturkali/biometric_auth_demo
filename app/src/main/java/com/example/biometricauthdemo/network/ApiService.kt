package com.example.biometricauthdemo.network

import com.example.biometricauthdemo.network.model.BaseResponseModel
import com.example.biometricauthdemo.network.model.LoginRequestModel
import com.example.biometricauthdemo.network.model.SignupRequestModel
import com.example.biometricauthdemo.network.model.TokenResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(Endpoints.SIGNUP_ENDPOINT)
    suspend fun signup(@Body body: SignupRequestModel): Response<BaseResponseModel<Any?>>

    @POST(Endpoints.LOGIN_ENDPOINT)
    suspend fun login(@Body body: LoginRequestModel): Response<BaseResponseModel<TokenResponseModel>>

}