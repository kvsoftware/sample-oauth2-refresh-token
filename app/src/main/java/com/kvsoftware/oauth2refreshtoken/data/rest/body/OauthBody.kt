package com.kvsoftware.oauth2refreshtoken.data.rest.body

import com.google.gson.annotations.SerializedName

data class OauthBody(
    @SerializedName("username") val username: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("grant_type") val grantType: String
) {
    companion object {
        const val GRANT_TYPE_PASSWORD = "password"
        const val GRANT_TYPE_REFRESH = "refresh_token"
    }
}