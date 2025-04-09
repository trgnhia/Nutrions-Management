package com.example.health.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.health.data.local.viewmodel.*
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.screens.login.BaseInfoScreen
import com.example.health.screens.login.CalculatingScreen
import com.example.health.screens.login.HealthMetricScreen
import com.example.health.screens.login.LoginScreen
import com.example.health.screens.login.SplashScreen
import com.example.health.screens.main.HomeScreen

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    defaultFoodViewModel : DefaultFoodViewModel,
    defaultExerciseViewModel : DefaultExerciseViewModel
) {
    val navController = rememberNavController()

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

        composable("calculating") {
            CalculatingScreen(
                navController = navController,
                baseInfoViewModel = baseInfoViewModel
            )
        }

        composable("health_metric") {
            HealthMetricScreen(
                navController = navController,
                healthMetricViewModel = healthMetricViewModel
            )
        }

        composable("home") {
            HomeScreen(
                authViewModel = authViewModel,
                accountViewModel = accountViewModel,
                baseInfoViewModel = baseInfoViewModel,
                healthMetricViewModel = healthMetricViewModel,
                navController = navController
            )
        }
    }
}
