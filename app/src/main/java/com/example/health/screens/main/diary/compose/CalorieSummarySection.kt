package com.example.health.screens.main.diary.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalorieSummarySection(loaded: Int, needed: Int, goal: Int, consumed: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Loaded
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$loaded", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            Text("loaded", color = Color.White, fontSize = 12.sp)
        }

        // Center: Circle Chart
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f), // luôn giữ tỉ lệ 1:1 giữa width và height
            contentAlignment = Alignment.Center
        ) {
            CalorieCircle(current = loaded, goal = goal , needed = needed)
        }

        // Right: Consumed
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$consumed", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            Text("consumption", color = Color.White, fontSize = 12.sp)
        }
    }
}