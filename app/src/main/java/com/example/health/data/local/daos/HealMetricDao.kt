package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.health.data.local.entities.HealthMetric
import kotlinx.coroutines.flow.Flow

@Dao
interface HealMetricDao {
    // lay tat ca cac ban ghi
    @Query("SELECT * FROM health_metric")
    fun getAllHealthMetrics(): Flow<List<HealthMetric>>

    // Lay ban ghi cuoi cung
    @Query("SELECT * FROM health_metric ORDER BY update_at DESC LIMIT 1")
    fun getLastHealthMetric(): Flow<HealthMetric?>

    // Them ban ghi moi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthMetric(healthMetric: HealthMetric)

    // xoa ban ghi duoc chon
    @Query("DELETE FROM health_metric WHERE metricId = :id")
    suspend fun deleteHealthMetricById(id: String)

    // cap nhat ban ghi
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHealthMetric(healthMetric: HealthMetric)
    // xoa moi ban gi
    @Query("DELETE FROM health_metric")
    suspend fun deleteAllHealthMetrics()


}