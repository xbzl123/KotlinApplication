package com.example.kotlinapplication

import com.example.kotlinapplication.objecttest.AsyncTaskAlternative
import getDeviceInfo
import java.util.*
import kotlin.properties.Delegates

fun main(args:Array<String>) {
    var demo = Demo("")
    demo.speakSomething("你好，世界")
    demo.sayHi("Kotlin",32)
    demo.showArray(demo.array)
    val names:Array<String> = arrayOf("jack","lus","Alex")
    demo.printArrays(names)
    println("\n"+"\t"+demo.area(40,25))

    println("the result of login is " + demo.login("admin","12345","is"))

    val person = Person(Gender.MAN)
    person.name = "Love"
    println(person.name)

    var device = Device("device1",1,"12-56-98-78-98-98")
    DevicePools.INSTANCE.insertDevice(device)

    println("the size of List is "+ getDeviceInfo(0).name)

    testLazy()

    println("the max value is "+ getMax(35,46))

    testForLooper()

    var method:(Int,Int)->Int
    method = actionPlus()
    println("===the plus result of two value is "+method.invoke(23,56))

    var method1:(Int,Int)->Int
    method1 = actionReduce()
    println("===the reduce result of two value is "+method1.invoke(65,44))
    action(5){ true }
    actionNotParamter(5) {}

    val d = ::b
    d.invoke(10)

    testReactiveScopeFun()

    val p = p()
    println(p.test)
    println(p.test)

    val o = O()
    o.name = "frist"
    o.name = "second"

    o.checkContent = "I like my cat."
    o.checkContent = "I kill my cat."
    o.checkContent = "I raise my cat."

    println("${o.checkContent}")

    val user:User1 = User1(mapOf("name" to "Kotlin","age" to 32))
    println(user.name)


    //抽象类
    var plane = Plane()
    plane.flyBy()

    AsyncTaskAlternative.doInBackground(
        {
            val result = "123456"
            println("I will get the winner11")
            result
        },{
            println("I will get the winner22")
            if (it.equals("123456")){
                println("I will get the winner")
            }
        }
    )

    val clother = Clother("male",42,"cotton","nature")
    val gender = clother.gender
    val size = clother.size
    val compose = clother.compose
    val style = clother.style
    println("the info of clother is gender :"+gender+",size :"+size+",compose :"+compose+",sytle :"+ style)
}

data class Clother(var gender:String,var size:Int,var compose:String,var style:String)

//作用域函数
fun testReactiveScopeFun(){
    var str = "Hello"

    str.let {
//        it.substring(3)
        it.replace("o","0000")
    }.apply {
        println("let the length is " + this.length)
    }

    str.run {
        this.substring(2)
    }.apply {
        println("run the length is " + this.length)
    }

    var person = Person(Gender.WOMAN)
    person.name = "mao"
    person.age = 26
    person.addr = "china"
    person.let {
        it.moveToOtherCity("Japan")
        it.agePlus()
    }.apply {
        println("the person name is ${person.name}, address is ${person.addr}, age is "+person.age)
    }


    val temps = mutableListOf("one","two","three","four","five","six")
    temps.map { it.length }.filter { it > 3 }.let(::println)


}
fun b(day:Int){
    println("how many day gone? " + day)
}

fun action(frist:Int,callback:(Int)->Boolean){
    if(frist.equals(5)){
        if (callback(frist))
        println("===返回的 true")
    }else{
        println("===返回的 false")
    }
}

fun actionNotParamter(value: Int,callback:(Int)->Unit)
{
    if (value.equals(5)){
        println("===返回的 actionNotParamter = "+callback(value))
    }
}
fun actionPlus(): (Int,Int)->Int{
    return {j,i-> i+j}
}

fun actionReduce():(Int,Int)->Int{
    return {i,j->i-j}
}

inline fun <reified T> ifPrintTypeisMatch(item : Any){
    if(item is T){
        print(item)
    }
}

fun getMax(a:Int,b:Int):Int{
    return if (a>b) a else b
}

fun testForLooper(){
    val range = 0..10
    for (i in range){
        testWhen(i)
//        println(i)
    }
}
fun testWhen(value:Int){
    when(value){
        2->{
            println(value)
        }
        3,6->{
            println(value)
        }
    }
}
fun testLazy() {

    val list = listOf(1, 2, 3, 4).asSequence()
    val result = list.map{
        println("Map = $it")
        it * 2
    }
    .filter {
        println("Filter = $it")
        it % 3 == 0
    }

    val find = (0..10).map { it * 2 }.filter { it > 8 }.find {
        it % 3 == 0
    }

    println(result.first())//只取集合的第一个元素

}

//延迟属性 委托
class p{
    val test:String by lazy {
        println("test lazy")//第一次调用才执行
        "Hello"
    }
}

//可观察属性 委托
class O{
    //这个变量有变化就检测到
    var name:String by Delegates.observable("<no name>"){
        property, oldValue, newValue ->
            println("$oldValue->$newValue")
    }
    //这个变量有变化不符合规则就拦截,不进行修改
    var checkContent:String by Delegates.vetoable("I love my home"){
        property, oldValue, newValue ->
        if (newValue.contains("kill")){
            false
        }
        true
    }
}
//属性映射 委托
class User1(map: Map<String,Any>){
    val name:String by map
    val age:Int by map
}


class Demo constructor(content: String?) {
    var cont:String? = ""

//        init {
//            cont = content
//        }

//    set(value) {
//        cont = value
//    }

    fun speakSomething(content:String?){
        cont = content
        println("the value is $cont")
    }

    var array:Array<Int> = arrayOf(1,2,3,4,5)
    fun showArray(array: Array<Int>){
        for (i in array){
            println(i)
        }
    }

    fun <T> printArrays(arr:Array<T> ){
        println(Arrays.toString(arr))
    }

    fun area(width:Int,heigth:Int):Int = width * heigth

    fun sayHi(name:String = "World",age:Int) = println("Hi : $name,your age is $age .")

    fun login(username:String,pwd:String,illegalStr: String):Boolean{
        fun validate1(pwd: String):Boolean {
            if (!"".equals(illegalStr) && "admin".equals(username) && pwd.equals("12345")){
                return true
            }
            return false
        }
        return validate1(pwd)
    }

}
