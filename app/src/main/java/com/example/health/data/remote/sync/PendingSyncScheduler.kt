package com.example.health.data.remote.sync

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object PendingSyncScheduler {
    fun schedule(context: Context) {
        val workManager = WorkManager.getInstance(context)

        workManager.getWorkInfosForUniqueWorkLiveData("PendingSync")
            .observeForever { works ->
                if (works.isNullOrEmpty()) {
                    val request = PeriodicWorkRequestBuilder<PendingSyncWorker>(15, TimeUnit.MINUTES)
                        .setConstraints(
                            Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                        )
                        .build()

                    workManager.enqueueUniquePeriodicWork(
                        "PendingSync",
                        ExistingPeriodicWorkPolicy.KEEP,
                        request
                    )
                }
            }
    }
}
