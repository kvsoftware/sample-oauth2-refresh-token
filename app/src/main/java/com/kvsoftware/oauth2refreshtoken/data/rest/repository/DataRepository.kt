package com.kvsoftware.oauth2refreshtoken.data.rest.repository

import com.kvsoftware.oauth2refreshtoken.data.rest.RestClient
import com.kvsoftware.oauth2refreshtoken.data.rest.service.DataService

class DataRepository(private val restClient: RestClient) {
    suspend fun getData(): Any {
        return restClient.createService(DataService::class.java).getData()
    }
}