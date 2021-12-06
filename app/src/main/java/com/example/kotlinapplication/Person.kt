package com.example.kotlinapplication

class Person constructor(var gender:Gender) {
    var name:String = ""
        //value和field都是后台字段
    set(value) {
        field = when(gender){
            Gender.MAN->"jack.$value"
            Gender.WOMAN->"lucy.$value"
        }
    }

    var addr:String = ""
    var age:Int = 0

    fun moveToOtherCity(city:String){
        addr = city
    }

    fun agePlus(){
        age++
    }
}

enum class Gender {
    MAN,WOMAN
}
