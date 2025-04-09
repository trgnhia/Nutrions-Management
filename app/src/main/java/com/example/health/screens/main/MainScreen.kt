package com.example.health.screens.main

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.health.navigation.BottomNavItem
import com.example.health.navigation.BottomNavigationBar
import com.example.health.navigation.graph.diaryNavGraph
import com.example.health.navigation.graph.planNavGraph
import com.example.health.navigation.graph.profileNavGraph
import com.example.health.navigation.graph.statisticalNavGraph
import com.example.health.navigation.graph.workoutNavGraph
import com.example.health.navigation.routes.PlanRoutes

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

//
//@Composable
//fun MainScreen(rootNavController: NavController) {
//    val bottomNavController = rememberNavController()
//
//    val bottomItems = listOf(
//        BottomNavItem.Diary,
//        BottomNavItem.Workout,
//        BottomNavItem.Plan,
//        BottomNavItem.Statistical,
//        BottomNavItem.Profile
//    )
//
//    val noBottomBarRoutes = listOf(
//        "plan/type",
//        "vegan", "vegan/plan", "vegan/plan/detail",
//        "highProtein", "highProtein/plan", "highProtein/plan/detail",
//        "keto", "keto/plan", "keto/plan/detail"
//    )
////    val noBottomBarRoutes = listOf(
////        PlanRoutes.Type.route,          // chính là "plan/type"
////        PlanRoutes.Vegan.route,
////        PlanRoutes.VeganPlan.route,
////        PlanRoutes.VeganPlanDetail.route,
////        PlanRoutes.HighProtein.route,
////        PlanRoutes.HighProteinPlan.route,
////        PlanRoutes.HighProteinPlanDetail.route,
////        PlanRoutes.Keto.route,
////        PlanRoutes.KetoPlan.route,
////        PlanRoutes.KetoPlanDetail.route
////    )
//
//    // 🔥 Sửa điểm này: dùng rootNavController để kiểm tra route
//    val currentRoute = rootNavController.currentBackStackEntryAsState().value?.destination?.route
//    val showBottomBar = currentRoute !in noBottomBarRoutes
//
//    Scaffold(
//        bottomBar = {
//            if (showBottomBar) {
//                BottomNavigationBar(bottomNavController, bottomItems)
//            }
//        }
//    ) { padding ->
//        NavHost(
//            navController = bottomNavController,
//            startDestination = BottomNavItem.Diary.route,
//            modifier = Modifier.padding(padding)
//        ) {
//            diaryNavGraph(bottomNavController)
//            workoutNavGraph(bottomNavController)
//            planNavGraph(bottomNavController)
//            statisticalNavGraph(bottomNavController)
//            profileNavGraph(bottomNavController)
//        }
//    }
//    Log.d("ROUTE", "Current route: $currentRoute")
//
//}
