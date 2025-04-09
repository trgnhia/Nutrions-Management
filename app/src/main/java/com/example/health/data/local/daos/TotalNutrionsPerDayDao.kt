package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.*
import com.example.health.data.local.entities.TotalNutrionsPerDay
import kotlinx.coroutines.flow.Flow
import java.util.Date

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
}
