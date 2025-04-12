package com.example.health.screens.main.diary.compose

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.health.R
import com.example.health.data.local.entities.BurnOutCaloPerDay
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.local.viewmodel.MacroViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeaderSection(
    selectedDate: Date,
    onDateChange: (Date) -> Unit,
    healthMetricViewModel: HealthMetricViewModel,
    macroViewModel: MacroViewModel,
    totalNutrionsPerDayViewModel: TotalNutrionsPerDayViewModel,
    burnOutCaloPerDayViewModel: BurnOutCaloPerDayViewModel,
    calorBurn: MutableState<Float>

) {
    val openDatePicker = remember { mutableStateOf(false) }

    val healthMetric by healthMetricViewModel.lastMetric.collectAsState()
    val macro by macroViewModel.macro.collectAsState()
    val uid = healthMetric?.Uid

    val totalNutrions = remember(uid, selectedDate) {
        uid?.let {
            totalNutrionsPerDayViewModel.getByDateAndUid(selectedDate, it)
        }
    }?.collectAsState(initial = null)

    val burnOut = produceState<BurnOutCaloPerDay?>(initialValue = null, selectedDate) {
        value = burnOutCaloPerDayViewModel.getByDate(selectedDate)
    }

    // Gán giá trị mặc định nếu chưa có dữ liệu
    val tdee = healthMetric?.TDEE ?: 0f
    val calor = totalNutrions?.value?.TotalCalo ?: 0f
    val macCarb = macro?.Carb ?: 0f
    val macPro = macro?.Protein ?: 0f
    val macFat = macro?.Fat ?: 0f
    val carb = totalNutrions?.value?.TotalCarb ?: 0f
    val pro = totalNutrions?.value?.TotalPro ?: 0f
    val fat = totalNutrions?.value?.TotalFat ?: 0f
    val burnCalo = burnOut.value?.TotalCalo ?: 0f
    val need = tdee - calor

    val selectedLocalDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val today = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDE8025))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔸 Ngày và điều hướng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (selectedLocalDate == today) "TODAY" else "",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 15.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    val newLocalDate = selectedLocalDate.minusDays(1)
                    onDateChange(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                }) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = Color.White)
                }

                IconButton(onClick = { openDatePicker.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Text(
                    selectedLocalDate.format(DateTimeFormatter.ofPattern("MMMM dd")),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(
                    onClick = {
                        val newLocalDate = selectedLocalDate.plusDays(1)
                        onDateChange(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    }
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Calorie summary
        CalorieSummarySection(calor.toInt(), need.toInt(), tdee.toInt(), burnCalo.toInt() , calorBurn)

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Macronutrient progress
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NutrientItem("Carbs", carb.toInt(), macCarb.toInt())
            NutrientItem("Protein", pro.toInt(), macPro.toInt())
            NutrientItem("Fat", fat.toInt(), macFat.toInt())
        }
    }

    if (openDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = { openDatePicker.value = false },
            confirmButton = {
                TextButton(onClick = { openDatePicker.value = false }) {
                    Text("OK")
                }
            }
        ) {
            val pickerState = rememberDatePickerState()
            DatePicker(state = pickerState)
            LaunchedEffect(pickerState.selectedDateMillis) {
                pickerState.selectedDateMillis?.let {
                    val newDate = Date(it)
//                    if (!newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(today)) {
//                        onDateChange(newDate)
//                    }
                    onDateChange(newDate)

                }
            }
        }
    }
}
