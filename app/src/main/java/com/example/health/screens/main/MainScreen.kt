package com.example.health.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.health.data.local.viewmodel.*
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.navigation.BottomNavItem
import com.example.health.navigation.BottomNavigationBar
import com.example.health.navigation.graph.*
import com.example.health.navigation.routes.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    rootNavController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    defaultFoodViewModel: DefaultFoodViewModel,
    defaultExerciseViewModel: DefaultExerciseViewModel,
    defaultDietMealInPlanViewModel: DefaultDietMealInPlanViewModel,
    macroViewModel: MacroViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    exerciseLogViewModel: ExerciseLogViewModel,
    eatenMealViewModel: EatenMealViewModel,
    eatenDishViewModel: EatenDishViewModel,
    burnOutCaloPerDayViewModel: BurnOutCaloPerDayViewModel,
    customFoodViewModel: CustomFoodViewModel,
) {
    val bottomNavController = rememberNavController()
    val bottomItems = listOf(
        BottomNavItem.Diary,
        BottomNavItem.Workout,
        BottomNavItem.Plan,
        BottomNavItem.Statistical,
        BottomNavItem.Profile
    )

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // ✅ Hiển thị BottomBar nếu route thuộc nhóm chính
    val showBottomBar = when {
        currentRoute == DiaryRoutes.Diary.route ||
                currentRoute == WorkoutRoutes.Workout.route ||
                currentRoute == PlanRoutes.Plan.route ||
                currentRoute == StatisticalRoutes.Statistical.route ||
                currentRoute?.startsWith("profile") == true -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(bottomNavController, bottomItems)
            }
        }
    ) { padding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Diary.route,
            modifier = Modifier.padding(padding)
        ) {
            diaryNavGraph(
                navController = bottomNavController,
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
                customFoodViewModel = customFoodViewModel
            )
            workoutNavGraph(
                navController = bottomNavController,
                defaultExerciseViewModel = defaultExerciseViewModel,
                exerciseLogViewModel = exerciseLogViewModel,
                burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel
            )
            planNavGraph(
                navController = bottomNavController,
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
                customFoodViewModel = customFoodViewModel
            )
            statisticalNavGraph(bottomNavController)
            profileNavGraph(
                navController = bottomNavController,
                accountViewModel = accountViewModel,
                baseInfoViewModel = baseInfoViewModel,
                healthMetricViewModel = healthMetricViewModel,
                macroViewModel = macroViewModel
            )
        }
    }
}
