package com.example.health.screens.main.plan.vegan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.health.data.local.entities.DefaultDietMealInPlan
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.ui.theme.MealType
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext


@Composable
fun HeaderWithBackButton(
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)) // 👈 Bo góc dưới
            .background(color = Color(0xFF466448)) // Xanh đậm
            .padding(vertical = 20.dp, horizontal = 12.dp)
    ) {
        // 👉 Back icon (nằm trái)
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(28.dp)
                .clickable { onBackClick() }
        )

        // 👉 Tiêu đề căn giữa tuyệt đối
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy( // 👈 Chữ to hơn
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}



@Composable
fun VeganPlanScreen(
    navController: NavController,
    viewModel: DefaultDietMealInPlanViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.syncIfNeeded(context)
    }

    val allPlans by viewModel.allPlans.collectAsState()
    val veganMeals = allPlans.filter { it.Type == 1 }
    val mealsGroupedByDay = veganMeals.groupBy { it.Id.last().digitToInt() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            HeaderWithBackButton(
                title = "Vegan diet meal plan",
                onBackClick = { navController.popBackStack() }
            )
        }

        mealsGroupedByDay.toSortedMap().forEach { (day, meals) ->
            item {
                DaySection(
                    day = day,
                    meals = meals,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun DaySection(day: Int, meals: List<DefaultDietMealInPlan>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "Day $day",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color(0xFF3C6E28),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            items(meals.sortedBy { getMealOrder(it.Id[1]) }) { meal ->
                MealItem(
                    meal = meal,
                    onClick = {
                        navController.navigate(PlanRoutes.VeganPlanDetail.route)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val totalCalories = meals.sumOf { it.TotalCalo.toInt() }
        Text(
            text = "Total Calories: $totalCalories kcal",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFFB8712D),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun MealItem(meal: DefaultDietMealInPlan, onClick: () -> Unit) {
    val mealType = MealType.fromPrefix(meal.Id[1])?.displayName ?: "Meal"

    Column(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE0F1D5))
            .clickable { onClick() }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = meal.UrlImage,
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = mealType,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                text = "${meal.TotalCalo.toInt()} kcal",
                style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray)
            )
        }
    }
}

// 🎯 Sắp xếp thứ tự: b < l < s < d
fun getMealOrder(char: Char): Int {
    return when (char) {
        'b' -> 0
        'l' -> 1
        's' -> 2
        'd' -> 3
        else -> 4
    }
}
