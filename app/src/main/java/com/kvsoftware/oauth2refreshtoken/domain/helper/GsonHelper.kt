package com.kvsoftware.oauth2refreshtoken.domain.helper

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import java.util.*

object GsonHelper {
    fun getGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                Date::class.java,
                JsonDeserializer<Date> { json, _, _ ->
                    if (json.asJsonPrimitive.isNumber) Date(json.asJsonPrimitive.asLong) else null
                })
            .create()
    }
}