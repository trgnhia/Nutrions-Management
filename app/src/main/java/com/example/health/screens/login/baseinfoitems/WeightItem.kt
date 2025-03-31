package com.example.health.screens.login.baseinfoitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.health.R

@Composable
fun WeightItem(weight: Float , onValueChange: (Float) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Ảnh nền
        Image(
            painter = painterResource(id = R.drawable.weight_background), // thay bằng ảnh nền thật
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tiêu đề
            Text(
                text = "How much do you weigh?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mô tả
            Text(
                text = "This helps us identify your goals and track your progress over time.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Picker + đơn vị
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 70.dp)
            ) {
                NumberPicker(
                    value = weight.toInt(),
                    onValueChange = { onValueChange(it.toFloat()) },
                    range = 40..150,
                    dividersColor = Color.Black,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 20.sp),
                    modifier = Modifier.fillMaxWidth(0.25f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "kg",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeightItem() {
    var weight by remember { mutableStateOf(60f) }
    WeightItem(weight = weight, onValueChange = { weight = it })
}
