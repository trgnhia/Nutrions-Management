package com.example.health.navigation.graph.child

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.diary.DiaryAdd
import com.example.health.screens.main.diary.compose.MealType
import com.example.health.screens.main.plan.Food
import com.example.health.screens.main.plan.Plan

fun NavGraphBuilder.foodNavGraph(
    navController: NavController,
    defaultFoodViewModel: DefaultFoodViewModel,
    eatenMealViewModel: EatenMealViewModel,
    eatenDishViewModel: EatenDishViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel
) {
    navigation(
        route = GraphRoute.Food.route,
        startDestination = "${DiaryRoutes.Add}?parent=from_plan"
    ){
        composable(
            route = "${DiaryRoutes.Add}?parent={parent}&mealType={mealType}",
            arguments = listOf(
                navArgument("parent") { nullable = true; defaultValue = null },
                navArgument("mealType") { defaultValue = 1 } // default = MORNING
            )
        ) { backStackEntry ->
            val parent = backStackEntry.arguments?.getString("parent") ?: "unknown"
            val mealType = backStackEntry.arguments?.getInt("mealType") ?: 1


            DiaryAdd(
                navController = navController,
                parent = parent,
                mealType =mealType,
                defaultFoodViewModel = defaultFoodViewModel,
                eatenMealViewModel = eatenMealViewModel,
                eatenDishViewModel = eatenDishViewModel,
                baseInfoViewModel = baseInfoViewModel,
                totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel
            )
        }
    }
}