package com.example.health

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.health.alarm.scheduleDaily7AMAlarm
import com.example.health.data.local.appdatabase.AppDatabase
import com.example.health.data.local.repostories.AccountRepository
import com.example.health.data.local.repostories.BaseInfoRepository
import com.example.health.data.local.repostories.BurnOutCaloPerDayRepository
import com.example.health.data.local.repostories.CustomExerciseRepository
import com.example.health.data.local.repostories.CustomFoodRepository
import com.example.health.data.local.repostories.DefaultDietMealInPlanRepository
import com.example.health.data.local.repostories.DefaultExerciseRepository
import com.example.health.data.local.repostories.DefaultFoodRepository
import com.example.health.data.local.repostories.DietDishRepository
import com.example.health.data.local.repostories.EatenDishRepository
import com.example.health.data.local.repostories.EatenMealRepository
import com.example.health.data.local.repostories.ExerciseLogRepository
import com.example.health.data.local.repostories.HealthMetricRepository
import com.example.health.data.local.repostories.MacroRepository
import com.example.health.data.local.repostories.NotifyRepository
import com.example.health.data.local.repostories.TotalNutrionsPerDayRepository
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.CustomExerciseViewModel
import com.example.health.data.local.viewmodel.CustomFoodViewModel
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.data.local.viewmodel.EatenDishViewModel
import com.example.health.data.local.viewmodel.EatenMealViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.local.viewmodel.MacroViewModel
import com.example.health.data.local.viewmodel.NotifyViewModel
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel
import com.example.health.data.local.viewmodelfactory.AccountViewModelFactory
import com.example.health.data.remote.auth.AuthViewModelFactory
import com.example.health.data.local.viewmodelfactory.BaseInfoViewModelFactory
import com.example.health.data.local.viewmodelfactory.BurnOutCaloPerDayViewModelFactory
import com.example.health.data.local.viewmodelfactory.CustomExerciseViewModelFactory
import com.example.health.data.local.viewmodelfactory.CustomFoodViewModelFactory
import com.example.health.data.local.viewmodelfactory.DefaultDietMealInPlanViewModelFactory
import com.example.health.data.local.viewmodelfactory.DefaultExerciseViewModelFactory
import com.example.health.data.local.viewmodelfactory.DefaultFoodViewModelFactory
import com.example.health.data.local.viewmodelfactory.DietDishViewModelFactory
import com.example.health.data.local.viewmodelfactory.EatenDishViewModelFactory
import com.example.health.data.local.viewmodelfactory.EatenMealViewModelFactory
import com.example.health.data.local.viewmodelfactory.ExerciseLogViewModelFactory
import com.example.health.data.local.viewmodelfactory.HealthMetricViewModelFactory
import com.example.health.data.local.viewmodelfactory.MacroViewModelFactory
import com.example.health.data.local.viewmodelfactory.NotifyViewModelFactory
import com.example.health.data.local.viewmodelfactory.TotalNutrionsPerDayViewModelFactory
import com.example.health.data.remote.sync.PendingSyncScheduler
import com.example.health.navigation.AppNavigation
import com.example.health.ui.theme.HealthTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PendingSyncScheduler.schedule(applicationContext)
        enableEdgeToEdge()
        val db = AppDatabase.getDatabase(applicationContext)
        val firestore = FirebaseFirestore.getInstance()

//        val accountRepository = AccountRepository(db.accountDao(), db.pendingActionDao(), firestore)
//        val baseInfoRepository = BaseInfoRepository(db.baseInfoDao(), db.pendingActionDao(), firestore)
//        val healthMetricRepository = HealthMetricRepository(db.healMetricDao(), db.pendingActionDao(), firestore)
//
//        val authViewModel = ViewModelProvider(
//            this,
//            AuthViewModelFactory(applicationContext, accountRepository)
//        )[AuthViewModel::class.java]
//
//        val accountViewModel = ViewModelProvider(
//            this,
//            AccountViewModelFactory(accountRepository)
//        )[AccountViewModel::class.java]
//
//        val baseInfoViewModel = ViewModelProvider(
//            this,
//            BaseInfoViewModelFactory(baseInfoRepository)
//        )[BaseInfoViewModel::class.java]
//
//        val healthMetricViewModel = ViewModelProvider(
//            this,
//            HealthMetricViewModelFactory(healthMetricRepository)
//        )[HealthMetricViewModel::class.java]
//        val defaultFoodRepository = DefaultFoodRepository(db.defaultFoodDao(), firestore)
//        val defaultExerciseRepository = DefaultExerciseRepository(db.defaultExerciseDao(), firestore)
//
//
//        val defaultFoodViewModel = ViewModelProvider(
//            this,
//            DefaultFoodViewModelFactory(defaultFoodRepository)
//        )[DefaultFoodViewModel::class.java]
//
//        val defaultExerciseViewModel = ViewModelProvider(
//            this,
//            DefaultExerciseViewModelFactory(defaultExerciseRepository)
//        )[DefaultExerciseViewModel::class.java]
//
//        val macroRepository = MacroRepository(db.macroDao(), db.pendingActionDao(),firestore)
//        val macroViewModel = ViewModelProvider(
//            this,
//            MacroViewModelFactory(macroRepository)
//        )[com.example.health.data.local.viewmodel.MacroViewModel::class.java]

        val accountRepository = AccountRepository(db.accountDao(), db.pendingActionDao(), firestore)
        val baseInfoRepository = BaseInfoRepository(db.baseInfoDao(), db.pendingActionDao(), firestore)
        val healthMetricRepository = HealthMetricRepository(db.healMetricDao(), db.pendingActionDao(), firestore)
        val defaultFoodRepository = DefaultFoodRepository(db.defaultFoodDao(), firestore)
        val defaultExerciseRepository = DefaultExerciseRepository(db.defaultExerciseDao(), firestore)
        val defaultDietMealInPlanRepository = DefaultDietMealInPlanRepository(db.defaultDietMealInPlanDao(), firestore)
        val macroRepository = MacroRepository(db.macroDao(), db.pendingActionDao(), firestore)
        val totalNutrionsRepository = TotalNutrionsPerDayRepository(db.totalNutrionsPerDayDao(), db.pendingActionDao(), firestore)
        val exerciseLogRepository = ExerciseLogRepository(db.exerciseLogDao(), db.pendingActionDao(), firestore)
        val eatenMealRepository = EatenMealRepository(db.eatenMealDao(), db.pendingActionDao(), firestore)
        val eatenDishRepository = EatenDishRepository(db.eatenDishDao(), db.pendingActionDao(), firestore)
        val burnOutRepository = BurnOutCaloPerDayRepository(db.burnOutCaloPerDayDao(), db.pendingActionDao(), firestore)
        val customFoodRepository = CustomFoodRepository(db.customFoodDao(), db.pendingActionDao(), firestore)
        val customExerciseRepository = CustomExerciseRepository(db.customExerciseDao(), db.pendingActionDao(), firestore)
        //val notifyRepository = NotifyRepository(db.notifyDao(), db.pendingActionDao(), firestore)
        //val dietDishRepository = DietDishRepository(db.dietDishDao(), db.pendingActionDao(), firestore)
        val notifyRepository = NotifyRepository(db.notifyDao(), firestore, db.pendingActionDao())
        val dietDishRepository = DietDishRepository(db.dietDishDao(), firestore)

        // ✅ ViewModel
        val authViewModel = ViewModelProvider(this, AuthViewModelFactory(applicationContext, accountRepository))[AuthViewModel::class.java]
        val accountViewModel = ViewModelProvider(this, AccountViewModelFactory(accountRepository))[AccountViewModel::class.java]
        val baseInfoViewModel = ViewModelProvider(this, BaseInfoViewModelFactory(baseInfoRepository))[BaseInfoViewModel::class.java]
        val healthMetricViewModel = ViewModelProvider(this, HealthMetricViewModelFactory(healthMetricRepository))[HealthMetricViewModel::class.java]
        val defaultFoodViewModel = ViewModelProvider(this, DefaultFoodViewModelFactory(defaultFoodRepository))[DefaultFoodViewModel::class.java]
        val defaultExerciseViewModel = ViewModelProvider(this, DefaultExerciseViewModelFactory(defaultExerciseRepository))[DefaultExerciseViewModel::class.java]
        val defaultDietMealInPlanViewModel = ViewModelProvider(this, DefaultDietMealInPlanViewModelFactory(defaultDietMealInPlanRepository))[DefaultDietMealInPlanViewModel::class.java]
        val macroViewModel = ViewModelProvider(this, MacroViewModelFactory(macroRepository))[MacroViewModel::class.java]
        val totalNutrionsViewModel = ViewModelProvider(this, TotalNutrionsPerDayViewModelFactory(totalNutrionsRepository))[TotalNutrionsPerDayViewModel::class.java]
        val exerciseLogViewModel = ViewModelProvider(this, ExerciseLogViewModelFactory(exerciseLogRepository))[ExerciseLogViewModel::class.java]
        val eatenMealViewModel = ViewModelProvider(this, EatenMealViewModelFactory(eatenMealRepository))[EatenMealViewModel::class.java]
        val eatenDishViewModel = ViewModelProvider(this, EatenDishViewModelFactory(eatenDishRepository))[EatenDishViewModel::class.java]
        val burnOutViewModel = ViewModelProvider(this, BurnOutCaloPerDayViewModelFactory(burnOutRepository))[BurnOutCaloPerDayViewModel::class.java]
        val customFoodViewModel = ViewModelProvider(this, CustomFoodViewModelFactory(customFoodRepository))[CustomFoodViewModel::class.java]
        val customExerciseViewModel = ViewModelProvider(this, CustomExerciseViewModelFactory(customExerciseRepository))[CustomExerciseViewModel::class.java]
        //val notifyViewModel = ViewModelProvider(this, NotifyViewModelFactory(notifyRepository))[NotifyViewModel::class.java]
        //val dietDishViewModel = ViewModelProvider(this, DietDishViewModelFactory(dietDishRepository))[DietDishViewModel::class.java]
        scheduleDaily7AMAlarm()
        val notifyViewModel = ViewModelProvider(this, NotifyViewModelFactory(notifyRepository))[NotifyViewModel::class.java]
        val dietDishViewModel = ViewModelProvider(this, DietDishViewModelFactory(dietDishRepository))[DietDishViewModel::class.java]

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HealthTheme {
                AppNavigation(
                    authViewModel = authViewModel,
                    accountViewModel = accountViewModel,
                    baseInfoViewModel = baseInfoViewModel,
                    healthMetricViewModel = healthMetricViewModel,
                    defaultFoodViewModel = defaultFoodViewModel,
                    defaultExerciseViewModel = defaultExerciseViewModel,
                    defaultDietMealInPlanViewModel = defaultDietMealInPlanViewModel,
                    macroViewModel = macroViewModel,
                    totalNutrionsPerDayViewModel = totalNutrionsViewModel,
                    exerciseLogViewModel = exerciseLogViewModel,
                    eatenMealViewModel = eatenMealViewModel,
                    eatenDishViewModel = eatenDishViewModel,
                    burnOutCaloPerDayViewModel = burnOutViewModel,
                    customFoodViewModel = customFoodViewModel,
                    customExerciseViewModel = customExerciseViewModel,

//                    notifyViewModel = notifyViewModel,
//                    dietDishViewModel = dietDishViewModel

                    notifyViewModel = notifyViewModel,
                    dietDishViewModel = dietDishViewModel
                )
            }
        }
    }
}

