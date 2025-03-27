package com.example.health.data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object HealthMetricUtil {
    fun generateMetricId(): String {
        return "metric_${System.currentTimeMillis()}"
    }
    fun calculateBMI(weight: Float, height: Float): Float {
        return weight / ((height / 100) * (height / 100)) // height cm → m
    }

    fun calculateBMR(
        weight: Float,
        height: Float,
        age: Int,
        gender: String
    ): Float {
        return when (gender.lowercase()) {
            "male" -> 66 + (13.7f * weight) + (5f * height) - (6.8f * age)
            "female" -> 655 + (9.6f * weight) + (1.8f * height) - (4.7f * age)
            else -> 0f
        }
    }

    fun calculateTDEE(bmr: Float, activityLevel: Int): Float {
        val multiplier = when (activityLevel) {
            1 -> 1.2f      // Sedentary (ít vận động)
            2 -> 1.375f    // Light activity (tập nhẹ 1-3 ngày/tuần)
            3 -> 1.55f     // Moderate activity (tập vừa 3-5 ngày/tuần)
            4 -> 1.725f    // Active (tập nặng 6-7 ngày/tuần)
            5 -> 1.9f      // Very Active (tập rất nặng, vận động viên)
            else -> 1.0f   // Mặc định
        }
        return bmr * multiplier
    }
    fun calculateWeightTarget() : Float{
        return 0f
    }
}