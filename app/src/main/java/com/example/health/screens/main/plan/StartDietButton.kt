package com.example.health.screens.main.plan

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

@Composable
fun StartDietButton(
    currentDietCode: Int,
    userDietCode: Int,
    onClick: () -> Unit,
    isStop: Boolean // 👈 Thêm mới
) {
    val context = LocalContext.current
    val isLocked = userDietCode != 0 && userDietCode != currentDietCode && !isStop

    val dietColor = when (currentDietCode) {
        1 -> Color(0xFF234F26)
        2 -> Color(0xFFE87046)
        3 -> Color(0xFF06487E)
        else -> Color.Gray
    }

    val textColor = Color.White
    val buttonText = if (isStop) "Stop your diet now" else "Start your diet now"

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                if (!isLocked) onClick()
                else Toast.makeText(context, "You already selected another diet.", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(45),
            colors = ButtonDefaults.buttonColors(
                containerColor = dietColor,
                contentColor = textColor
            ),
            enabled = true
        ) {
            Text(buttonText, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        if (isLocked) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(45))
                    .background(Color.White.copy(alpha = 0.4f))
            )
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Locked",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp)
            )
        }
    }
}



