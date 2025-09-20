package com.example.health.screens.main.diary.compose

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
import com.example.health.data.local.entities.EatenDish
import com.example.health.data.local.viewmodel.EatenDishViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailDefaultScreen(
    foodId: String,
    viewModel: EatenDishViewModel
) {
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
        image = food!!.UrlImage,
        name = food!!.DishName,
        calo = food!!.Calo,
        carb = food!!.Carb,
        fat = food!!.Fat,
        protein = food!!.Protein ,
        quantity = food!!.Quantity,
        quantityType = food!!.QuantityType
    )
}
