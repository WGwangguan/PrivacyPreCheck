package com.wg.privacyprecheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import com.wg.privacy.PrivacyPreCheck

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        PrivacyPreCheck.startHook()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val telephony = getSystemService(TELEPHONY_SERVICE) as? TelephonyManager
        try {
            telephony?.allCellInfo
            telephony?.imei
            telephony?.simOperator
        } catch (e: SecurityException) {
        }
    }
}