package com.kvsoftware.oauth2refreshtoken.data.rest

import android.util.Log
import com.kvsoftware.oauth2refreshtoken.data.rest.body.OauthBody
import com.kvsoftware.oauth2refreshtoken.data.rest.service.OauthService
import com.kvsoftware.oauth2refreshtoken.data.sharepref.SharedPreferences
import com.kvsoftware.oauth2refreshtoken.domain.helper.ConstantHelper
import com.kvsoftware.oauth2refreshtoken.domain.helper.GsonHelper
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RestClient constructor(private val sharedPreferences: SharedPreferences, baseUrl: String) {

    companion object {
        private const val TAG = "RestClient"

        // OkHttpClient configuration
        private const val HTTP_READ_TIMEOUT = 10000L
        private const val HTTP_CONNECT_TIMEOUT = 6000L

        // Headers
        private const val HTTP_HEADER_ACCEPT_LANGUAGE = "Accept-Language"
        private const val HTTP_HEADER_USER_AGENT = "User-Agent"
        private const val HTTP_HEADER_AUTHORIZATION = "Authorization"

        // Bearer
        private const val HTTP_BEARER = "Bearer"
        private const val OAUTH_REFRESH_TOKEN_RETRY_LIMIT = 3
    }

    // Initialize HTTP Logger interceptor
    private val restLogger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Initialize Authenticator
    private val restAuthenticator = object : Authenticator {
        private var retryCount = 0

        // This method will be called when got 401 unauthorized error
        override fun authenticate(route: Route?, response: Response): Request? {
            retryCount++
            refreshOauthToken(response.request, retryCount)
            return null
        }

        private fun refreshOauthToken(currentRequest: Request?, currentRetry: Int): Request? {
            return synchronized(this) {
                Log.i(TAG, "Refresh the oauth token : $currentRetry")

                if (currentRetry > OAUTH_REFRESH_TOKEN_RETRY_LIMIT) {
                    Log.i(TAG, "Retry limit reached")
                    retryCount = 0

                    // Do some action here when retry count reached the limit
                    // Put the code here to force user to go back to login page
                    //
                    // Example
                    // EventBus.getDefault().post(UnAuthorizedEvent())

                    return null
                }

                // Refresh the oauth token with current refresh token
                val response = createService(OauthService::class.java)
                    .token2(
                        OauthBody(
                            refreshToken = sharedPreferences.refreshToken,
                            grantType = OauthBody.GRANT_TYPE_REFRESH
                        )
                    ).execute()

                if (!response.isSuccessful) {
                    return null
                }

                val responseBody = response.body()
                if (responseBody == null) {
                    return null
                }

                Log.i(TAG, "Refresh the oauth token complete")
                Log.i(TAG, "Access token : ${responseBody.accessToken}")
                Log.i(TAG, "Refresh token : ${responseBody.refreshToken}")

                // Update new access token and refresh token
                sharedPreferences.accessToken = responseBody.accessToken
                sharedPreferences.refreshToken = responseBody.refreshToken

                retryCount = 0

                // Re-request with new access token and refresh token
                currentRequest
            }
        }
    }

    // Initialize OkHttp
    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        .authenticator(restAuthenticator)
        .addInterceptor(restLogger)
        .addInterceptor {
            val request = it.request()
            val newRequest = request.newBuilder()
                .addHeader(HTTP_HEADER_ACCEPT_LANGUAGE, Locale.getDefault().language)
                .addHeader(HTTP_HEADER_USER_AGENT, ConstantHelper.USER_AGENT)
            sharedPreferences.accessToken?.let { http ->
                newRequest.addHeader(HTTP_HEADER_AUTHORIZATION, "$HTTP_BEARER $http")
            }
            it.proceed(newRequest.build())
        }
        .build()

    // Initialize Retrofit
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