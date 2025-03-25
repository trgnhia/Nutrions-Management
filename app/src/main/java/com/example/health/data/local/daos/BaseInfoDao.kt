package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.health.data.local.entities.BaseInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface BaseInfoDao {

    // lay thong tin co ban
    @Query("SELECT * FROM base_info limit 1 ")
    fun getBaseInfo(): Flow<BaseInfo?>

    //Xoa thong tin co ban
    @Query("DELETE FROM base_info")
    suspend fun deleteBaseInfo()

    // them thong tin co ban
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBaseInfo(baseInfo: BaseInfo)

    // cap nhat thong tin co ban
    @Update
    suspend fun updateBaseInfo(baseInfo: BaseInfo)

}