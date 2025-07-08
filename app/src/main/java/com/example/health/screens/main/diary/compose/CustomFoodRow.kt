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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


//@Composable
//fun CustomFoodRow(
//    customFoods: List<CustomFood>,
//    onItemClick: (CustomFood) -> Unit,
//    onAddClick: () -> Unit
//) {
//    val displayItems = customFoods.take(5)
//
//    Column(modifier = Modifier.padding(vertical = 4.dp)) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 12.dp, vertical = 6.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text("Your Custom Food", style = MaterialTheme.typography.titleMedium)
//            // bam vao day de hien thi dialog them mon an voi cac truong thong tin
//            IconButton(onClick = onAddClick) {
//                Icon(Icons.Default.Add, contentDescription = "Add Custom Food")
//            }
//        }
//
//        LazyRow(
//            contentPadding = PaddingValues(horizontal = 12.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(displayItems, key = { it.id }) { food ->
//                CustomFoodCard(food, onClick = { onItemClick(food) })
//            }
//        }
//    }
//}


@Composable
fun CustomFoodRow(
    customFoods: List<CustomFood>,
    onItemClick: (CustomFood) -> Unit,
    onAddFood: (CustomFood) -> Unit,
    uid: String
) {
    var showDialog by remember { mutableStateOf(false) }
    val displayItems = customFoods.take(5)

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Your Custom Food", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = { showDialog = true }) {
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

    if (showDialog) {
        AddCustomFoodDialog(
            onDismiss = { showDialog = false },
            onAdd = {
                onAddFood(it)
                showDialog = false
            },
            uid = uid
        )
    }
}

