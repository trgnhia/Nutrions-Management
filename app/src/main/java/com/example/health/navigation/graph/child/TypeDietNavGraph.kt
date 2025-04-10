package com.example.health.navigation.graph.child

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.navigation.routes.GraphRoute

fun NavGraphBuilder.typeDietNavGraph(navController: NavController, baseInfoViewModel: BaseInfoViewModel,viewModel: DefaultDietMealInPlanViewModel) {
    navigation(
        route = GraphRoute.Type.route,
        startDestination = GraphRoute.Vegan.route
    ){
        veganNavGraph(navController, baseInfoViewModel,viewModel)
        highProteinNavGraph(navController,baseInfoViewModel)
        ketoNavGraph(navController,baseInfoViewModel)
    }
}