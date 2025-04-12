package com.example.health.screens.main.diary.compose

import NutritionTagFixedWidth
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

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

@Composable
fun DetailFoodLayout(
    image: String,
    name: String,
    calo: Float,
    carb: Float,
    fat: Float,
    protein: Float,
    quantity: Int,             // ⬅️ Thêm dòng này
    quantityType: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1000.dp) // tăng chiều cao background
            ) {
                Image(
                    painter = painterResource(id = R.drawable.top_back_dish),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = image,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFFDE8025)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Quantity of dish: $quantity $quantityType",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = Color.Gray
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Average nutrition facts for one serving.",
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
                    NutritionTagFixedWidth(R.drawable.calo, "Calories", calo, Color(0xFFFFE0B2))
                    NutritionTagFixedWidth(R.drawable.carb, "Carb", carb, Color(0xFFBBDEFB))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NutritionTagFixedWidth(R.drawable.pro, "Proteins", protein, Color(0xFFC8E6C9))
                    NutritionTagFixedWidth(R.drawable.fat, "Fat", fat, Color(0xFFFFF1C1))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
