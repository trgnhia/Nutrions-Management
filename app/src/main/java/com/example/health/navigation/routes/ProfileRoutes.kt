package com.example.health.navigation.routes

sealed class ProfileRoutes(val route: String){
    object Profile: ProfileRoutes("profile")
    object MacroSetting: ProfileRoutes("macro_setting")
    object UpdateBodyIndex: ProfileRoutes("update_body_index")
    object Reminder: ProfileRoutes("reminder")
}