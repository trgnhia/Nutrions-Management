package com.example.health.screens.main.diary

import android.content.ContentValues.TAG
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
    dietDishViewModel: DietDishViewModel,
    calorBurn: MutableState<Float>
) {
    val selectedDay = remember {
        mutableStateOf(Date().toStartOfDay())
    }

    val currentHour = remember { java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY) }

    val selectedMeal = remember {
        mutableStateOf(
            when (currentHour) {
                in 5..9 -> MealType.MORNING   // sáng (5h - 9h)
                in 10..14 -> MealType.LUNCH   // trưa (10h - 14h)
                in 15..18 -> MealType.SNACK // chiều (15h - 18h)
                in 19..24 -> MealType.DINNER  // tối (19h - 22h)
                else -> MealType.SNACK        // đêm / khuya
            }
        )
    }

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
                // 👉 Không theo chế độ ăn → hiển thị món ăn người dùng thêm + nút Add
                items(foodList.value.size + 1) { index ->
                    //Log.e(TAG, "food list: ${foodList.value}", )
                    if (index < foodList.value.size) {
                        val food = foodList.value[index]
                        //Log.e(TAG, "DiaryMainScreen: $food", )
                        FoodCard(index + 1, food, onClick = {
                            //Log.e(TAG, "DiaryMainScreen: $food", )
                            navController.navigate("diary/detail_default/${food.id}")
                        })
                    } else {
                        if(selectedDay.value.equals(Date().toStartOfDay())){
                            AddFoodCard(onClick = {
                                navController.navigate("${DiaryRoutes.Add}?parent=${ParenCompose.FROMDIARY}&mealType=${selectedMeal.value.type}")
                            })
                        }

                    }
                }
            }
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