package com.example.health.navigation.graph.child

import VeganDetailsScreen
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.vegan.*

fun NavGraphBuilder.veganNavGraph(
    navController: NavController,
    baseInfoViewModel: BaseInfoViewModel,
    viewModel: DefaultDietMealInPlanViewModel,
    dietDishViewModel: DietDishViewModel
) {
    navigation(
        route = GraphRoute.Vegan.route,
        startDestination = PlanRoutes.Vegan.route
    ) {
        composable(PlanRoutes.Vegan.route) {
            VeganMainScreen(navController, baseInfoViewModel)
        }

        composable(PlanRoutes.VeganPlan.route) {
            VeganPlanScreen(navController, viewModel)
        }

        composable(
            route = PlanRoutes.VeganPlanDetail.route,
            arguments = listOf(navArgument("mealPlanId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealPlanId = backStackEntry.arguments?.getString("mealPlanId") ?: return@composable
            VeganDetailsScreen(navController, dietDishViewModel, mealPlanId)
        }
    }
}
