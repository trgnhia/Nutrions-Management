package com.example.health.data.local.daos

import androidx.room.Dao

import androidx.room.*
import com.example.health.data.local.entities.quyen.EatenDish
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface EatenDishDao {

    // ✅ Thêm món ăn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eatenDish: EatenDish)

    // ✅ Sửa món ăn
    @Update
    suspend fun update(eatenDish: EatenDish)

    // ✅ Xoá món ăn
    @Delete
    suspend fun delete(eatenDish: EatenDish)

    // ✅ Lọc theo ngày & loại bữa
    @Query("""
        SELECT * FROM eaten_dish 
        INNER JOIN eaten_meal ON eaten_dish.idEatenMeal = eaten_meal.id
        WHERE eaten_meal.date = :date AND eaten_meal.type = :type
    """)
    fun getByDateAndType(date: Date, type: Int): Flow<List<EatenDish>>

    // ✅ Lấy tất cả món ăn trong một ngày (bỏ qua type)
    @Query("""
    SELECT * FROM eaten_dish 
    INNER JOIN eaten_meal ON eaten_dish.idEatenMeal = eaten_meal.id
    WHERE eaten_meal.date = :date
""")
    fun getByDate(date: Date): Flow<List<EatenDish>>
}
