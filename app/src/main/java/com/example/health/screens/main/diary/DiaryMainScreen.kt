package com.example.health.screens.main.diary

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
import com.example.health.navigation.routes.DiaryRoutes

@Composable
fun DiaryMainScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is diary main screen")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(DiaryRoutes.Add.route)
        }) {
            Text("Nav to Add")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            navController.navigate(DiaryRoutes.Info.route)
        }) {
            Text("Nav to Info")
        }
    }
}