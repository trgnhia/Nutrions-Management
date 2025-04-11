package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.AccountViewModel
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
    macroViewModel : MacroViewModel,
    accountViewModel: AccountViewModel
    ) {
    navigation(
        route = GraphRoute.Profile.route,
        startDestination = ProfileRoutes.Profile.route
    ){
        composable(ProfileRoutes.Profile.route){
            Profile(
                navController,
                accountViewModel,
                baseInfoViewModel,
                macroViewModel,
                healthMetricViewModel
            )
        }
        composable(ProfileRoutes.MacroSetting.route){
            MacroSetting(navController, macroViewModel)
        }
        composable(ProfileRoutes.UpdateBodyIndex.route){
            UpdateBodyIndex(navController, baseInfoViewModel,healthMetricViewModel)
        }
        composable(ProfileRoutes.Reminder.route){
            Reminder(navController)
        }


    }
}