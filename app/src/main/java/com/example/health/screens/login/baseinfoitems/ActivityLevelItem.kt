package com.example.health.screens.login.baseinfoitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.health.R

data class ActivityLevelOption(
    val level: Int,
    val title: String,
    val description: String,
    val iconRes: Int
)

val activityOptions = listOf(
    ActivityLevelOption(1, "Low", "I move only when I need to grab a cup of coffee", R.drawable.act_low),
    ActivityLevelOption(2, "Lightly", "A gentle personal exercise level, suitable for beginners.", R.drawable.act_light),
    ActivityLevelOption(3, "Moderate", "I don’t run much, but a good part of my day involves moving", R.drawable.act_moderate),
    ActivityLevelOption(4, "High", "I don’t get a chance to sit down during the day", R.drawable.act_high),
    ActivityLevelOption(5, "Extreme", "My job involves intense physical activity", R.drawable.act_extreme)
)

@Composable
fun ActivityLevelItem(activityLevel: Int, onValueChange: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp )
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What's your activity level?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        activityOptions.forEach { option ->
            ActivityLevelOptionCard(
                option = option,
                isSelected = activityLevel == option.level,
                onClick = { onValueChange(option.level) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
@Composable
fun ActivityLevelOptionCard(
    option: ActivityLevelOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFD0F0F8) else Color(0xFFF5F5F5)
    val borderColor = if (isSelected) Color(0xFF00BCD4) else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nội dung văn bản (trái)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = option.title,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = option.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(
                    width = 2.dp,
                    color =  Color(0xFF00BCD4) ,
                    shape = CircleShape
                )
        ) {
            Image(
                painter = painterResource(id = option.iconRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewActivityLevelItem() {
    var selectedLevel by remember { mutableStateOf(3) }
    ActivityLevelItem(activityLevel = selectedLevel, onValueChange = { selectedLevel = it })
}

