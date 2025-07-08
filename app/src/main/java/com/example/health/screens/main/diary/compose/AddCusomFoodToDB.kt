package com.example.health.screens.main.diary.compose
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.health.data.local.entities.CustomFood
import java.util.UUID

@Composable
fun AddCustomFoodDialog(
    onDismiss: () -> Unit,
    onAdd: (CustomFood) -> Unit,
    uid: String
) {
    var name by remember { mutableStateOf("") }
    var calo by remember { mutableStateOf("0.0") }
    var carb by remember { mutableStateOf("0.0") }
    var fat by remember { mutableStateOf("0.0") }
    var protein by remember { mutableStateOf("0.0") }
    var type by remember { mutableStateOf(1) } // 1: thịt, 2: rau, ...
    var quantity by remember { mutableStateOf("0.0") }
    var quantityType by remember { mutableStateOf("") }
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFF2F2F2))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Add Custom Food", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Food Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = calo,
                    onValueChange = { calo = it },
                    label = { Text("Calories") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = carb,
                    onValueChange = { carb = it },
                    label = { Text("Carbs") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = fat,
                    onValueChange = { fat = it },
                    label = { Text("Fat") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = protein,
                    onValueChange = { protein = it },
                    label = { Text("Protein") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = quantityType,
                    onValueChange = { quantityType = it },
                    label = { Text("Quantity Type (grams/ml/etc.)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Food Type")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(listOf("Meat/Fish" to 1, "Vegetable" to 2, "Starch" to 3, "Snack" to 4)) { (label, value) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .clickable { type = value }
                        ) {
                            RadioButton(selected = type == value, onClick = { type = value })
                            Text(label)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        if (
                            name.isBlank() || calo.isBlank() || carb.isBlank() ||
                            fat.isBlank() || protein.isBlank() || quantity.isBlank() || quantityType.isBlank()
                        ) {
                            // Bạn có thể show Toast hoặc Snackbar
                            Toast.makeText(context, "All fields are required.", Toast.LENGTH_SHORT).show()
                            return@TextButton
                        }

                        val food = CustomFood(
                            id = UUID.randomUUID().toString(),
                            Name = name,
                            Calo = calo.toFloatOrNull() ?: 0f,
                            Fat = fat.toFloatOrNull() ?: 0f,
                            Carb = carb.toFloatOrNull() ?: 0f,
                            Protein = protein.toFloatOrNull() ?: 0f,
                            Type = type,
                            Quantity = quantity.toIntOrNull() ?: 100,
                            QuantityType = quantityType,
                            Uid = uid,
                            UrlImage = ""
                        )
                        onAdd(food)
                    }) {
                        Text("Done", color = Color.Red)
                    }

                }
            }
        }
    }
}
