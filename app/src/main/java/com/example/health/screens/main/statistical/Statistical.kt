package com.example.health.screens.main.statistical

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.navigation.routes.PlanRoutes
import com.example.health.navigation.routes.StatisticalRoutes

@Composable
fun Statistical(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is statistical  screen")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(StatisticalRoutes.Analysis.route)
        }) {
            Text("Nav to analysis")
        }
    }
}