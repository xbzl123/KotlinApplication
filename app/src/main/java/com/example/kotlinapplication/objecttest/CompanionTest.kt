package com.example.kotlinapplication.objecttest

import com.example.kotlinapplication.ActionInterface
import com.example.kotlinapplication.objecttest.CompanionExample.talk
import com.example.kotlinapplication.objecttest.CompanionTest.Companion.getContent
//import com.example.kotlinapplication.objecttest.Talker.talk

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
object Talker{
     fun talk(){

    }
}
fun main() {
    println(getContent())
    talk()
}