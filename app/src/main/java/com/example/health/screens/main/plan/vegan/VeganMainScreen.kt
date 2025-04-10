package com.example.health.screens.main.plan.vegan

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.screens.main.plan.NoticeDialog

@Composable
fun VeganMainScreen(navController: NavController) {

    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    // TODO: xử lý khi người dùng nhấn bắt đầu kế hoạch
                    showDialog = true
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                //  .navigationBarsPadding(), // tránh che thanh điều hướng
                shape = RoundedCornerShape(45),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF234F26), // màu xanh đậm
                    contentColor = Color.White
                )
            ) {
                Text("Start your diet now", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        },
        modifier = Modifier.fillMaxSize()
    ){
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.top_back_vegan),
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
                            text = "Vegan",
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
                            navController.navigate(PlanRoutes.VeganPlan.route)
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

            // Blog-style content
            Column(modifier = Modifier.padding(16.dp)) {
                // Intro
                Text("About Raw Vegan Diet", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Following the raw vegan diet requires proper preparation to ensure you are still getting the vitamins and minerals your body requires. " +
                            "Adding supplements can help you avoid vitamin B12, vitamin D, or calcium deficiency.\n\n" +
                            "Though the raw vegan diet isn’t new, it has been regaining popularity recently. It combines the principles of veganism with those of raw foodism. " +
                            "While some people may choose to follow it for ethical or environmental reasons, most do it for its purported health benefits.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // First image between sections
                Image(
                    painter = painterResource(id = R.drawable.vegan_infor),
                    contentDescription = "Vegan info",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Section 1
                Text("What Is a Raw Vegan Diet?", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Raw veganism is a subset of veganism. Like veganism, it excludes all foods of animal origin. " +
                            "Then it adds the concept of raw foodism, which dictates that foods should be eaten completely raw or heated at temperatures below 104–118°F (40–48°C).\n\n" +
                            "The idea of eating only raw foods has existed since the 19th century when Presbyterian minister and dietary reformer Sylvester Graham promoted it as a way to avoid illness.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Second image
                Image(
                    painter = painterResource(id = R.drawable.vegan_infor_2),
                    contentDescription = "Vegan raw plate",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Section 2
                Text("Health Benefits", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The raw vegan diet is plentiful in nutrient-rich plant foods. It’s also linked to several health benefits.\n\n" +
                            "• May Improve Heart Health: Due to its focus on fruits, vegetables, nuts, and seeds.\n" +
                            "• May Reduce Diabetes Risk: Thanks to high fiber and low glycemic foods.\n" +
                            "• May Aid Weight Loss: Many studies link raw vegan diets with significant fat loss and healthier BMI.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Third image (optional)
                Image(
                    painter = painterResource(id = R.drawable.vegan_infor_1),
                    contentDescription = "Vegan benefits",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Summary
                Text("Summary", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "A raw vegan diet consists of mostly unprocessed, plant-based foods that are either completely raw or heated at very low temperatures. " +
                            "While it offers many health benefits, careful planning is needed to avoid deficiencies and ensure long-term sustainability.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(100.dp)) // Safe space for scroll end
            }
            if (showDialog) {
                NoticeDialog(
                    message = "When you start the diet, you will need to follow only the meals we provide and will not be allowed to eat food from outside. Are you ready?",
                    onAccept = {
                        showDialog = false
                        // TODO: Start diet or navigate
                    },
                    onDecline = { showDialog = false },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }
}

//D:\Mobile_Prj\Nutrions-Management\app\src\main\res\drawable