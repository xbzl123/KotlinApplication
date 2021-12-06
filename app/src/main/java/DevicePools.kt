import com.example.kotlinapplication.Device

enum class DevicePools {
    INSTANCE;
    var devices = arrayListOf<Device>()
    open fun insertDevice(device: Device){
        devices.add(device)
    }
}

fun getDeviceInfo(id: Int):Device{
    return DevicePools.INSTANCE.devices.get(id)
}