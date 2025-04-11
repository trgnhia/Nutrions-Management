package com.example.health.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.health.data.local.entities.Macro
import com.example.health.data.local.viewmodel.*
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.data.utils.MacroCalculator
import com.example.health.data.utils.toStartOfDay
import com.example.health.navigation.MainNavigation

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    navController: NavController,
    defaultFoodViewModel : DefaultFoodViewModel,
    defaultExerciseViewModel : DefaultExerciseViewModel,
    defaultDietMealInPlanViewModel : DefaultDietMealInPlanViewModel,
    macroViewModel : MacroViewModel,
    totalNutrionsPerDayViewModel : TotalNutrionsPerDayViewModel,
    exerciseLogViewModel : ExerciseLogViewModel,
    eatenMealViewModel : EatenMealViewModel,
    eatenDishViewModel : EatenDishViewModel,
    burnOutCaloPerDayViewModel : BurnOutCaloPerDayViewModel,
    customFoodViewModel : CustomFoodViewModel,
    customExerciseViewModel : CustomExerciseViewModel,
    //notifyViewModel : NotifyViewModel,
    //dietDishViewModel : DietDishViewModel
) {
    val account = accountViewModel.account.collectAsState().value
    val rootNavController = rememberNavController()
    val lastMetric by healthMetricViewModel.lastMetric.collectAsState()
    val macro by macroViewModel.macro.collectAsState()

    LaunchedEffect(account) {
        account?.Uid?.let { uid ->
            val today = java.util.Date().toStartOfDay()

            // TOTAL NUTRITIONS
            val totalExists = totalNutrionsPerDayViewModel.getByDateAndUidOnce(today, uid)
            if (totalExists == null) {
                totalNutrionsPerDayViewModel.insert(
                    com.example.health.data.local.entities.TotalNutrionsPerDay(
                        id = com.example.health.data.utils.IdUtil.generateId(),
                        Date = today,
                        Uid = uid,
                        TotalCalo = 0f,
                        TotalPro = 0f,
                        TotalCarb = 0f,
                        TotalFat = 0f,
                        DietType = 0
                    )
                )
            }

            // BURN OUT CALO
            val burnExists = burnOutCaloPerDayViewModel.getByDate(today)
            if (burnExists == null) {
                burnOutCaloPerDayViewModel.insert(
                    com.example.health.data.local.entities.BurnOutCaloPerDay(
                        DateTime = today,
                        TotalCalo = 0,
                        Uid = uid
                    )
                )
            }
//            // ✅ CHECK MACRO
//            if (macroViewModel.macro.value == null) {
//                val baseInfo = baseInfoViewModel.baseInfo.value
//                baseInfo?.let {
//                    val tdee = healthMetricViewModel.lastMetric.value?.TDEE ?: 2000f
//                    val result = MacroCalculator.calculateMacros(
//                        tdee = tdee.toInt(),
//                        carbPercent = 40f,
//                        proteinPercent = 35f,
//                        fatPercent = 25f
//                    )
//
//                    val macro = Macro(
//                        Uid = uid,
//                        Calo = result.carbInGrams,
//                        Protein = result.proteinInGrams,
//                        Fat = result.fatInGrams,
//                        Carb = result.carbInGrams,
//                        TDEE = tdee
//                    )
//                    macroViewModel.insert(macro)
//                }
//            }
        }}
        LaunchedEffect(account?.Uid, lastMetric, macro) {
            val uid = account?.Uid
            val tdee = lastMetric?.TDEE

            if (uid != null && tdee != null && macro == null) {
                val result = MacroCalculator.calculateMacros(
                    tdee = tdee.toInt(),
                    carbPercent = 40f,
                    proteinPercent = 35f,
                    fatPercent = 25f
                )

                val newMacro = Macro(
                    Uid = uid,
                    Calo = result.carbInGrams,
                    Protein = result.proteinInGrams,
                    Fat = result.fatInGrams,
                    Carb = result.carbInGrams,
                    TDEE = tdee
                )

                macroViewModel.insert(newMacro)
            }
    }

    MainNavigation(
        navController = rootNavController,
        authViewModel = authViewModel,
        accountViewModel = accountViewModel,
        baseInfoViewModel = baseInfoViewModel,
        healthMetricViewModel = healthMetricViewModel,
        defaultFoodViewModel = defaultFoodViewModel,
        defaultExerciseViewModel = defaultExerciseViewModel,
        defaultDietMealInPlanViewModel = defaultDietMealInPlanViewModel,
        macroViewModel = macroViewModel,
        totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
        exerciseLogViewModel = exerciseLogViewModel,
        eatenMealViewModel = eatenMealViewModel,
        eatenDishViewModel = eatenDishViewModel,
        burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel,
        customFoodViewModel  = customFoodViewModel,
        customExerciseViewModel = customExerciseViewModel,
        //notifyViewModel = notifyViewModel,
        //dietDishViewModel = dietDishViewModel
    )
}
