package com.example.health.screens.main.diary

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.data.local.entities.BurnOutCaloPerDay
import com.example.health.data.local.viewmodel.*
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.screens.main.diary.compose.HeaderSection
import com.example.health.screens.main.diary.compose.MealTabs
import com.example.health.screens.main.diary.compose.MealType
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryMainScreen(
    navController: NavController,
    accountViewModel: AccountViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
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
) {
    val selectedDay = remember {
        mutableStateOf(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
    }
    val selectedMeal = remember { mutableStateOf(MealType.MORNING) }

    val accountState = accountViewModel.account.collectAsState()
    val account = accountState.value

    if (account == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Đang tải tài khoản...")
        }
        return
    }

    val uid = account.Uid
    val baseInfo = baseInfoViewModel.baseInfo.collectAsState()
    val isDiet = baseInfo.value?.IsDiet

    val healthMetric = healthMetricViewModel.lastMetric.collectAsState()
    val macro = macroViewModel.macro.collectAsState()

    val totalNutrionsPerDay = totalNutrionsPerDayViewModel.getByDateAndUid(
        selectedDay.value, uid
    ).collectAsState()

    val burnOutCaloPerDay = produceState<BurnOutCaloPerDay?>(
        initialValue = null, selectedDay.value
    ) {
        value = burnOutCaloPerDayViewModel.getByDate(selectedDay.value)
    }

    val eatenDish = eatenDishViewModel.getByDateAndType(
        selectedDay.value, selectedMeal.value.type
    ).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeaderSection(
            selectedDate = selectedDay.value,
            onDateChange = { selectedDay.value = it },
            healthMetricViewModel = healthMetricViewModel,
            macroViewModel = macroViewModel,
            totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
            burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel
        )

        MealTabs(
            meals = MealType.entries.map { it.label },
            selectedMeal = selectedMeal.value.label,
            onMealChange = {
                selectedMeal.value = MealType.fromLabel(it)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Danh sách món ăn: ${selectedMeal.value.label}",
            modifier = Modifier.padding(16.dp)
        )

        // TODO: hiển thị danh sách món ăn từ eatenDish.value ở đây
    }
}

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "This is diary main screen")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            navController.navigate(DiaryRoutes.Add.route)
//        }) {
//            Text("Nav to Add")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(onClick = {
//            navController.navigate(DiaryRoutes.Info.route)
//        }) {
//            Text("Nav to Info")
//        }
//    }
