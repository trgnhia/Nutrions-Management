package com.example.health.screens.main.diary.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.health.R
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.local.viewmodel.*
import com.example.health.data.utils.toStartOfDay
import com.example.health.screens.main.ParenCompose
import com.example.health.screens.main.diary.AddFood
import java.io.File
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewMore(
    navController: NavController,
    foodType: Int,
    defaultFoodViewModel: DefaultFoodViewModel,
    eatenMealViewModel: EatenMealViewModel,
    eatenDishViewModel: EatenDishViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    accountViewModel: AccountViewModel,
    parent: String,
    mealType: Int
) {
    val account = accountViewModel.account.collectAsState().value
    val uid = account?.Uid ?: return
    val today: Date = Date().toStartOfDay()

    val foodList = remember { defaultFoodViewModel.getRandomFoodsByType(20, foodType) }
    val foods by foodList.collectAsState(initial = emptyList())
    var search by remember { mutableStateOf("") }
    val selectedFood = remember { mutableStateOf<DefaultFood?>(null) }

    val typeName = when (foodType) {
        1 -> "Meat / Fish"
        2 -> "Vegetable / Fruit"
        3 -> "Starch"
        4 -> "Snack / Light meal"
        else -> "Food"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Title
            item(span = { GridItemSpan(3) }) {
                Text(
                    text = typeName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Search bar
            item(span = { GridItemSpan(3) }) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Find...") },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_diary_unselected),
                            contentDescription = "Search"
                        )
                    }
                )
            }

            // Food cards
            items(foods.filter { it.Name.contains(search, ignoreCase = true) }, key = { it.Id }) { food ->
                FoodGridCard(
                    food = food,
                    onClick = { selectedFood.value = food },
                    canEdit = parent == ParenCompose.FROMDIARY
                )
            }
        }

        // Floating Add Button
        if (parent == ParenCompose.FROMDIARY) {
            FloatingActionButton(
                onClick = { /* TODO: mở thêm món ăn */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_diary_unselected),
                    contentDescription = "Add"
                )
            }
        }

        // Dialog khi chọn món ăn
        selectedFood.value?.let { food ->
            FoodDetailDialog(
                food = food,
                parent = parent,
                onDismiss = { selectedFood.value = null },
                onSave = { weight, calo, fat, carb, protein ->
                    AddFood(
                        uid = uid,
                        eatenDishViewModel = eatenDishViewModel,
                        eatenMealViewModel = eatenMealViewModel,
                        totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                        today = today,
                        foodID = food.Id,
                        dishName = food.Name,
                        calo = calo,
                        fat = fat,
                        carb = carb,
                        protein = protein,
                        type = mealType,
                        quantityType = food.QuantityType,
                        quantity = weight,
                        urlImage = food.UrlImage
                    )
                    selectedFood.value = null
                }
            )
        }
    }
}

@Composable
fun FoodGridCard(
    food: DefaultFood,
    onClick: () -> Unit,
    canEdit: Boolean
) {
    val context = LocalContext.current
    val imageRequest = remember(food.UrlImage) {
        ImageRequest.Builder(context)
            .data(File(food.UrlImage))
            .crossfade(true)
            .build()
    }

    Card(
        modifier = Modifier
            .width(110.dp) // ✅ tất cả card cùng kích thước
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = food.Name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = food.Name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                softWrap = false
            )

//            if (canEdit) {
//                Button(
//                    onClick = onAddClick,
//                    modifier = Modifier
//                        .padding(top = 4.dp)
//                        .height(30.dp)
//                        .fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
//                    contentPadding = PaddingValues(horizontal = 0.dp)
//                ) {
//                    Text("Add", style = MaterialTheme.typography.labelSmall)
//                }
//            }
        }
    }
}

