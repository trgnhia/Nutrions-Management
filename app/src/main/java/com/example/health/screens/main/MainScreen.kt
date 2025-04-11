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
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.CustomFoodViewModel
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.local.viewmodel.MacroViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.navigation.BottomNavItem
import com.example.health.navigation.BottomNavigationBar
import com.example.health.navigation.graph.*
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.navigation.routes.ProfileRoutes
import com.example.health.navigation.routes.StatisticalRoutes
import com.example.health.navigation.routes.WorkoutRoutes

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    rootNavController: NavController,
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
) {
    val bottomNavController = rememberNavController()
    val bottomItems = listOf(
        BottomNavItem.Diary,
        BottomNavItem.Workout,
        BottomNavItem.Plan,
        BottomNavItem.Statistical,
        BottomNavItem.Profile
    )

    // 🔍 Lấy route hiện tại để xác định có hiển thị BottomBar không
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = when (currentRoute) {
        DiaryRoutes.Diary.route,
        WorkoutRoutes.Workout.route,
        PlanRoutes.Plan.route,
        StatisticalRoutes.Statistical.route,
        ProfileRoutes.Profile.route -> true
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
                navController =bottomNavController,
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
                customFoodViewModel = customFoodViewModel,
            )
            workoutNavGraph(
                navController = bottomNavController,
                defaultExerciseViewModel = defaultExerciseViewModel,
                exerciseLogViewModel = exerciseLogViewModel,
                burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel,
            )
            planNavGraph(
                navController =bottomNavController,
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
                customFoodViewModel = customFoodViewModel,
            )
            statisticalNavGraph(bottomNavController)
            profileNavGraph(
                navController = bottomNavController,
                baseInfoViewModel = baseInfoViewModel,
                healthMetricViewModel = healthMetricViewModel,
                macroViewModel = macroViewModel,
            )
        }
    }
}
