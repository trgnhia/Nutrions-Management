package com.example.health.navigation.graph.child

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.highprotein.*

fun NavGraphBuilder.highProteinNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.HighProtein.route,
        startDestination = PlanRoutes.HighProtein.route
    ){
        composable(PlanRoutes.HighProtein.route){
            HighProtein(navController)
        }
        composable(PlanRoutes.HighProteinPlan.route){
            Plan(navController)
        }
        composable(PlanRoutes.HighProteinPlanDetail.route) {
            Details(navController)
        }

    }
}