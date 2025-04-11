package com.example.health.navigation.graph.child

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.CustomFoodViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.diary.DiaryAdd
import com.example.health.screens.main.diary.compose.MealType
import com.example.health.screens.main.diary.compose.ViewMore
import com.example.health.screens.main.plan.Food
import com.example.health.screens.main.plan.Plan

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.foodNavGraph(
    navController: NavController,
    defaultFoodViewModel: DefaultFoodViewModel,
    eatenMealViewModel: EatenMealViewModel,
    eatenDishViewModel: EatenDishViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    customFoodViewModel: CustomFoodViewModel,
    accountViewModel: AccountViewModel
) {
    navigation(
        route = GraphRoute.Food.route,
        startDestination = "${DiaryRoutes.Add}?parent=from_plan"
    ){
        composable(
            route = "${DiaryRoutes.Add}?parent={parent}&mealType={mealType}&selectedDay={selectedDay}",
            arguments = listOf(
                navArgument("parent") { nullable = true; defaultValue = null },
                navArgument("mealType") {  defaultValue = 1 } // default = MORNING
                ,
                navArgument("selectedDay") { type = androidx.navigation.NavType.LongType; defaultValue = 0L }
            )
        ) { backStackEntry ->
            val parent = backStackEntry.arguments?.getString("parent") ?: "unknown"
            val mealType = backStackEntry.arguments?.getInt("mealType") ?: 1
            val selectedDay = backStackEntry.arguments?.getLong("selectedDay") ?: 0L
            DiaryAdd(
                navController = navController,
                parent = parent,
                mealType =mealType,
                defaultFoodViewModel = defaultFoodViewModel,
                eatenMealViewModel = eatenMealViewModel,
                eatenDishViewModel = eatenDishViewModel,
                baseInfoViewModel = baseInfoViewModel,
                totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                customFoodViewModel = customFoodViewModel,
                accountViewModel = accountViewModel,
                selectedDay = selectedDay
            )
        }
    }
    composable(
        route = "${DiaryRoutes.ViewMore.route}?parent={parent}&foodtype={foodType}&mealType={mealType}&selectedDay={selectedDay}",
        arguments = listOf(
            navArgument("parent") { nullable = true; defaultValue = null },
            navArgument("foodType") { defaultValue = 1 },
            navArgument("mealType") { defaultValue = 1 }, // truyền kèm mealType
            navArgument("selectedDay") { type = androidx.navigation.NavType.LongType; defaultValue = 0L }
        )
    ) { backStackEntry ->
        val parent = backStackEntry.arguments?.getString("parent") ?: "unknown"
        val foodType = backStackEntry.arguments?.getInt("foodType") ?: 1
        val mealType = backStackEntry.arguments?.getInt("mealType") ?: 1
        val selectedDay = backStackEntry.arguments?.getLong("selectedDay") ?: 0L

        ViewMore(
            navController = navController,
            foodType = foodType,
            defaultFoodViewModel = defaultFoodViewModel,
            eatenMealViewModel = eatenMealViewModel,
            eatenDishViewModel = eatenDishViewModel,
            baseInfoViewModel = baseInfoViewModel,
            totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
            accountViewModel = accountViewModel,
            parent = parent,
            mealType = mealType,
            selectedDay = selectedDay

        )
    }
}