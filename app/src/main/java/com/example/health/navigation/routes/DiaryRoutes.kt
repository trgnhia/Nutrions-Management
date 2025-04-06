package com.example.health.navigation.routes

sealed class DiaryRoutes(val route: String ){
    object Diary : DiaryRoutes("diary")
    object Add : DiaryRoutes("diary/add")
    object Info : DiaryRoutes("diary/info")
}

