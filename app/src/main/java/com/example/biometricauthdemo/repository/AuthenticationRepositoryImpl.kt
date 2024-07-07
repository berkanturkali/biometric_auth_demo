package com.example.biometricauthdemo.repository

import com.example.biometricauthdemo.Resource
import com.example.biometricauthdemo.network.ApiService
import com.example.biometricauthdemo.network.ApiUtils
import com.example.biometricauthdemo.network.model.BaseResponseModel
import com.example.biometricauthdemo.network.model.LoginRequestModel
import com.example.biometricauthdemo.network.model.SignupRequestModel
import com.example.biometricauthdemo.network.model.TokenResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val service: ApiService
) : AuthenticationRepository {

    override suspend fun signup(body: SignupRequestModel): Flow<Resource<BaseResponseModel<Any?>>> {
        return ApiUtils.safeApiCall {
            service.signup(body)
        }
    }

    override suspend fun login(body: LoginRequestModel): Flow<Resource<BaseResponseModel<TokenResponseModel>>> {
        return ApiUtils.safeApiCall {
            service.login(body)
        }
    }
}