package com.example.health.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.data.local.viewmodel.*

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    navController: NavController
) {
    val account = accountViewModel.account.collectAsState().value
    val baseInfo = baseInfoViewModel.baseInfo.collectAsState().value
    val lastMetric = healthMetricViewModel.lastMetric.collectAsState(initial = null).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Chào mừng trở lại!", style = MaterialTheme.typography.titleLarge)

        account?.let {
            Text("Tên: ${it.Name}")
            Text("Email: ${it.Email}")
        }

        baseInfo?.let {
            Text("Tuổi: ${it.Age}")
            Text("Chiều cao: ${it.Height} cm")
            Text("Cân nặng: ${it.Weight} kg")
            Text("Mục tiêu: ${it.Goal}")
        }

        lastMetric?.let {
            Text("\nChỉ số gần nhất:", style = MaterialTheme.typography.titleMedium)
            Text("BMI: ${it.BMI}")
            Text("BMR: ${it.BMR}")
            Text("TDEE: ${it.TDEE}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            authViewModel.signOut(accountViewModel, baseInfoViewModel, healthMetricViewModel)
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text("Đăng xuất")
        }
    }
}