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
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.screens.main.diary.DiaryAdd
import com.example.health.screens.main.diary.DiaryMainScreen
import com.example.health.screens.main.diary.DiaryInfo

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
           )
        }
        composable(DiaryRoutes.Add.route){
            DiaryAdd(navController)
        }
        composable(DiaryRoutes.Info.route){
            DiaryInfo(navController)
        }
    }
}