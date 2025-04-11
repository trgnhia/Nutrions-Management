package com.example.health.screens.main.diary.compose

import com.example.health.R
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.health.data.local.entities.DefaultFood
import java.util.Date

@Composable
fun MealSummaryButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_diary_unselected), // icon bạn cung cấp
                contentDescription = "Meal summary"
            )
        }
    }
}

@Composable
fun TodayMealDialog(
    foods: List<DefaultFood>,
    onDismiss: () -> Unit,
    onSaveClick: () -> Unit,
    selectDay: Date
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Today's menu", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(12.dp))

                foods.forEach { food ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        AsyncImage(
                            model = food.UrlImage.ifBlank { R.drawable.default_dish },
                            contentDescription = food.Name,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(food.Name, modifier = Modifier.weight(1f))

                        // Quantity box
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(onClick = { /* -1 */ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_mute), // hoặc tên file của bạn
                                    contentDescription = "Decrease"
                                )
                            }
                            Text("${food.Quantity}")
                            IconButton(onClick = { /* +1 */ }) {
                                Icon(Icons.Default.Add, contentDescription = "Increase")
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(food.QuantityType)
                            IconButton(onClick = { /* remove item */ }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onSaveClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }
}

