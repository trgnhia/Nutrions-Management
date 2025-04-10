package com.example.health.screens.main.diary.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CalorieCircle(current: Int, goal: Int , needed : Int) {
    val ratio = (current.toFloat() / goal).coerceIn(0f, 1f)
    val sweepAngle = ratio * 360f

    Box(modifier = Modifier.size(130.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.White, style = Stroke(width = 10f))
            drawArc(
                color = Color(0xFF4CAF50),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 10f)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${needed}", color = Color.White, fontSize = 20.sp)
            Text("Need to recharge", color = Color.White, fontSize = 12.sp)
        }
    }
}