package com.example.health.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.data.local.entities.HealthMetric
import com.example.health.data.local.viewmodel.HealthMetricViewModel

@Composable
fun HealthMetricScreen(
    navController: NavController,
    healthMetricViewModel: HealthMetricViewModel
) {
    val metric = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<HealthMetric>("metric") ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Chỉ số sức khoẻ của bạn", style = MaterialTheme.typography.titleLarge)

        Text("BMR: ${metric.BMR}")
        Text("BMI: ${metric.BMI}")
        Text("TDEE: ${metric.TDEE}")
        Text("Weight target: ${metric.WeightTarget}")

        Button(onClick = {
            healthMetricViewModel.insertHealthMetric(metric)
            navController.navigate("home") {
                popUpTo("health_metric") { inclusive = true }
            }
        }) {
            Text("Xác nhận và tiếp tục")
        }
    }
}
