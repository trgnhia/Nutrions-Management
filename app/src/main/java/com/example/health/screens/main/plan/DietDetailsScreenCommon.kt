package com.example.health.screens.main.plan

import DietDishCard
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.screens.main.plan.vegan.HeaderWithBackButton

@Composable
fun DietDetailsScreenCommon(
    navController: NavController,
    viewModel: DietDishViewModel,
    mealPlanId: String
) {
    val context = LocalContext.current

    LaunchedEffect(mealPlanId) {
        viewModel.syncIfNeeded(context)
        viewModel.loadByMealId(mealPlanId)
    }

    val dishes by viewModel.dishes.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderWithBackButton(
            title = "Meal Detail",
            onBackClick = { navController.popBackStack() }
        )

        LazyColumn(modifier = Modifier.padding(12.dp)) {
            items(dishes) { dish ->
                DietDishCard(dish = dish)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
