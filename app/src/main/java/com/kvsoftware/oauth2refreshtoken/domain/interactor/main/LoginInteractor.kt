package com.kvsoftware.oauth2refreshtoken.domain.interactor.main

import com.kvsoftware.oauth2refreshtoken.data.rest.body.OauthBody
import com.kvsoftware.oauth2refreshtoken.data.rest.repository.OauthRepository
import com.kvsoftware.oauth2refreshtoken.data.rest.response.OauthResponse
import com.kvsoftware.oauth2refreshtoken.domain.interactor.base.BaseInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val oauthRepository: OauthRepository) :
    BaseInteractor<OauthResponse, LoginInteractor.Params>() {

    override suspend fun invoke(params: Params): OauthResponse {
        return withContext(Dispatchers.IO) {
            oauthRepository.token(
                OauthBody(
                    username = params.username,
                    password = params.password,
                    grantType = OauthBody.GRANT_TYPE_PASSWORD
                )
            )
        }
    }

    class Params(val username: String, val password: String)

}