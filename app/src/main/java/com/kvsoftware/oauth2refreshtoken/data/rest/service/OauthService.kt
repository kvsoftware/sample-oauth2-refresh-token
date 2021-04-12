package com.kvsoftware.oauth2refreshtoken.data.rest.service

import com.kvsoftware.oauth2refreshtoken.data.rest.body.OauthBody
import com.kvsoftware.oauth2refreshtoken.data.rest.response.OauthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface OauthService {
    @POST("oauth/token")
    suspend fun token(@Body oauthBody: OauthBody): OauthResponse

    @POST("oauth/token")
    fun token2(@Body oauthBody: OauthBody): Call<OauthResponse>
}