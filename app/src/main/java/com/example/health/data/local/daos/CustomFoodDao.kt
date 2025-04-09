package com.example.health.data.local.daos

import androidx.room.*
import com.example.health.data.local.entities.CustomFood

import kotlinx.coroutines.flow.Flow

@Dao
interface CustomFoodDao {

    // ✅ Thêm hoặc cập nhật 1 món ăn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customFood: CustomFood)

    // ✅ Xoá 1 món ăn
    @Delete
    suspend fun delete(customFood: CustomFood)

    @Update
    suspend fun update(customFood: CustomFood)

    // ✅ Lấy toàn bộ món ăn của người dùng
    @Query("SELECT * FROM custom_food WHERE uid = :uid")
    fun getAllByUser(uid: String): Flow<List<CustomFood>>

    // 🔍 Tìm kiếm món ăn tuỳ chỉnh theo tên (gần đúng, không phân biệt hoa thường)
    @Query("SELECT * FROM custom_food WHERE uid = :uid AND name LIKE '%' || :query || '%' COLLATE NOCASE")
    fun searchByName(uid: String, query: String): Flow<List<CustomFood>>

}
