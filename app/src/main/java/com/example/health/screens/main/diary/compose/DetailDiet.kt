package com.example.health.screens.main.diary.compose

import NutritionTagFixedWidth
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.health.R
import com.example.health.data.local.entities.DietDish
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.data.utils.calculateNutritionByWeight
import com.example.health.screens.main.diary.AddFood
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailDietScreen(
    dishId: String,
    uid: String,
    today: Date,
    mealType: Int,
    viewModel: DietDishViewModel,
    eatenDishViewModel: EatenDishViewModel,
    eatenMealViewModel: EatenMealViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var dish by remember { mutableStateOf<DietDish?>(null) }

    var quantityText by remember { mutableStateOf("") }

    LaunchedEffect(dishId) {
        scope.launch {
            dish = viewModel.getDishById(dishId)
        }
    }

    if (dish == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val initialQuantity = dish!!.Quantity

    Box(modifier = Modifier.fillMaxSize()) {
        // Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1500.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_back_dish),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = dish!!.UrlImage,
                contentDescription = dish!!.Name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${dish!!.Name} (${dish!!.Quantity}${dish!!.QuantityType})",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFFDE8025)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "On average, one serving of ${dish!!.Name.lowercase()} (${dish!!.Quantity}${dish!!.QuantityType}) contains about ${dish!!.Calo.toInt()} kcal.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nutritions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nutritions",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDE8025)
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            val current = quantityText.toIntOrNull() ?: 1
                            if (current > 1) quantityText = (current - 1).toString()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE8025)),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Text("-", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = quantityText,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() }) {
                                quantityText = input
                            }
                        },
                        placeholder = { Text("") },
                        modifier = Modifier
                            .width(70.dp)
                            .height(55.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFDE8025)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val current = quantityText.toIntOrNull() ?: 1
                            quantityText = (current + 1).toString()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE8025)),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Text("+", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text("gram", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NutritionTagFixedWidth(R.drawable.calo, "Calories", dish!!.Calo, Color(0xFFFFE0B2))
                    NutritionTagFixedWidth(R.drawable.carb, "Carb", dish!!.Carb, Color(0xFFBBDEFB))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NutritionTagFixedWidth(R.drawable.pro, "Proteins", dish!!.Protein, Color(0xFFC8E6C9))
                    NutritionTagFixedWidth(R.drawable.fat, "Fat", dish!!.Fat, Color(0xFFFFF1C1))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            val isValidInput = quantityText.isNotBlank() && quantityText.all { it.isDigit() }
            val isChanged = isValidInput

            Button(
                onClick = {
                    val inputQuantity = quantityText.toFloatOrNull()
                    if (inputQuantity != null) {
                        val result = calculateNutritionByWeight(
                            defaultWeight = dish!!.Quantity.toFloat(),
                            actualWeight = inputQuantity,
                            calories = dish!!.Calo,
                            fat = dish!!.Fat,
                            carb = dish!!.Carb,
                            protein = dish!!.Protein
                        )

                        AddFood(
                            uid = uid,
                            eatenDishViewModel = eatenDishViewModel,
                            eatenMealViewModel = eatenMealViewModel,
                            totalNutrionsPerDayViewModel = totalNutrionsPerDayViewModel,
                            today = today,
                            foodID = dish!!.FoodId,
                            dishName = dish!!.Name,
                            calo = result.calories,
                            fat = result.fat,
                            carb = result.carb,
                            protein = result.protein,
                            type = mealType,
                            quantityType = dish!!.QuantityType,
                            quantity = result.actualWeight,
                            urlImage = dish!!.UrlImage
                        )

                        Toast.makeText(context, "The dish has been added to the diary.", Toast.LENGTH_LONG).show()
                    }
                },
                enabled = isChanged,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isChanged) Color(0xFFDE8025) else Color.Gray
                ),
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("SAVE", color = Color.White)
            }

        }
    }
}
