package com.kvsoftware.oauth2refreshtoken.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.name
    }

    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun initialize() {
    }

    fun login() {
        viewModelScope.launch {
            isLoading.postValue(true)
            isLoading.postValue(false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            isLoading.postValue(true)
            isLoading.postValue(false)
        }
    }

    fun getData() {
        viewModelScope.launch {
            isLoading.postValue(true)
            isLoading.postValue(false)
        }
    }

}