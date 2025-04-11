package com.example.health.screens.main.diary.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.utils.calculateNutritionByWeight
import com.example.health.screens.main.ParenCompose

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
    ) -> Unit = { _, _, _, _, _ -> }
) {
    var weightInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            if (parent == ParenCompose.FROMDIARY) {
                TextButton(onClick = {
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
                    onDismiss()
                }) {
                    Text("Save")
                }
            } else {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        },
        title = { Text("Nutri info:") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Default quantity: ${food.Quantity}g")
                Text("Calories: ${food.Calo} kcal")
                Text("Carb: ${food.Carb}g")
                Text("Fat: ${food.Fat}g")
                Text("Protein: ${food.Protein}g")

                if (parent == ParenCompose.FROMDIARY) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Enter your ate quantity:")
                    OutlinedTextField(
                        value = weightInput,
                        onValueChange = { weightInput = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}