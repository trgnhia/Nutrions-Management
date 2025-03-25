package com.example.health.data.local.repostories

import com.example.health.data.local.appdatabase.AppDatabase
import com.example.health.data.local.daos.AccountDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.Account
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class AccountRepository(
    private val accountDao: AccountDao,
    private val pendingActionDao: PendingActionDao,
    private val firestore: FirebaseFirestore


) {
    fun getAccount(): Flow<Account?> = accountDao.getAccount()
    suspend fun insertAccount(account: Account) {
        accountDao.insertAccount(account)
        try{
            firestore.collection("accounts").document(account.Uid).set(account)
        }
        catch (_: Exception) {
            val json = Gson().toJson(account)
            val action = PendingAction(
                type = PendingActionTypes.INSERT_ACCOUNT,
                uid = account.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }

    }
    suspend fun deleteAccount() {
        accountDao.deleteAccount()
    }
    suspend fun updateStatus(uid: String, status: String) {
        accountDao.updateStatus(uid, status)
        try{
            firestore.collection("accounts").document(uid).update("status", status)
        }
        catch (_: Exception) {
            val action = PendingAction(
                type = PendingActionTypes.UPDATE_STATUS,
                uid = uid,
                payload = status
            )
            pendingActionDao.insert(action)
        }
    }
    suspend fun fetchFromRemote(uid: String): Account? {
        try{
            val snapshot = firestore.collection("accounts").document(uid).get().await()
            val remote = snapshot.toObject(Account::class.java)
            remote?.let { accountDao.insertAccount(it) }
            return remote
        }
        catch (_: Exception) {
            return null
        }
    }
}