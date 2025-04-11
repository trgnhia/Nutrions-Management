package com.example.health.data.local.daos

import androidx.room.*
import com.example.health.data.local.entities.CustomExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomExerciseDao {

    // ✅ Thêm hoặc cập nhật một bài tập tuỳ chỉnh
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customExercise: CustomExercise)

    // ✅ Xoá một bài tập tuỳ chỉnh
    @Delete
    suspend fun delete(customExercise: CustomExercise)

    // ✅ Cập nhật thông tin bài tập tuỳ chỉnh
    @Update
    suspend fun update(customExercise: CustomExercise)

    // ✅ Lấy toàn bộ bài tập của người dùng
    @Query("SELECT * FROM custom_exercise WHERE uid = :uid")
    fun getAllByUser(uid: String): Flow<List<CustomExercise>>

    // 🔍 Tìm kiếm bài tập theo tên (không phân biệt hoa thường)
    @Query("SELECT * FROM custom_exercise WHERE uid = :uid AND name LIKE '%' || :query || '%' COLLATE NOCASE")
    fun searchByName(uid: String, query: String): Flow<List<CustomExercise>>
}
