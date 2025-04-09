package com.example.health.data.local.daos

import androidx.room.Dao

import androidx.room.*
import com.example.health.data.local.entities.quyen.BurnOutCaloPerDay
import java.util.Date

@Dao
interface BurnOutCaloPerDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(burn: BurnOutCaloPerDay)

    @Update
    suspend fun update(burn: BurnOutCaloPerDay)

    @Delete
    suspend fun delete(burn: BurnOutCaloPerDay)

    @Query("SELECT * FROM burn_out_calo_per_day WHERE dateTime = :date LIMIT 1")
    suspend fun getByDate(date: Date): BurnOutCaloPerDay?

    @Query("""
    SELECT SUM(caloBurn) FROM exercise_log 
    WHERE uid = :uid AND date(dateTime) = date(:date)
""")
    suspend fun getTotalCaloBurnedByDate(
        uid: String,
        date: Date
    ): Int?

}
