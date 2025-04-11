package com.example.health.screens.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.navigation.routes.ProfileRoutes

@Composable
fun Profile(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = R.drawable.profile_avatar),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(120.dp).clip(RoundedCornerShape(50))
        )
        Text(
            text = "Hoc Beo Dang Giam Can",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "nthoc09102004@gmail.com",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileOption(
        text = "Current Weight: 80kg",
        iconId = R.drawable.ic_weight
        )
        ProfileOption(
            text = "Target: 75kg",
            iconId = R.drawable.ic_target
        )




        Spacer(modifier = Modifier.height(16.dp))

        @Composable
        fun ProfileButton(text: String, onClick: () -> Unit, backgroundColor: Color) {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    // Tăng padding bên trong để có không gian bên trong nút rộng rãi hơn
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
            ) {
                // Đặt text thành chữ in hoa
                Text(text = text.uppercase(), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }

// Ví dụ sử dụng hàm ProfileButton đã chỉnh sửa
        ProfileButton(
            text = "Macro Setting",
            onClick = { navController.navigate(ProfileRoutes.MacroSetting.route) },
            backgroundColor = Color(0xFFFF8000) // Mã màu #FF8000
        )
        ProfileButton(
            text = "Update Body Index",
            onClick = { navController.navigate(ProfileRoutes.UpdateBodyIndex.route) },
            backgroundColor = Color(0xFF0076B0) // Mã màu #0076B0
        )

        ProfileButton(
            text = "Set Meal Reminder",
            onClick = { navController.navigate(ProfileRoutes.Reminder.route) },
            backgroundColor = Color(0xFF323F26) // Green
        )

        ProfileSettings(navController)
    }
}

@Composable
fun ProfileOption(text: String, iconId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp)
    }
}

@Composable
fun ProfileButton(text: String, onClick: () -> Unit, backgroundColor: Color) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ProfileSettings(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp), // Đã chỉnh sửa padding từ 24dp xuống 15dp
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        SettingsOption(
            iconId = R.drawable.ic_privacypolicy,
            text = "Privacy Policy",
            onClick = { /* TODO: Add navigation logic here */ }
        )
        SettingsOption(
            iconId = R.drawable.ic_help_support,
            text = "Help & Support",
            onClick = { /* TODO: Add navigation logic here */ }
        )
        SettingsOption(
            iconId = R.drawable.ic_contact_us,
            text = "Contact Us",
            onClick = { /* TODO: Add navigation logic here */ }
        )
        SettingsOption(
            iconId = R.drawable.ic_logout,
            text = "Log Out",
            onClick = { /* TODO: Implement logout logic or navigate */ }
        )
    }
}

@Composable
fun SettingsOption(iconId: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp), // Đã chỉnh sửa padding để tạo cảm giác thoải mái hơn
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = text,
            modifier = Modifier.size(24.dp), // Đã thay đổi kích thước icon xuống 24dp
            tint = Color.Black // Thiết lập màu của icon là đen
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.Black,
            fontSize = 16.sp // Đã thay đổi kích thước font xuống 16sp
        )
    }
}


