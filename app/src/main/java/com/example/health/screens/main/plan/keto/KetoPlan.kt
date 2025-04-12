package com.example.health.screens.main.plan.keto
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.health.data.local.entities.DefaultDietMealInPlan
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.vegan.HeaderWithBackButton
import com.example.health.ui.theme.MealType
@Composable
fun KetoPlanScreen(
    navController: NavController,
    viewModel: DefaultDietMealInPlanViewModel,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.syncIfNeeded(context)
    }

    val allPlans by viewModel.allPlans.collectAsState()
    val ketoMeals = allPlans.filter { it.Type == 3 } // 3 = Keto
    val mealsGroupedByDay = ketoMeals.groupBy { it.Id.last().digitToInt() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            HeaderWithBackButton(
                title = "Keto diet meal plan",
                onBackClick = { navController.popBackStack() },
                backgroundColor = Color(0xFF4E5477)
            )
        }

        mealsGroupedByDay.toSortedMap().forEach { (day, meals) ->
            item {
                DaySection(
                    day = day,
                    meals = meals,
                    navController = navController,
                    detailRoute = PlanRoutes.KetoPlanDetail.route
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun DaySection(
    day: Int,
    meals: List<DefaultDietMealInPlan>,
    navController: NavController,
    detailRoute: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "Day $day",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color(0xFFBFC3E5),
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
                        navController.navigate(PlanRoutes.KetoPlanDetail.createRoute(meal.Id))
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
            .background(Color(0xFFC9CBE7))
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

fun getMealOrder(char: Char): Int {
    return when (char) {
        'b' -> 0
        'l' -> 1
        's' -> 2
        'd' -> 3
        else -> 4
    }
}


