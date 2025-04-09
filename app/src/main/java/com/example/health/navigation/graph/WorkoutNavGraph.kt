package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.WorkoutRoutes
import com.example.health.screens.main.workout.Sync
import com.example.health.screens.main.workout.Workout

fun NavGraphBuilder.workoutNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Workout.route,
        startDestination = WorkoutRoutes.Workout.route
    ){
        composable(WorkoutRoutes.Workout.route){
            Workout(navController)
        }
        composable(WorkoutRoutes.Sync.route){
            Sync(navController)
        }
    }
}