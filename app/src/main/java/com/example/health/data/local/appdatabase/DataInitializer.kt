package com.example.health.data.local.appdatabase

import android.content.Context

object DataInitializer {
    suspend fun initialize(context: Context, db: AppDatabase) {
        AssetDataImporter.importAll(context, db)
    }
}