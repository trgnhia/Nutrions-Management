package com.example.health.screens.login.baseinfoitems

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.health.R
import com.example.health.data.utils.HealthMetricUtil
import com.example.health.data.utils.to1DecimalString
import com.example.health.data.utils.to2DecimalString

@Composable
fun BodyIndexesItem(age: Int, height: Float, weight: Float, gender: String, activityLevel: Int) {
    val bmi = HealthMetricUtil.calculateBMI(weight, height)
    val bmr = HealthMetricUtil.calculateBMR(weight, height, age, gender)
    val tdee = HealthMetricUtil.calculateTDEE(bmr, activityLevel)
    val addition = HealthMetricUtil.bodyAddition(bmi)
    val advice = HealthMetricUtil.advice(bmi)
    val bmrAssessment = HealthMetricUtil.bmrAssessment(bmr, gender)

    var showBmiDialog by remember { mutableStateOf(false) }
    var showBmrDialog by remember { mutableStateOf(false) }
    var showTdeeDialog by remember { mutableStateOf(false) }

    if (showBmiDialog) BmiDetailDialog { showBmiDialog = false }
    if (showBmrDialog) BmrDetailDialog { showBmrDialog = false }
    if (showTdeeDialog) TdeeDetailDialog { showTdeeDialog = false }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.index_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Overview of body indexes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.Black
            )

            IndexBox(
                title = "BMI",
                value = bmi.to1DecimalString(),
                description = addition,
                iconRes = R.drawable.icon_bmr,
                borderColor = Color(0xFFAA00FF),
                bmi = bmi,
                onClick = { showBmiDialog = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            IndexBox(
                title = "BMR",
                value = bmr.to2DecimalString(),
                description = bmrAssessment,
                iconRes = R.drawable.icon_bmr,
                borderColor = Color(0xFFFF9800),
                onClick = { showBmrDialog = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            IndexBox(
                title = "TDEE",
                value = tdee.to2DecimalString(),
                description = getTdeeLevelDescription(activityLevel),
                iconRes = R.drawable.icon_bmr,
                borderColor = Color(0xFF03A9F4),
                onClick = { showTdeeDialog = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Suggestion",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = advice,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
@Composable
fun IndexBox(title: String, value: String, description: String, iconRes: Int, borderColor: Color, bmi: Float? = null , onClick: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder(true),
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(borderColor, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "$title: $description", fontWeight = FontWeight.Bold, color = Color.Black)
                    if (title != "BMR")
                        Text(text = value, style = MaterialTheme.typography.bodyMedium)
                }
            }

            // 👉 Thêm gauge cho BMI
            if (title == "BMI" && bmi != null) {
                Spacer(modifier = Modifier.height(8.dp))
                BmiGauge(bmi = bmi)
            }
        }
    }
}

fun getTdeeLevelDescription(activityLevel: Int): String {
    return when (activityLevel) {
        1 -> "Low exercise"
        2 -> "Lightly active"
        3 -> "Moderate"
        4 -> "High"
        5 -> "Extreme"
        else -> "Unknown"
    }
}@Composable
fun BmiGauge(bmi: Float) {
    var showDialog by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable { showDialog = true }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val colors = listOf(
                    Color(0xFF2196F3), // Blue - Underweight
                    Color(0xFF4CAF50), // Green - Normal
                    Color(0xFFCDDC39), // Light Yellow - Slightly Overweight
                    Color(0xFFFF9800), // Orange - Overweight
                    Color(0xFFF44336)  // Red - Obese
                )

                val segments = listOf(16f, 18.5f, 23f, 25f, 30f, 40f)

                val segmentWidth = size.width / (segments.last() - segments.first())
                val barHeight = 16.dp.toPx()

                for (i in 0 until colors.size) {
                    val start = (segments[i] - segments.first()) * segmentWidth
                    val width = (segments[i + 1] - segments[i]) * segmentWidth

                    drawRoundRect(
                        color = colors[i],
                        topLeft = Offset(start, size.height / 2 - barHeight / 2),
                        size = Size(width, barHeight),

                    )
                }

                // Vẽ tam giác đánh dấu
                val triangleX = ((bmi - segments.first()).coerceIn(0f, segments.last() - segments.first())) * segmentWidth
                val triangleSize = 10.dp.toPx()
                val triangleY = size.height / 2 + barHeight / 2 // đáy thanh
                drawPath(
                    path = Path().apply {
                        moveTo(triangleX, triangleY - triangleSize) // đỉnh tam giác dưới
                        lineTo(triangleX - 10, triangleY )
                        lineTo(triangleX + 10, triangleY)
                        close()
                    },
                    color = Color.Black
                )

            }
        }

        if (showDialog) {
            BmiExplanationDialog(bmi = bmi, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun BmiExplanationDialog(bmi: Float, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Got it")
            }
        },
        title = {
            Text("BMI Evaluation")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Your BMI: ${"%.1f".format(bmi)}")
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow("< 18.5", "Underweight", Color(0xFF2196F3))
                InfoRow("18.5 - 22.9", "Normal", Color(0xFF4CAF50))
                InfoRow("23 - 24.9", "Slightly Overweight", Color(0xFFCDDC39))
                InfoRow("25 - 29.9", "Overweight", Color(0xFFFF9800))
                InfoRow("30+", "Obese", Color(0xFFF44336))
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun InfoRow(range: String, label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        Text("$range: $label", style = MaterialTheme.typography.bodyMedium)
    }
}
@Composable
fun BmiDetailDialog(onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Got it") }
        },
        title = { Text("BMI Index") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = buildAnnotatedString {
                        append("BMI stands for ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("\"Body Mass Index\"")
                        }
                        append(", which is referred to as the body mass index.\n\n")
                        append("Through BMI, we can determine whether an adult’s body is underweight, normal, or overweight, allowing us to find the right \"body transformation\" plan.\n\n")
                        append("Based on your BMI, you can determine a weight adjustment plan:")
                        append("\n• Below 18.5 (Underweight): You need to consume more calories than your body requires.")
                        append("\n• 18.5 - 24.9 (Healthy weight): Maintain your calorie intake at the required level. No need to lose weight. If you still feel your body is not well-proportioned, consider exercising to tone your body and reduce excess fat.")
                        append("\n• 25 and above (Overweight): Create a calorie deficit to lose weight.")
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Image(
                    painter = painterResource(id = R.drawable.bmi_info), // thêm ảnh vào drawable
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color(0xFFE1F5FE) // xanh nhạt
    )
}
@Composable
fun BmrDetailDialog(onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Got it") }
        },
        title = { Text("BMR Index") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "BMR (Basal Metabolic Rate) – Basic Metabolic Rate\n\n" +
                            "BMR represents the minimum amount of energy your body needs to maintain essential functions.\n" +
                            "Even when at rest or asleep, vital organs such as the circulatory and respiratory systems continue to function to sustain life.\n" +
                            "That’s why ensuring a minimum daily calorie intake is crucial.\n\n" +
                            "Without it, your body may feel fatigued and unable to concentrate.\n\n" +
                            "Common BMR Calculation Method:\n\n" +
                            "• For women:\n" +
                            "BMR = 655 + [9.6 × Weight (kg)] + [1.8 × Height (cm)] − (4.7 × Age)\n\n" +
                            "• For men:\n" +
                            "BMR = 66 + [13.7 × Weight (kg)] + [5 × Height (cm)] − (6.8 × Age)\n\n",
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color(0xFFD7FFD9) // xanh lá nhạt
    )
}
@Composable
fun TdeeDetailDialog(onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Got it") }
        },
        title = { Text("TDEE Index") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "What is TDEE?\n\n" +
                            "TDEE (Total Daily Energy Expenditure) refers to the total amount of energy your body consumes in a day.\n\n" +
                            "In other words, it represents the total calories your body uses in 24 hours, including basal metabolism (BMR) and physical activities.\n\n" +
                            "TDEE Formula:\n" +
                            "TDEE = BMR × R\n\n"
//                            + "Where R is a variable factor that depends on a person’s activity level:\n\n" +
//                            "• Sedentary (little to no activity): TDEE = BMR × 1.2\n" +
//                            "• Light activity (1–3 workouts/week or light labor): TDEE = BMR × 1.375\n" +
//                            "• Moderate activity (4–5 workouts/week or moderate labor): TDEE = BMR × 1.55\n" +
//                            "• High activity (6–7 workouts/week or intense labor): TDEE = BMR × 1.725\n" +
//                            "• Very high activity (heavy training or extreme labor): TDEE = BMR × 1.9\n\n"
                            ,
                    style = MaterialTheme.typography.bodyMedium
                )
                Image(
                    painter = painterResource(id = R.drawable.tdee_info), // thêm ảnh vào drawable
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color(0xFFFFF8E1) // vàng nhạt
    )
}

@Preview
@Composable
fun PreviewBodyIndexesItem() {
    BodyIndexesItem(age = 25, height = 170f, weight = 70f, gender = "male", activityLevel = 2)
}
