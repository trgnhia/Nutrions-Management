package com.example.health.screens.main.diary.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NutrientItem(title: String, current: Int, max: Int) {
    val progressRatio = (current.toFloat() / max).coerceIn(0f, 1f)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = Color.White, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Canvas(modifier = Modifier
            .width(60.dp)
            .height(4.dp)
            .clip(CircleShape)) {
            drawRect(color = Color.White.copy(alpha = 0.2f))
            drawRect(
                color = Color.White,
                size = androidx.compose.ui.geometry.Size(size.width * progressRatio, size.height)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text("$current / $max", color = Color.White, fontSize = 12.sp)
    }
}
