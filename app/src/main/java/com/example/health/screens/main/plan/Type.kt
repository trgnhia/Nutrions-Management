package com.example.health.screens.main.plan

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import com.example.health.navigation.routes.GraphRoute

@Composable
fun Type(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 👉 Top header với background + ảnh + tiêu đề
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_back_diet),
                contentDescription = "Header background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 👉 Column chứa icon back, tiêu đề, nút 30 days
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Healthy Eating\nSupport",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "7-Day\nMeal Plan",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Text("7 days")
                }
            }
        }

        // Line separator
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 24.dp)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 👉 Các loại chế độ ăn
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DietTypeCard(
                title = "Vegan",
                description = "Veggies only. No meat \n or animal products.",
                backgroundRes = R.drawable.vegan_btn_back,
                onClick = { navController.navigate(GraphRoute.Vegan.route) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DietTypeCard(
                title = "High Protein",
                description = "Lean meat & seafood \n to build muscle.",
                backgroundRes = R.drawable.high_btn_back,
                onClick = { navController.navigate(GraphRoute.HighProtein.route) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DietTypeCard(
                title = "Keto",
                description = "Low-carb, high-fat for fat burning.",
                backgroundRes = R.drawable.keto_btn_back,
                onClick = { navController.navigate(GraphRoute.Keto.route) }
            )
        }
    }
}
@Composable
fun DietTypeCard(
    title: String,
    description: String,
    backgroundRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Lớp mờ overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color.Black.copy(alpha = 0.25f))
        )

        // Nội dung chữ căn giữa
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start // bạn có thể đổi thành CenterHorizontally nếu muốn căn giữa tuyệt đối
        ) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp // 👉 nhỏ hơn
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                color = Color.White,
                fontSize = 12.sp, // 👉 nhỏ hơn
                lineHeight = 13.sp,
            )
        }
    }
}


