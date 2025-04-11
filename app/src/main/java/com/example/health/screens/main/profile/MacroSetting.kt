package com.example.health.screens.main.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.viewmodel.MacroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MacroSetting(navController: NavController , macroViewModel: MacroViewModel) {
    var macro = macroViewModel.macro.collectAsState()
    // thay cac bien carb , ... bang macro.carb .... goi ham update o day
    var carbs by remember { mutableStateOf(30) }
    var protein by remember { mutableStateOf(30) }
    var fat by remember { mutableStateOf(30) }
    val total = carbs + protein + fat

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "MACRO SETTING",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF8000))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                "Adjust Macro Ratio",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MacroPickerColumn("Carbs", carbs) { carbs = it }
                MacroPickerColumn("Protein", protein) { protein = it }
                MacroPickerColumn("Fat", fat) { fat = it }
            }

            Spacer(Modifier.height(16.dp))

            if (total != 100) {
                Text(
                    "Macronutrients must equal 100%\n% Total: ${total}%",
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    "% Total: 100%",
                    color = Color.Green,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                },
                enabled = total == 100,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (total == 100) Color(0xFFFF8000) else Color.Gray
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("UPDATE", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun MacroPickerColumn(title: String, selected: Int, onSelectedChange: (Int) -> Unit) {
    val percentages = (0..100 step 5).toList()
    val listState = rememberLazyListState()

    // Tự động nhận giá trị tại vị trí trung tâm khi scroll
    LaunchedEffect(
        listState.firstVisibleItemIndex,
        listState.firstVisibleItemScrollOffset
    ) {
        val centerIndex = listState.firstVisibleItemIndex + 2 // vì padding top+bottom là 24dp
        if (centerIndex in percentages.indices) {
            val value = percentages[centerIndex]
            if (value != selected) {
                onSelectedChange(value)
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .height(150.dp)
                .width(80.dp)
        ) {
            LazyColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                items(percentages) { value ->
                    val isSelected = value == selected
                    Text(
                        "$value%",
                        fontSize = if (isSelected) 18.sp else 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.Black else Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Optional: Highlight vùng giữa
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(36.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent)
            )
        }
    }
}
