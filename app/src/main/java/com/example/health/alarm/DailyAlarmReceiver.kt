package com.example.health.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyAlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("DailyAlarm", "Alarm triggered at 7AM")
        // Gọi xử lý tạo bản ghi mỗi ngày
        CoroutineScope(Dispatchers.IO).launch {
            val worker = AutoDailyInitWorker(context)
            worker.initAllForToday()
        }
    }
}
