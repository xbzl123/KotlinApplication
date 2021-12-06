package com.example.kotlinapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.kotlinapplication.Picker.OnActionComplete
import com.example.kotlinapplication.livedata.NameViewModel
import java.security.Permissions
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(),ActionInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        vars(1,2,3,4,5)  // 输出12345
        // Example of a call to a native method
        val textView = findViewById<TextView>(R.id.sample_text)
        textView.text = stringFromJNI()

        val sampleText = findViewById<TextView>(R.id.sample2_text)

        val model:NameViewModel = NameViewModel()

        //建立观察者
        val nameObserver = Observer<String>{
            sampleText.setText(it)
        }
        //注册观察者
        model.currentName.observe(this,nameObserver)

        var device = Device("device1",1,"12-56-98-78-98-98")
        DevicePools.INSTANCE.insertDevice(device)
        var name by Delegates.observable("no-name"){
            property, oldValue, newValue->
                sampleText.setText(newValue)
        }
        name = device.name
        textView.setOnClickListener {v: View ->
            Toast.makeText(this,"test!!!",Toast.LENGTH_LONG).show()
            device.name = "device temp"
//            handlerPermission()
            model.currentName.value = "new name"
        }

        sampleText.setOnClickListener {
            Picker.showPicker(this)
        }
        val x = (1 shl 2)
        val y = (10 shr 2)
        Log.e("test","the result x is "+x)
        Log.e("test","the result y is "+y)
        val user:User
        var name1 = "jack"
        user = User("java","book")
//        user.name = name1
        val temp = object : HttpInterface {
            override fun onResult(msg: String?) {
                Log.e("test","the result msg is "+msg)
            }
        }

        temp.onResult(user.add)
        testEOSC()
        testArrays()
        val clother = Clother("male",51,"cotton","classic")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Picker.onActivityResult(requestCode, resultCode, data,object :OnActionComplete{
            override fun onSuccess(uri: List<Uri>) {
                Log.e("test","the result onSuccess is "+uri)

            }

            override fun onFailure() {
                Log.e("test","the result onFailure ")
            }

        })
    }
    private fun handlerPermission(){
        val storage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        val checkSelfPermission = ActivityCompat.checkSelfPermission(this,storage)
        if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){

        }else{
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,storage)){
                ActivityCompat.requestPermissions(this,arrayOf(storage),1)
//            }
        }

        val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) {

            val toString = it.toString()
            BitmapFactory.decodeFile(toString)
        }
//        val registerForActivityResult =
//            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
//                if (it != null){
//
//                }
//            }
        //使用androidx的函数
//        val registerForActivityResult =
//            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
//                if (it[android.Manifest.permission.WRITE_EXTERNAL_STORAGE]!!){
//                    Log.d("ActivityResultContracts","WRITE_EXTERNAL_STORAGE success")
//                }else{
//                    Log.d("ActivityResultContracts","WRITE_EXTERNAL_STORAGE fail")
//                }
//                if (it[android.Manifest.permission.CAMERA]!!){
//                    Log.d("ActivityResultContracts","CAMERA success")
//                }else{
//                    Log.d("ActivityResultContracts","CAMERA fail")
//                }
//            }
//
//        var permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA)
//        registerForActivityResult.launch(permissions)
    }

    private fun testArrays() {
        var string:List<String> = mutableListOf("a","b","c")
        var any:List<Any> = string
//        var string:MutableList<String> = mutableListOf("a","b","c")
//        var any:MutableList<Any> = string
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"you has permission !",Toast.LENGTH_LONG).show()
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    interface HttpInterface{
        fun onResult(msg:String?)
    }

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onProcess(action: String?) {
    }

    class Producer<out T>{
          fun produce(){
            println("produce = ====")
//            val clz = T::class.java
//            val mCreate = clz.getDeclaredConstructor()
//            mCreate.isAccessible = true
//            return mCreate.newInstance()
        }
    }

    class Consumer<in T>{
        fun consume(t:T){
            println("consume = ==="+t)
        }
    }
    fun testEOSC(){
        var products:List<out TextView> = ArrayList<Button>()
        //等同于List<? extends TextView> products = new ArrayList<Button>();
//    var consumer:List<in Button> = ArrayList<TextView>()//报错
        val producer:Producer<TextView> = Producer<Button>()
        producer.produce()

        val consumer:Consumer<Button> = Consumer<TextView>()
        consumer.consume(Button(this))
    }


    fun vars(vararg v:Int){
        for(vt in v){
            print(vt)
        }
    }
}