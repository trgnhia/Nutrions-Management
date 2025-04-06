package com.example.health.navigation.routes

sealed class PlanRoutes(val route: String){
    object Plan: PlanRoutes("plan")
    object Type: PlanRoutes("plan/type")
    object Food: PlanRoutes("food")

    object Vegan: PlanRoutes("vegan")
    object VeganPlan: PlanRoutes("vegan/plan")
    object VeganPlanDetail: PlanRoutes("vegan/plan/detail")

    object HighProtein : PlanRoutes("highProtein")
    object HighProteinPlan : PlanRoutes("highProtein/plan")
    object HighProteinPlanDetail : PlanRoutes("highProtein/plan/detail")

    object Keto : PlanRoutes("keto")
    object KetoPlan : PlanRoutes("keto/plan")
    object KetoPlanDetail : PlanRoutes("keto/plan/detail")
}