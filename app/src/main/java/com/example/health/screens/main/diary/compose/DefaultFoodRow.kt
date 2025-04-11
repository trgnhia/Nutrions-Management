package com.example.health.screens.main.diary.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.health.data.local.entities.DefaultFood
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.screens.main.diary.AddFood
import java.util.Date

// ✅ File: DefaultFoodRow.kt
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DefaultFoodRow(
    title: String,
    foods: List<DefaultFood>,
    onItemClick: (DefaultFood) -> Unit,
    onViewMoreClick: () -> Unit,
    onSaveFood: () -> Unit,
    parent: String,
    uid: String,
    mealType: Int,
    today : Date,
    eatenMealViewModel: EatenMealViewModel,
    eatenDishViewModel: EatenDishViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    selecDay : Date
) {
    val displayItems = foods.take(5)
    var selectedFood by remember { mutableStateOf<DefaultFood?>(null) }


    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = onViewMoreClick) {
                Text("View More")
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(displayItems, key = { it.Id }) { food ->
                DefaultFoodCard(food, onClick = { selectedFood = food })
            }
        }

        selectedFood?.let { food ->
            FoodDetailDialog(
                food = food,
                parent = parent,
                onDismiss = { selectedFood = null },
                onSave = { weight, calo, fat, carb, protein ->
                    AddFood(
                        uid = uid,
                        eatenDishViewModel = eatenDishViewModel,
                        eatenMealViewModel = eatenMealViewModel,
                        totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                        today = today,
                        foodID = food.Id,
                        dishName = food.Name,
                        calo = calo,
                        fat = fat,
                        carb = carb,
                        protein = protein,
                        type = mealType,
                        quantityType = food.QuantityType,
                        quantity = weight,
                        urlImage = food.UrlImage,
                    )
                    onSaveFood()
                },
                selectedDay = selecDay
            )
        }
    }
}
