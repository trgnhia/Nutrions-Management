package com.example.health.navigation.graph.child

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.keto.Details
import com.example.health.screens.main.plan.keto.Keto
import com.example.health.screens.main.plan.keto.Plan

fun NavGraphBuilder.ketoNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Keto.route,
        startDestination = PlanRoutes.Keto.route
    ){
        composable(PlanRoutes.Keto.route){
            Keto(navController)
        }
        composable(PlanRoutes.KetoPlan.route){
            Plan(navController)
        }
        composable(PlanRoutes.KetoPlanDetail.route) {
            Details(navController)
        }

    }
}