package com.example.kotlinapplication

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * ThreadUtils
 * @author longyanghe
 * @date 2022-03-10
 */


object ThreadUtils {
    private val handler = Handler(Looper.getMainLooper())
    private val coreSize = Runtime.getRuntime().availableProcessors() + 1

    private val fix: ExecutorService = Executors.newFixedThreadPool(coreSize)
    private val cache: ExecutorService = Executors.newCachedThreadPool()
    private val single: ExecutorService = Executors.newSingleThreadExecutor()
    private val scheduled: ExecutorService = Executors.newScheduledThreadPool(coreSize)

    fun <T> T.mainThread(delayMillis: Long = 0, block: T.() -> Unit) {
        handler.postDelayed({
            block()
        }, delayMillis)
    }


    fun <T> T.singlePool(block: T.() -> Unit) {
        single.execute {
            block()
        }
    }

    fun <T> T.fixPool(block: T.() -> Unit) {
        fix.execute {
            block()
        }
    }

    fun <T> T.cachePool(block: T.() -> Unit) {
        cache.execute {
            block()
        }
    }
}