package com.example.health.navigation.graph.child

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.Type


fun NavGraphBuilder.dietNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Diet.route,
        startDestination = PlanRoutes.Type.route
    ){
        composable(PlanRoutes.Type.route){
            Type(navController)
        }
        typeDietNavGraph(navController)
    }
}