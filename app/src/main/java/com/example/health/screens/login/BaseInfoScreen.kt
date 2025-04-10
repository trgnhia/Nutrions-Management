package com.example.health.screens.login

import AgeItem
import android.health.connect.datatypes.BoneMassRecord
import android.telephony.SmsMessage.SubmitPdu
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.local.entities.BaseInfo
import com.example.health.data.local.entities.HealthMetric
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.utils.HealthMetricUtil
import com.example.health.screens.login.baseinfoitems.ActivityLevelItem
import com.example.health.screens.login.baseinfoitems.BodyIndexesItem
import com.example.health.screens.login.baseinfoitems.GenderItem
import com.example.health.screens.login.baseinfoitems.HeightItem
import com.example.health.screens.login.baseinfoitems.NameItem
import com.example.health.screens.login.baseinfoitems.ResultsItem
import com.example.health.screens.login.baseinfoitems.WeightItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class BaseInfoInput(
    val name: String,
    val age: Int,
    val height: Float,
    val weight: Float,
    val gender: String,
    val activityLevel: Int,
)
data class MetricInput(
    val height : Float,
    val weight: Float,
    var weightTarget: Float,
    val bmr: Float,
    val bmi: Float,
    val tdee: Float,
    val calorPerDay: Float,
    val restDays: Int,
    val updateAt: Date
)
@Composable
fun BaseInfoScreen(
    authViewModel: AuthViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    navController: NavController,
    healthMetricViewModel: HealthMetricViewModel
) {
    val uid = authViewModel.currentUser?.uid ?: return
    val baseInfo by baseInfoViewModel.baseInfo.collectAsState()
    val healthMetric by healthMetricViewModel.lastMetric.collectAsState()


    val defaultInfo = baseInfo?.let {
        BaseInfoInput(
            name = it.Name,
            age = it.Age,
            height = it.Height,
            weight = it.Weight,
            gender = it.Gender,
            activityLevel = it.ActivityLevel,
        )
    } ?: BaseInfoInput("", 0, 0f, 0f, "" , 0)
    val defaultMetric = healthMetric?.let {
        MetricInput(
            height = it.Height,
            weight = it.Weight,
            weightTarget = it.WeightTarget,
            bmr = it.BMR,
            bmi = it.BMI,
            tdee = it.TDEE,
            calorPerDay = it.CalorPerDay,
            restDays = it.RestDay,
            updateAt = it.UpdateAt
        )
    }?: MetricInput(0f,0f,0f,0f,0f,0f,0f,0,Date())

    OnboardingScreen(
        uid = uid,
        default = defaultInfo,
        defaultMetric = defaultMetric,
        onDone = { info, metric ->
            // Lưu Room
            val base = BaseInfo(
                Uid = uid,
                Name = info.name,
                Age = info.age,
                Height = info.height,
                Weight = info.weight,
                Gender = info.gender,
                ActivityLevel = info.activityLevel,
            )
            val metricF = HealthMetric(
                HealthMetricUtil.generateMetricId(),
                uid,
                metric.height,
                metric.weight,
                metric.weightTarget,
                metric.bmr,
                metric.bmi,
                metric.tdee,
                metric.calorPerDay,
                metric.restDays,
                metric.updateAt
            )

            baseInfoViewModel.insertBaseInfo(base)
            healthMetricViewModel.insertHealthMetric(metricF)
            // Cập nhật trạng thái & điều hướng
            authViewModel.updateStatus(uid, "completed")
            navController.navigate("health_metric") {
                popUpTo("base_info") { inclusive = true }
            }
        }
    )
}

@Composable
fun OnboardingScreen(
    uid: String,
    default: BaseInfoInput,
    defaultMetric: MetricInput,
    onDone: (BaseInfoInput , MetricInput) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 8 })
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf(default.name) }
    var age by remember {
        mutableIntStateOf(if (default.age in 10..100) default.age else 20)
    }
    var height by remember {
        mutableFloatStateOf(if (default.height in 100f..230f) default.height else 160f)
    }
    var weight by remember {
        mutableFloatStateOf(if (default.weight in 40f..150f) default.weight else 60f)
    }

    var gender by remember { mutableStateOf(
        if(default.gender == "") "Male" else default.gender
    ) }
    var activityLevel by remember { mutableIntStateOf(
        if(default.activityLevel in 1..5) default.activityLevel else 3
    ) }

    var TargetWeight by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(defaultMetric.weightTarget, height) {
        TargetWeight = if (defaultMetric.weightTarget > 0f)
            defaultMetric.weightTarget
        else
            HealthMetricUtil.calculateWeightTarget(height)
    }


    val BMR by remember(weight, height, age, gender) {
        derivedStateOf { HealthMetricUtil.calculateBMR(weight, height, age, gender) }
    }

    val BMI by remember(weight, height) {
        derivedStateOf { HealthMetricUtil.calculateBMI(weight, height) }
    }

    val TDEE by remember(BMR, activityLevel) {
        derivedStateOf { HealthMetricUtil.calculateTDEE(BMR, activityLevel) }
    }

    val diff by remember(weight, TargetWeight) {
        derivedStateOf { HealthMetricUtil.diffWeight(weight, TargetWeight) }
    }

    val calorPerDay by remember(TDEE, diff) {
        derivedStateOf { HealthMetricUtil.calculateCalorieDeltaPerDay(TDEE, diff) }
    }

    val RestDay by remember(diff, calorPerDay) {
        derivedStateOf { HealthMetricUtil.restDay(diff, calorPerDay) }
    }
    val now = Date()
    val UpdateAt by remember {
        mutableStateOf(now) // chỉ cần lấy 1 lần
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Nền là ảnh
        Image(
            painter = painterResource(id = R.drawable.whitebackground), // Thay bằng tên ảnh của bạn
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
            )
        Column(
            modifier = Modifier
                .fillMaxSize()
                ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            StepProgressBar(currentStep = pagerState.currentPage, totalSteps = 8)

            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f) , key = {it} ) { page ->
                when(page){
                    0 -> NameItem(name, onValueChange = { name = it })
                    1 -> AgeItem(age, onValueChange = { age = it })
                    2 -> HeightItem(height, onValueChange = { height = it })
                    3-> WeightItem(weight, onValueChange = { weight = it })
                    4 -> GenderItem(gender, onValueChange = { gender = it })
                    5 -> ActivityLevelItem(activityLevel, onValueChange = { activityLevel = it })
                    6 -> BodyIndexesItem(age , height , weight , gender , activityLevel)
                    7 -> ResultsItem(weight, TargetWeight, RestDay , onValueChange = {TargetWeight = it})
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    Button(onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }) {
                        Text("Back")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    if (pagerState.currentPage == 7) {
                        onDone(
                            BaseInfoInput(
                                name, age, height, weight, gender, activityLevel
                            ),
                            MetricInput(
                                height,weight, TargetWeight , BMR ,BMI , TDEE ,calorPerDay , RestDay , UpdateAt
                            )

                        )
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.navigationBarsPadding()
                ) {
                    Text(if (pagerState.currentPage == 7) "Finish" else "Next")
                }
            }
        }
    }
}

@Composable
fun StepProgressBar(currentStep: Int, totalSteps: Int = 7) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(top = 35.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until totalSteps) {
            Canvas(
                modifier = Modifier
                    .weight(0.5f)
                    .height(4.dp)
            ) {
                drawLine(
                    color = if (i == currentStep) Color(0xFF00BEBE) else Color.LightGray,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            if (i < totalSteps - 1) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}

