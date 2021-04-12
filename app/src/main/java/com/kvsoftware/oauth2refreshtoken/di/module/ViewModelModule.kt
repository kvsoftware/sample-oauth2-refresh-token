package com.kvsoftware.oauth2refreshtoken.di.module

import com.kvsoftware.oauth2refreshtoken.data.rest.RestClient
import com.kvsoftware.oauth2refreshtoken.data.rest.repository.DataRepository
import com.kvsoftware.oauth2refreshtoken.data.rest.repository.OauthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideOauthRepository(restClient: RestClient): OauthRepository {
        return OauthRepository(restClient)
    }

    @Provides
    fun provideDataRepository(restClient: RestClient): DataRepository {
        return DataRepository(restClient)
    }

}