package com.bdlbsc.doper.utils.retrofit

import com.bdlbsc.doper.utils.okhttp.OkHttpUtils.client
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Retrofit 工具类
 */
object RetrofitUtils  {
    // 缓冲
    private val cache =
        HashMap<String, Retrofit>()

    fun getRetrofitClient(baseUrl: String): Retrofit {
        val cacheRetrofit = cache[baseUrl]
        return if (cacheRetrofit == null) {
            val builder = Retrofit.Builder()
            builder.client(client())
            builder.addConverterFactory(GsonConverterFactory.create())
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            builder.baseUrl(baseUrl)
            val retrofit = builder.build()
            cache[baseUrl] = retrofit
            retrofit
        } else {
            cacheRetrofit
        }
    }
}