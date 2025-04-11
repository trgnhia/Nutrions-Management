package com.example.health.screens.main.diary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.local.entities.EatenDish
import com.example.health.data.local.entities.EatenMeal
import com.example.health.data.local.entities.TotalNutrionsPerDay
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.data.utils.toStartOfDay
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
fun AddFood(
    uid: String,
    eatenDishViewModel: EatenDishViewModel,
    eatenMealViewModel: EatenMealViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    today: Date,
    foodID: String,
    dishName: String,
    calo: Float,
    fat: Float,
    carb: Float,
    protein: Float,
    type: Int,
    quantityType: String,
    quantity: Float,
    urlImage: String
) {
    eatenMealViewModel.viewModelScope.launch {
        // 1. Tìm bản ghi bữa ăn theo ngày và loại
        var meal = eatenMealViewModel.getMealByDateAndType(today, type)

        // 2. Nếu chưa có → tạo mới
        if (meal == null) {
            val newMeal = EatenMeal(
                id = UUID.randomUUID().toString(),
                Date = today.toStartOfDay(), // chuẩn hoá giờ 00:00:00
                Uid = uid,
                TotalCalos = 0f,
                TotalFats = 0f,
                TotalCarbs = 0f,
                TotalPro = 0f,
                Type = type
            )
            eatenMealViewModel.insert(newMeal)
            meal = newMeal
        }

        // 3. Thêm món ăn vào bảng EatenDish
        val newDish = EatenDish(
            id = UUID.randomUUID().toString(),
            FoodId = foodID,
            IdEatenMeal = meal.id,
            DishName = dishName,
            Calo = calo,
            Fat = fat,
            Carb = carb,
            Protein = protein,
            QuantityType = quantityType,
            Quantity = quantity,
            UrlImage = urlImage
        )
        eatenDishViewModel.insert(newDish,uid)

        // 4. Cập nhật lại dinh dưỡng cho meal
        val updatedMeal = meal.copy(
            TotalCalos = meal.TotalCalos + calo,
            TotalFats = meal.TotalFats + fat,
            TotalCarbs = meal.TotalCarbs + carb,
            TotalPro = meal.TotalPro + protein
        )
        eatenMealViewModel.update(updatedMeal)

        // 5. Đảm bảo TotalNutrionsPerDay tồn tại → rồi luôn cập nhật
        var existingTotal = totalNutrionsPerDayViewModel.getByDateAndUidOnce(today.toStartOfDay(), uid)

// Nếu chưa có, tạo mới trước
        if (existingTotal == null) {
            val newTotal = TotalNutrionsPerDay(
                id = UUID.randomUUID().toString(),
                Date = today.toStartOfDay(),
                Uid = uid,
                TotalCalo = calo,
                TotalPro = protein,
                TotalCarb = carb,
                TotalFat = fat,
                DietType = type
            )
            totalNutrionsPerDayViewModel.insert(newTotal)

            // Sau khi insert xong, cập nhật lại biến để đảm bảo luôn có bản ghi
            existingTotal = newTotal
        }

// Tính lại tổng dinh dưỡng
        val updatedTotal = existingTotal.copy(
            TotalCalo = existingTotal.TotalCalo + calo,
            TotalPro = existingTotal.TotalPro + protein,
            TotalCarb = existingTotal.TotalCarb + carb,
            TotalFat = existingTotal.TotalFat + fat
        )
// Luôn cập nhật sau cùng
        totalNutrionsPerDayViewModel.update(updatedTotal)
    }
}
