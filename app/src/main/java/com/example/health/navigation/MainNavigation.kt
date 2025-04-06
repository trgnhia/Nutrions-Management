package com.example.health.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.health.screens.main.MainScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    // đây là nav tổng quát nhất ( gọi toàn bộ ứng dụng luôn ( sẽ được gọi trong main))
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            // compose được khai báo ở nav này
            MainScreen(navController)
        }
    }
}