package com.kvsoftware.oauth2refreshtoken.data.rest.response

import com.google.gson.annotations.SerializedName

data class OauthResponse(
    @SerializedName("access_token") var accessToken: String,
    @SerializedName("token_type") var tokenType: String,
    @SerializedName("expires_in") var expiresIn: Long,
    @SerializedName("refresh_token") var refreshToken: String,
    @SerializedName("id_token") var idToken: String,
    @SerializedName("created_at") var createdAt: Long
)