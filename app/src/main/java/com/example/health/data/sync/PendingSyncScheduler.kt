package com.example.health.data.sync

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object PendingSyncScheduler {
    fun schedule(context: Context) {
        val request = PeriodicWorkRequestBuilder<PendingSyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "PendingSync",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}