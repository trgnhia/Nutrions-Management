package com.example.health.screens.main.plan.highprotein
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
import com.example.health.navigation.routes.PlanRoutes

@Composable
fun HighProteinMainScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    // TODO: xử lý khi người dùng nhấn bắt đầu kế hoạch
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                //  .navigationBarsPadding(), // tránh che thanh điều hướng
                shape = RoundedCornerShape(45),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE87046), // màu xanh đậm
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
                    painter = painterResource(id = R.drawable.top_back_high),
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
                            text = "High Protein",
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
                            navController.navigate(PlanRoutes.HighProteinPlan.route)
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

            // Nội dung chính
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Why Choose a High-Protein Diet?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "A high-protein diet emphasizes protein-rich foods such as meat, dairy, legumes, and eggs, " +
                            "while reducing the intake of refined carbs and sugars. It helps promote satiety, muscle growth, and fat loss.\n\n" +
                            "This diet is popular among athletes, bodybuilders, and people looking to maintain lean muscle mass or lose weight effectively.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.high_pro_infor),
                    contentDescription = "High protein image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text("Top Benefits", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Muscle Building: Protein is essential for repairing and growing muscle tissues.\n" +
                            "• Fat Loss: Protein-rich foods increase satiety and help burn more calories.\n" +
                            "• Better Metabolism: High protein intake boosts metabolism through the thermic effect of food.\n" +
                            "• Reduced Cravings: Helps control hunger hormones and reduce late-night snacking.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.high_pro_infor_1),
                    contentDescription = "Benefits of high protein diet",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text("Foods to Focus On", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = " Lean meats (chicken, turkey, beef)\n" +
                            " Fish and seafood (salmon, tuna, shrimp)\n" +
                            " Eggs and egg whites\n" +
                            " Greek yogurt, cottage cheese\n" +
                            " Legumes, tofu, tempeh\n" +
                            " Protein powders (whey, plant-based)\n\n" +
                            "Avoid high-sugar snacks, processed foods, and refined carbs to make the most of this plan.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.high_pro_infor_2),
                    contentDescription = "High protein food examples",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text("Conclusion", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "A high-protein diet is a powerful nutritional strategy for those looking to build muscle, lose fat, and stay energized throughout the day. " +
                            "With proper planning and balanced choices, it can support a healthy, active lifestyle — and your fitness goals.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}
