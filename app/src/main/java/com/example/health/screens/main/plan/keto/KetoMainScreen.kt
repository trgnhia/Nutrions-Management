package com.example.health.screens.main.plan.keto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.NoticeDialog
import com.example.health.screens.main.plan.StartDietButton


@Composable
fun KetoMainScreen(navController: NavController, baseInfoViewModel: BaseInfoViewModel) {
    val baseInfo = baseInfoViewModel.baseInfo.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val userDiet = baseInfo.value?.IsDiet ?: 0
    val isStop = userDiet == 3

    var showStartDialog by remember { mutableStateOf(false) }
    var showStopDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            StartDietButton(
                currentDietCode = 3,
                userDietCode = userDiet,
                isStop = isStop, // 👈 truyền vào
                onClick = {
                    if (isStop) showStopDialog = true
                    else showStartDialog = true
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding) // đảm bảo nội dung không bị che
                .background(Color.White)
        ) {
            // === HEADER ===
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.top_back_keto),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 20.dp, top = 36.dp, end = 24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(26.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Keto",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Diet",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 34.dp)
                    )

                    Spacer(modifier = Modifier.height(28.dp))
                    Text(
                        text = "7 days Meal Plan",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 34.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedButton(
                        onClick = {
                            navController.navigate(PlanRoutes.KetoPlan.route)
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = ButtonDefaults.outlinedButtonBorder,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(start = 34.dp)
                    ) {
                        Text("View Plan", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // === NỘI DUNG CHÍNH (cuộn) ===
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Introduction to Keto Diet", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "The ketogenic (keto) diet is a low-carb, high-fat diet that offers many health benefits. " +
                            "It involves drastically reducing carbohydrate intake and replacing it with fat. This reduction in carbs puts your body into a metabolic state called ketosis.\n\n" +
                            "When this happens, your body becomes incredibly efficient at burning fat for energy. It also turns fat into ketones in the liver, which can supply energy for the brain.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.keto_infor),
                    contentDescription = "Keto overview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("Benefits of the Keto Diet", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Weight Loss: Keto is very effective for fat loss and improving metabolic health.\n" +
                            "• Improved Energy: Ketones provide a more stable energy source than glucose.\n" +
                            "• Mental Focus: Many people report improved clarity and focus on a keto diet.\n" +
                            "• Reduced Blood Sugar and Insulin Levels: It can help manage or reverse type 2 diabetes.\n" +
                            "• May Reduce Risk of Heart Disease: Healthy fats and reduced sugar may lower cholesterol and triglycerides.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.keto_infor_1),
                    contentDescription = "Keto benefits",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("Foods to Eat and Avoid", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Eat: Meat, fatty fish, eggs, butter, nuts, healthy oils, avocados, and low-carb vegetables.\n" +
                            "Avoid: Sugary foods, grains, fruits (except berries), beans, root vegetables, and low-fat products.\n\n" +
                            "Meal planning is essential to make sure you're eating the right macros and nutrients for long-term success.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.keto_infor_1_2),
                    contentDescription = "Keto food examples",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("Summary", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The keto diet is a powerful way to shift your metabolism and achieve better health. " +
                            "However, it's important to follow it properly and consult a healthcare provider if needed. When done right, keto can lead to improved energy, fat loss, and overall wellness.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

               // Spacer(modifier = Modifier.height(40.dp)) // Chừa chỗ tránh nội dung dính sát
            }
            if (showStartDialog) {
                NoticeDialog(
                    message = "When you start the diet, you will need to follow only the meals we provide and will not be allowed to eat food from outside. Are you ready?",
                    onAccept = {
                        showStartDialog= false
                        baseInfo.value?.let { baseInfoViewModel.startDiet(it.Uid,3) }
                    },
                    onDecline = { showStartDialog = false },
                    onDismiss = { showStartDialog = false }
                )
            }
            if (showStopDialog) {
                NoticeDialog(
                    message = "Are you sure you want to stop your current diet plan?",
                    onAccept = {
                        showStopDialog = false
                        baseInfo.value?.let { baseInfoViewModel.startDiet(it.Uid, 0) } // 👈 Set isDiet = 0
                    },
                    onDecline = { showStopDialog = false },
                    onDismiss = { showStopDialog = false }
                )
            }
        }
    }
}
