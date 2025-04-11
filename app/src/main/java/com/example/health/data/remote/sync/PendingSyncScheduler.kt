package com.example.health.data.remote.sync

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object PendingSyncScheduler {
    private var hasScheduled = false

    fun schedule(context: Context) {
        if (hasScheduled) return

        hasScheduled = true

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
