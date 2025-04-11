package com.example.health.screens.main.plan.keto

import androidx.compose.runtime.Composable

import androidx.navigation.NavController
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.screens.main.plan.DietDetailsScreenCommon


@Composable
fun KetoDetailsScreen(
    navController: NavController,
    viewModel: DietDishViewModel,
    mealPlanId: String
) {
    DietDetailsScreenCommon(
        navController = navController,
        viewModel = viewModel,
        mealPlanId = mealPlanId
    )
}
