package com.example.health.navigation


import com.example.health.R
import com.example.health.navigation.routes.GraphRoute

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val iconResSelected: Int,
    val iconResUnselected: Int
) {
    object Diary : BottomNavItem(
        route = GraphRoute.Diary.route,
        label = "Diary",
        iconResSelected = R.drawable.ic_diary_selected,
        iconResUnselected = R.drawable.ic_diary_unselected
    )

    object Workout : BottomNavItem(
        route = GraphRoute.Workout.route,
        label = "Workout",
        iconResSelected = R.drawable.ic_workout_selected,
        iconResUnselected = R.drawable.ic_workout_unselected
    )

    object Plan : BottomNavItem(
        route = GraphRoute.Plan.route,
        label = "Plan",
        iconResSelected = R.drawable.ic_plan_selected,
        iconResUnselected = R.drawable.ic_plan_unselected
    )

    object Statistical : BottomNavItem(
        route = GraphRoute.Statistical.route,
        label = "Statistics",
        iconResSelected = R.drawable.ic_statistical_selected,
        iconResUnselected = R.drawable.ic_statistical_unselected
    )

    object Profile : BottomNavItem(
        route = GraphRoute.Profile.route,
        label = "Profile",
        iconResSelected = R.drawable.ic_profile_selected,
        iconResUnselected = R.drawable.ic_profile_unselected
    )
}

