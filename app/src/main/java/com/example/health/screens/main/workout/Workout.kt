package com.example.health.screens.main.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.CustomExerciseViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.navigation.routes.WorkoutRoutes

@Composable
fun Workout(
    navController: NavController,
    defaultExerciseViewModel : DefaultExerciseViewModel,
    exerciseLogViewModel : ExerciseLogViewModel,
    burnOutCaloPerDayViewModel : BurnOutCaloPerDayViewModel,
    customExerciseViewModel : CustomExerciseViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WorkoutScreenContent(navController)
    }
}

@Composable
fun WorkoutScreenContent(navController: NavController) {
    var showPopup by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }

    val exercises = listOf(
        Exercise("Abdominal Exercises", R.drawable.abs_exercise, "Nằm ngửa, co gối 90 độ, hai tay đặt sau đầu hoặc bắt chéo trước ngực...", "Belly"),
        Exercise("Push-up Exercises", R.drawable.pushup_exercise, "Chống tay xuống sàn, hạ thấp cơ thể, sau đó đẩy lên...", "Arms & Chest"),
        Exercise("Squad Exercises", R.drawable.squat_exercise, "Đứng thẳng, hạ thấp cơ thể như ngồi ghế...", "Legs"),
        Exercise("Plank Exercises", R.drawable.plank_exercise, "Chống khuảy tay xuống sàn, giữ thẳng người...", "Core")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFCC99))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Workout", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF6600))
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search ...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageIconWithLabelHorizontal(
                    resId = R.drawable.auto_sync,
                    label = "Auto Sync",
                    onClick = { navController.navigate(WorkoutRoutes.Sync.route) }
                )
                ImageIconWithLabelHorizontal(
                    resId = R.drawable.add_calories,
                    label = "Add Calories",
                    onClick = { showPopup = true }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Exercises",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF6600),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Recommended",
                fontSize = 14.sp,
                color = Color(0xFF009688),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            exercises.forEach { exercise ->
                ExerciseItem(exercise = exercise, onClick = {
                    selectedExercise = exercise
                })
            }
        }

        if (showPopup) {
            AddCaloriesDialog(
                onDismiss = { showPopup = false },
                onSkip = { showPopup = false },
                onDone = { title, kcal, minutes -> showPopup = false }
            )
        }

        selectedExercise?.let {
            ExerciseDetailDialog(
                exercise = it,
                onDismiss = { selectedExercise = null },
                onAdd = { selectedExercise = null }
            )
        }
    }
}

@Composable
fun ImageIconWithLabelHorizontal(resId: Int, label: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Black)
    }
}

@Composable
fun ExerciseItem(exercise: Exercise, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFE0E0E0))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = exercise.imageRes),
            contentDescription = exercise.title,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(exercise.title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ExerciseDetailDialog(exercise: Exercise, onDismiss: () -> Unit, onAdd: (Int) -> Unit) {
    var time by remember { mutableStateOf(30) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(exercise.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    painter = painterResource(id = exercise.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF2F2F2))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFECB3))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Time", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(12.dp))
                    IconButton(onClick = { if (time > 1) time-- }) { Text("-", fontSize = 20.sp) }
                    Text(time.toString(), fontWeight = FontWeight.Bold)
                    IconButton(onClick = { time++ }) { Text("+", fontSize = 20.sp) }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Minute")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("INSTRUCT", fontWeight = FontWeight.Bold, color = Color(0xFFFF6600))
                Text(exercise.instruction, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(12.dp))

                Text("FOCUS AREA", fontWeight = FontWeight.Bold, color = Color(0xFFFF6600))
                Text("\uD83D\uDC49 ${exercise.focusArea}", fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { onAdd(time) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933))
                ) {
                    Text("ADD EXERCISE", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun AddCaloriesDialog(
    onDismiss: () -> Unit,
    onSkip: () -> Unit,
    onDone: (title: String, kcal: String, minutes: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var kcal by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFF2F2F2))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Add Calories", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp).clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = kcal,
                    onValueChange = { kcal = it },
                    placeholder = { Text("kcal(Obligatory)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = minutes,
                    onValueChange = { minutes = it },
                    placeholder = { Text("Minutes") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onSkip) { Text("Cancel", color = Color.Black) }
                    TextButton(onClick = { onDone(title, kcal, minutes) }) { Text("Done", color = Color.Red) }
                }
            }
        }
    }
}

// Data class
data class Exercise(
    val title: String,
    val imageRes: Int,
    val instruction: String,
    val focusArea: String
)