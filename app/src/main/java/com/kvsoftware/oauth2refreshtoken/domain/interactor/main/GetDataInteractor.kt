package com.kvsoftware.oauth2refreshtoken.domain.interactor.main

import com.kvsoftware.oauth2refreshtoken.data.rest.repository.DataRepository
import com.kvsoftware.oauth2refreshtoken.domain.interactor.base.BaseInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDataInteractor @Inject constructor(private val dataRepository: DataRepository) :
    BaseInteractor<Any, GetDataInteractor.Params>() {

    override suspend fun invoke(params: Params): Any {
        return withContext(Dispatchers.IO) {
            dataRepository.getData()
        }
    }

    class Params

}