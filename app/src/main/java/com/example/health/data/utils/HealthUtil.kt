package com.example.health.data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object HealthMetricUtil {
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateMetricId(): String {
        val now = LocalDateTime.now()
        val timePart = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        val randomPart = UUID.randomUUID().toString().take(8) // rút ngắn cho dễ nhìn
        return "${timePart}_$randomPart"
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

    fun calculateTDEE(bmr: Float, activityLevel: String): Float {
        val multiplier = when (activityLevel.lowercase()) {
            "sedentary" -> 1.2f
            "light" -> 1.375f
            "moderate" -> 1.55f
            "active" -> 1.725f
            "very active" -> 1.9f
            else -> 1.0f
        }
        return bmr * multiplier
    }
}