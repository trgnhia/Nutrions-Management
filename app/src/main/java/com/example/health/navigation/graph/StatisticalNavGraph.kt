package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.StatisticalRoutes
import com.example.health.screens.main.statistical.Analysis
import com.example.health.screens.main.statistical.Statistical

fun NavGraphBuilder.statisticalNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Statistical.route,
        startDestination = StatisticalRoutes.Statistical.route
    ){
        composable(StatisticalRoutes.Statistical.route){
            Statistical(navController)
        }
        composable(StatisticalRoutes.Analysis.route){
            Analysis(navController)
        }


    }
}