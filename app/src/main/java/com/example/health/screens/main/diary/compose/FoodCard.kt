package com.example.health.screens.main.diary.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.health.R
import com.example.health.data.local.entities.EatenDish
import java.util.Date

@Composable
fun FoodCard(
    index: Int,
    food: EatenDish,
    onClick: () -> Unit,
    selectedDay: Date
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Tiêu đề "Món ăn x"
            Text(
                text = "Dish $index",
                color = Color(0xFFDE8025),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Ảnh hình tròn
            Image(
                painter = rememberAsyncImagePainter(
                    model = food.UrlImage,
                    placeholder = painterResource(id = R.drawable.default_dish),
                    error = painterResource(R.drawable.default_dish)
                    ,
                    onError = {
                        Log.e("ImageLoad", "Load failed: ${it.result.throwable}")
                    },
                    onSuccess = {
                        Log.d("ImageLoad", "Image loaded successfully")
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tên món ăn + lượng ăn
            Text(
                text = "${food.DishName} (${food.Quantity})",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Calories
            Text(
                text = "${food.Calo} kcal",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
    }
}