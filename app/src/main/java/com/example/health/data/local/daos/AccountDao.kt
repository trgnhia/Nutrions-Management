package com.example.health.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.health.data.local.entities.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    // lay ra tai khoan trong room
    @Query ("SELECT * FROM account LIMIT 1 ")
    fun getAccount(): Flow<Account?>

    // them tai khoan vua dang nhap
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account)

    // Xóa thông tin tài khoản
    @Query("DELETE FROM account")
    suspend fun deleteAccount()

    // cap nhat trang thai
    @Query("UPDATE account SET status = :status WHERE Uid = :uid")
    suspend fun updateStatus(uid: String, status: String)

    @Query("SELECT * FROM account LIMIT 1")
    suspend fun getAccountOnce(): Account?
}