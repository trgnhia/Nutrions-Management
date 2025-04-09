package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.health.data.local.daos.quyen.DailyNutritionSummary
import com.example.health.data.local.entities.EatenMeal
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface EatenMealDao {

    // ✅ Thêm hoặc cập nhật một bữa ăn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: EatenMeal)

    // ✅ Sửa thông tin bữa ăn (nếu không dùng REPLACE)
    @Update
    suspend fun update(meal: EatenMeal)

    // ✅ Xoá một bữa ăn
    @Delete
    suspend fun delete(meal: EatenMeal)

    // ✅ Lấy bữa ăn theo ngày và type (sáng/trưa/tối/snack)
    @Query("SELECT * FROM eaten_meal WHERE date = :date AND type = :type LIMIT 1")
    suspend fun getMealByDateAndType(date: Date, type: Int): EatenMeal?

    // ✅ Lấy toàn bộ bữa ăn trong ngày (mọi type)
    @Query("SELECT * FROM eaten_meal WHERE date = :date")
    fun getMealsByDate(date: Date): Flow<List<EatenMeal>>

    // ✅ Lấy tất cả bữa ăn (toàn bộ lịch sử)
    @Query("SELECT * FROM eaten_meal ORDER BY date DESC")
    fun getAllMeals(): Flow<List<EatenMeal>>

    @Query("""
    SELECT 
        SUM(totalCalos) as totalCalos,
        SUM(totalFats) as totalFats,
        SUM(totalCarbs) as totalCarbs,
        SUM(totalPro) as totalPro
    FROM eaten_meal
    WHERE uid = :uid AND date = :date
""")
    suspend fun getDailyNutritionSummary(
        uid: String,
        date: Date
    ): DailyNutritionSummary?
}


