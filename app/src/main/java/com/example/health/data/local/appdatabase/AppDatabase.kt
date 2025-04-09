package com.example.health.data.local.appdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.health.data.local.converters.Converters
import com.example.health.data.local.daos.*
import com.example.health.data.local.daos.quyen.BurnOutCaloPerDayDao
import com.example.health.data.local.daos.quyen.CustomFoodDao
import com.example.health.data.local.daos.quyen.EatenDishDao
import com.example.health.data.local.daos.quyen.EatenMealDao
import com.example.health.data.local.daos.quyen.MacroDao
import com.example.health.data.local.daos.quyen.TotalNutrionsPerDayDao
import com.example.health.data.local.entities.*
import com.example.health.data.local.entities.quyen.BurnOutCaloPerDay
import com.example.health.data.local.entities.quyen.CustomFood
import com.example.health.data.local.entities.quyen.EatenDish
import com.example.health.data.local.entities.quyen.EatenMeal
import com.example.health.data.local.entities.quyen.Macro
import com.example.health.data.local.entities.quyen.TotalNutrionsPerDay

@Database(
    entities = [
        Account::class,
        BaseInfo::class,
        HealthMetric::class,
        PendingAction::class,
        BurnOutCaloPerDay::class,
        CustomFood::class,
        DefaultDietMealInPlan::class,
        DefaultExercise::class,
        DefaultFood::class,
        DietDish::class,
        EatenDish::class,
        EatenMeal::class,
        ExerciseLog::class,
        Notify::class,
        TotalNutrionsPerDay::class,
        Macro::class
    ],
    version = 11,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun baseInfoDao(): BaseInfoDao
    abstract fun healMetricDao(): HealMetricDao
    abstract fun pendingActionDao(): PendingActionDao
    abstract fun burnOutCaloPerDayDao(): BurnOutCaloPerDayDao
    abstract fun customFoodDao(): CustomFoodDao
    abstract fun defaultDietMealInPlanDao(): DefaultDietMealInPlanDao
    abstract fun defaultExerciseDao(): DefaultExerciseDao
    abstract fun defaultFoodDao(): DefaultFoodDao
    abstract fun dietDishDao(): DietDishDao
    abstract fun eatenDishDao(): EatenDishDao
    abstract fun eatenMealDao(): EatenMealDao
    abstract fun exerciseLogDao(): ExerciseLogDao
    abstract fun notifyDao(): NotifyDao
    abstract fun totalNutrionsPerDayDao(): TotalNutrionsPerDayDao
    abstract fun macroDao(): MacroDao

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
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
