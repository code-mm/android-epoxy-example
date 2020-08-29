package com.ms.app.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.ms.app.common.BaseRepository
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Timber.plant(DebugTree())
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    val baseRepository by lazy {
        BaseRepository()
    }

    companion object {
        var INSTANCE: App? = null
    }
}