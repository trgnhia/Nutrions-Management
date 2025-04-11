package com.example.health.screens.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.health.data.local.viewmodel.HealthMetricViewModel

@Composable
fun Profile(
    navController: NavController,
    currentWeight: Float,
    userName: String,
    userEmail: String, // ✅ Email được truyền vào từ AuthViewModel
    healthMetricViewModel: HealthMetricViewModel
) {
    val healthMetric by healthMetricViewModel.lastMetric.collectAsState()
    val current = healthMetric?.Weight ?: currentWeight
    val displayWeight = if (current.isNaN()) "0.0" else "%.1f".format(current)
    val targetWeight = healthMetric?.WeightTarget ?: 0f
    val displayTarget = if (targetWeight.isNaN() || targetWeight == 0f) "Not Set" else "%.1f".format(targetWeight)

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
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(50))
        )

        Text(
            text = userName,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = userEmail,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileOption(
            text = "Current Weight: ${displayWeight}kg",
            iconId = R.drawable.ic_weight
        )

        ProfileOption(
            text = "Target: ${displayTarget}kg",
            iconId = R.drawable.ic_target
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileButton(
            text = "Macro Setting",
            onClick = { navController.navigate(ProfileRoutes.MacroSetting) },
            backgroundColor = Color(0xFFFF8000)
        )

        ProfileButton(
            text = "Update Body Index",
            onClick = { navController.navigate(ProfileRoutes.UpdateBodyIndex) },
            backgroundColor = Color(0xFF0076B0)
        )

        ProfileButton(
            text = "Set Meal Reminder",
            onClick = { navController.navigate(ProfileRoutes.Reminder) },
            backgroundColor = Color(0xFF323F26)
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
        Text(
            text = text.uppercase(),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProfileSettings(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        SettingsOption(R.drawable.ic_privacypolicy, "Privacy Policy") {}
        SettingsOption(R.drawable.ic_help_support, "Help & Support") {}
        SettingsOption(R.drawable.ic_contact_us, "Contact Us") {}
        SettingsOption(R.drawable.ic_logout, "Log Out") {}
    }
}

@Composable
fun SettingsOption(iconId: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = Color.Black, fontSize = 16.sp)
    }
}
