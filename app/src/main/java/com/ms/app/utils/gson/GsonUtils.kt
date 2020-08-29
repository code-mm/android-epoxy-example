package com.bdlbsc.doper.utils.gson

import com.google.gson.Gson
import java.lang.reflect.Type

object GsonUtils {
    private val gson = Gson()
    fun toJson(`object`: Any?): String {
        return gson.toJson(`object`)
    }

    fun <T> fromJson(json: String?, classOfT: Class<T>?): T {
        return gson.fromJson(json, classOfT)
    }

    fun <T> fromJson(json: String?, typeOfT: Type?): T {
        return gson.fromJson(json, typeOfT)
    }
}