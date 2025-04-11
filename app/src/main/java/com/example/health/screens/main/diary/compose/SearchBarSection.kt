package com.example.health.screens.main.diary.compose
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.health.R
import com.example.health.data.local.entities.DefaultFood

@Composable
fun SearchBarWithResult(
    searchText: String,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    results: List<DefaultFood>,
    onResultClick: (DefaultFood) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp)
    ) {
        // 🔍 Search bar
        OutlinedTextField(
            value = searchText,
            onValueChange = onTextChange,
            placeholder = { Text("Search online food...") },
            trailingIcon = {
                IconButton(onClick = onSearchClick) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            )
        )

        // ⬇️ Dropdown result (hiện khi có kết quả và có text)
        if (results.isNotEmpty() && searchText.isNotBlank()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    results.take(5).forEach { food ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onResultClick(food) }
                                .padding(vertical = 6.dp)
                        ) {
                            AsyncImage(
                                model = food.UrlImage.ifBlank { R.drawable.default_dish },
                                contentDescription = food.Name,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(food.Name, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
