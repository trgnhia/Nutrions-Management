package com.example.health.screens.login.baseinfoitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.health.R

@Composable
fun GenderItem(gender: String , onValueChange: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.gender_background), // ảnh người đứng
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "What is your gender?",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 13.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Collecting your gender information will help us calculate your body measurements accurately.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            GenderOption(
                selected = gender == "Male",
                iconRes = R.drawable.male,
                label = "Male",
                onClick = { onValueChange("Male") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            GenderOption(
                selected = gender == "Female",
                iconRes = R.drawable.female,
                label = "Female",
                onClick = { onValueChange("Female") }
            )
        }
    }
}

@Composable
fun GenderOption(
    selected: Boolean,
    iconRes: Int,
    label: String,
    onClick: () -> Unit
) {
    val bgColor = if (selected) Color(0xFF00BEBE) else Color(0xFFE0E0E0)
    val textColor = if (selected) Color.White else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(bgColor, shape = RoundedCornerShape(25.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Preview
@Composable
fun PreviewGenderItem() {
    var gender by remember { mutableStateOf("Male") }
    GenderItem(gender = gender, onValueChange = { gender = it })
}
