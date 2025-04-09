package com.example.health.navigation.graph.child

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.Food
import com.example.health.screens.main.plan.Plan

fun NavGraphBuilder.foodNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Food.route,
        startDestination = PlanRoutes.Food.route
    ){
        composable(PlanRoutes.Food.route){
            Food(navController)
        }

    }
}