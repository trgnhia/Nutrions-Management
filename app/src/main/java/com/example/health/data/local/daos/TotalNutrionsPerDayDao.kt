package com.example.health.data.local.daos

import androidx.room.*
import com.example.health.data.local.entities.TotalNutrionsPerDay
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TotalNutrionsPerDayDao {

    // ✅ Thêm hoặc cập nhật bản ghi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(total: TotalNutrionsPerDay)

    // ✅ Cập nhật bản ghi
    @Update
    suspend fun update(total: TotalNutrionsPerDay)

    // ✅ Xoá bản ghi
    @Delete
    suspend fun delete(total: TotalNutrionsPerDay)

    // ✅ Lấy bản ghi theo ngày và UID (Flow để theo dõi thay đổi)
    @Query("SELECT * FROM total_nutrions_per_day WHERE date = :date AND uid = :uid LIMIT 1")
    fun getByDateAndUid(date: Date, uid: String): Flow<TotalNutrionsPerDay?>

    // ✅ Lấy toàn bộ bản ghi theo người dùng (tất cả các ngày)
    @Query("SELECT * FROM total_nutrions_per_day WHERE uid = :uid ORDER BY date DESC")
    fun getAllByUser(uid: String): Flow<List<TotalNutrionsPerDay>>

    // ✅ Truy vấn để tính tổng dinh dưỡng từ bảng `eaten_meal`
    @Query(
        """
        SELECT 
            IFNULL(SUM(totalCalos), 0) AS totalCalo,
            IFNULL(SUM(totalPro), 0) AS totalPro,
            IFNULL(SUM(totalCarbs), 0) AS totalCarb,
            IFNULL(SUM(totalFats), 0) AS totalFat
        FROM eaten_meal
        WHERE uid = :uid AND date = :date
    """
    )
    suspend fun getDailyNutritionAggregate(
        uid: String,
        date: Date
    ): NutritionAggregate?
}
