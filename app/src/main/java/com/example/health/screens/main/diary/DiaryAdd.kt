package com.example.health.screens.main.diary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.health.data.local.entities.EatenDish
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.screens.main.ParenCompose

@Composable
fun DiaryAdd(
    navController: NavController  ,
    parent: String ,
    mealType : Int,
    defaultFoodViewModel: DefaultFoodViewModel,
    eatenMealViewModel: EatenMealViewModel,
    eatenDishViewModel: EatenDishViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "This is add screen from $parent")
            if (parent == ParenCompose.FROMDIARY) {
                when (mealType) {
                    1 -> Text("🍳 Breakfast")
                    2 -> Text("🍛 Lunch")
                    3 -> Text("🍲 Dinner")
                    4 -> Text("🍪 Snack")
                    else -> Text("🍽 Unknown Meal")
                }
            }
            else{
                Text(text = "$parent screen has no meal")
            }
        }


    }
}