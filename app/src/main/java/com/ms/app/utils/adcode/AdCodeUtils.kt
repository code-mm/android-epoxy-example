package com.bdlbsc.doper.utils.adcode

import com.ms.app.app.App.Companion.INSTANCE
import java.io.BufferedReader
import java.io.InputStreamReader

object AdCodeUtils {
    @JvmStatic
    val adCode: String?
        get() {
            try {
                val inputReader = InputStreamReader(
                    INSTANCE!!.applicationContext.resources.assets
                        .open("adcode.dat")
                )
                val bufReader = BufferedReader(inputReader)
                var line: String? = ""
                var Result: String? = ""
                while (bufReader.readLine().also { line = it } != null) Result += line
                return Result
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
}