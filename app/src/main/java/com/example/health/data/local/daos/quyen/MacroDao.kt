package com.example.health.data.local.daos.quyen

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.health.data.local.entities.quyen.Macro
import kotlinx.coroutines.flow.Flow

@Dao
interface MacroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(macro: Macro)

    @Update
    suspend fun update(macro: Macro)

    // ✅ Truy vấn bản ghi Macro duy nhất
    @Query("SELECT * FROM macro LIMIT 1")
    fun getMacro(): Flow<Macro?>

}