package com.example.kotlinapplication.coroutine

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * HttpRepository
 * @author longyanghe
 * @date 2022-03-24
 */
class HttpRepository(val request:String) {
    suspend fun makeHttpRequest():Result<String>{
        return withContext(Dispatchers.IO){
            val url = URL(request)
            (url.openConnection() as HttpURLConnection).run {
                Log.e("makeHttpRequest","responseCode = "+responseCode)
                Log.e("makeHttpRequest","responseMessage = "+responseMessage)
                Log.e("makeHttpRequest","ismainthread = "+ (Looper.myLooper() == Looper.getMainLooper()))
                BufferedReader(InputStreamReader(inputStream)).useLines {
                    val stringBuilder = StringBuilder()
                    it.forEach {
                        stringBuilder.append(it)
                    }
                    return@withContext Result.Success(stringBuilder.toString())
                }
            }
            return@withContext Result.Error(Exception("123456"))
        }
    }

        suspend fun <T> runIOThread(process:(count:Int)->T,count:Int){
            withContext(Dispatchers.IO){
                process.invoke(count)
            }
        }

}