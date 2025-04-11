package com.example.health.data.utils

data class MacroPercent(
    val carbPercent: Float,
    val proteinPercent: Float,
    val fatPercent: Float
)

fun calculateMacroPercentFromGrams(
    tdee: Int,
    carbGram: Float,
    proteinGram: Float,
    fatGram: Float
): MacroPercent {
    val rawCarbPercent = (carbGram * 4f / tdee) * 100f
    val rawProteinPercent = (proteinGram * 4f / tdee) * 100f
    val rawFatPercent = (fatGram * 9f / tdee) * 100f

    return MacroPercent(
        carbPercent = rawCarbPercent,
        proteinPercent = rawProteinPercent,
        fatPercent = rawFatPercent
    )
}

/*
cach dung:
val tdee = 2500
val result = calculateMacroPercentFromGrams(
    tdee = tdee,
    carbGram = 250f,
    proteinGram = 180f,
    fatGram = 70f
)

println("Carb %: ${result.carbPercent}")
println("Protein %: ${result.proteinPercent}")
println("Fat %: ${result.fatPercent}")
 */