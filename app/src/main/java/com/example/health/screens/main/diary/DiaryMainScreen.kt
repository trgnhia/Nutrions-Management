package com.example.health.screens.main.diary

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.data.local.entities.BurnOutCaloPerDay
import com.example.health.data.local.viewmodel.*
import com.example.health.data.utils.toStartOfDay
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.screens.main.ParenCompose
import com.example.health.screens.main.diary.compose.AddFoodCard
import com.example.health.screens.main.diary.compose.DietDishCardInDiary
import com.example.health.screens.main.diary.compose.FoodCard
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
    dietDishViewModel: DietDishViewModel
) {
    val selectedDay = remember {
        mutableStateOf(Date().toStartOfDay())
    }
    val selectedMeal = remember { mutableStateOf(MealType.MORNING) }

    val accountState = accountViewModel.account.collectAsState()
    val account = accountState.value

    if (account == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        }
        return
    }

    val baseInfo by baseInfoViewModel.baseInfo.collectAsState()
    val dietCode = baseInfo?.IsDiet ?: 0
    val startDate = baseInfo?.DietStartDate ?: 0L

    val dayIndex = if (startDate > 0) {
        ((selectedDay.value.time - startDate) / (1000 * 60 * 60 * 24)).toInt() + 1
    } else 1

    LaunchedEffect(selectedDay.value, dietCode) {
        if (dietCode != 0) {
            val mealIds = listOf("b", "l", "s", "d").map { meal -> "$dietCode${meal}$dayIndex" }
            dietDishViewModel.loadDishesForMealIds(mealIds)
        }
    }

    val dishes by dietDishViewModel.dishes.collectAsState()
    val foodList = eatenDishViewModel.getByDateAndType(
        selectedDay.value, selectedMeal.value.type
    ).collectAsState(initial = emptyList())

    val currentMealKey = when (selectedMeal.value) {
        MealType.MORNING -> "b"
        MealType.LUNCH -> "l"
        MealType.DINNER -> "d"
        MealType.SNACK -> "s"
    }

    val mealDishesFromPlan = dishes.filter { it.MealPlanId.contains(currentMealKey) }

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
            burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel,
            calorBurn = calorBurn
        )

        MealTabs(
            meals = MealType.entries.map { it.label },
            selectedMeal = selectedMeal.value.label,
            onMealChange = {
                selectedMeal.value = MealType.fromLabel(it)
            },
            selectedDay = selectedDay.value,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Food list",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (dietCode == 0) {
                // Trường hợp không theo chế độ ăn → món ăn do người dùng thêm
                items(foodList.value.size + 1) { index ->
                    if (index < foodList.value.size) {
                        FoodCard(index + 1, foodList.value[index] , selectedDay = selectedDay.value, onClick = {
                            navController.navigate(DiaryRoutes.Info.route)
                        })
                    } else {
                        if(selectedDay.value.equals(Date().toStartOfDay())){
                            AddFoodCard(onClick = {
                                navController.navigate("${DiaryRoutes.Add}?parent=${ParenCompose.FROMDIARY}&mealType=${selectedMeal.value.type}&selectedDay=${selectedDay.value.time}")
                            })
                            Log.e("Check day", "DiaryMainScreen: selectday: " + selectedDay.value, )
                            Log.e("Check day", "DiaryMainScreen: today: " + Date().toStartOfDay(), )
                        }
                    }
                }
            } else {
                // Trường hợp đang theo chế độ ăn → chỉ hiển thị các món ăn từ kế hoạch
                items(mealDishesFromPlan.size) { index ->
                    val dish = mealDishesFromPlan[index]
                    DietDishCardInDiary(dish = dish)
                }
            }
        }
    }
}

//package com.example.health.screens.main.diary
//
//import android.os.Build
//import android.util.Log
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.health.data.local.entities.BurnOutCaloPerDay
//import com.example.health.data.local.viewmodel.*
//import com.example.health.navigation.routes.DiaryRoutes
//import com.example.health.screens.main.ParenCompose
//import com.example.health.screens.main.diary.compose.AddFoodCard
//import com.example.health.screens.main.diary.compose.FoodCard
//import com.example.health.screens.main.diary.compose.HeaderSection
//import com.example.health.screens.main.diary.compose.MealTabs
//import com.example.health.screens.main.diary.compose.MealType
//import java.time.LocalDate
//import java.time.ZoneId
//import java.util.Date
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun DiaryMainScreen(
//    navController: NavController,
//    accountViewModel: AccountViewModel,
//    baseInfoViewModel: BaseInfoViewModel,
//    healthMetricViewModel: HealthMetricViewModel,
//    defaultFoodViewModel : DefaultFoodViewModel,
//    defaultExerciseViewModel : DefaultExerciseViewModel,
//    defaultDietMealInPlanViewModel : DefaultDietMealInPlanViewModel,
//    macroViewModel : MacroViewModel,
//    totalNutrionsPerDayViewModel : TotalNutrionsPerDayViewModel,
//    exerciseLogViewModel : ExerciseLogViewModel,
//    eatenMealViewModel : EatenMealViewModel,
//    eatenDishViewModel : EatenDishViewModel,
//    burnOutCaloPerDayViewModel : BurnOutCaloPerDayViewModel,
//    customFoodViewModel : CustomFoodViewModel,
//) {
//    val selectedDay = remember {
//        mutableStateOf(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
//    }
//    val selectedMeal = remember { mutableStateOf(MealType.MORNING) }
//
//    val accountState = accountViewModel.account.collectAsState()
//    val account = accountState.value
//
//    if (account == null) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text("Đang tải tài khoản...")
//        }
//        return
//    }
//    val uid = account.Uid
//    val baseInfo = baseInfoViewModel.baseInfo.collectAsState()
//    val isDiet = baseInfo.value?.IsDiet
//
//    val healthMetric = healthMetricViewModel.lastMetric.collectAsState()
//    val macro = macroViewModel.macro.collectAsState()
//
//    val totalNutrionsPerDay = totalNutrionsPerDayViewModel.getByDateAndUid(
//        selectedDay.value, uid
//    ).collectAsState()
//
//    val burnOutCaloPerDay = produceState<BurnOutCaloPerDay?>(
//        initialValue = null, selectedDay.value
//    ) {
//        value = burnOutCaloPerDayViewModel.getByDate(selectedDay.value)
//    }
//
//    val foodList = eatenDishViewModel.getByDateAndType(
//        selectedDay.value, selectedMeal.value.type
//    ).collectAsState(initial = emptyList())
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        HeaderSection(
//            selectedDate = selectedDay.value,
//            onDateChange = { selectedDay.value = it },
//            healthMetricViewModel = healthMetricViewModel,
//            macroViewModel = macroViewModel,
//            totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
//            burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel
//        )
//        MealTabs(
//            meals = MealType.entries.map { it.label },
//            selectedMeal = selectedMeal.value.label,
//            onMealChange = {
//                selectedMeal.value = MealType.fromLabel(it)
//            }
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            "Food list",
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//        )
//
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier.fillMaxSize().padding(12.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(foodList.value.size + 1) { index ->
//                if (index < foodList.value.size) {
//                    FoodCard(index + 1, foodList.value[index], onClick = {
//                        navController.navigate(DiaryRoutes.Info.route)
//                    })
//                } else {
//                    AddFoodCard(onClick = {
//                        navController.navigate("${DiaryRoutes.Add}?parent=${ParenCompose.FROMDIARY}&mealType=${selectedMeal.value.type}")
//                    })
//                }
//            }
//
//        }
//
//    }
//}