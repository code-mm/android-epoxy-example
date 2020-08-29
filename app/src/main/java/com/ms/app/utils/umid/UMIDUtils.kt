package com.bdlbsc.doper.utils.umid

import android.content.Context
import com.ms.app.app.App.Companion.INSTANCE
import com.ms.app.utils.apk.SystemUtils
import com.bdlbsc.doper.utils.md5.MD5Utils.md5

/**
 * @author maohuawei created in 2018/12/17
 *
 *
 * UMID
 */
object UMIDUtils {
    @JvmStatic
    val umid: String
        get() {
            val sharedPreferences = INSTANCE!!.applicationContext
                .getSharedPreferences(
                    "com.chujian.module.mta.umid",
                    Context.MODE_PRIVATE
                )
            val umid = sharedPreferences.getString("umid", "")
            if (umid == null || umid == "") {
                val s = md5(SystemUtils.getSysInfo())
                sharedPreferences.edit().putString("umid", s).commit()
                return s
            }
            return umid
        }
}