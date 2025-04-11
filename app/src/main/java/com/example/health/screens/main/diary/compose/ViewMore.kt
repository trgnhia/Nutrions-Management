package com.example.health.screens.main.diary.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF105C5C))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = typeName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(48.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF105C5C))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search food...") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_find),
                            contentDescription = "Search"
                        )
                    },
                    shape = RoundedCornerShape(25.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                items(foods.filter { it.Name.contains(search, ignoreCase = true) }, key = { it.Id }) { food ->
                    FoodGridCard(
                        food = food,
                        onClick = { selectedFood.value = food },
                        canEdit = parent == ParenCompose.FROMDIARY
                    )
                }
            }
        }

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
            .width(180.dp)
            .padding(4.dp),
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
                    .size(90.dp)
                    .clip(CircleShape)
                    .clickable { onClick() }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = food.Name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                maxLines = 1,
                softWrap = false,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (canEdit) {
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                        .background(Color(0xFF105C5C))
                        .clickable { onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Add", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
