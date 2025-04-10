package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.CustomFoodViewModel
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.local.viewmodel.MacroViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.navigation.graph.child.dietNavGraph
import com.example.health.navigation.graph.child.foodNavGraph
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.Plan

fun NavGraphBuilder.planNavGraph(
    navController: NavController,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    defaultFoodViewModel : DefaultFoodViewModel,
    defaultExerciseViewModel : DefaultExerciseViewModel,
    defaultDietMealInPlanViewModel : DefaultDietMealInPlanViewModel,
    macroViewModel : MacroViewModel,
    totalNutrionsPerDayViewModel : TotalNutrionsPerDayViewModel,
    exerciseLogViewModel : ExerciseLogViewModel,
    eatenMealViewModel : EatenMealViewModel,
    eatenDishViewModel : EatenDishViewModel,
    burnOutCaloPerDayViewModel : BurnOutCaloPerDayViewModel,
    customFoodViewModel : CustomFoodViewModel,
) {
    navigation(
        route = GraphRoute.Plan.route,
        startDestination = PlanRoutes.Plan.route
    ){
        composable(PlanRoutes.Plan.route){
            Plan(navController)
        }
        dietNavGraph(navController)

        foodNavGraph(
            navController = navController,
            defaultFoodViewModel = defaultFoodViewModel,
            eatenMealViewModel = eatenMealViewModel,
            eatenDishViewModel = eatenDishViewModel,
            baseInfoViewModel = baseInfoViewModel,
            totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel
            )

    }
}