package com.example.health.screens.main.workout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.entities.DefaultExercise
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.CustomExerciseViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.navigation.routes.WorkoutRoutes
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.example.health.data.local.entities.Account
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.utils.toStartOfDay
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Workout(
    navController: NavController,
    defaultExerciseViewModel: DefaultExerciseViewModel,
    exerciseLogViewModel: ExerciseLogViewModel,
    burnOutCaloPerDayViewModel: BurnOutCaloPerDayViewModel,
    customExerciseViewModel: CustomExerciseViewModel,
    accountViewModel: AccountViewModel
) {
    // Observe the exercises from the ViewModel
    val exercises by defaultExerciseViewModel.defaultExercises.collectAsState(initial = emptyList())
    val account by accountViewModel.account.collectAsState(initial = Account())
    val uid = account?.Uid
    val today: Date = Date().toStartOfDay()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WorkoutScreenContent(
            navController = navController,
            exercises = exercises,
            showPopup = remember { mutableStateOf(false) },
            selectedExercise = remember { mutableStateOf<DefaultExercise?>(null) },
            uid = uid ?: "",
            exerciseLogViewModel = exerciseLogViewModel,
            burnOutCaloPerDayViewModel = burnOutCaloPerDayViewModel,
            today = today
        )
    }
}

