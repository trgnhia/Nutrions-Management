package com.example.health.data.utils

import kotlin.math.abs

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
            "male" -> (10 * weight) + (6.25f * height) - (5 * age) + 5
            "female" -> (10 * weight) + (6.25f * height) - (5 * age) - 161
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
    fun calculateWeightTarget(currentHeight: Float, targetBMI: Float = 22.5f): Float {
        val heightM = currentHeight / 100f
        return targetBMI * heightM * heightM
    }

    fun diffWeight(currentWeight: Float, targetWeight: Float) : Float{
        return targetWeight - currentWeight;
    }

    fun calculateCalorieDeltaPerDay(tdee: Float , diffWeight: Float): Float {
        val dif = abs(diffWeight)
        return if(dif <= 0) 0f
        else if(dif <= 1) tdee * 0.02f
        else if(dif <=2) tdee * 0.05f
        else if(dif <= 4) tdee * 0.1f
        else if(dif <= 6) tdee * 0.15f
        else if(dif <= 8) tdee * 0.2f
        else tdee * 0.25f
    }

    fun restDay(diffWeight: Float, calorieDeltaPerDay: Float): Int {
        if (calorieDeltaPerDay == 0f) return 0 // tránh chia cho 0
        val totalCalories = diffWeight * 7700  // mỗi 1kg ≈ 7700 kcal
        return (totalCalories / calorieDeltaPerDay).toInt()
    }
    fun bodyAddition(bmi: Float):String{
        return when {
            bmi < 16 -> "Severely underweight"
            bmi < 17 -> "Underweight"
            bmi < 18.5 -> "Slightly underweight"
            bmi < 23 -> "Normal"
            bmi < 25 -> "Slightly overweight"
            bmi < 27.5 -> "Overweight"
            bmi < 30 -> "Obese I"
            bmi < 35 -> "Obese II"
            else -> "Obese III"
        }
    }

    fun advice(bmi: Float): String {
        return when {
            bmi < 16 -> "😟 Severely underweight. Consult a doctor."
            bmi < 17 -> "🧍 Underweight. Improve your diet."
            bmi < 18.5 -> "🍽️ Slightly underweight. Eat more nutritious food."
            bmi < 23 -> "✅ Normal weight. Keep it up!"
            bmi < 25 -> "👌 Healthy, but watch your weight."
            bmi < 27.5 -> "🏃 Slightly overweight. Exercise more."
            bmi < 30 -> "⚠️ Overweight. Consider lifestyle changes."
            bmi < 35 -> "❗ Obese I. Start losing weight seriously."
            bmi < 40 -> "🚨 Obese II. Seek medical advice."
            else -> "🆘 Obese III. Immediate medical help needed."
        }
    }
}