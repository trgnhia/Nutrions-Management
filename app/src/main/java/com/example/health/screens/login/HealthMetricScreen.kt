package com.example.health.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.entities.HealthMetric
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.utils.HealthMetricUtil
import com.example.health.screens.loader.ActLoader
import kotlinx.coroutines.delay
import java.util.Date

@Composable
fun HealthMetricScreen(
    navController: NavController,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    onLoadData: suspend () -> Unit
) {
    val baseInfo by baseInfoViewModel.baseInfo.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        baseInfo?.let {
            // 🔢 Tính toán chỉ số
            val bmr = HealthMetricUtil.calculateBMR(it.Weight, it.Height, it.Age, it.Gender)
            val bmi = HealthMetricUtil.calculateBMI(it.Weight, it.Height)
            val tdee = HealthMetricUtil.calculateTDEE(bmr, it.ActivityLevel)
            val weightTarget = HealthMetricUtil.calculateWeightTarget(it.Height)
            val metricId = HealthMetricUtil.generateMetricId()
            val dif = HealthMetricUtil.diffWeight(it.Weight, weightTarget)
            val calorDeltaPerDay = HealthMetricUtil.calculateCalorieDeltaPerDay(tdee, dif)
            val resDay = HealthMetricUtil.restDay(dif, calorDeltaPerDay)
            val now = Date()

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
                UpdateAt = now
            )

            // ✅ Lưu vào Room
            healthMetricViewModel.insertHealthMetric(metric)

            // ✅ Load dữ liệu mặc định nếu cần
            onLoadData()

            delay(3000) // Optional: giữ UI mượt

            isLoading = false
            navController.navigate("home") {
                popUpTo("health_metric") { inclusive = true }
            }
        }
    }

    // UI Loading
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.loadding_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActLoader()
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "We are setting up your personalized plan, please wait...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
