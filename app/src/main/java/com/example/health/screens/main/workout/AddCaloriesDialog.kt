package com.example.health.screens.main.workout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.health.data.local.entities.DefaultExercise
import com.example.health.data.utils.calculateActualCaloBurn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCaloriesDialog(
    onSkip: () -> Unit,
    onDismiss: () -> Unit,
    onAdd: (Int) -> Unit,
    exercises: List<DefaultExercise>  // List of exercises to choose from
) {
    var title by remember { mutableStateOf("") }
    var kcal by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    var selectedExercise by remember { mutableStateOf<DefaultExercise?>(null) }
    var expanded by remember { mutableStateOf(false) }



    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFF2F2F2))
                .padding(20.dp)
        ) {
            Column {
                // Header section with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Add Calories",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onSkip() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title field with rounded corners
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Title") },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // kcal field
                OutlinedTextField(
                    value = kcal,
                    onValueChange = { kcal = it },
                    placeholder = { Text("kcal (Obligatory)") },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Minutes field
                OutlinedTextField(
                    value = minutes,
                    onValueChange = { minutes = it },
                    placeholder = { Text("Minutes") },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onSkip) {
                        Text("Cancel", color = Color.Black)
                    }
                    TextButton(onClick = {
                        onAdd(0)
                    }) {
                        Text("Done", color = Color.Red)
                    }
                }
            }
        }
    }
}
