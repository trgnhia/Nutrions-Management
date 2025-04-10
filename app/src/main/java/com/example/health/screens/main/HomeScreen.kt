package com.example.health.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.health.data.local.viewmodel.*
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.navigation.MainNavigation

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    navController: NavController,
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
) {
    val account = accountViewModel.account.collectAsState().value
    val baseInfo = baseInfoViewModel.baseInfo.collectAsState().value
    val lastMetric = healthMetricViewModel.lastMetric.collectAsState(initial = null).value
    val rootNavController = rememberNavController()
    MainNavigation(
        navController = rootNavController,
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
    )
}