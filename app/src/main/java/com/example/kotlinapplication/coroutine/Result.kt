package com.example.kotlinapplication.coroutine

import java.lang.Exception

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * Result
 * @author longyanghe
 * @date 2022-03-24
 */
sealed class Result<out T>{
    data class Success<out T>(val data:T):Result<T>()
    data class Error(val e:Exception):Result<Nothing>()
}
