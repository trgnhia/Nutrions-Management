package com.example.health.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.initializer.fetchAllDefaultData
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.data.remote.auth.AuthState
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.CustomExerciseViewModel
import com.example.health.data.local.viewmodel.CustomFoodViewModel
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.local.viewmodel.MacroViewModel
import com.example.health.data.local.viewmodel.NotifyViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.screens.loader.ModernLoader
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
    defaultFoodViewModel : DefaultFoodViewModel,
    defaultExerciseViewModel : DefaultExerciseViewModel,
    defaultDietMealInPlanViewModel : DefaultDietMealInPlanViewModel,
    macroViewModel : MacroViewModel,
    totalNutrionsPerDayViewModel : TotalNutrionsPerDayViewModel,
    exerciseLogViewModel : ExerciseLogViewModel,
    eatenMealViewModel : EatenMealViewModel,
    eatenDishViewModel : EatenDishViewModel,
    burnOutCaloPerDayViewModel : BurnOutCaloPerDayViewModel,
    customFoodViewModel : CustomFoodViewModel,
    customExerciseViewModel : CustomExerciseViewModel,
    notifyViewModel : NotifyViewModel,
    dietDishViewModel : DietDishViewModel
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
                            val jobs = listOf(
                                async { accountViewModel.syncIfNeeded(uid) },
                                async { baseInfoViewModel.syncIfNeeded(uid) },
                                async { healthMetricViewModel.syncIfNeeded(uid) },
                                // async { defaultFoodViewModel.syncIfNeeded(context) },
                                // async { defaultExerciseViewModel.syncIfNeeded(context) },
                                // async { defaultDietMealInPlanViewModel.syncIfNeeded(context) },
                                async { macroViewModel.syncIfNeeded(uid) },
                                async { totalNutrionsPerDayViewModel.syncIfNeeded(uid) },
                                async { exerciseLogViewModel.syncIfNeeded(uid) },
                                async { eatenMealViewModel.syncIfNeeded(uid) },
                                async { eatenDishViewModel.syncIfNeeded(uid) },
                                async { burnOutCaloPerDayViewModel.syncIfNeeded(uid) },
                                // async { customFoodViewModel.syncIfNeeded(uid) },
                                // async { customExerciseViewModel.syncIfNeeded(uid) },
                                // async { notifyViewModel.syncIfNeeded(uid) },
                                // async { dietDishViewModel.syncIfNeeded(uid) }
                            )
                            jobs.awaitAll() // ⏳ đợi tất cả coroutine hoàn thành
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
        // ✅ Hình nền toàn màn
        Image(
            painter = painterResource(id = R.drawable.main_app_bg), // 🔁 Thay bằng tên ảnh bạn có trong drawable
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )


        if (isProcessing.value) {
            ModernLoader()
        }
    }

}
