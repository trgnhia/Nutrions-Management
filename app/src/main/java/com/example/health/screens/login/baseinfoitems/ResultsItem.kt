package com.example.health.screens.login.baseinfoitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.health.R
import kotlin.math.ceil
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsItem(weight: Float, targetWeight: Float, restDays: Int, onValueChange: (Float) -> Unit) {
    val months = ceil(restDays/ 30.0).toInt()

    var showDialog by remember { mutableStateOf(false) }
    var inputTarget by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.plan_background),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Card: Plan Info
            Card(
                shape = RoundedCornerShape(16.dp),
                border = ButtonDefaults.outlinedButtonBorder,
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Your Plan is Ready", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Your goal", fontWeight = FontWeight.Bold)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = weight.toInt().toString(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Image(
                            painter = painterResource(id =
                            if (targetWeight < weight) R.drawable.ic_arrow_down
                            else R.drawable.ic_arrow_up
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = targetWeight.toInt().toString(),
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit target weight",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        showDialog = true
                                        inputTarget = targetWeight.toInt().toString()
                                    }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Follow our recommendations and you will achieve your desired goal by $months month",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Card: Tips
            Card(
                shape = RoundedCornerShape(16.dp),
                border = ButtonDefaults.outlinedButtonBorder,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "How to achieve your goals",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    GoalRow(R.drawable.ic_diet, "Build a personalized diet")
                    GoalRow(R.drawable.ic_recipe, "Discover weight loss recipes")
                    GoalRow(R.drawable.ic_calories, "Track Daily Calories Easily")
                    GoalRow(R.drawable.ic_reminder, "Get daily reminders and recommendations")
                    GoalRow(R.drawable.ic_progress, "Continuous progress tracking")
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            val value = inputTarget.toFloatOrNull()
                            if (value != null && value > 0f) {
                                onValueChange(value)
                                showDialog = false
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BEBE))
                    ) {
                        Text(
                            text = "Confirm",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Enter your target weight") },
                text = {
                    OutlinedTextField(
                        value = inputTarget,
                        onValueChange = { newValue ->
                            val digitsOnly = newValue.filter { it.isDigit() }
                            inputTarget = digitsOnly
                        },
                        label = { Text("Target weight (kg)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF00BEBE),
                            focusedLabelColor = Color(0xFF00BEBE),
                            cursorColor = Color(0xFF00BEBE)
                        )
                    )


                },
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Composable
fun GoalRow(icon: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .padding(end = 8.dp)
        )
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultsItem() {
    ResultsItem(
        weight = 88f,
        targetWeight = 80f,
        restDays = 90,
        onValueChange = {} // preview nên để trống
    )
}
