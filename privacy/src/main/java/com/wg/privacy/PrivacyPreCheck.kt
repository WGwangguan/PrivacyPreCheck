package com.wg.privacy

import android.util.Log
import de.robv.android.xposed.DexposedBridge
import de.robv.android.xposed.XC_MethodHook

/**
 * Created by WG on 2022/11/9.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
object PrivacyPreCheck {
    private const val TAG = "PrivacyPreCheck"

    @JvmStatic
    fun startHook() {
        Log.i(TAG, "startHook:")

        val privacyHook = object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
                try {
                    val hook0bj = param?.thisObject
                    var clsName = "unknownClass"
                    if (hook0bj != null) {
                        clsName = hook0bj.javaClass.name
                        var mdName = "unknownMethod"
                        if (param.method != null) {
                            mdName = param.method.name
                        }
                        Log.d(TAG, "###################################################")
                        Log.d(TAG, "############## 检测到敏感 api 调用 ##################")
                        Log.d(TAG, "###################################################")
                        Log.d(TAG, "#### className: $clsName")
                        Log.d(TAG, "#### methodName: $mdName")
                        Log.d(TAG, "#### value: ${param.result}")
                        val trace =
                            Log.getStackTraceString(Throwable()).replace("java.lang.Throwable", "")
                        Log.d(TAG, "\n $trace")
                        Log.d(TAG, "###################################################")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        try {
            val config = PrivacyConfig.getConfig()
            config.forEach {
                DexposedBridge.hookAllMethods(it.clazz, it.methodName, privacyHook)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}