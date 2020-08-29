package com.bdlbsc.doper.utils.download

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.HttpURLConnection
import java.net.URL

object DownloadUtils {
    fun getURLimage(s: String?): Bitmap? {
        var bmp: Bitmap? = null
        try {
            val myurl = URL(s)
            // 获得连接
            val conn =
                myurl.openConnection() as HttpURLConnection
            conn.connectTimeout = 6000 //设置超时
            conn.doInput = true
            conn.useCaches = false //不缓存
            conn.connect()
            val `is` = conn.inputStream //获得图片的数据流
            bmp = BitmapFactory.decodeStream(`is`)
            `is`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bmp
    }
}