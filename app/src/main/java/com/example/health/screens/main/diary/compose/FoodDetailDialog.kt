package com.example.health.screens.main.diary.compose

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.utils.calculateNutritionByWeight
import com.example.health.data.utils.toStartOfDay
import com.example.health.screens.main.ParenCompose
import java.util.Date

@Composable
fun FoodDetailDialog(
    food: DefaultFood,
    parent: String,
    onDismiss: () -> Unit,
    onSave: (
        weight: Float,
        calo: Float,
        fat: Float,
        carb: Float,
        protein: Float
    ) -> Unit = { _, _, _, _, _ -> },
    selectedDay: Date
) {
    var weightInput by remember { mutableStateOf("") }
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            if (parent == ParenCompose.FROMDIARY) {
                TextButton(
                    onClick = {
                        val weight = weightInput.toFloatOrNull() ?: 0f
                        val result = calculateNutritionByWeight(
                            defaultWeight = food.Quantity.toFloat(),
                            actualWeight = weight,
                            calories = food.Calo,
                            fat = food.Fat,
                            carb = food.Carb,
                            protein = food.Protein
                        )

                        onSave(
                            result.actualWeight,
                            result.calories,
                            result.fat,
                            result.carb,
                            result.protein
                        )
                        Toast.makeText(context, "Add food to diary successfully", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    }
                ) {
                    Text("Save", color = MaterialTheme.colorScheme.primary)
                }
            } else {
                TextButton(onClick = onDismiss) {
                    Text("OK", color = MaterialTheme.colorScheme.primary)
                }
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Enter your consumed quantity (g):",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize, // to hơn body
                    fontWeight = FontWeight.Bold,                             // in đậm
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoRow(label = "Default quantity", value = "${food.Quantity}g")
                InfoRow(label = "Calories", value = "${food.Calo} kcal")
                InfoRow(label = "Carbs", value = "${food.Carb}g")
                InfoRow(label = "Fat", value = "${food.Fat}g")
                InfoRow(label = "Protein", value = "${food.Protein}g")

                if (parent == ParenCompose.FROMDIARY && selectedDay == Date().toStartOfDay() ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Enter your consumed quantity (g):",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    OutlinedTextField(
                        value = weightInput,
                        onValueChange = { weightInput = it },
                        placeholder = { Text("e.g., 150") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
        }
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
