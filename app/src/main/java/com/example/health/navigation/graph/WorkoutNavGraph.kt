package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.WorkoutRoutes
import com.example.health.screens.main.workout.Sync
import com.example.health.screens.main.workout.Workout

fun NavGraphBuilder.workoutNavGraph(
    navController: NavController,
    defaultExerciseViewModel : DefaultExerciseViewModel,
    exerciseLogViewModel : ExerciseLogViewModel,
    burnOutCaloPerDayViewModel : BurnOutCaloPerDayViewModel,
    customExerciseViewModel : DefaultExerciseViewModel,
) {
    navigation(
        route = GraphRoute.Workout.route,
        startDestination = WorkoutRoutes.Workout.route
    ){
        composable(WorkoutRoutes.Workout.route){
            Workout(navController , defaultExerciseViewModel , exerciseLogViewModel , burnOutCaloPerDayViewModel , customExerciseViewModel)
        }
        composable(WorkoutRoutes.Sync.route){
            Sync(navController)
        }
    }
}