package com.example.kotlinapplication

class Device {
    var name:String = ""
    var id:Int = -1
    var macAddr:String = ""

    constructor(name: String, id: Int, macAddr: String) {
        this.name = name
        this.id = id
        this.macAddr = macAddr
    }
}

