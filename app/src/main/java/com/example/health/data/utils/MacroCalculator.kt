package com.example.health.data.utils

data class MacroResult(
    val carbInGrams: Float,
    val proteinInGrams: Float,
    val fatInGrams: Float
)

object MacroCalculator {

    /**
     * Tính toán lượng carb, protein và fat (gram/ngày) dựa trên TDEE và tỉ lệ phần trăm các chất.
     * @param tdee Tổng năng lượng tiêu hao hàng ngày (kcal)
     * @param carbPercent Tỉ lệ phần trăm carb (ví dụ: 40f cho 40%)
     * @param proteinPercent Tỉ lệ phần trăm protein
     * @param fatPercent Tỉ lệ phần trăm fat
     * @return MacroResult chứa gram carb, protein, fat mỗi ngày
     */
    fun calculateMacros(
        tdee: Int,
        carbPercent: Float,
        proteinPercent: Float,
        fatPercent: Float
    ): MacroResult {
        // Chuyển phần trăm thành tỉ lệ (0.4, 0.35, 0.25)
        val carbRatio = carbPercent / 100f
        val proteinRatio = proteinPercent / 100f
        val fatRatio = fatPercent / 100f

        // Tính kcal mỗi chất
        val carbKcal = tdee * carbRatio
        val proteinKcal = tdee * proteinRatio
        val fatKcal = tdee * fatRatio

        // Đổi kcal → gram
        val carbGrams = carbKcal / 4f
        val proteinGrams = proteinKcal / 4f
        val fatGrams = fatKcal / 9f

        return MacroResult(
            carbInGrams = roundToOneDecimal(carbGrams),
            proteinInGrams = roundToOneDecimal(proteinGrams),
            fatInGrams = roundToOneDecimal(fatGrams)
        )
    }

    private fun roundToOneDecimal(value: Float): Float {
        return try {
            String.format("%.1f", value).replace(",", ".").toFloat()
        } catch (e: Exception) {
            0f
        }
    }
}

/* CÁCH SỬ DỤNG hehe
val result = MacroCalculator.calculateMacros(
    tdee = 2500,
    carbPercent = 40f,
    proteinPercent = 35f,
    fatPercent = 25f
)

println("Carbs: ${result.carbInGrams}g")
println("Protein: ${result.proteinInGrams}g")
println("Fat: ${result.fatInGrams}g")
*/
