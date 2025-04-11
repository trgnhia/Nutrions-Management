package com.example.health.navigation.graph.child

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.keto.KetoDetailsScreen
import com.example.health.screens.main.plan.keto.KetoMainScreen
import com.example.health.screens.main.plan.keto.KetoPlanScreen

fun NavGraphBuilder.ketoNavGraph(
    navController: NavController,
    baseInfoViewModel: BaseInfoViewModel,
    defaultDietMealInPlanViewModel: DefaultDietMealInPlanViewModel,
    dietDishViewModel: DietDishViewModel
) {
    navigation(
        route = GraphRoute.Keto.route,
        startDestination = PlanRoutes.Keto.route
    ) {
        composable(PlanRoutes.Keto.route) {
            KetoMainScreen(navController, baseInfoViewModel)
        }
        composable(PlanRoutes.KetoPlan.route) {
            KetoPlanScreen(navController, defaultDietMealInPlanViewModel)
        }
        composable(
            PlanRoutes.KetoPlanDetail.route,
            arguments = listOf(navArgument("mealPlanId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealPlanId = backStackEntry.arguments?.getString("mealPlanId") ?: ""
            KetoDetailsScreen(
                navController = navController,
                viewModel = dietDishViewModel,
                mealPlanId = mealPlanId
            )
        }
    }
}
