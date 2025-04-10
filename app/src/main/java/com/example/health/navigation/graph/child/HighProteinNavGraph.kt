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
import com.example.health.screens.main.plan.highprotein.*
import com.example.health.screens.main.plan.keto.KetoDetailsScreen

fun NavGraphBuilder.highProteinNavGraph(navController: NavController,baseInfoViewModel: BaseInfoViewModel,
                        defaultDietMealInPlanViewModel: DefaultDietMealInPlanViewModel,
                                        dietDishViewModel: DietDishViewModel) {
    navigation(
        route = GraphRoute.HighProtein.route,
        startDestination = PlanRoutes.HighProtein.route
    ){
        composable(PlanRoutes.HighProtein.route){
            HighProteinMainScreen(navController,baseInfoViewModel)
        }
        composable(PlanRoutes.HighProteinPlan.route){
            HighProteinPlanScreen(navController,defaultDietMealInPlanViewModel)
        }


        composable(
            PlanRoutes.HighProteinPlanDetail.route, // <-- route có {mealPlanId}
            arguments = listOf(navArgument("mealPlanId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealPlanId = backStackEntry.arguments?.getString("mealPlanId") ?: ""
            HighProteinDetailsScreen(
                navController = navController,
                viewModel = dietDishViewModel, // <-- truyền đúng ViewModel
                mealPlanId = mealPlanId
            )
        }

    }
}