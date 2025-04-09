package com.example.health.navigation.routes

sealed class GraphRoute(val route: String) {
    object Diary : GraphRoute("diary_graph")
    object Workout : GraphRoute("workout_graph")
    object Plan : GraphRoute("plan_graph")
    object Statistical : GraphRoute("statistical_graph")
    object Profile : GraphRoute("profile_graph")

    object Diet : GraphRoute("diet_graph")
    object Food : GraphRoute("food_graph")
    object Type : GraphRoute("type_graph")
    object Vegan : GraphRoute("vegan_graph")
    object HighProtein : GraphRoute("highProtein_graph")
    object Keto : GraphRoute("keto_graph")


}
