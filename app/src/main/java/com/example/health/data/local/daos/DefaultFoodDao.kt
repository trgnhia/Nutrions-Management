package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.health.data.local.entities.DefaultFood
import kotlinx.coroutines.flow.Flow

@Dao
interface DefaultFoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<DefaultFood>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DefaultFood) // ✅ thêm dòng này

    @Query("SELECT * FROM default_food")
    fun getAll(): Flow<List<DefaultFood>>

    @Query("SELECT * FROM default_food WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DefaultFood?

    @Query("SELECT * FROM default_food WHERE type = :type")
    fun getByType(type: Int): Flow<List<DefaultFood>>

    // ✅ Truy vấn lấy món ăn ngẫu nhiên theo type
    @Query("SELECT * FROM default_food WHERE type = :type ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomFoodsByType(count: Int, type: Int): List<DefaultFood>
}
