package com.example.health.navigation.routes

sealed class ProfileRoutes(val route: String){
    object Profile: ProfileRoutes("profile")
}