package com.example.health.screens.login.baseinfoitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.health.R

@Composable
fun NameItem(name: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Nền là ảnh
        Image(
            painter = painterResource(id = R.drawable.welcome), // thay bằng tên ảnh của bạn
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Nội dung chính
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Tiêu đề
            Text(
                text = "Welcome to Health Mate",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top  = 10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mô tả
            Text(
                text = "Health Mate is excited that you have started your journey towards becoming the best version of yourself. We need some basic personal information from you to start your journey.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Label nhập tên
            Text(
                text = "What should we call you?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onValueChange,
                placeholder = {
                    Text("Enter your name", color = Color.Gray)
                },
                shape = RoundedCornerShape(32.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFB9B7B7),
                    focusedContainerColor = Color(0xFFB9B7B7),
                    disabledContainerColor =Color(0xFFB9B7B7),
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    // ✅ Đây là các màu chữ chính
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black
                ),
                // ✅ Dự phòng: nếu hệ thống không nhận màu chữ từ colors thì textStyle vẫn đảm bảo
                textStyle = LocalTextStyle.current.copy(color = Color.Black)
            )

        }
    }
}
@Preview
@Composable
fun Preview(){
    NameItem("Hello") { }
}