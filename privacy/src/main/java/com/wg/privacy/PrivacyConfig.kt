package com.wg.privacy

import android.app.ActivityManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.ClipData
import android.content.ClipboardManager
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.location.LocationManager
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import java.net.NetworkInterface
import java.util.*

/**
 * Created by WG on 2022/11/8.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
场景说明：
未经用户同意，存在收集IMEI、设备id，sim，androidId，设备MAC地址和软件安装列表、通讯录和短信的行为。
整改建议：
隐私政策隐私弹窗必须使用明确的“同意\拒绝”按钮；只有当用户点击“同意”后，APP和SDK才能调用系统接口和读取收集用户的信息。
客户端如何做？
①用户在点击隐私政策协议“同意”按钮前，APP和SDK不能调用系统的敏感权限接口，特别是能获取IMEI、IMSI、MAC、IP、Android、已安装应用列表、硬件序列表、手机号码、位置等等信息的系统接口。
②集成的第三方SDK建议升级到最新版本，用户在点击隐私政策协议“同意”按钮后，SDK再进行初始化。
③技术同学可在“同意”按钮上加入判定函数，当用户点击“同意”后，APP和SDK再执行调用系统接口的相关函数行为。
 */
object PrivacyConfig {

    fun getConfig(): LinkedList<PrivacyApi> {
        val linkedList = LinkedList<PrivacyApi>()

        // 设备信息，网络信息，蓝牙信息，定位信息，运行进程、已安装文件、传感器列表

        // 设备信息
        // device_id
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getDeviceId"))
        // android_id
        linkedList.add(PrivacyApi(Settings.Secure::class.java, "getString"))
        // 串码
        linkedList.add(PrivacyApi(Build::class.java, "getSerial"))
        // imsi
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getSubscriberId"))
        // imei
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getImei"))
        // meid
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getMeid"))
        // 网络运营商
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getSimOperator"))
        // 网络运营商名字
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getNetworkOperatorName"))
        // sim 运营商
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getSimOperator"))
        // sim 运营商名字
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getSimOperatorName"))
        // sim 序列号
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getSimSerialNumber"))
        // all cell
        linkedList.add(PrivacyApi(TelephonyManager::class.java, "getAllCellInfo"))

        // 网络信息
        // mac 地址
        linkedList.add(PrivacyApi(WifiInfo::class.java, "getMacAddress"))
        linkedList.add(PrivacyApi(NetworkInterface::class.java, "getHardwareAddress"))
        // wifi ssid
        linkedList.add(PrivacyApi(WifiInfo::class.java, "getSSID"))
        linkedList.add(PrivacyApi(NetworkInfo::class.java, "getExtraInfo"))
        // wifi bssid
        linkedList.add(PrivacyApi(WifiInfo::class.java, "getBSSID"))
        // wifi rssi
        linkedList.add(PrivacyApi(WifiInfo::class.java, "getRssi"))
        // wifi ip
        linkedList.add(PrivacyApi(WifiInfo::class.java, "getIpAddress"))
        // wifi 状态
        linkedList.add(PrivacyApi(WifiManager::class.java, "setWifiEnabled"))
        linkedList.add(PrivacyApi(WifiManager::class.java, "isWifiEnabled"))
        linkedList.add(PrivacyApi(WifiManager::class.java, "getWifiState"))
        linkedList.add(PrivacyApi(NetworkInterface::class.java, "getNetworkInterfaces"))
        // wifi 扫描
        linkedList.add(PrivacyApi(WifiManager::class.java, "startScan"))

        // 蓝牙信息
        // 蓝牙扫描
        linkedList.add(PrivacyApi(BluetoothLeScanner::class.java, "startScan"))

        // 定位信息
        linkedList.add(PrivacyApi(LocationManager::class.java, "getLastKnownLocation"))
        linkedList.add(PrivacyApi(LocationManager::class.java, "getCurrentLocation"))

        // 运行进程
        linkedList.add(PrivacyApi(ActivityManager::class.java, "getRunningAppProcesses"))

        // 已安装应用
        linkedList.add(PrivacyApi(Class.forName("android.app.ApplicationPackageManager"), "getInstalledPackages"))

        // 传感器列表
        linkedList.add(PrivacyApi(SensorManager::class.java, "getSensorList"))

        // 粘贴板
        linkedList.add(PrivacyApi(ClipboardManager::class.java, "getText"))
        linkedList.add(PrivacyApi(ClipboardManager::class.java, "getPrimaryClip"))
        linkedList.add(PrivacyApi(ClipData::class.java, "getItemAt"))

        return linkedList
    }
}

data class PrivacyApi(
    val clazz: Class<*>,
    val methodName: String
)