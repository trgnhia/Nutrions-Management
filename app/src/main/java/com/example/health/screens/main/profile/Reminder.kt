package com.example.health.screens.main.profile

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.NotifyViewModel
import com.example.health.data.utils.DateUtils
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import kotlinx.datetime.LocalTime
import network.chaintech.kmp_date_time_picker.utils.MAX
import network.chaintech.kmp_date_time_picker.utils.MIN
import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity
import androidx.compose.runtime.LaunchedEffect
import com.example.health.alarm.AlarmScheduler


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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reminder(
    navController: NavController,
    notifyViewModel: NotifyViewModel,
    accountViewModel: AccountViewModel
) {
    val context = LocalContext.current
    val orange = Color(0xFFFF8000)
    val meals = listOf("Breakfast", "Lunch", "Dinner", "Snack")
    val icons = listOf("🥞", "🍝", "🍲", "🍪")
    val uid = accountViewModel.getCurrentUid()
    val notifyList by notifyViewModel.getAllByUid(uid).collectAsState()

    // ✅ Xin quyền POST_NOTIFICATIONS cho Android 13+
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val isGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            if (!isGranted && context is ComponentActivity) {
                ActivityCompat.requestPermissions(context, arrayOf(permission), 1001)
            }
        }

        // ✅ Xin quyền SCHEDULE_EXACT_ALARM cho Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = android.net.Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            }
        }
    }

    val reminderStates = remember {
        meals.associateWith { mutableStateOf(true) }.toMutableMap()
    }
    // ✅ Thời gian hiển thị theo Notify trong DB
    val timeStates = remember {
        meals.associateWith { mutableStateOf<kotlinx.datetime.LocalTime?>(null) }.toMutableMap()
    }
    LaunchedEffect(notifyList) {
        notifyList.forEach { notify ->
            val meal = meals.find { it.lowercase() == notify.id } ?: return@forEach
            val localTime = DateUtils.toKxLocalTime(notify.NotifyTime)
            timeStates[meal]?.value = localTime
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

//            meals.forEachIndexed { index, meal ->
//                MealItem(
//                    icon = icons[index],
//                    meal = meal,
//                    //time = timeStates[meal]!!.value,
//                    time = timeStates[meal]?.value ?: LocalTime(7 + meals.indexOf(meal), 15),
//
//                    reminderEnabled = reminderStates[meal]!!.value,
//                    onTimeClick = {
//                        selectedMeal = meal
//                    },
//                    onSwitchToggle = {
//                        reminderStates[meal]?.value = it
//                    }
//                )
//            }
            meals.forEachIndexed { index, meal ->
                MealItem(
                    icon = icons[index],
                    meal = meal,
                    time = timeStates[meal]?.value ?: LocalTime(7 + index * 3, 15),
                    reminderEnabled = reminderStates[meal]?.value == true,
                    onTimeClick = {
                        selectedMeal = meal
                    },
                    onSwitchToggle = { isEnabled ->
                        // ✅ Cập nhật trạng thái UI
                        reminderStates[meal]?.value = isEnabled

                        // ✅ Gọi alarm logic
                        val notify = notifyList.find { it.id == meal.lowercase() }
                        if (notify != null) {
                            if (isEnabled) {
                                AlarmScheduler.scheduleAlarm(context, notify)
                            } else {
                                AlarmScheduler.cancelAlarm(context, notify)
                            }
                        }
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
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = R.drawable.bell_image),
                contentDescription = "Reminder Bell",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        selectedMeal?.let { meal ->
            WheelTimePickerView(
                showTimePicker = true,
                height = 200.dp,
                startTime = timeStates[meal]?.value ?: LocalTime(7 + meals.indexOf(meal), 15),
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
                    notifyViewModel.updateNotifyTime(
                        uid = accountViewModel.getCurrentUid(),
                        mealId = meal.lowercase(),
                        newTime = DateUtils.toTodayDate(newTime.hour, newTime.minute),
                        context = context
                    )
                    selectedMeal = null // ✅ Dismiss sau khi xong
                },
                onDismiss = {
                    selectedMeal = null // ✅ Bắt buộc để tránh đơ giao diện
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
        //  Biểu tượng món ăn
        Text(
            text = icon,
            fontSize = 28.sp,
            modifier = Modifier.size(36.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        // Tên bữa ăn
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = meal,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Hiển thị giờ nếu switch bật
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(enabled = reminderEnabled) { onTimeClick() } // ✅ chỉ clickable khi bật switch
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

        // 🔁 Switch bật/tắt thông báo
        FlippableSwitch(
            isChecked = reminderEnabled,
            onCheckedChange = onSwitchToggle
        )
    }
}
