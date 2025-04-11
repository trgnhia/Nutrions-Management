package com.example.health.screens.main.diary.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.health.data.local.entities.DefaultFood
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun DefaultFoodRow(
    title: String,
    foods: List<DefaultFood>,
    onItemClick: (DefaultFood) -> Unit,
    onViewMoreClick: () -> Unit,
    onSaveFood: () -> Unit,
    parent: String
) {
    val displayItems = foods.take(5)
    var selectedFood by remember { mutableStateOf<DefaultFood?>(null) }

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = onViewMoreClick) {
                Text("View More")
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(displayItems, key = { it.Id }) { food ->
                DefaultFoodCard(food, onClick = { selectedFood = food })
            }
        }
        selectedFood?.let {
            FoodDetailDialog(
                food = it,
                parent = parent,
                onDismiss = { selectedFood = null },
                onSave = { weight ->
                    onSaveFood()
                }
            )
        }
    }
}
