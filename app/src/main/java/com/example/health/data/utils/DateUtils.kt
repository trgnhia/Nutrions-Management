package com.example.health.data.utils


import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.time.*
import java.util.*
object DateUtils {

    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }

    fun formatShort(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }

    fun timeAgo(date: Date): String {
        val diff = System.currentTimeMillis() - date.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return when {
            days > 0 -> "$days day(s) ago"
            hours > 0 -> "$hours hour(s) ago"
            minutes > 0 -> "$minutes min(s) ago"
            else -> "Just now"
        }
    }

    /**
     * Convert giờ + phút (LocalTime) về Date (set ngày hôm nay, giờ/phút cố định)
     */
//    fun toTodayDate(hour: Int, minute: Int): Date {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val today = java.time.LocalDate.now()
//            val localTime = java.time.LocalTime.of(hour, minute)
//            val dateTime = java.time.LocalDateTime.of(today, localTime)
//            Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
//
//        } else {
//            // Dùng Calendar thay thế
//            val calendar = Calendar.getInstance().apply {
//                set(Calendar.HOUR_OF_DAY, hour)
//                set(Calendar.MINUTE, minute)
//                set(Calendar.SECOND, 0)
//                set(Calendar.MILLISECOND, 0)
//            }
//            calendar.time
//        }
//    }

//    fun toTodayDate(hour: Int, minute: Int): java.sql.Date {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val today = java.time.LocalDate.now()
//            val localTime = java.time.LocalTime.of(hour, minute)
//            val dateTime = java.time.LocalDateTime.of(today, localTime)
//            val instant = dateTime.atZone(ZoneId.systemDefault()).toInstant()
//            java.sql.Date(instant.toEpochMilli()) // ✅ trả về java.sql.Date
//        } else {
//            val calendar = Calendar.getInstance().apply {
//                set(Calendar.HOUR_OF_DAY, hour)
//                set(Calendar.MINUTE, minute)
//                set(Calendar.SECOND, 0)
//                set(Calendar.MILLISECOND, 0)
//            }
//            java.sql.Date(calendar.timeInMillis) // ✅ trả về java.sql.Date
//        }
//    }

    fun toTodayDate(hour: Int, minute: Int): java.sql.Date {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val today = java.time.LocalDate.now()
            val localTime = java.time.LocalTime.of(hour, minute)
            val dateTime = java.time.LocalDateTime.of(today, localTime)
            val instant = dateTime.atZone(ZoneId.systemDefault()).toInstant()
            java.sql.Date(instant.toEpochMilli())
        } else {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            java.sql.Date(calendar.timeInMillis)
        }
    }

    /**
     * Convert từ Date về LocalTime (để dùng hiển thị trong UI)
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalTime(date: Date): LocalTime {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toKxLocalTime(date: java.util.Date): kotlinx.datetime.LocalTime {
        val instant = date.toInstant()
        val zone = ZoneId.systemDefault()
        val localTime = instant.atZone(zone).toLocalTime()
        return kotlinx.datetime.LocalTime(localTime.hour, localTime.minute)
    }

}
