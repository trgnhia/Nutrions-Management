package com.example.health.screens.main.profile

import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.viewmodel.MacroViewModel
import com.example.health.data.utils.MacroCalculator
import com.example.health.data.utils.calculateMacroPercentFromGrams

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MacroSetting(navController: NavController , macroViewModel: MacroViewModel) {
    val macro = macroViewModel.macro.collectAsState()
    if(macro.value == null) {
        Log.e("Null macro", "MacroSetting: Maccro is null", )
    }

    // thay cac bien carb , ... bang macro.carb .... goi ham update o day
    val _carbs= macro.value?.Carb?.toInt() ?: 0
    val _protein = macro.value?.Protein?.toInt() ?: 0
    val _fat = macro.value?.Fat?.toInt() ?: 0

    val result = macro.value?.TDEE?.toInt()?.let {
        calculateMacroPercentFromGrams(
            it,
        _carbs.toFloat(),
        _protein.toFloat(),
        _fat.toFloat()
        )
    }
    var carbs by remember { mutableStateOf(result?.carbPercent?.toInt() ?: 0) }
    var protein by remember { mutableStateOf(result?.proteinPercent?.toInt() ?: 0) }
    var fat by remember { mutableStateOf(result?.fatPercent?.toInt() ?: 0) }
    Log.e("Check percent: ", "MacroSetting: "+ carbs+" "+protein+" "+fat+" ", )
    val total = carbs + protein + fat
    val context = LocalContext.current
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
                MacroPickerColumn("Carbs", carbs, { carbs = it }, initialValue = carbs)
                MacroPickerColumn("Protein", protein, { protein = it }, initialValue = protein)
                MacroPickerColumn("Fat", fat, { fat = it }, initialValue = fat)
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
                    macro.value?.let { current ->
                        val result_ = MacroCalculator.calculateMacros(
                            tdee = current.TDEE.toInt(),
                            carbPercent = carbs.toFloat(),
                            proteinPercent = protein.toFloat(),
                            fatPercent = fat.toFloat()
                        )
                        val carbs_ = result_.carbInGrams
                        val protein_ = result_.proteinInGrams
                        val fat_ = result_.fatInGrams

                        val updatedMacro = current.copy(
                            Carb = carbs_,
                            Protein = protein_,
                            Fat = fat_,
                        )
                        macroViewModel.update(updatedMacro)
                        Toast.makeText(context, "Update macro success", Toast.LENGTH_LONG).show()

                    }
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
fun MacroPickerColumn(
    title: String,
    selected: Int,
    onSelectedChange: (Int) -> Unit,
    initialValue: Int
) {
    val percentages = (0..100 step 1).toList()
    val initialIndex = percentages.indexOf(initialValue).coerceAtLeast(0)
    Log.e("check percen", "MacroPickerColumn: " + initialIndex, )
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = (initialIndex - 2).coerceAtLeast(0))
    var isFirstLoad by remember { mutableStateOf(true) }

    LaunchedEffect(
        listState.firstVisibleItemIndex,
        listState.firstVisibleItemScrollOffset
    ) {
        val centerIndex = listState.firstVisibleItemIndex + 2
        if (centerIndex in percentages.indices) {
            val newValue = percentages[centerIndex]
            if (!isFirstLoad && newValue != selected) {
                onSelectedChange(newValue)
            }
        }
        isFirstLoad = false
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
