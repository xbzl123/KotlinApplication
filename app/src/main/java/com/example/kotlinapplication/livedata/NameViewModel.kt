package com.example.kotlinapplication.livedata

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinapplication.ThreadUtils.mainThread
import com.example.kotlinapplication.coroutine.HttpRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception

fun simpleFlow() = flow {
    Log.e("simpleFlow","simpleFlow start>>>>>>>>>>>")
    for (i in 0..12){
        delay(500)
        emit(i)
    }
}
class NameViewModel: ViewModel() {
    val currentName:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    val currentFlow = MutableStateFlow("")

    val httpRepository = HttpRepository("https://www.so.com/")

    fun HttpRequest(){
        viewModelScope.launch {
            val makeHttpRequest = try {
                httpRepository.makeHttpRequest()
            }catch (e:Exception){
                com.example.kotlinapplication.coroutine.Result.Error(Exception(""))
            }
            Log.e("makeHttpRequest","makeHttpRequest = "
            +",ismainthread = "+ (Looper.myLooper() == Looper.getMainLooper()) )
            when(makeHttpRequest){
                is com.example.kotlinapplication.coroutine.Result.Success<String>->{
                    Log.e("makeHttpRequest","result = "+makeHttpRequest.data)
                }
                else ->{}
            }

        }
    }

    fun runIOProcess(process: (count:Int)->Int,count:Int){
        viewModelScope.launch {
            httpRepository.runIOThread(process,count)
        }
    }

}