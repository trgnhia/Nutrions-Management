package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.health.data.local.entities.HealMetric
import kotlinx.coroutines.flow.Flow

@Dao
interface HealMetricDao {
    // lay tat ca cac ban ghi
    @Query("SELECT * FROM health_metric")
    fun getAllHealthMetrics(): Flow<List<HealMetric>>

    // Lay ban ghi cuoi cung
    @Query("SELECT * FROM health_metric ORDER BY update_at DESC LIMIT 1")
    fun getLastHealthMetric(): Flow<HealMetric?>

    // Them ban ghi moi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthMetric(healthMetric: HealMetric)

    // xoa ban ghi duoc chon
    @Query("DELETE FROM health_metric WHERE metricId = :id")
    suspend fun deleteHealthMetricById(id: String)

    // cap nhat ban ghi
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHealthMetric(healthMetric: HealMetric)

}