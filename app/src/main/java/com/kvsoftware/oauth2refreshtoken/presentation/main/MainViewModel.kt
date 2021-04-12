package com.kvsoftware.oauth2refreshtoken.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvsoftware.oauth2refreshtoken.data.sharepref.SharedPreferences
import com.kvsoftware.oauth2refreshtoken.domain.interactor.main.GetDataInteractor
import com.kvsoftware.oauth2refreshtoken.domain.interactor.main.LoginInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val loginInteractor: LoginInteractor,
    private val getDataInInteractor: GetDataInteractor
) :
    ViewModel() {

    companion object {
        // Todo - Put your username and password here
        private const val USERNAME = ""
        private const val PASSWORD = ""
    }

    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val isLogged: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun initialize() {
        isLogged.postValue(sharedPreferences.accessToken != null)
    }

    fun login() {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val response = loginInteractor.invoke(LoginInteractor.Params(USERNAME, PASSWORD))
                sharedPreferences.apply {
                    accessToken = response.accessToken
                    refreshToken = response.refreshToken
                }
                isLogged.postValue(true)
            } catch (e: Exception) {
            }
            isLoading.postValue(false)
        }
    }

    fun logout() {
        isLoading.postValue(true)
        sharedPreferences.accessToken = null
        isLogged.postValue(false)
        isLoading.postValue(false)
    }

    fun getData() {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val response = getDataInInteractor.invoke(GetDataInteractor.Params())
            } catch (e: Exception) {

            }
            isLoading.postValue(false)
        }
    }

}