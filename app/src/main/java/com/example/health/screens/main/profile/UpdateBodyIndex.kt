package com.example.health.screens.main.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.health.R
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel

@Composable
fun UpdateBodyIndex(navController: NavController, baseInfoViewModel: BaseInfoViewModel, healthMetricViewModel: HealthMetricViewModel) {
    var baseInfo = baseInfoViewModel.baseInfo.collectAsState()
    var metric = healthMetricViewModel.lastMetric.collectAsState()
    // Goi cac bien can truyen vao thi goi o day : copy chat 2 cai entities baseInfo voi healthmetric

//    var height by remember { mutableStateOf(170) }
//    var currentWeight by remember { mutableStateOf(88) }
//    var targetWeight by remember { mutableStateOf(80) }
//    var trainingIntensity by remember { mutableStateOf("Lightly") }
    val height = remember { mutableStateOf(metric.value?.Height?.toInt() ?: 0) }
    val currentWeight = remember { mutableStateOf(metric.value?.Weight?.toInt() ?: 0) }
    val targetWeight = remember { mutableStateOf(metric.value?.WeightTarget?.toInt() ?: 0) }
    val act = remember { mutableStateOf(baseInfo.value?.ActivityLevel ?: 0) }

    var trainingIntensity = when(act.value){
        1 -> "Low"
        2 -> "Lightly"
        3 -> "Moderate"
        4 -> "High"
        else -> "Extreme"
    }
    val context = LocalContext.current
    var isEditingField by remember { mutableStateOf<String?>(null) }
    var tempInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFF6F00))
                .padding(16.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "UPDATE BODY INDEX",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        EditableRow("Height", "${height.value}", R.drawable.ic_height, onEditClick = {
            isEditingField = "Height"
            tempInput = height.value.toString()
        })
        EditableRow("Current Weight", "${currentWeight.value}", R.drawable.ic_current_weight, onEditClick = {
            isEditingField = "Current Weight"
            tempInput = currentWeight.value.toString()
        })
        EditableRow("Target Weight", "${targetWeight.value}", R.drawable.ic_current_weight, onEditClick = {
            isEditingField = "Target Weight"
            tempInput = targetWeight.value.toString()
        })
        EditableRow("Training intensity", trainingIntensity, R.drawable.ic_training, onEditClick = {
            isEditingField = "Training Intensity"
        })

        StaticRow("Age", baseInfo.value?.Age.toString(), R.drawable.ic_age)
        baseInfo.value?.let { StaticRow("Gender", it.Gender, R.drawable.ic_gender) }
        StaticRow("BMI", metric.value?.BMI?.toInt().toString(), R.drawable.ic_bmi)
        StaticRow("BMR",  metric.value?.BMR?.toInt().toString(), R.drawable.ic_bmr)
        StaticRow("TDEE",  metric.value?.TDEE?.toInt().toString(), R.drawable.ic_tdee)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Save logic */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F00)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp)
        ) {
            Text("UPDATE", color = Color.White, fontSize = 16.sp)
        }

        if (isEditingField == "Height" || isEditingField == "Current Weight" || isEditingField == "Target Weight") {
            AlertDialog(
                onDismissRequest = {
                    isEditingField = null
                    tempInput = ""
                },
                title = { Text("Edit Your ${isEditingField}") },
                text = {
                    OutlinedTextField(
                        value = tempInput,
                        onValueChange = { tempInput = it },
                        label = { Text("Enter new value") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            when (isEditingField) {
                                "Height" -> tempInput.toIntOrNull()?.let { height.value = it }
                                "Current Weight" -> tempInput.toIntOrNull()?.let { currentWeight.value = it }
                                "Target Weight" -> tempInput.toIntOrNull()?.let { targetWeight.value = it }
                            }
                            isEditingField = null
                            tempInput = ""
                        }
                    ) {
                        Text("Save", color = Color(0xFFFF6F00), fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        isEditingField = null
                        tempInput = ""
                    }) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            )
        } else if (isEditingField == "Training Intensity") {
            AlertDialog(
                onDismissRequest = {
                    isEditingField = null
                },
                title = { Text("Edit Your Training intensity") },
                text = {
                    Column {
                        listOf(
                            "Low" to "I move only when I need to grab a cup of Coffee",
                            "Lightly" to "Lightly is a gentle personal exercise level, suitable for beginners.",
                            "Moderate" to "I don't run around much, but a good part of my day involves moving",
                            "High" to "I don't get a chance to sit down during the day",
                            "Extreme" to "My job involves intense physical activity"
                        ).forEach { (level, desc) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(1.dp, Color(0xFFCCCCCC), RoundedCornerShape(16.dp))
                                    .clickable {
                                        trainingIntensity = level
                                        isEditingField = null
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = level, fontWeight = FontWeight.Bold)
                                    Text(text = desc, fontSize = 12.sp)
                                }
                                AsyncImage(
                                    model = "https://example.com/image_$level.png", // TODO: Replace with real image links
                                    contentDescription = level,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        isEditingField = null
                        val actUpdate = when(trainingIntensity){
                            "Low" -> 1
                            "Lightly" -> 2
                            "Moderate" -> 3
                            "High" -> 4
                            else -> 5
                        }
                        baseInfo.value?.let { current ->
                            val updatedBaseInfo = current.copy(
                                ActivityLevel = actUpdate
                            )
                            baseInfoViewModel.updateBaseInfo(updatedBaseInfo)
                        }
                        metric.value?.let {
                            val updatedHealthMetric = it.copy(
                                Weight = currentWeight.value.toFloat(),
                                Height = height.value.toFloat(),
                                WeightTarget = targetWeight.value.toFloat()
                            )
                            healthMetricViewModel.insertHealthMetric(updatedHealthMetric)
                        }
                        Toast.makeText(context, "Update success", Toast.LENGTH_LONG).show()

                    }) {
                        Text("Save", color = Color(0xFFFF6F00), fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { isEditingField = null }) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            )
        }
    }
}

@Composable
fun EditableRow(label: String, value: String, iconRes: Int, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = label, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(12.dp))
        IconButton(onClick = onEditClick) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }
    }
}

@Composable
fun StaticRow(label: String, value: String, iconRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = label, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
