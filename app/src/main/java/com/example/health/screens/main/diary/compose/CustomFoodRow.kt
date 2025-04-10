package com.example.health.screens.main.diary.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.health.data.local.entities.CustomFood
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.filled.Add


@Composable
fun CustomFoodRow(
    customFoods: List<CustomFood>,
    onItemClick: (CustomFood) -> Unit,
    onAddClick: () -> Unit
) {
    val displayItems = customFoods.take(5)

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Your Custom Food", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Custom Food")
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(displayItems, key = { it.id }) { food ->
                CustomFoodCard(food, onClick = { onItemClick(food) })
            }
        }
    }
}
