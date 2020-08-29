package com.bdlbsc.doper.utils.apk

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.ms.app.app.App.Companion.INSTANCE

class ApkUtils {
    val appName: String?
        get() {
            try {
                val packageManager =
                    INSTANCE!!.applicationContext.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    INSTANCE!!.applicationContext.packageName, 0
                )
                val labelRes = packageInfo.applicationInfo.labelRes
                return INSTANCE!!.applicationContext.resources.getString(labelRes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    val versionCode: String
        get() {
            try {
                val packageManager =
                    INSTANCE!!.applicationContext.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    INSTANCE!!.applicationContext.packageName, 0
                )
                return packageInfo.versionCode.toString() + ""
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return 0.toString() + ""
        }

    val versionName: String?
        get() {
            try {
                val packageManager =
                    INSTANCE!!.applicationContext.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    INSTANCE!!.applicationContext.packageName, 0
                )
                return packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

    fun isPackageInstalled(s: String?): Boolean {
        if (s == null || "" == s) return false
        var info: ApplicationInfo? = null
        return try {
            info = INSTANCE!!.applicationContext.packageManager
                .getApplicationInfo(s, 0)
            info != null
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun getMeta(s: String?): String? {
        try {
            val appInfo =
                INSTANCE!!.applicationContext.packageManager
                    .getApplicationInfo(
                        INSTANCE!!.applicationContext.packageName,
                        PackageManager.GET_META_DATA
                    )
            return appInfo.metaData.getString(s)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    val isDebug: Boolean
        get() {
            try {
                val info =
                    INSTANCE!!.applicationContext.applicationInfo
                return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

    val packageName: String?
        get() {
            try {
                val appInfo =
                    INSTANCE!!.applicationContext.packageManager
                        .getApplicationInfo(
                            INSTANCE!!.applicationContext.packageName,
                            PackageManager.GET_META_DATA
                        )
                return appInfo.packageName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

    companion object {
        val instance = ApkUtils()
    }
}