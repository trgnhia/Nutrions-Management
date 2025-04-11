package com.example.health.navigation.graph

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.local.viewmodel.MacroViewModel
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.ProfileRoutes
import com.example.health.screens.main.profile.MacroSetting
import com.example.health.screens.main.profile.Profile
import com.example.health.screens.main.profile.Reminder
import com.example.health.screens.main.profile.UpdateBodyIndex

fun NavGraphBuilder.profileNavGraph(
    navController: NavController,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    macroViewModel: MacroViewModel,
    accountViewModel: com.example.health.data.local.viewmodel.AccountViewModel // ✅ thêm dòng này
) {
    navigation(
        route = GraphRoute.Profile.route,
        startDestination = ProfileRoutes.ProfileWithWeight
    ) {
        composable(
            route = ProfileRoutes.ProfileWithWeight,
            arguments = listOf(
                navArgument("weight") {
                    type = NavType.FloatType
                    defaultValue = 0f
                }
            )
        ) { backStackEntry ->
            val weight = backStackEntry.arguments?.getFloat("weight") ?: 0f
            val baseInfo by baseInfoViewModel.baseInfo.collectAsState()
            val account by accountViewModel.account.collectAsState() // ✅ lấy account

            val userName = baseInfo?.Name ?: "User"
            val userEmail = account?.Email ?: "Unknown Email" // ✅ sửa lỗi ở đây

            Profile(
                navController = navController,
                currentWeight = weight,
                userName = userName,
                userEmail = userEmail,
                healthMetricViewModel = healthMetricViewModel
            )
        }

        // Không đổi các phần còn lại
        composable(ProfileRoutes.MacroSetting) {
            MacroSetting(navController)
        }

        composable(ProfileRoutes.UpdateBodyIndex) {
            val baseInfo by baseInfoViewModel.baseInfo.collectAsState()
            val healthMetric by healthMetricViewModel.lastMetric.collectAsState()

            val height = (baseInfo?.Height ?: 160f).toInt()
            val currentWeight = (healthMetric?.Weight ?: 60f).toInt()
            val targetWeight = (healthMetric?.WeightTarget ?: 55f).toInt()
            val activityLevel = baseInfo?.ActivityLevel ?: 3
            val age = baseInfo?.Age ?: 20
            val gender = baseInfo?.Gender ?: "Male"
            val bmi = healthMetric?.BMI ?: 22.0f
            val bmr = healthMetric?.BMR ?: 1600f
            val tdee = healthMetric?.TDEE ?: 2200f

            val trainingLabel = when (activityLevel) {
                1 -> "Low"
                2 -> "Lightly"
                3 -> "Moderate"
                4 -> "High"
                5 -> "Extreme"
                else -> "Moderate"
            }

            UpdateBodyIndex(
                navController = navController,
                heightDefault = height,
                currentWeightDefault = currentWeight,
                targetWeightDefault = targetWeight,
                trainingLevelDefault = trainingLabel,
                age = age,
                gender = gender,
                bmi = bmi,
                bmr = bmr,
                tdee = tdee,
                healthMetricViewModel = healthMetricViewModel
            )
        }

        composable(ProfileRoutes.Reminder) {
            Reminder(navController)
        }
    }
}

