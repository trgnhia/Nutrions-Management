package com.example.health.screens.main

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.*
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    navController: NavController,
    defaultFoodViewModel: DefaultFoodViewModel,
    defaultExerciseViewModel: DefaultExerciseViewModel,
    defaultDietMealInPlanViewModel: DefaultDietMealInPlanViewModel,
    macroViewModel: MacroViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    exerciseLogViewModel: ExerciseLogViewModel,
    eatenMealViewModel: EatenMealViewModel,
    eatenDishViewModel: EatenDishViewModel,
    burnOutCaloPerDayViewModel: BurnOutCaloPerDayViewModel,
    customFoodViewModel: CustomFoodViewModel,
    customExerciseViewModel: CustomExerciseViewModel,
    notifyViewModel: NotifyViewModel,
    dietDishViewModel: DietDishViewModel
) {
    val account = accountViewModel.account.collectAsState().value
    val rootNavController = rememberNavController()
    val lastMetric by healthMetricViewModel.lastMetric.collectAsState()
    val macro by macroViewModel.macro.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity

    val calorBurnToday = remember { mutableStateOf(0f) }

    // 🔹 1. Tự động fetch Google Fit nếu đã cấp quyền
    LaunchedEffect(account) {
        account?.Uid?.let { uid ->
            val today = Date().toStartOfDay()

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

            // GOOGLE FIT: kiểm tra quyền và lấy dữ liệu
            val fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                .build()

            val googleAccount = GoogleSignIn.getAccountForExtension(context, fitnessOptions)

            if (GoogleSignIn.hasPermissions(googleAccount, fitnessOptions)) {
                val end = ZonedDateTime.now().toInstant().toEpochMilli()
                val start = ZonedDateTime.now().with(LocalTime.MIN).toInstant().toEpochMilli()

                val request = DataReadRequest.Builder()
                    .read(DataType.TYPE_CALORIES_EXPENDED)
                    .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                    .build()

                Fitness.getHistoryClient(context, googleAccount)
                    .readData(request)
                    .addOnSuccessListener { response ->
                        var calories = 0f
                        for (dataSet in response.dataSets) {
                            for (dp in dataSet.dataPoints) {
                                if (dp.dataType == DataType.TYPE_CALORIES_EXPENDED) {
                                    calories += dp.getValue(Field.FIELD_CALORIES).asFloat()
                                }
                            }
                        }
                        Log.d("GoogleFit", "Fetched Google Fit calories: $calories")
                        calorBurnToday.value = calories
                    }
                    .addOnFailureListener {
                        Log.e("GoogleFit", "Failed to fetch calories", it)
                    }
            } else {
                Log.d("GoogleFit", "No permission → skip fetching")
            }
        }
    }

    // 🔹 2. Tự tạo Macro nếu chưa có
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

    // 🔹 3. Truyền `calorBurnToday` cho các màn cần dùng
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
        notifyViewModel = notifyViewModel,
        dietDishViewModel = dietDishViewModel,
        calorBurn = calorBurnToday // 👈 truyền đi
    )
}
