package com.example.health.screens.main.plan.highprotein

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.screens.main.plan.DietDetailsScreenCommon

//@Composable
//fun HighProteinDetailsScreen(navController: NavController){
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "This is high protein details screen")
//
//        Spacer(modifier = Modifier.height(16.dp))
//    }
//}

@Composable
fun HighProteinDetailsScreen(
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
