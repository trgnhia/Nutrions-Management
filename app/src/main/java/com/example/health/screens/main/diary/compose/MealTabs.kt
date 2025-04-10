package com.example.health.screens.main.diary.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MealTabs(meals: List<String>, selectedMeal: String, onMealChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        meals.forEach { meal ->
            val selected = meal == selectedMeal
            OutlinedButton(
                onClick = { onMealChange(meal) },
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, if (selected) Color(0xFFDE8025) else Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selected) Color(0xFFDE8025) else Color.White,
                    contentColor = if (selected) Color.White else Color.Black
                )
            ) {
                Text(meal)
            }

        }
    }
}
