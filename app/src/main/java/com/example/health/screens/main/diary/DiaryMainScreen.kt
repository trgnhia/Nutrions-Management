package com.example.health.screens.main.diary

import DietDishCard
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.data.local.entities.BurnOutCaloPerDay
import com.example.health.data.local.viewmodel.*
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.screens.main.ParenCompose
import com.example.health.screens.main.diary.compose.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


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
        mutableStateOf(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
    }
    val selectedMeal = remember { mutableStateOf(MealType.MORNING) }

    val account = accountViewModel.account.collectAsState().value ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Đang tải tài khoản...")
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
                // 👉 Không theo chế độ ăn → hiển thị món ăn người dùng thêm + nút Add
                items(foodList.value.size + 1) { index ->
                    if (index < foodList.value.size) {
                        val food = foodList.value[index]
                        FoodCard(index + 1, food, onClick = {
                            navController.navigate("diary/detail_default/${food.FoodId}")
                        })
                    } else {
                        AddFoodCard(onClick = {
                            navController.navigate("${DiaryRoutes.Add}?parent=${ParenCompose.FROMDIARY}&mealType=${selectedMeal.value.type}")
                        })
                    }
                }
            } else {
                // 👉 Đang theo chế độ ăn → hiển thị các món theo kế hoạch
//                items(mealDishesFromPlan.size) { index ->
//                    val dish = mealDishesFromPlan[index]
//                    DietDishCardInDiary(dish = dish, onClick = {
//                        navController.navigate("diary/detail_diet/${dish.Id}")
//                    })
//                }
                items(mealDishesFromPlan.size) { index ->
                    val dish = mealDishesFromPlan[index]
                    DietDishCardInDiary(dish = dish, onClick = {
                        val uid = account.Uid
                        val mealType = selectedMeal.value.type
                        val today = selectedDay.value
                        val route = DiaryRoutes.DetailDiet.createRoute(
                            id = dish.Id,
                            uid = uid,
                            mealType = mealType,
                            date = today.time
                        )
                        navController.navigate(route)
                    })
                }

            }
        }

    }
}