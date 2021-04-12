package com.kvsoftware.oauth2refreshtoken.data.rest.service

import retrofit2.http.GET

interface DataService {

    // Todo - Customize API url path and response here
    @GET("")
    suspend fun getData(): Any
}