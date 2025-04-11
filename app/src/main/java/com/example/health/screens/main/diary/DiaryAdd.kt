package com.example.health.screens.main.diary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.viewmodel.*
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.screens.main.ParenCompose
import com.example.health.screens.main.diary.compose.*
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.utils.toStartOfDay
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryAdd(
    navController: NavController,
    parent: String,
    mealType: Int,
    defaultFoodViewModel: DefaultFoodViewModel,
    eatenMealViewModel: EatenMealViewModel,
    eatenDishViewModel: EatenDishViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    customFoodViewModel: CustomFoodViewModel,
    accountViewModel: AccountViewModel,
    selectedDay: Long
) {
    val searchText = remember { mutableStateOf("") }
    val searchResult = remember { mutableStateListOf<DefaultFood>() }
    val showDialog = remember { mutableStateOf(false) }
    val account = accountViewModel.account.collectAsState().value
    val uid = account?.Uid
    val today: Date = Date().toStartOfDay()
    val selectDay = Date(selectedDay).toStartOfDay()

    val customFoods by customFoodViewModel.getAllByUser(uid!!).collectAsState(initial = emptyList())
    val meatFlow = remember { defaultFoodViewModel.getRandomFoodsByType(5, 1) }
    val vegetableFlow = remember { defaultFoodViewModel.getRandomFoodsByType(5, 2) }
    val starchFlow = remember { defaultFoodViewModel.getRandomFoodsByType(5, 3) }
    val snackFlow = remember { defaultFoodViewModel.getRandomFoodsByType(5, 4) }

    val meatFoods by meatFlow.collectAsState(initial = emptyList())
    val vegetableFoods by vegetableFlow.collectAsState(initial = emptyList())
    val starchFoods by starchFlow.collectAsState(initial = emptyList())
    val snackFoods by snackFlow.collectAsState(initial = emptyList())

    val canEdit = parent == ParenCompose.FROMDIARY
    // State to handle selected tab
    val selectedTab = remember { mutableStateOf("Discover") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header and Search Bar
            SearchBarWithResult(
                searchText = searchText.value,
                onTextChange = { searchText.value = it },
                onSearchClick = {
                    // Trigger API call and update searchResult
                },
                results = searchResult,
                onResultClick = {
                    // Show detail or add to selection
                }
            )

            // Tab Navigation buttons (Discover & Eaten Meal)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { selectedTab.value = "Discover" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab.value == "Discover") MaterialTheme.colorScheme.primary else Color.Gray
                    )
                ) {
                    Text("Discover")
                }
                Button(
                    onClick = { selectedTab.value = "Eaten meal" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab.value == "Eaten meal") MaterialTheme.colorScheme.primary else Color.Gray
                    )
                ) {
                    Text("Eaten Meal")
                }
            }

            // Content that changes based on selectedTab
            if (selectedTab.value == "Discover") {
                // Discover Tab: Show Custom Food Row and Default Food Rows
                CustomFoodRow(
                    customFoods = customFoods,
                    onAddClick = { /* Show popup */ },
                    onItemClick = { /* Detail */ }
                )

                // Meat
                if (uid != null) {
                    DefaultFoodRow(
                        title = "Meat / Fish",
                        foods = meatFoods,
                        onViewMoreClick = {
                            navController.navigate("${DiaryRoutes.ViewMore.route}?parent=$parent&foodtype=1&mealType=$mealType&selectedDay=${selectedDay}")
                        },
                        onItemClick = {}, // Không cần vì Dialog mở trong DefaultFoodRow rồi
                        onSaveFood = {
                            // Refresh hoặc hiển thị snackbar sau khi lưu thành công nếu cần
                        },
                        parent = parent,
                        uid = uid,
                        mealType = mealType,
                        today =today ,
                        eatenMealViewModel = eatenMealViewModel,
                        eatenDishViewModel = eatenDishViewModel,
                        totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                        selecDay = selectDay
                    )
                }

// Vegetable
                if (uid != null) {
                    DefaultFoodRow(
                        title = "Vegetable / Fruit",
                        foods = vegetableFoods,
                        onViewMoreClick = {
                            navController.navigate("${DiaryRoutes.ViewMore.route}?parent=$parent&foodtype=2&mealType=$mealType&selectedDay=${selectedDay}")
                        },
                        onItemClick = {},
                        onSaveFood = {},
                        parent = parent,
                        uid = uid,
                        mealType = mealType,
                        today =today ,
                        eatenMealViewModel = eatenMealViewModel,
                        eatenDishViewModel = eatenDishViewModel,
                        totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                        selecDay = selectDay
                    )
                }

// Starch
                if (uid != null) {
                    DefaultFoodRow(
                        title = "Starch",
                        foods = starchFoods,
                        onViewMoreClick = {
                            navController.navigate("${DiaryRoutes.ViewMore.route}?parent=$parent&foodtype=3&mealType=$mealType&selectedDay=${selectedDay}")
                        },
                        onItemClick = {},
                        onSaveFood = {},
                        parent = parent,
                        uid = uid,
                        mealType = mealType,
                        today = today ,
                        eatenMealViewModel = eatenMealViewModel,
                        eatenDishViewModel = eatenDishViewModel,
                        totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                        selecDay = selectDay
                    )
                }
// Snack
                if (uid != null) {
                    DefaultFoodRow(
                        title = "Snack / Light meal",
                        foods = snackFoods,
                        onViewMoreClick = {
                            navController.navigate("${DiaryRoutes.ViewMore.route}?parent=$parent&foodtype=4&mealType=$mealType&selectedDay=${selectedDay}")
                        },
                        onItemClick = {},
                        onSaveFood = {},
                        parent = parent,
                        uid = uid,
                        mealType = mealType,
                        today =today ,
                        eatenMealViewModel = eatenMealViewModel,
                        eatenDishViewModel = eatenDishViewModel,
                        totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                        selecDay = selectDay
                    )
                }
            } else {
            }
        }
        // Only show the Meal Summary Button if we are in the "Discover" tab
        if (canEdit && selectedTab.value == "Discover" && selectedDay == today.time) {
            MealSummaryButton { showDialog.value = true }
        }
        // Dialog for today's meals
        if (showDialog.value) {
            TodayMealDialog(
                foods = searchResult, // temporary sample
                onDismiss = { showDialog.value = false },
                onSaveClick = { showDialog.value = false },
                selectDay = selectDay
            )
        }
    }
}