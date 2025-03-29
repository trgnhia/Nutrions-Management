package com.example.health.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.data.local.entities.HealthMetric
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.utils.HealthMetricUtil
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CalculatingScreen(
    navController: NavController,
    baseInfoViewModel: BaseInfoViewModel
) {
    val baseInfo by baseInfoViewModel.baseInfo.collectAsState()

    LaunchedEffect(Unit) {
        delay(1500) // loading giả lập
        baseInfo?.let {
            val bmr = HealthMetricUtil.calculateBMR(it.Weight, it.Height, it.Age, it.Gender)
            val bmi = HealthMetricUtil.calculateBMI(it.Weight, it.Height)
            val tdee = HealthMetricUtil.calculateTDEE(bmr, it.ActivityLevel)
            val weightTarget = HealthMetricUtil.calculateWeightTarget(it.Height)
            val metricId = HealthMetricUtil.generateMetricId()
            val dif = HealthMetricUtil.diffWeight(it.Weight, weightTarget)
            val calorDeltaPerDay = HealthMetricUtil.calculateCalorieDeltaPerDay(tdee,dif)
            val resDay = HealthMetricUtil.restDay(dif,calorDeltaPerDay)
            val metric = HealthMetric(
                metricId = metricId,
                Uid = it.Uid,
                Height = it.Height,
                Weight = it.Weight,
                WeightTarget = weightTarget,
                BMR = bmr,
                BMI = bmi,
                TDEE = tdee,
                CalorPerDay = calorDeltaPerDay,
                RestDay = resDay,
                UpdateAt = HealthMetricUtil.getCurrentDateTime()
            )

            navController.currentBackStackEntry?.savedStateHandle?.set("metric", metric)
            navController.navigate("health_metric")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
            Text("Đang tính toán chỉ số sức khoẻ...")
        }
    }
}



