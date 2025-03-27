package com.example.health.screens.login

import AgeItem
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
import com.example.health.data.local.viewmodel.AuthViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.screens.login.baseinfoitems.AchiveGoalItem
import com.example.health.screens.login.baseinfoitems.ActivityLevelItem
import com.example.health.screens.login.baseinfoitems.GenderItem
import com.example.health.screens.login.baseinfoitems.GoalItem
import com.example.health.screens.login.baseinfoitems.HeightItem
import com.example.health.screens.login.baseinfoitems.NameItem
import com.example.health.screens.login.baseinfoitems.WeightItem
import kotlinx.coroutines.launch

data class BaseInfoInput(
    val name: String,
    val age: Int,
    val height: Float,
    val weight: Float,
    val goal: Int,
    val gender: String,
    val activityLevel: Int,
    val goalAchieve: Float
)

@Composable
fun BaseInfoScreen(
    authViewModel: AuthViewModel,
    baseInfoViewModel: BaseInfoViewModel,
    navController: NavController
) {
    val uid = authViewModel.currentUser?.uid ?: return
    val baseInfo by baseInfoViewModel.baseInfo.collectAsState()

    val defaultInfo = baseInfo?.let {
        BaseInfoInput(
            name = it.Name,
            age = it.Age,
            height = it.Height,
            weight = it.Weight,
            goal = it.Goal,
            gender = it.Gender,
            activityLevel = it.ActivityLevel,
            goalAchieve = it.GoalAchivement
        )
    } ?: BaseInfoInput("", 0, 0f, 0f, 1 ,"" , 0, 50f)

    OnboardingScreen(
        uid = uid,
        default = defaultInfo,
        onDone = { info ->
            // Lưu Room
            val base = BaseInfo(
                Uid = uid,
                Name = info.name,
                Age = info.age,
                Height = info.height,
                Weight = info.weight,
                Goal = info.goal,
                Gender = info.gender,
                ActivityLevel = info.activityLevel,
                GoalAchivement = info.goalAchieve
            )
            baseInfoViewModel.insertBaseInfo(base)

            // Cập nhật trạng thái & điều hướng
            authViewModel.updateStatus(uid, "completed")
            navController.navigate("calculating") {
                popUpTo("base_info") { inclusive = true }
            }
        }
    )
}

@Composable
fun OnboardingScreen(
    uid: String,
    default: BaseInfoInput,
    onDone: (BaseInfoInput) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 7 })
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf(default.name) }
    var age by remember { mutableStateOf(default.age) }
    var height by remember { mutableStateOf(default.height) }
    var weight by remember { mutableStateOf(default.weight) }
    var goal by remember { mutableStateOf(default.goal) }
    var gender by remember { mutableStateOf(default.gender) }
    var activityLevel by remember { mutableStateOf(default.activityLevel) }
    var goalAchieve by remember { mutableStateOf(default.goalAchieve) }

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
            StepProgressBar(currentStep = pagerState.currentPage, totalSteps = 7)

            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f) , key = {it} ) { page ->
    //            when (page) {
    //                0 -> Item1(name, onValueChange = { name = it })
    //                1 -> Item2(age, onValueChange = { age = it })
    //                2 -> Item3(height, onValueChange = { height = it })
    //                3 -> Item4(weight, onValueChange = { weight = it })
    //                4 -> Item5(goal, onValueChange = { goal = it })
    //                5 -> Item6(gender, onValueChange = { gender = it })
    //                6 -> Item7(activityLevel, onValueChange = { activityLevel = it })
    //                7 -> Item8(goalAchieve, onValueChange = { goalAchieve = it })
    //                8 -> Item9(onConfirm = {
    //                    onDone(
    //                        BaseInfoInput(
    //                            name, age, height, weight, goal, gender, activityLevel, goalAchieve
    //                        )
    //                    )
    //                })
    //            }
                when(page){
                    0 -> NameItem(name, onValueChange = { name = it })
                    1 -> AgeItem(age, onValueChange = { age = it })
    //                2 -> HeightItem(height, onValueChange = { height = it })
    //                3-> WeightItem(weight, onValueChange = { weight = it })
    //                4 -> GoalItem(goal, onValueChange = { goal = it })
    //                5 -> GenderItem(gender, onValueChange = { gender = it })
    //                6 -> ActivityLevelItem(activityLevel, onValueChange = { activityLevel = it })
    //                7 -> AchiveGoalItem(goalAchieve, onValueChange = { goalAchieve = it })
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
                    if (pagerState.currentPage == 6) {
                        onDone(
                            BaseInfoInput(
                                name, age, height, weight, goal, gender, activityLevel, goalAchieve
                            )
                        )
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }) {
                    Text(if (pagerState.currentPage == 6) "Finish" else "Next")
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