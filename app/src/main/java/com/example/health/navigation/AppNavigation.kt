package com.example.health.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.health.data.initializer.fetchAllDefaultData
import com.example.health.data.local.viewmodel.*
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.screens.login.BaseInfoScreen
import com.example.health.screens.login.HealthMetricScreen
import com.example.health.screens.login.LoginScreen
import com.example.health.screens.login.SplashScreen
import com.example.health.screens.main.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
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
    //notifyViewModel : NotifyViewModel,
    //dietDishViewModel : DietDishViewModel
    notifyViewModel : NotifyViewModel,
    dietDishViewModel : DietDishViewModel

) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(
                navController = navController,
                authViewModel = authViewModel,
                accountViewModel = accountViewModel,
                baseInfoViewModel = baseInfoViewModel,
                healthMetricViewModel = healthMetricViewModel,
                defaultFoodViewModel = defaultFoodViewModel,
                defaultExerciseViewModel = defaultExerciseViewModel
            )
        }

        composable("login") {
            LoginScreen(authViewModel = authViewModel, navController = navController)
        }

        composable("base_info") {
            BaseInfoScreen(
                authViewModel = authViewModel,
                baseInfoViewModel = baseInfoViewModel,
                navController = navController,
                healthMetricViewModel = healthMetricViewModel
            )
        }

//        composable("calculating") {
//            CalculatingScreen(
//                navController = navController,
//                baseInfoViewModel = baseInfoViewModel
//            )
//        }

        composable("health_metric") {
            HealthMetricScreen(
                navController = navController,
                baseInfoViewModel = baseInfoViewModel,
                healthMetricViewModel = healthMetricViewModel,
                macroViewModel = macroViewModel,
                notifyViewModel = notifyViewModel,
                onLoadData = {
                    fetchAllDefaultData(
                        context = context,
                        defaultFoodViewModel = defaultFoodViewModel,
                        defaultExerciseViewModel = defaultExerciseViewModel,

                    )
                }
            )
        }

        composable("home") {
            HomeScreen(
                authViewModel = authViewModel,
                accountViewModel = accountViewModel,
                baseInfoViewModel = baseInfoViewModel,
                healthMetricViewModel = healthMetricViewModel,
                navController = navController,
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
                dietDishViewModel = dietDishViewModel
            )
        }
    }
}
