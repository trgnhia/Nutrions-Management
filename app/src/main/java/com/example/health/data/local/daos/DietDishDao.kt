package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.health.data.local.entities.DietDish
import kotlinx.coroutines.flow.Flow

@Dao
interface DietDishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dishes: List<DietDish>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dish: DietDish)

    @Query("SELECT * FROM diet_dish WHERE mealPlanId = :mealPlanId")
    suspend fun getByMealPlanId(mealPlanId: String): List<DietDish>

    @Query("SELECT * FROM diet_dish WHERE id = :id")
    suspend fun getById(id: String): DietDish?

}
