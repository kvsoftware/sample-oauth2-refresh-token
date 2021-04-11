package com.kvsoftware.oauth2refreshtoken.data.rest

import androidx.viewbinding.BuildConfig
import com.kvsoftware.oauth2refreshtoken.domain.helper.GsonHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient constructor(baseUrl: String) {

    companion object {
        private const val HTTP_READ_TIMEOUT = 10000L
        private const val HTTP_CONNECT_TIMEOUT = 6000L
    }

    // HTTP Logger
    private val restLogger = when (BuildConfig.DEBUG) {
        true -> HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        false -> HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(restLogger)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonHelper.getGson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

}