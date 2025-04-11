package com.example.health.data.local.daos

import androidx.room.*
import com.example.health.data.local.entities.DefaultDietMealInPlan
import kotlinx.coroutines.flow.Flow

@Dao
interface DefaultDietMealInPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<DefaultDietMealInPlan>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: DefaultDietMealInPlan)

    @Query("SELECT * FROM default_diet_meal_in_plan")
    fun getAll(): Flow<List<DefaultDietMealInPlan>>

    @Query("SELECT * FROM default_diet_meal_in_plan WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DefaultDietMealInPlan?
}
