package com.example.health.screens.main.diary.compose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.health.R
import com.example.health.data.local.entities.DefaultFood
import java.io.File

@Composable
fun DefaultFoodCard(
    food: DefaultFood,
    onClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        Log.d("ImageDebug", "Url = ${food.UrlImage}")
    }
    val context = LocalContext.current
    val imageRequest = remember(food.UrlImage) {
        ImageRequest.Builder(context)
            .data(File(food.UrlImage)) // ✅ truyền File thay vì String
            .crossfade(true)
            .build()
    }

    Card(
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = food.Name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = food.Name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                softWrap = false
            )
        }
    }
}
