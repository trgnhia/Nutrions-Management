package com.example.health.data.local.appdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.health.data.local.daos.*
import com.example.health.data.local.entities.*
import com.example.health.data.local.converters.Converters

@Database(
    entities = [Account::class, BaseInfo::class, HealthMetric::class, PendingAction::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class) // đang ky typeconvert
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