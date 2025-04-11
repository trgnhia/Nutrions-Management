package com.example.health.navigation.graph.child

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes

import com.example.health.screens.main.plan.vegan.*

fun NavGraphBuilder.veganNavGraph(navController: NavController, baseInfoViewModel: BaseInfoViewModel) {
    navigation(
        route = GraphRoute.Vegan.route,
        startDestination = PlanRoutes.Vegan.route
    ){
        composable(PlanRoutes.Vegan.route){
            VeganMainScreen(navController, baseInfoViewModel)
        }
        composable(PlanRoutes.VeganPlan.route){
            VeganPlanScreen(navController)
        }
        composable(PlanRoutes.VeganPlanDetail.route){
            VeganDetailsScreen(navController)
        }
    }
}