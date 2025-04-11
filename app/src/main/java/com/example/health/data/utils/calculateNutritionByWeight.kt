package com.example.health.data.utils

/**
 * Tính các chỉ số dinh dưỡng theo khối lượng thực tế.
 *
 * @param defaultWeight Khối lượng mặc định của món ăn (ví dụ: 100g)
 * @param actualWeight Khối lượng thực tế người dùng nhập (ví dụ: 200g)
 * @param calories Lượng calories ứng với defaultWeight
 * @param fat Lượng fat ứng với defaultWeight
 * @param carb Lượng carb ứng với defaultWeight
 * @param protein Lượng protein ứng với defaultWeight
 *
 * @return NutritionResult gồm actualWeight và các chỉ số dinh dưỡng đã tính
 */
fun calculateNutritionByWeight(
    defaultWeight: Float,
    actualWeight: Float,
    calories: Float,
    fat: Float,
    carb: Float,
    protein: Float
): NutritionResult {
    if (defaultWeight <= 0f) return NutritionResult(0f, 0f, 0f, 0f, 0f)

    val ratio = actualWeight / defaultWeight
    return NutritionResult(
        actualWeight = actualWeight,
        calories = calories * ratio,
        fat = fat * ratio,
        carb = carb * ratio,
        protein = protein * ratio
    )
}

data class NutritionResult(
    val actualWeight: Float,
    val calories: Float,
    val fat: Float,
    val carb: Float,
    val protein: Float
)

/*
Cach dung:
val result = calculateNutritionByWeight(
    defaultWeight = 100f,
    actualWeight = 200f,
    calories = 150f,
    fat = 6f,
    carb = 18f,
    protein = 10f
)

println("Weight: ${result.actualWeight}g")
println("Calories: ${result.calories}")
println("Fat: ${result.fat}")
println("Carb: ${result.carb}")
println("Protein: ${result.protein}")
 */
