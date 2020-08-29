package com.bdlbsc.doper.utils.toast

import android.widget.Toast
import com.ms.app.app.App
import com.bdlbsc.doper.utils.thread.ThreadPoolUtils


object ToastUtils {
    private var toast: Toast? = null
    fun show(text: String?) {
        ThreadPoolUtils.runOnMainThread(Runnable {
            run {
                if (toast == null) {
                    toast = Toast.makeText(App.INSTANCE, text, Toast.LENGTH_LONG)
                } else {
                    toast!!.setText(text)
                }
                toast!!.show()
            }
        })
    }
}