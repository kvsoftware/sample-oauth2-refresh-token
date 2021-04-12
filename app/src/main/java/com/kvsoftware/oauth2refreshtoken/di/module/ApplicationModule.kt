package com.kvsoftware.oauth2refreshtoken.di.module

import android.content.Context
import com.kvsoftware.oauth2refreshtoken.data.rest.RestClient
import com.kvsoftware.oauth2refreshtoken.data.sharepref.SharedPreferences
import com.kvsoftware.oauth2refreshtoken.domain.helper.ConstantHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return SharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideRestClient(sharedPreferences: SharedPreferences): RestClient {
        return RestClient(sharedPreferences, ConstantHelper.BASE_URL)
    }

}