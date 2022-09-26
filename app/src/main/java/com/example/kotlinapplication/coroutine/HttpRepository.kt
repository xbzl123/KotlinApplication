package com.example.kotlinapplication.coroutine

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * HttpRepository
 * 协程切换线程
 * @author longyanghe
 * @date 2022-03-24
 */
class HttpRepository(val url:String) {
    suspend fun makeHttpRequest():Result<String>{
        return withContext(Dispatchers.IO){
            val url = URL(url)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.run {
                BufferedReader(InputStreamReader(inputStream)).useLines {
                    val stringBuilder = StringBuilder()
                    it.forEach {
                        stringBuilder.append(it)
                    }
                    return@withContext Result.Success(stringBuilder.toString())
                }
            }
            return@withContext Result.Error(Exception("404"))
        }
    }

        suspend fun <T> runIOThread(process:(count:Int)->T,count:Int){
            withContext(Dispatchers.IO){
                process.invoke(count)
            }
        }

}