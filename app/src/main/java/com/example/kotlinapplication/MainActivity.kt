package com.example.kotlinapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.kotlinapplication.Picker.OnActionComplete
import com.example.kotlinapplication.livedata.NameViewModel
import com.example.kotlinapplication.livedata.simpleFlow
import com.example.kotlinapplication.objecttest.AsyncTaskAlternative
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

@InternalCoroutinesApi
class MainActivity : AppCompatActivity(),ActionInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mBinding = DataBindingUtil.inflate(layoutInflater,R.layout.activity_main,parent,false)
        setContentView(R.layout.activity_main)
        //可变参数
//        vars(1,2,3,4,5)  // 输出12345
        // Example of a call to a native method

        val model = NameViewModel()

        val liveDataTest = findViewById<TextView>(R.id.sample_text)
        liveDataTest.text = stringFromJNI()
        //建立观察者
        val nameObserver = Observer<String>{
            liveDataTest.setText(it)
        }
        //注册观察者
        model.currentName.observe(this,nameObserver)
        liveDataTest.setOnClickListener {
            model.currentName.value = "new name"
        }

        val liveDataTest1 = findViewById<TextView>(R.id.sample3_text)

        val flowTest = findViewById<TextView>(R.id.sample2_text)

        val fileTest = findViewById<TextView>(R.id.sample4_text)

        val networkTest = findViewById<TextView>(R.id.sample5_text)
        networkTest.setOnClickListener {
            model.HttpRequest()
        }

        val requestPermission = findViewById<TextView>(R.id.sample6_text)


        var device = Device("device1",1,"12-56-98-78-98-98")
        DevicePools.insertDevice(device)
        var name by Delegates.observable("no-name"){
            property, oldValue, newValue->
            liveDataTest1.setText(newValue)
        }

        liveDataTest1.setOnClickListener {
//            name = device.name
            startActivity(Intent(this,TestActivity::class.java))
        }
        requestPermission.setOnClickListener {
            handlerPermission()

        }
        flowTest.setOnClickListener {v: View ->
            Toast.makeText(this,"test!!!",Toast.LENGTH_LONG).show()
            Log.e("simpleFlow","testFlowIsCode")
            model.runIOProcess({
                testFlowIsCode(it)
//                flow<Int> {
//                    for (i in 0..it){
//                        delay(500)
//                        emit(i)
//                    }
//                }.collect(object :FlowCollector<Int>{
//                    override suspend fun emit(value: Int) {
//                        Log.e("runIOProcess","it = "+value)
//                    }
//                })
                return@runIOProcess 0
            },10)
        }

        fileTest.setOnClickListener {
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


        AsyncTaskAlternative.doInBackground(
            {
                val result = "123456"
                println("I will get the winner11")
                Log.e("test","the frist is "+(Looper.getMainLooper() == Looper.myLooper()))
                result

            },{
                println("I will get the winner22")
                if (it.equals("123456")){
                    println("I will get the winner")
                    Log.e("test","the second msg is "+(Looper.getMainLooper() == Looper.myLooper()))
                }
            }
        )

        xml2Java<String>("D:\\KotlinApplication\\app\\src\\main\\assets\\test1.xml")

    }

    fun doInBackgroundStandard(processing: () -> String?,callback: (data: String?) -> Unit){
        lifecycleScope.launch(Dispatchers.IO){
            val result = processing.invoke()
            withContext(Dispatchers.Main){
                callback.invoke(result)
            }
        }
    }

    fun getCountryLocation() {
        val geocoder = Geocoder(this, Locale.US)
        //坐标转成位置类
        val fromLocation = geocoder.getFromLocationName("NewYork",10)
        val lastLocation = fromLocation.get(0)
    }

    override fun onResume() {
        super.onResume()
        val stringArray = resources.obtainTypedArray(R.array.counties_pre_phone)
        for (i in 0..stringArray.length()-1){

            Log.e("qqq","result1 = "+stringArray.getTextArray(i).get(0))
            Log.e("qqq","result2 = "+stringArray.getTextArray(i).get(1))
        }

//        getCountryLocation()
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

//    val registerForActivityResult =
//        registerForActivityResult(ActivityResultContracts.GetContent()) {
//            val toString = it.toString()
//            BitmapFactory.decodeFile(toString)
//        }
    private fun handlerPermission(){
        val storage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        val checkSelfPermission = ActivityCompat.checkSelfPermission(this,storage)
        if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){

        }else{
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,storage)){
                ActivityCompat.requestPermissions(this,arrayOf(storage),1)
//            }
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

    @InternalCoroutinesApi
    fun testFlowIsCode(count: Int) = runBlocking {
        val flow = simpleFlow()
        Log.e("simpleFlow","simpleFlow collect>>>>>>>>>>>")

//        flow.collect { value -> //                if (value > count){
//            //                    return
//            //                }
//            Log.e("simpleFlow", "simpleFlow start>>>>>>>>>>>" + value)
//        }
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

