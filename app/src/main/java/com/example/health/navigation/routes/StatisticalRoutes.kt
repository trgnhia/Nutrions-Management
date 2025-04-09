package com.example.health.navigation.routes

sealed class StatisticalRoutes(val route:String){
    object Statistical: StatisticalRoutes("statistical")
    object Analysis: StatisticalRoutes("statistical/analysis")
}