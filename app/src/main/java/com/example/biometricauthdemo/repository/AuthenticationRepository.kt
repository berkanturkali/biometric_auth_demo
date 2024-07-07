package com.example.biometricauthdemo.repository

import com.example.biometricauthdemo.Resource
import com.example.biometricauthdemo.network.model.BaseResponseModel
import com.example.biometricauthdemo.network.model.LoginRequestModel
import com.example.biometricauthdemo.network.model.SignupRequestModel
import com.example.biometricauthdemo.network.model.TokenResponseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body

interface AuthenticationRepository {

    suspend fun signup(body: SignupRequestModel): Flow<Resource<BaseResponseModel<Any?>>>

    suspend fun login(body: LoginRequestModel): Flow<Resource<BaseResponseModel<TokenResponseModel>>>
}