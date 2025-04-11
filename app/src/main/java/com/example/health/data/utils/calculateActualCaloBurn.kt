package com.example.health.data.utils

/**
 * Tính lượng calo thực tế người dùng đã tiêu hao.
 *
 * @param caloBurn Calo tiêu hao ứng với defaultUnit
 * @param defaultUnit Đơn vị chuẩn (ví dụ: 30 phút, 20 lần,...)
 * @param actualUnit Đơn vị người dùng thực tế thực hiện
 *
 * @return Lượng calo đốt cháy thực tế
 */
fun calculateActualCaloBurn(
    caloBurn: Int,
    defaultUnit: Int,
    actualUnit: Int
): Int {
    if (defaultUnit <= 0 || actualUnit <= 0) return 0
    val ratio = actualUnit.toFloat() / defaultUnit.toFloat()
    return (caloBurn * ratio).toInt()
}
/*cach dung
val result = calculateActualCaloBurn(
    caloBurn = 150,
    defaultUnit = 30,
    actualUnit = 20
)
println("Calo burned: $result kcal") // ➜ ~100 kcal
*/
