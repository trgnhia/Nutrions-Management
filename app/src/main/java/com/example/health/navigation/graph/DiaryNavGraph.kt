package com.example.health.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.screens.main.diary.DiaryAdd
import com.example.health.screens.main.diary.DiaryMainScreen
import com.example.health.screens.main.diary.DiaryInfo

fun NavGraphBuilder.diaryNavGraph(navController: NavController) {
    navigation(
        route = GraphRoute.Diary.route,
        startDestination = DiaryRoutes.Diary.route
    ){
        composable(DiaryRoutes.Diary.route){
           DiaryMainScreen(navController)
        }
        composable(DiaryRoutes.Add.route){
            DiaryAdd(navController)
        }
        composable(DiaryRoutes.Info.route){
            DiaryInfo(navController)
        }
    }
}