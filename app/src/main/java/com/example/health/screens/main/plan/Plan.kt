package com.example.health.screens.main.plan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.navigation.routes.DiaryRoutes
import com.example.health.navigation.routes.GraphRoute
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.ParenCompose

@Composable
fun Plan(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header với ảnh nền và chữ
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(375f / 320f) // 🔁 Tỷ lệ chuẩn 375:320
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_back_plan),
                contentDescription = null,
                contentScale = ContentScale.Fit, // ✅ Hiển thị toàn bộ ảnh, không crop
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 40.dp, end = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Set Your\nCustom \nPlan",
                    fontSize = 33.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    lineHeight = 40.sp, // 👉 khoảng cách dòng (~125% của fontSize)
                    modifier = Modifier.padding(start = 25.dp)
                )

                Spacer(modifier = Modifier.height(45.dp))

                Text(
                    text = "Or choose from our meal plans.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Các nút Food & Diet
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            PlanOptionCard(
                title = "Food",
                description = "Healthy Meals for your daily intake",
                backgroundRes = R.drawable.food_back,
                titleFontSize = 25.sp,       // ⬅ tăng kích thước tiêu đề
                descriptionFontSize = 20.sp, // ⬅ tăng mô tả
                onClick = {
                    navController.navigate("${DiaryRoutes.Add}?parent=${ParenCompose.FROMPLAN}")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PlanOptionCard(
                title = "Diet Plan",
                description = "Customized 7-day diet programs",
                backgroundRes = R.drawable.diet_back,
                titleFontSize = 25.sp,
                descriptionFontSize = 20.sp,
                onClick = { navController.navigate(GraphRoute.Diet.route) }
            )
        }
    }
}



@Composable
fun PlanOptionCard(
    title: String,
    description: String,
    backgroundRes: Int,
    titleFontSize: androidx.compose.ui.unit.TextUnit = 20.sp,
    descriptionFontSize: androidx.compose.ui.unit.TextUnit = 14.sp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Overlay mờ nếu cần
        Box(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = titleFontSize,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = Color.White,
                fontSize = descriptionFontSize
            )
        }
    }
}
