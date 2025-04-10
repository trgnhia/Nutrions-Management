package com.example.health.screens.main.diary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.data.local.viewmodel.*
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.screens.main.ParenCompose
import com.example.health.screens.main.diary.compose.*
import com.example.health.data.local.entities.DefaultFood

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
    accountViewModel: AccountViewModel
) {
    val searchText = remember { mutableStateOf("") }
    val searchResult = remember { mutableStateListOf<DefaultFood>() }
    val showDialog = remember { mutableStateOf(false) }
    val account = accountViewModel.account.collectAsState().value
    val uid = account?.Uid

    val customFoods by customFoodViewModel.getAllByUser(uid!!).collectAsState(initial = emptyList())


    val meatFlow = remember { defaultFoodViewModel.getRandomFoodsByType(5,1) }
    val vegetableFlow = remember { defaultFoodViewModel.getRandomFoodsByType(5,2) }
    val starchFlow = remember { defaultFoodViewModel.getRandomFoodsByType(5,3) }
    val snackFlow = remember { defaultFoodViewModel.getRandomFoodsByType(5,4) }

    val meatFoods by meatFlow.collectAsState(initial = emptyList())
    val vegetableFoods by vegetableFlow.collectAsState(initial = emptyList())
    val starchFoods by starchFlow.collectAsState(initial = emptyList())
    val snackFoods by snackFlow.collectAsState(initial = emptyList())
    val canEdit = parent == ParenCompose.FROMDIARY

    Box(modifier = Modifier.fillMaxSize()) {
        // Bọc Column trong verticalScroll để cuộn dọc
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {

            SearchBarWithResult(
                searchText = searchText.value,
                onTextChange = { searchText.value = it },
                onSearchClick = {
                    // TODO: Trigger API call and update searchResult
                },
                results = searchResult,
                onResultClick = {
                    // TODO: Show detail or add to selection
                }
            )

            CustomFoodRow(
                customFoods = customFoods,
                onAddClick = { /* Show popup */ },
                onItemClick = { /* Detail */ }
            )

            DefaultFoodRow("Meat / Fish", meatFoods, onViewMoreClick = {}, onItemClick = {})
            DefaultFoodRow("Vegetable / Fruit", vegetableFoods,  onViewMoreClick = {}, onItemClick = {})
            DefaultFoodRow("Starch", starchFoods,  onViewMoreClick = {}, onItemClick = {})
            DefaultFoodRow("Snack / Light meal", snackFoods,  onViewMoreClick = {}, onItemClick = {})
        }

        if (canEdit) {
            MealSummaryButton { showDialog.value = true }
        }

        if (showDialog.value) {
            TodayMealDialog(
                foods = searchResult, // temporary sample
                onDismiss = { showDialog.value = false },
                onSaveClick = { showDialog.value = false }
            )
        }
    }
}