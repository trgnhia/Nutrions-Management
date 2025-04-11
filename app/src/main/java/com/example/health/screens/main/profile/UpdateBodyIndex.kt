package com.example.health.screens.main.profile

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.health.R
import com.example.health.data.local.entities.BaseInfo
import com.example.health.data.local.entities.HealthMetric
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.utils.HealthMetricUtil
import kotlinx.coroutines.launch
import java.util.Date
import com.example.health.data.remote.auth.AuthViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun UpdateBodyIndex(
    navController: NavController,
    baseInfoViewModel: BaseInfoViewModel,
    healthMetricViewModel: HealthMetricViewModel,
    authViewModel: AuthViewModel
) {
    val scope = rememberCoroutineScope()
    val currentUser: FirebaseUser? = authViewModel.currentUser
    val uid = currentUser?.uid ?: return

    // State management
    var height by remember { mutableStateOf(170) }
    var currentWeight by remember { mutableStateOf(88) }
    var targetWeight by remember { mutableStateOf(80) }
    var trainingIntensity by remember { mutableStateOf("Lightly") }
    var age by remember { mutableStateOf(21) }
    var gender by remember { mutableStateOf("Male") }

    // State cho dialog chỉnh sửa
    var isEditingField by remember { mutableStateOf<String?>(null) }
    var tempInput by remember { mutableStateOf("") }
    
    // Loading và error states
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Tính toán các chỉ số
    val BMR by remember(currentWeight, height, age, gender) {
        derivedStateOf { HealthMetricUtil.calculateBMR(currentWeight.toFloat(), height.toFloat(), age, gender) }
    }

    val BMI by remember(currentWeight, height) {
        derivedStateOf { HealthMetricUtil.calculateBMI(currentWeight.toFloat(), height.toFloat()) }
    }

    val activityLevel = when(trainingIntensity) {
        "Low" -> 1
        "Lightly" -> 2
        "Moderate" -> 3
        "High" -> 4
        "Extreme" -> 5
        else -> 3
    }

    val TDEE by remember(BMR, activityLevel) {
        derivedStateOf { HealthMetricUtil.calculateTDEE(BMR, activityLevel) }
    }

    // Load dữ liệu ban đầu
    LaunchedEffect(Unit) {
        baseInfoViewModel.baseInfo.collect { baseInfo ->
            baseInfo?.let {
                height = it.Height.toInt()
                currentWeight = it.Weight.toInt()
                age = it.Age
                gender = it.Gender
                trainingIntensity = when(it.ActivityLevel) {
                    1 -> "Low"
                    2 -> "Lightly"
                    3 -> "Moderate"
                    4 -> "High"
                    5 -> "Extreme"
                    else -> "Moderate"
                }
            }
        }
    }

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

        EditableRow("Height", "$height cm", R.drawable.ic_height, onEditClick = {
            isEditingField = "Height"
            tempInput = height.toString()
        })
        EditableRow("Current Weight", "$currentWeight kg", R.drawable.ic_current_weight, onEditClick = {
            isEditingField = "Current Weight"
            tempInput = currentWeight.toString()
        })
        EditableRow("Target Weight", "$targetWeight kg", R.drawable.ic_current_weight, onEditClick = {
            isEditingField = "Target Weight"
            tempInput = targetWeight.toString()
        })
        EditableRow("Training intensity", trainingIntensity, R.drawable.ic_training, onEditClick = {
            isEditingField = "Training Intensity"
        })

        StaticRow("Age", "$age", R.drawable.ic_age)
        StaticRow("Gender", gender, R.drawable.ic_gender)
        StaticRow("BMI", String.format("%.1f", BMI), R.drawable.ic_bmi)
        StaticRow("BMR", String.format("%.1f", BMR), R.drawable.ic_bmr)
        StaticRow("TDEE", String.format("%.1f", TDEE), R.drawable.ic_tdee)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        isLoading = true
                        
                        // Cập nhật BaseInfo
                        val baseInfo = BaseInfo(
                            Uid = uid,
                            Name = "", // Giữ nguyên tên
                            Age = age,
                            Height = height.toFloat(),
                            Weight = currentWeight.toFloat(),
                            Gender = gender,
                            ActivityLevel = activityLevel
                        )
                        baseInfoViewModel.insertBaseInfo(baseInfo)

                        // Cập nhật HealthMetric
                        val healthMetric = HealthMetric(
                            HealthMetricUtil.generateMetricId(),
                            uid,
                            height.toFloat(),
                            currentWeight.toFloat(),
                            targetWeight.toFloat(),
                            BMR,
                            BMI,
                            TDEE,
                            TDEE, // CalorPerDay = TDEE
                            0, // RestDay
                            Date()
                        )
                        healthMetricViewModel.insertHealthMetric(healthMetric)

                        showSuccessDialog = true
                    } catch (e: Exception) {
                        errorMessage = e.message ?: "Có lỗi xảy ra khi cập nhật"
                        showErrorDialog = true
                    } finally {
                        isLoading = false
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F00)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("UPDATE", color = Color.White, fontSize = 16.sp)
            }
        }

        // Dialog chỉnh sửa số liệu
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = when (isEditingField) {
                            "Height" -> tempInput.toIntOrNull()?.let { it !in 100..250 } ?: false
                            "Current Weight" -> tempInput.toIntOrNull()?.let { it !in 30..200 } ?: false
                            "Target Weight" -> tempInput.toIntOrNull()?.let { it !in 30..200 } ?: false
                            else -> false
                        }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            when (isEditingField) {
                                "Height" -> {
                                    val newHeight = tempInput.toIntOrNull()
                                    if (newHeight != null && newHeight in 100..250) {
                                        height = newHeight
                                        isEditingField = null
                                        tempInput = ""
                                    }
                                }
                                "Current Weight" -> {
                                    val newWeight = tempInput.toIntOrNull()
                                    if (newWeight != null && newWeight in 30..200) {
                                        currentWeight = newWeight
                                        isEditingField = null
                                        tempInput = ""
                                    }
                                }
                                "Target Weight" -> {
                                    val newTarget = tempInput.toIntOrNull()
                                    if (newTarget != null && newTarget in 30..200) {
                                        targetWeight = newTarget
                                        isEditingField = null
                                        tempInput = ""
                                    }
                                }
                            }
                        },
                        enabled = when (isEditingField) {
                            "Height" -> tempInput.toIntOrNull()?.let { it in 100..250 } ?: false
                            "Current Weight" -> tempInput.toIntOrNull()?.let { it in 30..200 } ?: false
                            "Target Weight" -> tempInput.toIntOrNull()?.let { it in 30..200 } ?: false
                            else -> false
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
        }

        // Dialog chọn cường độ tập luyện
        if (isEditingField == "Training Intensity") {
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
                                    model = "https://example.com/image_$level.png",
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
                    TextButton(onClick = { isEditingField = null }) {
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

        // Dialog thông báo thành công
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("Thành công") },
                text = { Text("Đã cập nhật thông tin thành công!") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showSuccessDialog = false
                            navController.popBackStack()
                        }
                    ) {
                        Text("OK", color = Color(0xFFFF6F00))
                    }
                }
            )
        }

        // Dialog thông báo lỗi
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Lỗi") },
                text = { Text(errorMessage) },
                confirmButton = {
                    TextButton(
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("OK", color = Color(0xFFFF6F00))
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
