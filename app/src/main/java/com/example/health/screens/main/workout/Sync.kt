package com.example.health.screens.main.workout

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.health.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.*
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

private const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001

@Composable
fun Sync(navController: NavController,
         calorBurn: MutableState<Float>
) {
    val context = LocalContext.current
    val activity = context as Activity
    var isConnected by remember { mutableStateOf(false) }
    var stepCount by remember { mutableStateOf(0) }
    var calories by remember { mutableStateOf(0f) }
    var distance by remember { mutableStateOf(0f) }

    val fitnessOptions = remember {
        FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .build()
    }
    val account = remember { GoogleSignIn.getAccountForExtension(context, fitnessOptions) }

    // Nếu đã cấp quyền thì auto load
    LaunchedEffect(Unit) {
        if (GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            Log.d("GoogleFit", "Auto-connected to Google Fit")
            connectGoogleFit(activity) { steps, cal, dist ->
                stepCount = steps
                calories = cal
                distance = dist
                isConnected = true
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("GoogleFit", "ACTIVITY_RECOGNITION granted")
            connectGoogleFit(activity) { steps, cal, dist ->
                stepCount = steps
                calories = cal
                distance = dist
                isConnected = true
            }
        } else {
            Log.e("GoogleFit", "ACTIVITY_RECOGNITION permission denied")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFF6600))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.popBackStack() }
                )
            }
            Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) {
                Text(
                    text = "Auto Sync",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(20.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.auto_sync),
                    contentDescription = "Google Fit",
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Google Fit", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Connect to Google Fit to access fitness data across other apps and sync data with your watch.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (!isConnected) {
                    Button(
                        onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACTIVITY_RECOGNITION
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                Log.d("GoogleFit", "Requesting ACTIVITY_RECOGNITION permission")
                                permissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
                            } else {
                                Log.d("GoogleFit", "Permission already granted. Connecting to Google Fit...")
                                connectGoogleFit(activity) { steps, cal, dist ->
                                    stepCount = steps
                                    calories = cal
                                    distance = dist
                                    isConnected = true

                                }
                            }
                        },

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("CONNECT", color = Color.White, fontSize = 16.sp)
                    }
                }

                if (isConnected) {
                    calorBurn.value = calories
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Synced Info", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Steps Today: $stepCount", fontSize = 14.sp)
                    Text("Calories Burned: ${String.format("%.1f", calories)} kcal", fontSize = 14.sp)
                    Text("Distance: ${String.format("%.2f", distance)} m", fontSize = 14.sp)
                }
            }
        }
    }
}

fun connectGoogleFit(context: Activity, onResult: (Int, Float, Float) -> Unit) {
    val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)

    if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
        Log.d("GoogleFit", "Requesting Google Fit permissions...")
        GoogleSignIn.requestPermissions(
            context,
            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
            account,
            fitnessOptions
        )
        return
    }

    val end = ZonedDateTime.now().toInstant().toEpochMilli()
    val start = ZonedDateTime.now().with(LocalTime.MIN).toInstant().toEpochMilli()

    val request = DataReadRequest.Builder()
        .read(DataType.TYPE_STEP_COUNT_DELTA)
        .read(DataType.TYPE_CALORIES_EXPENDED)
        .read(DataType.TYPE_DISTANCE_DELTA)
        .setTimeRange(start, end, TimeUnit.MILLISECONDS)
        .build()

    Log.d("GoogleFit", "Reading data from Google Fit...")
    Fitness.getHistoryClient(context, account)
        .readData(request)
        .addOnSuccessListener { response ->
            var steps = 0
            var calories = 0f
            var distance = 0f

            for (dataSet in response.dataSets) {
                for (dp in dataSet.dataPoints) {
                    when (dp.dataType) {
                        DataType.TYPE_STEP_COUNT_DELTA -> {
                            steps += dp.getValue(Field.FIELD_STEPS).asInt()
                        }
                        DataType.TYPE_CALORIES_EXPENDED -> {
                            calories += dp.getValue(Field.FIELD_CALORIES).asFloat()
                        }
                        DataType.TYPE_DISTANCE_DELTA -> {
                            distance += dp.getValue(Field.FIELD_DISTANCE).asFloat()
                        }
                    }
                }
            }

            Log.d("GoogleFit", "Read success: $steps steps, $calories kcal, $distance m")
            onResult(steps, calories, distance)
        }
        .addOnFailureListener {
            Log.e("GoogleFit", "Read failed: ${it.message}", it)
            onResult(0, 0f, 0f)
        }
}
