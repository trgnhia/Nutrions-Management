package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.graph.child.dietNavGraph
import com.example.health.navigation.graph.child.foodNavGraph
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.Plan

fun NavGraphBuilder.planNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Plan.route,
        startDestination = PlanRoutes.Plan.route
    ){
        composable(PlanRoutes.Plan.route){
            Plan(navController)
        }
        dietNavGraph(navController)

        foodNavGraph(navController)

    }
}