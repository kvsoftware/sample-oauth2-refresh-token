package com.kvsoftware.oauth2refreshtoken.data.sharepref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferences(context: Context) {

    companion object {
        private const val PREF_FAVORITES = "pref_favorites"
    }

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

}