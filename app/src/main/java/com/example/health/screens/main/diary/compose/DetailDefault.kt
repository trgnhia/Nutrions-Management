package com.example.health.screens.main.diary.compose

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.local.entities.EatenDish
import com.example.health.data.local.entities.EatenMeal
import com.example.health.data.local.entities.TotalNutrionsPerDay
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.data.utils.calculateNutritionByWeight
import com.example.health.data.utils.toStartOfDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

@Composable
fun DetailDefaultScreen(
    foodId: String,
    viewModel: EatenDishViewModel,
    eatenDishViewModel: EatenDishViewModel ,
    eatenMealViewModel: EatenMealViewModel ,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    defaultFoodViewModel: DefaultFoodViewModel,
    selectedDate: Date

) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var food by remember { mutableStateOf<EatenDish?>(null) }

    LaunchedEffect(foodId) {
            viewModel.getById(foodId).collect { dish ->
                food = dish
            }
    }

    if (food == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // 🧡 Giao diện giống DetailDietScreen nhưng lấy từ DefaultFood
    // Gợi ý dùng lại layout code từ DetailDietScreen
    DetailFoodLayout(
        eatenDish = food!!,
        selectedDate = selectedDate ,
        onUpdateQuantity  = { it ->
            scope.launch {
                updateEatenDish(
                    scope = scope,
                    newQuantity = it,
                    eatenDish = food!!,
                    eatenDishViewModel = eatenDishViewModel,
                    eatenMealViewModel = eatenMealViewModel,
                    totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                    defaultFoodViewModel = defaultFoodViewModel

                )
            }
            Toast.makeText(context, "Update success ", Toast.LENGTH_SHORT).show()
            }
        )
}
suspend fun updateEatenDish(
    scope: CoroutineScope,
    newQuantity : Float ,
    eatenDish: EatenDish ,
    eatenDishViewModel: EatenDishViewModel ,
    eatenMealViewModel: EatenMealViewModel ,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    defaultFoodViewModel: DefaultFoodViewModel
    ){



    val meal = eatenMealViewModel.getByID(eatenDish.IdEatenMeal)
    val uid = meal?.Uid


    val defaultFood = defaultFoodViewModel.getById(eatenDish.FoodId)

    val weight = newQuantity ?: eatenDish.Quantity.toFloat()
    val result = defaultFood?.Quantity?.let {
        calculateNutritionByWeight(
        defaultWeight = it.toFloat() ,
        actualWeight = weight,
        calories = defaultFood.Calo,
        fat = defaultFood.Fat,
        carb = defaultFood.Carb,
        protein = defaultFood.Protein
        )
    }
    val updatedEatenDish = eatenDish.copy(
        Quantity = result?.actualWeight ?: eatenDish.Quantity,
        Calo = result?.calories ?: eatenDish.Calo,
        Fat = result?.fat ?: eatenDish.Fat,
        Carb = result?.carb ?: eatenDish.Carb,
        Protein = result?.protein ?: eatenDish.Protein
    )
    if (uid != null) {
        eatenDishViewModel.update(uid = uid , dish = updatedEatenDish   )
    }
    val copyMeal = meal?.copy(
        TotalPro = meal.TotalPro - eatenDish.Protein + updatedEatenDish.Protein,
        TotalCarbs = meal.TotalCarbs - eatenDish.Carb + updatedEatenDish.Carb,
        TotalFats = meal.TotalFats - eatenDish.Fat + updatedEatenDish.Fat,
        TotalCalos = meal.TotalCalos - eatenDish.Calo + updatedEatenDish.Calo

    )
    if (copyMeal != null) {
            eatenMealViewModel.update(meal = copyMeal)
    }
    val today = meal?.Date?.toStartOfDay()
    scope.launch {
        val existingTotal = today?.let {
            if (uid != null) {
                totalNutrionsPerDayViewModel.getByDateAndUidOnce(it, uid)
            } else null
        }

        if (existingTotal != null) {
            // xử lý object ở đây
            val updatedTotal = existingTotal.copy(
                TotalCalo = existingTotal.TotalCalo - eatenDish.Calo + updatedEatenDish.Calo,
                TotalPro = existingTotal.TotalPro - eatenDish.Protein + updatedEatenDish.Protein,
                TotalCarb = existingTotal.TotalCarb - eatenDish.Carb + updatedEatenDish.Carb,
                TotalFat = existingTotal.TotalFat - eatenDish.Fat + updatedEatenDish.Fat
            )
            totalNutrionsPerDayViewModel.update(updatedTotal)
        }
    }
}