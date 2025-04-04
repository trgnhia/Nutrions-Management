package com.example.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.appdatabase.AppDatabase
import com.example.health.data.local.repostories.AccountRepository
import com.example.health.data.local.repostories.BaseInfoRepository
import com.example.health.data.local.repostories.HealthMetricRepository
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.example.health.data.local.viewmodelfactory.AccountViewModelFactory
import com.example.health.data.local.viewmodelfactory.AuthViewModelFactory
import com.example.health.data.local.viewmodelfactory.BaseInfoViewModelFactory
import com.example.health.data.local.viewmodelfactory.HealthMetricViewModelFactory
import com.example.health.data.remote.sync.PendingSyncScheduler
import com.example.health.navigation.AppNavigation
import com.example.health.ui.theme.HealthTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PendingSyncScheduler.schedule(this)
        enableEdgeToEdge()
        val db = AppDatabase.getDatabase(applicationContext)
        val firestore = FirebaseFirestore.getInstance()

        val accountRepository = AccountRepository(db.accountDao(), db.pendingActionDao(), firestore)
        val baseInfoRepository = BaseInfoRepository(db.baseInfoDao(), db.pendingActionDao(), firestore)
        val healthMetricRepository = HealthMetricRepository(db.healMetricDao(), db.pendingActionDao(), firestore)

        val authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(application, accountRepository)
        )[AuthViewModel::class.java]

        val accountViewModel = ViewModelProvider(
            this,
            AccountViewModelFactory(application, accountRepository)
        )[AccountViewModel::class.java]

        val baseInfoViewModel = ViewModelProvider(
            this,
            BaseInfoViewModelFactory(application, baseInfoRepository)
        )[BaseInfoViewModel::class.java]

        val healthMetricViewModel = ViewModelProvider(
            this,
            HealthMetricViewModelFactory(application, healthMetricRepository)
        )[HealthMetricViewModel::class.java]

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HealthTheme {
                AppNavigation(
                    authViewModel = authViewModel,
                    accountViewModel = accountViewModel,
                    baseInfoViewModel = baseInfoViewModel,
                    healthMetricViewModel = healthMetricViewModel
                )
            }
        }
    }
}

