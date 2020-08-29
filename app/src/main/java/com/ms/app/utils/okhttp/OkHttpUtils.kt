package com.bdlbsc.doper.utils.okhttp

import com.ms.app.utils.okhttp.LogInterceptor
import okhttp3.*
import java.util.concurrent.TimeUnit


object OkHttpUtils {

    private var client: OkHttpClient? = null
    fun client(): OkHttpClient? {
        if (client == null) {
            client = OkHttpClient.Builder()
                .addInterceptor(LogInterceptor())
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
        }
        return client
    }


}