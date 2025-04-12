package com.example.health.screens.main.statistical

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import kotlin.math.roundToInt

@Composable
fun Analysis(navController: NavController) {
    val backgroundColor = Color(0xFFF5F5F5)

    val caloriesConsumedPercent = 0f
    val caloriesBurnedPercent = 0f

    val tabs = listOf("Week", "Month", "Year")
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Fake Data theo tab
    val (caloriesConsumed, caloriesBurned, carbs, protein, fat) = when (selectedTabIndex) {
        0 -> listOf( // Week
            listOf(1800f, 2000f, 1900f, 2200f, 2100f, 2300f, 2400f),
            listOf(1600f, 1800f, 1700f, 2000f, 1900f, 2100f, 2000f),
            listOf(200f, 220f, 210f, 250f, 240f, 260f, 270f),
            listOf(100f, 120f, 110f, 130f, 140f, 150f, 160f),
            listOf(80f, 90f, 85f, 100f, 95f, 110f, 105f)
        )
        1 -> listOf( // Month (4 tuần)
            listOf(8000f, 8200f, 8400f, 8600f),
            listOf(7800f, 8000f, 8200f, 8300f),
            listOf(900f, 920f, 940f, 960f),
            listOf(420f, 440f, 460f, 480f),
            listOf(300f, 320f, 340f, 360f)
        )
        else -> listOf( // Year (12 tháng)
            List(12) { (1600..2400).random().toFloat() },
            List(12) { (1400..2200).random().toFloat() },
            List(12) { (150..300).random().toFloat() },
            List(12) { (90..180).random().toFloat() },
            List(12) { (70..150).random().toFloat() }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 🔙 Back + Calories
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .clickable { navController.popBackStack() }
                    .padding(start = 4.dp)
            )

            Text(
                text = "Calories",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF8000) // 🎯 Màu cam #ff8000
            )
        }

        // 🔘 Progress summary
        ProgressSummary(caloriesConsumedPercent, caloriesBurnedPercent)

        // 🟢 Tab selector
        TabSelector(tabs, selectedTabIndex) { selectedTabIndex = it }

        // 📊 Charts
        ChartCard("Calories Chart", listOf(caloriesConsumed to Color(0xFF00C853), caloriesBurned to Color(0xFFD50000)))
        ChartCard("Carb Chart", listOf(carbs to Color(0xFF00C853)))
        ChartCard("Protein Chart", listOf(protein to Color(0xFFD50000)))
        ChartCard("Fat Chart", listOf(fat to Color(0xFFFF4081)))
    }
}

@Composable
fun ProgressSummary(consumed: Float, burned: Float) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProgressCircle(
                    percentage = consumed,
                    label = "Calories consumed",
                    color = Color(0xFFFF9800),
                    trackColor = Color(0xFFE0E0E0) // 🔁 thay từ vàng nhạt sang xám nhạt
                )
                ProgressCircle(
                    percentage = burned,
                    label = "Calories burned",
                    color = Color(0xFF00C853),
                    trackColor = Color(0xFFE0E0E0) // 🔁 thay từ xanh nhạt sang xám nhạt
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Planned calories: ........",
                color = Color(0xFFFF9800),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold // ⬅️ thêm dòng này để in đậm
            )
        }
    }
}

@Composable
fun TabSelector(tabs: List<String>, selected: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(12.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = index == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .background(
                        if (isSelected) Color.White else Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab,
                    fontSize = 14.sp,
                    color = if (isSelected) Color.Black else Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun ProgressCircle(percentage: Float, label: String, color: Color, trackColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = trackColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8.dp.toPx())
                )
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360 * (percentage / 100f),
                    useCenter = false,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8.dp.toPx())
                )
            }
            Text("${percentage.roundToInt()}%", fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 12.sp, color = Color.Black)
    }
}

@Composable
fun ChartCard(title: String, datasets: List<Pair<List<Float>, Color>>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, fontSize = 16.sp)
                Text("Export", fontSize = 14.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            SimpleLineChart(datasets)
        }
    }
}

@Composable
fun SimpleLineChart(
    datasets: List<Pair<List<Float>, Color>>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(horizontal = 8.dp)
) {
    Canvas(modifier = modifier) {
        val maxPoints = datasets.maxOf { it.first.maxOrNull() ?: 0f }.coerceAtLeast(1f)
        val minPoints = datasets.minOf { it.first.minOrNull() ?: 0f }
        val chartHeight = size.height
        val chartWidth = size.width
        val pointSpacing = chartWidth / (datasets.first().first.size - 1)

        datasets.forEach { (data, color) ->
            val path = Path()
            data.forEachIndexed { index, value ->
                val x = index * pointSpacing
                val y = chartHeight - ((value - minPoints) / (maxPoints - minPoints)) * chartHeight
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            drawPath(
                path = path,
                color = color,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 4f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}
