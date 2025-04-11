package com.example.health.screens.main.profile

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import kotlinx.datetime.LocalTime
import network.chaintech.kmp_date_time_picker.utils.MAX
import network.chaintech.kmp_date_time_picker.utils.MIN


@Composable
fun FlippableSwitch(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    enabled: Boolean = true,
    thumbContent: (@Composable () -> Unit)? = { },
    colors: SwitchColors = SwitchDefaults.colors(),
    onCheckedChange: ((Boolean) -> Unit)?
) {
    val rotation by animateFloatAsState(
        targetValue = if (isChecked) {
            360f
        } else {
            0f
        },
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    Switch(
        checked = isChecked,
        enabled = enabled,
        colors = colors,
        thumbContent = thumbContent,
        onCheckedChange = onCheckedChange,
        modifier = modifier
            .graphicsLayer {
                transformOrigin = TransformOrigin.Center
                rotationY = rotation
            }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reminder(navController: NavController) {
    val orange = Color(0xFFFF8000)
    val meals = listOf("Breakfast", "Lunch", "Dinner", "Snack")
    val icons = listOf("🥞", "🍝", "🍲", "🍪")

    val reminderStates: Map<String, MutableState<Boolean>> = remember {
        meals.associateWith { mutableStateOf(true) }
    }

    val timeStates: Map<String, MutableState<LocalTime>> = remember {
        meals.associateWith {
            mutableStateOf(LocalTime(7 + meals.indexOf(it) * 3, 15))
        }
    }

    var selectedMeal by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Set Meal Reminder", color = Color.White)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = orange),
                modifier = Modifier.height(56.dp) // giảm chiều cao header
            )

            Spacer(modifier = Modifier.height(8.dp))

            meals.forEachIndexed { index, meal ->
                MealItem(
                    icon = icons[index],
                    meal = meal,
                    time = timeStates[meal]!!.value,
                    reminderEnabled = reminderStates[meal]!!.value,
                    onTimeClick = {
                        selectedMeal = meal
                    },
                    onSwitchToggle = {
                        reminderStates[meal]?.value = it
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Set meal reminders throughout the day.The app will notify you on time to help maintain healthy eating habits.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 24.dp),
                color = Color.Gray,
                lineHeight = 20.sp,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = R.drawable.bell_image),
                contentDescription = "Reminder Bell",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(160.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .navigationBarsPadding()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orange),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Next", color = Color.White, fontSize = 16.sp)
            }
        }

        selectedMeal?.let { meal ->
            WheelTimePickerView(
                showTimePicker = true,
                height = 200.dp,
                startTime = timeStates[meal]!!.value,
                minTime = LocalTime.MIN(),
                maxTime = LocalTime.MAX(),
                dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
                doneLabel = "Done",
                title = meal,
                titleStyle = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    lineHeight = 32.sp,
                    color = Color.Gray
                ),
                doneLabelStyle = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    lineHeight = 32.sp,
                    color = orange
                ),
                textStyle = TextStyle(color = Color.Black),
                rowCount = 3,
                onDoneClick = { newTime ->
                    timeStates[meal]?.value = newTime
                    selectedMeal = null
                },
                onDismiss = {
                    selectedMeal = null
                }
            )
        }
    }
}

@Composable
fun MealItem(
    icon: String,
    meal: String,
    time: LocalTime,
    reminderEnabled: Boolean,
    onTimeClick: () -> Unit,
    onSwitchToggle: (Boolean) -> Unit
) {
    val orange = Color(0xFFFF8000)

    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(width = 2.dp, color = orange, shape = RoundedCornerShape(24.dp))
            .background(Color(0xFFF8F8F8))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 28.sp,
            modifier = Modifier.size(36.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = meal, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    if (reminderEnabled) onTimeClick()
                }
                .background(orange)
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = "%02d:%02d".format(time.hour, time.minute),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        FlippableSwitch(
            isChecked = reminderEnabled,
            onCheckedChange = onSwitchToggle
        )
    }
}
