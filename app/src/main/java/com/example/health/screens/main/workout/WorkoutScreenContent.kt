package com.example.health.screens.main.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.entities.DefaultExercise
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.navigation.routes.WorkoutRoutes
import java.util.Date

@Composable
fun WorkoutScreenContent(
    navController: NavController,
    exercises: List<DefaultExercise>,
    showPopup: MutableState<Boolean>,
    selectedExercise: MutableState<DefaultExercise?>,
    uid: String,
    exerciseLogViewModel: ExerciseLogViewModel,
    burnOutCaloPerDayViewModel: BurnOutCaloPerDayViewModel,
    today : Date
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header and Search Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFCC99))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Workout",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF6600)
            )
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
                    onClick = { showPopup.value = true }
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = exercises, key = { it.Id }) { exercise ->
                ExerciseItem(exercise = exercise, onClick = {
                    selectedExercise.value = exercise
                })
            }

            item {
                Spacer(modifier = Modifier.height(80.dp)) // tránh bị FAB che
            }
        }

        if (showPopup.value) {
            AddCaloriesDialog(
                onSkip = { showPopup.value = false },
                onDismiss = { showPopup.value = false  },
                onAdd = { showPopup.value = false  },
                exercises = exercises  // Pass the list of exercises to the dialog
            )
        }

        selectedExercise.value?.let {
            ExerciseDetailDialog(
                exercise = it,
                onDismiss = { selectedExercise.value = null },
                onDone = { _, kcal, minutes, selectedExercise ->
                    if (selectedExercise != null) {
                        AddExercise(
                            uid = uid,
                            burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel,
                            exerciseLogViewModel = exerciseLogViewModel,
                            today = today,
                            exerciseID = selectedExercise.Id ?: "",
                            caloBurn = kcal,
                            unitType = selectedExercise.UnitType,
                            unit = minutes,
                            name = selectedExercise.Name
                        )
                    }


                }
            )
        }

    }
}


