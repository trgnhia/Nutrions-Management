package com.example.health.screens.main.diary.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.health.data.local.entities.DefaultFood
import com.example.health.screens.main.ParenCompose // Đảm bảo ParenCompose được định nghĩa đúng trong project

@Composable
fun FoodDetailDialog(
    food: DefaultFood,
    parent: String,
    onDismiss: () -> Unit,
    onSave: (Float) -> Unit = {}
) {
    var weightInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            if (parent == ParenCompose.FROMDIARY) {
                TextButton(onClick = {
                    val weight = weightInput.toFloatOrNull() ?: 0f
                    onSave(weight)
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
