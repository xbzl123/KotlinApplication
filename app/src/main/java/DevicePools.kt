import com.example.kotlinapplication.Device

object DevicePools {
    var devices = arrayListOf<Device>()
    fun insertDevice(device: Device){
        devices.add(device)
    }
    fun getDeviceInfo(id: Int):Device{
        return devices.get(id)
    }
}

