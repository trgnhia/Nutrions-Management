package com.example.health.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.data.remote.auth.AuthState
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.screens.loader.ModernLoader
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel
) {
    val authState by authViewModel.authState.collectAsState()
    val isProcessing = remember { mutableStateOf(true) } // Để kiểm tra khi dữ liệu đồng bộ xong

    LaunchedEffect(authState) {
        delay(3500) // Giả lập splash màn hình
        when (authState) {
            is AuthState.Authenticated -> {
                val uid = authViewModel.currentUser?.uid
                if (uid != null) {
                    // Đồng bộ dữ liệu từ Firestore về Room
                    accountViewModel.fetchFromRemote(uid)
                    delay(500)
                    baseInfoViewModel.fetchFromRemote(uid)
                    healthMetricViewModel.fetchAllFromRemote(uid)
                    // Đợi dữ liệu đồng bộ
                    delay(5000) // Thêm delay một chút cho an toàn
                    isProcessing.value = false // Dữ liệu đã đồng bộ xong, có thể điều hướng
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }
            is AuthState.AuthenticatedButNotRegistered -> {
                navController.navigate("base_info") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isProcessing.value) {
            ModernLoader()
        }
    }
}


