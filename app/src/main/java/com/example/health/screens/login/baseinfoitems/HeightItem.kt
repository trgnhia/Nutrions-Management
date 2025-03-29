package com.example.health.screens.login.baseinfoitems


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.health.R


@Composable
fun HeightItem(height: Float, onValueChange: (Float) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.height_background), // Ảnh như trong mẫu
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
            Text("How tall are you?", style = MaterialTheme.typography.titleLarge , fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Your height will help us calculate your body measurements.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 70.dp)

            ) {
                NumberPicker(
                    value = height.toInt(),
                    onValueChange = { onValueChange(it.toFloat()) },
                    range = 100..230,
                    dividersColor = Color.Black,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    modifier = Modifier.padding(start = 16.dp)
                        .fillMaxWidth(0.25f)
                )

                Text(
                    text = "cm",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewHeightItem() {
    var height by remember { mutableStateOf(170f) }
    HeightItem(height = height , onValueChange = { height = it })
}
