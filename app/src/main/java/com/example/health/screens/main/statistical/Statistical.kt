package com.example.health.screens.main.statistical

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.navigation.routes.StatisticalRoutes

@Composable
fun Statistical(navController: NavController) {
    val orange = Color(0xFFFF9800)
    val lightGrayBackground = Color(0xFFF5F5F5)
    val currentWeight = 84.5f
    val startWeight = 85f
    val targetWeight = 80f
    val progressPercent =
        ((startWeight - currentWeight) / (startWeight - targetWeight)).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGrayBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "Statistical",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
        )

        // Card: Weight fluctuation
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Weight fluctuation",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Semi Circular Progress
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                ) {
                    CircularProgressIndicator(
                        progress = progressPercent,
                        strokeWidth = 12.dp,
                        color = orange,
                        trackColor = Color(0xFFE0E0E0),
                        modifier = Modifier
                            .size(180.dp)
                            .rotate(180f) // Lật ngược nửa vòng
                    )

                    // Che phần nửa dưới bằng màu xám nhạt
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .align(Alignment.BottomCenter)
                            .background(lightGrayBackground)
                    )

                    // Nội dung ở giữa vòng
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${(progressPercent * 100).toInt()}%",
                            style = MaterialTheme.typography.titleLarge.copy(color = orange)
                        )
                        Text("You have lost ${"%.1f".format(startWeight - currentWeight)}kg")
                        Text(
                            text = "Your current weight is ${"%.1f".format(currentWeight)}kg",
                            style = MaterialTheme.typography.bodySmall.copy(color = orange)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Start\n${startWeight} kg", color = Color.Gray)
                    Text("Target\n${targetWeight} kg", color = Color.Gray)
                }
            }
        }

        // Button: Nutritional value analysis
        Button(
            onClick = {
                navController.navigate(StatisticalRoutes.Analysis.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = orange
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_statistical), // icon tuỳ chỉnh
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Nutritional value analysis", color = Color.Black)
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }
}
