package com.example.health.screens.main.diary.compose

import android.os.Build
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
    fallbackTdee: Float = 2000f,
    fallbackCarb: Float = 200f,
    fallbackPro: Float = 100f,
    fallbackFat: Float = 70f,
) {
    val openDatePicker = remember { mutableStateOf(false) }
    val healthMetric = healthMetricViewModel.lastMetric.collectAsState()
    val macro = macroViewModel.macro.collectAsState()
    val totalNutrionsPerDay = healthMetric.value?.Uid?.let {
        totalNutrionsPerDayViewModel.getByDateAndUid(selectedDate, it).collectAsState(initial = null)
    }
    val burnOutCaloPerDay = produceState<BurnOutCaloPerDay?>(initialValue = null, selectedDate) {
        value = burnOutCaloPerDayViewModel.getByDate(selectedDate)
    }

    val tdee = healthMetric.value?.TDEE ?: fallbackTdee // tdee có sẵn r , giá tri mac dinh o day ko quan trong
    val calor = totalNutrionsPerDay?.value?.TotalCalo ?: 500f
    val macCarb = macro.value?.Carb ?: fallbackCarb
    val macPro = macro.value?.Protein ?: fallbackPro
    val macFat = macro.value?.Fat ?: fallbackFat

    val need = tdee - calor

    val carb = totalNutrionsPerDay?.value?.TotalCarb ?: 25f
    val pro = totalNutrionsPerDay?.value?.TotalPro ?: 50f
    val fat = totalNutrionsPerDay?.value?.TotalFat ?: 15f

    val burnCalo = burnOutCaloPerDay.value?.TotalCalo ?: 350f

    val selectedLocalDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val today = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDE8025))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (selectedLocalDate == today) {
                Text(
                    text = "TODAY",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 15.dp)
                )
            } else {
                Text(
                    text = "",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

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
                        if (!newLocalDate.isAfter(today)) {
                            onDateChange(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        }
                    },
                    enabled = selectedLocalDate < today
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CalorieSummarySection(calor.toInt(),need.toInt(), tdee.toInt(), burnCalo.toInt())

        Spacer(modifier = Modifier.height(16.dp))

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
                    if (!newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(today)) {
                        onDateChange(newDate)
                    }
                }
            }
        }
    }
}