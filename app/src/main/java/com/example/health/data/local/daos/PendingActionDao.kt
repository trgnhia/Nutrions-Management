package com.example.health.data.local.daos

import androidx.room.*
import com.example.health.data.local.entities.PendingAction

@Dao
interface PendingActionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(action: PendingAction)

    @Query("SELECT * FROM pending_actions")
    suspend fun getAll(): List<PendingAction>

    @Delete
    suspend fun delete(action: PendingAction)
}