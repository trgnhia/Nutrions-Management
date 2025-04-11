package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.health.data.local.entities.Notify
import kotlinx.coroutines.flow.Flow

@Dao
interface NotifyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notify: Notify)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifies: List<Notify>)

    @Update
    suspend fun update(notify: Notify)

    @Delete
    suspend fun delete(notify: Notify)

    @Query("SELECT * FROM Notify WHERE uid = :uid")
    fun getAllByUid(uid: String): Flow<List<Notify>>

    @Query("DELETE FROM Notify WHERE uid = :uid")
    suspend fun deleteAllByUid(uid: String)
}