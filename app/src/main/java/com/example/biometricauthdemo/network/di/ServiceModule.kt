package com.example.biometricauthdemo.network.di

import com.example.biometricauthdemo.BuildConfig
import com.example.biometricauthdemo.DeviceManager
import com.example.biometricauthdemo.network.ApiService
import com.example.biometricauthdemo.network.LanguageInterceptor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private const val TIMEOUT_DURATION = 2L


    @[Provides Singleton]
    fun provideJacksonObjectMapper() = jacksonObjectMapper()


    @[Provides Singleton]
    fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }

    @[Provides Singleton]
    fun provideOkHttpClient(
        languageInterceptor: LanguageInterceptor,
        okHttpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_DURATION, TimeUnit.MINUTES)
            .readTimeout(TIMEOUT_DURATION, TimeUnit.MINUTES)
            .writeTimeout(TIMEOUT_DURATION, TimeUnit.MINUTES)
            .addInterceptor(languageInterceptor)
            .addInterceptor(okHttpLoggingInterceptor)
            .build()
    }

    @[Provides Singleton]
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        jacksonObjectMapper: ObjectMapper,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(if (DeviceManager.checkTheDeviceIsEmulator()) BuildConfig.EMULATOR_BASE_URL else BuildConfig.PHYSICAL_DEVICE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper))
            .build()
    }

    @[Provides Singleton]
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create()
    }
}