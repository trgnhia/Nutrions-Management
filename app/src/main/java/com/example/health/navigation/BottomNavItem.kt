package com.example.health.navigation

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable


import androidx.compose.ui.res.painterResource
import com.example.health.R
import com.example.health.navigation.routes.GraphRoute

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
) {
    object Diary : BottomNavItem(
        route = GraphRoute.Diary.route,
        label = "Diary",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_diet),
                contentDescription = "Diary"
            )
        }
    )

    object Workout : BottomNavItem(
        route = GraphRoute.Workout.route,
        label = "Workout",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_workout),
                contentDescription = "Workout"
            )
        }
    )

    object Plan : BottomNavItem(
        route = GraphRoute.Plan.route,
        label = "Plan",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_recipe),
                contentDescription = "Plan"
            )
        }
    )

    object Statistical : BottomNavItem(
        route = GraphRoute.Statistical.route,
        label = "Statistics",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_statistical),
                contentDescription = "Statistics"
            )
        }
    )

    object Profile : BottomNavItem(
        route = GraphRoute.Profile.route,
        label = "Profile",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile"
            )
        }
    )
}

