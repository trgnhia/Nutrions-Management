package com.example.health.alarm
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.health.data.local.entities.Notify
import java.sql.Date

object AlarmScheduler {
    fun scheduleAlarm(context: Context, notify: Notify) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()) {
            Log.w("AlarmScheduler", "Exact alarm not allowed. Skipping alarm.")
            return
        }
        Log.d("AlarmScheduler", "Scheduling ${notify.id} at ${Date(notify.NotifyTime.time)}")

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("message", notify.Message)
            putExtra("mealId", notify.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notify.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            notify.NotifyTime.time,
            pendingIntent
        )

        Log.d("AlarmScheduler", "Alarm set for ${notify.id} at ${notify.NotifyTime}")
    }
}
