package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.screens.main.diary.Add
import com.example.health.screens.main.diary.Diary
import com.example.health.screens.main.diary.Info

fun NavGraphBuilder.diaryNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Diary.route,
        startDestination = DiaryRoutes.Diary.route
    ){
        composable(DiaryRoutes.Diary.route){
           Diary(navController)
        }
        composable(DiaryRoutes.Add.route){
            Add(navController)
        }
        composable(DiaryRoutes.Info.route){
            Info(navController)
        }
    }
}