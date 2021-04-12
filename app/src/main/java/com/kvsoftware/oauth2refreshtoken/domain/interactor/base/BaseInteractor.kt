package com.kvsoftware.oauth2refreshtoken.domain.interactor.base

abstract class BaseInteractor<Type, in Params> where Type : Any? {
    abstract suspend operator fun invoke(params: Params): Type
}