package com.example.health.screens.main.diary.compose

import NutritionTagFixedWidth
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

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
import com.example.health.data.local.entities.EatenDish
import com.example.health.data.utils.toStartOfDay
import java.util.Date

@Composable
fun DetailFoodLayout(
    eatenDish: EatenDish,
    selectedDate: Date,

    onUpdateQuantity: (Float) -> Unit // callback để update về ViewModel
) {
    var quantityInput by remember { mutableStateOf(eatenDish.Quantity.toString()) }
    val today = remember { Date().toStartOfDay() }
    val isToday = selectedDate.toStartOfDay() == today
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
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
                model = eatenDish.UrlImage,
                contentDescription = eatenDish.DishName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = eatenDish.DishName,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFFDE8025)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TextField thay cho hiển thị cứng
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                androidx.compose.material3.OutlinedTextField(
                    value = quantityInput,
                    onValueChange = { if (isToday) quantityInput = it },
                    label = { Text("Quantity") },
                    singleLine = true,
                    enabled = isToday,
                    modifier = Modifier.width(120.dp)
                )
                Text(text = eatenDish.QuantityType)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Button update
            if(isToday){
                androidx.compose.material3.Button(
                    onClick = {
                        val newQuantity = quantityInput.toFloatOrNull()

                        if (newQuantity != null && newQuantity > 0) {
                            onUpdateQuantity(newQuantity)
                        }
                    }
                ) {
                    Text("Update")
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

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
                    NutritionTagFixedWidth(R.drawable.calo, "Calories", eatenDish.Calo, Color(0xFFFFE0B2))
                    NutritionTagFixedWidth(R.drawable.carb, "Carb", eatenDish.Carb, Color(0xFFBBDEFB))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NutritionTagFixedWidth(R.drawable.pro, "Proteins", eatenDish.Protein, Color(0xFFC8E6C9))
                    NutritionTagFixedWidth(R.drawable.fat, "Fat", eatenDish.Fat, Color(0xFFFFF1C1))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
