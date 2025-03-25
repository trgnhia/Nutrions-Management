package com.example.health.data.local.appdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.health.data.local.daos.*
import com.example.health.data.local.entities.*

@Database(
    entities = [Account::class, BaseInfo::class, HealMetric::class, PendingAction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun baseInfoDao(): BaseInfoDao
    abstract fun healMetricDao(): HealMetricDao
    abstract fun pendingActionDao(): PendingActionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "health_app_database"
                )
                    .fallbackToDestructiveMigration() // Reset database nếu có thay đổi version
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}