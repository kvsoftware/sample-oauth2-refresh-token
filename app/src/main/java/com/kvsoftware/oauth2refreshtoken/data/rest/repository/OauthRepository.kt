package com.kvsoftware.oauth2refreshtoken.data.rest.repository

import com.kvsoftware.oauth2refreshtoken.data.rest.RestClient
import com.kvsoftware.oauth2refreshtoken.data.rest.body.OauthBody
import com.kvsoftware.oauth2refreshtoken.data.rest.response.OauthResponse
import com.kvsoftware.oauth2refreshtoken.data.rest.service.OauthService

class OauthRepository(private val restClient: RestClient) {
    suspend fun token(oauthBody: OauthBody): OauthResponse {
        return restClient.createService(OauthService::class.java).token(oauthBody)
    }
}