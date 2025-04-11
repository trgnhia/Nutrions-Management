package com.example.health.screens.main.workout

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.health.R
import com.example.health.data.local.entities.DefaultExercise
import com.example.health.data.utils.calculateActualCaloBurn


@Composable
fun ExerciseDetailDialog(
    exercise: DefaultExercise,
    onDismiss: () -> Unit,
    onDone: (title: String, kcal: Int, minutes: Int, selectedExercise: DefaultExercise?) -> Unit,)
{
    var time by remember { mutableStateOf(30) }
    val context = LocalContext.current
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
                    Text(exercise.Name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                    painter = painterResource(id = R.drawable.abs_exercise), // Placeholder icon
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
                Text(exercise.UnitType, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(12.dp))

                Text("FOCUS AREA", fontWeight = FontWeight.Bold, color = Color(0xFFFF6600))
                Text(exercise.Unit.toString(), fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                   onClick = {
                        Log.e("ADD CALORIES", "AddCaloriesDialog: CLick add calories", )
                        val unit = time
                        val result = exercise.let {
                            calculateActualCaloBurn(
                                caloBurn = exercise.CaloBurn,
                                defaultUnit = it.Unit,
                                actualUnit = time
                            )
                        }
                       exercise.let {
                           onDone(
                               exercise.Name,
                               result ?: 0,
                               unit,
                               exercise
                           )
                           Toast.makeText(context, "Add exercise to diary successfully", Toast.LENGTH_SHORT).show()


                           onDismiss()
                       }
                   },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933))
                ) {
                    Text("ADD EXERCISE", color = Color.White)
                }
            }
        }
    }
}
