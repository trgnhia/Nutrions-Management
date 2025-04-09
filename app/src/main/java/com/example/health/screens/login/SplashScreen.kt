package com.example.health.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.health.data.initializer.fetchAllDefaultData
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.data.remote.auth.AuthState
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.screens.loader.ModernLoader
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun SplashScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    defaultFoodViewModel: DefaultFoodViewModel,
    defaultExerciseViewModel: DefaultExerciseViewModel
) {
    val authState by authViewModel.authState.collectAsState()
    val isProcessing = remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(authState) {
        delay(4000) // Cho hiệu ứng splash mượt

        when (authState) {
            is AuthState.Authenticated -> {
                val uid = authViewModel.currentUser?.uid
                if (uid != null) {
                    try {
                        coroutineScope {
                            launch { accountViewModel.syncIfNeeded(uid) }
                            launch { baseInfoViewModel.syncIfNeeded(uid) }
                            launch { healthMetricViewModel.syncIfNeeded(uid) }
                            launch { defaultFoodViewModel.syncIfNeeded(context) }
                            launch { defaultExerciseViewModel.syncIfNeeded(context) }
                        }

                        isProcessing.value = false
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Optional: Hiển thị Dialog hoặc retry
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
