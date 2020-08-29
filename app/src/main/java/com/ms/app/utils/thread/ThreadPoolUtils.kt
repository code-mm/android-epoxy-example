package com.bdlbsc.doper.utils.thread

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author maohuawei created in 2018/10/8
 */
object ThreadPoolUtils {
    /**
     * 线程池
     *
     *
     * 常规方法
     * private static ExecutorService singleThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
     * ----------
     * 线程优化
     * 获取CPU核心数
     * java Runtime.getRuntime().availableProcessors()
     *
     *
     * 核心线程数
     * CPU核心数+1
     *
     *
     * 最大线程数
     * CPU核心数*2+1
     */
    var singleThreadExecutor: ExecutorService =
        ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() + 1,
            Runtime.getRuntime().availableProcessors() * 2 + 1,
            0L,
            TimeUnit.MILLISECONDS,
            LinkedBlockingQueue()
        )

    /**
     * 实例化Handler
     */
    private val handler = Handler(Looper.getMainLooper())

    /**
     * 调度到UI线程
     *
     * @param runnable
     */
    fun runOnMainThread(runnable: Runnable?) {
        handler.post(runnable)
    }

    /**
     * 工作线程
     *
     * @param runnable
     */
    fun runSubThread(runnable: Runnable?) {
        if (!singleThreadExecutor.isShutdown) {
            singleThreadExecutor.execute(runnable)
        }
    }

    /**
     * 关闭线程池
     */
    fun kill() {
        if (!singleThreadExecutor.isShutdown) {
            singleThreadExecutor.shutdown()
        }
    }
}