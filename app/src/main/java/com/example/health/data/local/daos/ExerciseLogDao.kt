package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.*
import com.example.health.data.local.entities.ExerciseLog
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExerciseLogDao {

    // ✅ Thêm 1 log
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: ExerciseLog)

    // ✅ Sửa log
    @Update
    suspend fun update(log: ExerciseLog)

    // ✅ Xóa log
    @Delete
    suspend fun delete(log: ExerciseLog)

    // ✅ Lấy tất cả log của người dùng (theo uid)
    @Query("SELECT * FROM exercise_log WHERE uid = :uid ORDER BY dateTime DESC")
    fun getAllByUser(uid: String): Flow<List<ExerciseLog>>

    // ✅ Lấy log theo ngày cụ thể
    @Query("""
    SELECT * FROM exercise_log 
    WHERE uid = :uid AND date(dateTime) = date(:date)
    ORDER BY dateTime DESC
""")
    fun getByDate(uid: String, date: Date): Flow<List<ExerciseLog>>


    // ✅ Tính tổng calo đã đốt trong 1 ngày
    @Query("""
    SELECT SUM(caloBurn) FROM exercise_log 
    WHERE uid = :uid AND date(dateTime) = date(:date)
""")
    suspend fun getTotalCaloBurnedByDate(uid: String, date: Date): Int?

}
