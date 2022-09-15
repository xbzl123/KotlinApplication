package com.example.kotlinapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * ViewModel
 * @author longyanghe
 * @date 2022-02-25
 */
class ViewModel: ViewModel() {
    fun asynWork(processing:()->String,callback:(String)->Unit){
        viewModelScope.launch (Dispatchers.IO){
            val result = processing.invoke()
            withContext(Dispatchers.Main){
                callback.invoke(result)
            }
        }
    }
}