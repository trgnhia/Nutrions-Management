package com.example.health.alarm
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.health.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message") ?: "It's meal time!"
        val mealId = intent.getStringExtra("mealId") ?: "meal"
        android.util.Log.e("AlarmReceiver", "⏰ Received alarm for $mealId with message: $message")
        val channelId = "meal_reminder_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Tạo notification channel nếu cần
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Meal Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification) // thêm icon của bạn
            .setContentTitle("Meal Reminder")
            .setContentText(message)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(mealId.hashCode(), notification)
    }
}
