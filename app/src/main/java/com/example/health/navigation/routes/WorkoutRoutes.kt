package com.example.health.navigation.routes

sealed class WorkoutRoutes(val route: String ){
    object Workout: DiaryRoutes("workout")
    object Sync: DiaryRoutes("workout/sync")

}