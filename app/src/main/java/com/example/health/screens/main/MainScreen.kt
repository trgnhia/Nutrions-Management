package com.example.health.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.health.navigation.BottomNavItem
import com.example.health.navigation.BottomNavigationBar
import com.example.health.navigation.graph.*
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.navigation.routes.ProfileRoutes
import com.example.health.navigation.routes.StatisticalRoutes
import com.example.health.navigation.routes.WorkoutRoutes

@Composable
fun MainScreen(rootNavController: NavController) {
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
            diaryNavGraph(bottomNavController)
            workoutNavGraph(bottomNavController)
            planNavGraph(bottomNavController)
            statisticalNavGraph(bottomNavController)
            profileNavGraph(bottomNavController)
        }
    }
}
