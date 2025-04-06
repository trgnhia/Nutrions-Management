package com.example.health.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.health.navigation.BottomNavItem
import com.example.health.navigation.BottomNavigationBar
import com.example.health.navigation.graph.diaryNavGraph
import com.example.health.navigation.graph.planNavGraph
import com.example.health.navigation.graph.profileNavGraph
import com.example.health.navigation.graph.statisticalNavGraph
import com.example.health.navigation.graph.workoutNavGraph

@Composable
fun MainScreen(rootNavController: NavController){
    val bottomNavController = rememberNavController()
    val bottomItems = listOf(BottomNavItem.Diary, BottomNavItem.Workout, BottomNavItem.Plan, BottomNavItem.Statistical, BottomNavItem.Profile)
    Scaffold(
        bottomBar = {
            BottomNavigationBar(bottomNavController, bottomItems) // bottom nav
        }
    ) { padding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Diary.route, // graph của Home sẽ l cái ầu tiên
            modifier = Modifier.padding(padding)
        ) {
            // khai báo các graph được sinh ra từ đây ( graph sẽ gọi các compose nhưng nó ko phải compose)
//            homeNavGraph(bottomNavController)
//            profileNavGraph(bottomNavController)
            diaryNavGraph(bottomNavController)
            workoutNavGraph(bottomNavController)
            planNavGraph(bottomNavController)
            statisticalNavGraph(bottomNavController)
            profileNavGraph(bottomNavController)
        }
    }

}