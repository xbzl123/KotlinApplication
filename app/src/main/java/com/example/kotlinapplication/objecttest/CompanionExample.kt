package com.example.kotlinapplication.objecttest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CompanionExample {
    val name = "jack"
    fun talk(){
        println("I am a object!"+"my name is "+ name)
    }
}
//协程切换线程
object AsyncTaskAlternative {
     fun <T> doInBackground(processing: () -> T?, callback: (data: T?) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            // do long running task here
            val result = processing.invoke()
            withContext(Dispatchers.Main) {
                // change the thread to main and execute the callback
                callback.invoke(result)
            }
        }
    }
}