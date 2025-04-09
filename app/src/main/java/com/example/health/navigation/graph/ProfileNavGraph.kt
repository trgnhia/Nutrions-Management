package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.ProfileRoutes
import com.example.health.screens.main.profile.MacroSetting
import com.example.health.screens.main.profile.Profile
import com.example.health.screens.main.profile.Reminder
import com.example.health.screens.main.profile.UpdateBodyIndex

fun NavGraphBuilder.profileNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Profile.route,
        startDestination = ProfileRoutes.Profile.route
    ){
        composable(ProfileRoutes.Profile.route){
            Profile(navController)
        }
        composable(ProfileRoutes.MacroSetting.route){
            MacroSetting(navController)
        }
        composable(ProfileRoutes.UpdateBodyIndex.route){
            UpdateBodyIndex(navController)
        }
        composable(ProfileRoutes.Reminder.route){
            Reminder(navController)
        }


    }
}