package com.example.health.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.screens.main.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation(
    navController: NavHostController,
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
    dietDishViewModel : DietDishViewModel,
    calorBurn: MutableState<Float>

) {
    // đây là nav tổng quát nhất ( gọi toàn bộ ứng dụng luôn ( sẽ được gọi trong main))
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            // compose được khai báo ở nav này
            MainScreen(
                rootNavController = navController,
                authViewModel = authViewModel,
                accountViewModel = accountViewModel,
                baseInfoViewModel = baseInfoViewModel,
                healthMetricViewModel = healthMetricViewModel,
                defaultFoodViewModel = defaultFoodViewModel,
                defaultExerciseViewModel = defaultExerciseViewModel,
                defaultDietMealInPlanViewModel = defaultDietMealInPlanViewModel,
                macroViewModel = macroViewModel,
                totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                exerciseLogViewModel = exerciseLogViewModel,
                eatenMealViewModel = eatenMealViewModel,
                eatenDishViewModel = eatenDishViewModel,
                burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel,
                customFoodViewModel  = customFoodViewModel,
                customExerciseViewModel = customExerciseViewModel,
                notifyViewModel = notifyViewModel,
                dietDishViewModel = dietDishViewModel,
                calorBurn = calorBurn
                )
        }
    }
}