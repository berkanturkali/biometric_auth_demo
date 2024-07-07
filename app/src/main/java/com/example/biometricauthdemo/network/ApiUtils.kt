package com.example.biometricauthdemo.network

import android.util.Log
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.Resource
import com.example.biometricauthdemo.UiText
import com.example.biometricauthdemo.network.model.BaseResponseModel
import com.example.biometricauthdemo.network.model.ErrorResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

object ApiUtils {
    fun <ResponseType> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<BaseResponseModel<ResponseType>>
    ): Flow<Resource<BaseResponseModel<ResponseType>>> {
        return flow {
            try {
                emit(Resource.Loading())

                val response = withContext(dispatcher) { apiCall() }
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()))
                } else {
                    handleUnsuccessful(response.errorBody()?.parseError())
                }
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }
    private const val TAG = "ApiUtils"

    private suspend fun <T> FlowCollector<Resource<T>>.handleUnsuccessful(error: ErrorResponse?) {
        val message: String? = error?.message
        Log.i(TAG, "handleUnsuccessful: message = $message")
        emit(
            if (message != null) {
                Resource.Error(code = null, text = UiText.DynamicString(value = message))
            } else {
                Resource.Error(
                    code = null,
                    text = UiText.StringResource(resId = R.string.unknown_error_message)
                )
            }

        )
    }

    private suspend fun <T> FlowCollector<Resource<T>>.handleException(exception: Exception) {
        when (exception) {
            is ConnectException -> {
                emit(Resource.Error(text = UiText.DynamicString(exception.message)))
            }

            is SocketTimeoutException -> {
                emit(Resource.Error(text = UiText.StringResource(R.string.timeout_error_message)))
            }

            is TimeoutCancellationException -> {
                emit(Resource.Error(text = UiText.StringResource(R.string.timeout_error_message)))
            }

            is IOException -> {
                emit(
                    Resource.Error(
                        text = exception.localizedMessage?.let { message ->
                            UiText.DynamicString(message)
                        }
                            ?: UiText.StringResource(R.string.network_connection_error_message)
                    ))
            }

            is HttpException -> {
                val error = exception.response()?.errorBody()?.parseError()
                val message = error?.message
                emit(Resource.Error(code = exception.code(), text = UiText.DynamicString(message)))
            }

            is IllegalArgumentException -> {
                return
            }

            else -> {
                emit(Resource.Error(text = UiText.StringResource(R.string.unknown_error_message)))
            }
        }
    }
}

fun ResponseBody.parseError(): ErrorResponse? {
    val objectMapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    return try {
        objectMapper.readValue(string())
    } catch (exception: Exception) {
        null
    }
}
