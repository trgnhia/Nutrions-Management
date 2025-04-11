package com.example.health.screens.main.diary.compose

import NutritionTagFixedWidth
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.health.R
import com.example.health.data.local.entities.DietDish
import com.example.health.data.local.viewmodel.DietDishViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailDietScreen(
    dishId: String,
    viewModel: DietDishViewModel
) {
    val scope = rememberCoroutineScope()
    var dish by remember { mutableStateOf<DietDish?>(null) }

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

    Box(modifier = Modifier.fillMaxSize()) {
        // --- Background curve ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1500.dp) // ✅ tăng chiều cao của nền cong
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_back_dish),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }

        // --- Main content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp), // ✅ Dịch xuống để ảnh món đè lên nền cong
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
                text = "Trung bình, một phần ${dish!!.Name.lowercase()} chứa khoảng ${dish!!.Calo.toInt()} kcal.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nutritions",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDE8025)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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

            Button(
                onClick = { /* TODO: Save action */ },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE8025)),
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("SAVE", color = Color.White)
            }
        }
    }
}

