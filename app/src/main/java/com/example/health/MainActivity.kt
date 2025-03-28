package com.example.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.health.data.local.appdatabase.AppDatabase
import com.example.health.data.local.repostories.AccountRepository
import com.example.health.data.local.repostories.BaseInfoRepository
import com.example.health.data.local.repostories.HealthMetricRepository
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.remote.auth.AuthViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
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

        val authViewModel = AuthViewModel(application, accountRepository)
        val accountViewModel = AccountViewModel(application, accountRepository)
        val baseInfoViewModel = BaseInfoViewModel(application, baseInfoRepository)
        val healthMetricViewModel = HealthMetricViewModel(application, healthMetricRepository)

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

