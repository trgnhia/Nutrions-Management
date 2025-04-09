package com.example.health.data.local.daos

import androidx.room.*
import com.example.health.data.local.entities.CustomFood
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomFoodDao {

    // ✅ Thêm hoặc cập nhật 1 món ăn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customFood: CustomFood)

    // ✅ Thêm nhiều món ăn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CustomFood>)

    // ✅ Xoá 1 món ăn
    @Delete
    suspend fun delete(customFood: CustomFood)

    // ✅ Xoá theo ID
    @Query("DELETE FROM custom_food WHERE id = :id")
    suspend fun deleteById(id: String)

    // ✅ Xoá toàn bộ món ăn của người dùng
    @Query("DELETE FROM custom_food WHERE uid = :uid")
    suspend fun deleteAllByUser(uid: String)

    // ✅ Lấy toàn bộ món ăn của người dùng
    @Query("SELECT * FROM custom_food WHERE uid = :uid")
    fun getAllByUser(uid: String): Flow<List<CustomFood>>

    // ✅ Tìm món ăn theo ID
    @Query("SELECT * FROM custom_food WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): CustomFood?
}
