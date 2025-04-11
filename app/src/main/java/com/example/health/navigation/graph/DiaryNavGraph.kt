package com.example.health.navigation.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.CustomFoodViewModel
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.local.viewmodel.MacroViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.screens.main.diary.DiaryAdd
import com.example.health.screens.main.diary.DiaryMainScreen
import com.example.health.screens.main.diary.DiaryInfo
import com.example.health.screens.main.diary.compose.MealType
import com.example.health.screens.main.diary.compose.ViewMore

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.diaryNavGraph(
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
    dietDishViewModel: DietDishViewModel

    ) {
    navigation(
        route = GraphRoute.Diary.route,
        startDestination = DiaryRoutes.Diary.route
    ){
        composable(DiaryRoutes.Diary.route){
           DiaryMainScreen(
               navController = navController,
               accountViewModel = accountViewModel,
               baseInfoViewModel = baseInfoViewModel,
               healthMetricViewModel = healthMetricViewModel,
               defaultFoodViewModel = defaultFoodViewModel,
               defaultExerciseViewModel = defaultExerciseViewModel,
               defaultDietMealInPlanViewModel = defaultDietMealInPlanViewModel,
               macroViewModel = macroViewModel,
               totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
               exerciseLogViewModel = exerciseLogViewModel,
               eatenMealViewModel = eatenMealViewModel,
               eatenDishViewModel = eatenDishViewModel,
               burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel,
               customFoodViewModel = customFoodViewModel,
               dietDishViewModel = dietDishViewModel
           )
        }
        composable(
            route = "${DiaryRoutes.Add}?parent={parent}&mealType={mealType}",
            arguments = listOf(
                navArgument("parent") { nullable = true; defaultValue = null },
                navArgument("mealType") {  defaultValue = 1 } // default = MORNING
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
                totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                customFoodViewModel = customFoodViewModel,
                accountViewModel = accountViewModel
            )
        }
        composable(DiaryRoutes.Info.route){
            DiaryInfo(navController)
        }
        composable(
            route = "${DiaryRoutes.ViewMore.route}?parent={parent}&foodtype={foodType}&mealType={mealType}",
            arguments = listOf(
                navArgument("parent") { nullable = true; defaultValue = null },
                navArgument("foodType") { defaultValue = 1 },
                navArgument("mealType") { defaultValue = 1 } // truyền kèm mealType
            )
        ) { backStackEntry ->
            val parent = backStackEntry.arguments?.getString("parent") ?: "unknown"
            val foodType = backStackEntry.arguments?.getInt("foodType") ?: 1
            val mealType = backStackEntry.arguments?.getInt("mealType") ?: 1

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
                mealType = mealType
            )
        }

    }
}