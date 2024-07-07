package com.example.biometricauthdemo.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageInterceptor @Inject constructor() : Interceptor {

    companion object {
        private const val ACCEPT_LANGUAGE_HEADER_KEY = "Accept-Language"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        val language = Locale.getDefault().language

        requestBuilder.addHeader(ACCEPT_LANGUAGE_HEADER_KEY, language)
        val request = requestBuilder.build()

        return chain.proceed(request)

    }
}