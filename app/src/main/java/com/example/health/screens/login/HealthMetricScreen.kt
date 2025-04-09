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
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.screens.loader.ActLoader
import kotlinx.coroutines.delay

@Composable
fun HealthMetricScreen(
    navController: NavController,
    onLoadData: suspend () -> Unit
) {
        // State để điều hướng khi xong
        var isLoading by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            // Gọi suspend lambda load dữ liệu
            onLoadData()

            // Đợi 1 chút để UI mượt (optional)
            delay(500L)

            isLoading = false
            navController.navigate("home") {
                popUpTo("loading") { inclusive = true }
            }
        }

        // UI Loading
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.whitebackground), // Thay bằng hình bạn có
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // Nội dung trung tâm
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActLoader() // Loader đã có sẵn

                Spacer(modifier = Modifier.height(24.dp))

                androidx.compose.material3.Text(
                    text = "We are setting up your personalized plan, please wait...",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                )
            }
        }
}
