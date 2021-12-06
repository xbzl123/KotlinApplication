package com.example.kotlinapplication.objecttest

import androidx.activity.result.ActivityResult
import com.example.kotlinapplication.ActionInterface

class CompanionTest {
    val content = "伴生对象可以访问我"

    companion object: ActionInterface {
        fun getContent():String{
            return CompanionTest().content
        }

        override fun onProcess(action: String?) {
        }
    }
}

fun main() {
    println(CompanionTest.getContent())
    CompanionExample.talk()
}