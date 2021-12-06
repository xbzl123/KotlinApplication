package com.example.kotlinapplication

 open class User {
     var name:String? = "maike"
     var add = "earth"
         get() {
             return field + " nb";
         }
         set(value) {
             field =  "set " + value
         }
     constructor(name: String?, add: String) {
         this.name = name
         this.add = add
     }

 }