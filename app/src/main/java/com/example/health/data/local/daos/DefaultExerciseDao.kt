package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.health.data.local.entities.DefaultExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface DefaultExerciseDao {
    @Query("SELECT * FROM default_exercise")
    fun getAll(): Flow<List<DefaultExercise>>

    @Query("SELECT * FROM default_exercise WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DefaultExercise?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<DefaultExercise>)
}
