package com.example.health.screens.main.plan.vegan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun VeganMainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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
                    text = "30 days Meal Plan",
                    fontSize = 13.sp,
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

        Column(modifier = Modifier.padding(16.dp)) {
            Text("About Vegan diet", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "The vegan diet is a diet that completely eliminates all animal-derived products, " +
                        "including meat, fish, eggs, dairy, honey, and any food containing animal-based ingredients. " +
                        "Vegans consume only plant-based foods such as vegetables, fruits, grains, nuts, and " +
                        "plant-based products like almond milk, soy milk, tofu, etc.",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}
