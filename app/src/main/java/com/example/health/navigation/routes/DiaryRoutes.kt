package com.example.health.navigation.routes

sealed class DiaryRoutes(val route: String ){
    object Diary : DiaryRoutes("diary")
    object Add : DiaryRoutes("diary/add")
    object Info : DiaryRoutes("diary/info")
    object ViewMore : DiaryRoutes("diary/view_more")
   // object DetailDiet : DiaryRoutes("diary/detail_diet/{id}")
    object DetailDefault : DiaryRoutes("diary/detail_default/{foodId}") {
        fun createRoute(foodId: String): String = "diary/detail_default/$foodId"
    }
    object DetailDiet : DiaryRoutes("diary/detail_diet/{id}?uid={uid}&mealType={mealType}&date={date}") {
        fun createRoute(id: String, uid: String, mealType: Int, date: Long): String {
            return "diary/detail_diet/$id?uid=$uid&mealType=$mealType&date=$date"
        }
    }

}

