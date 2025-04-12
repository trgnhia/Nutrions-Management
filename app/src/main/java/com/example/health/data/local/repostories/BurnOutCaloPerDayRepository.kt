package com.example.health.data.local.repostories

import android.util.Log
import com.example.health.data.local.daos.BurnOutCaloPerDayDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.BurnOutCaloPerDay
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import java.util.*
class BurnOutCaloPerDayRepository(
    private val dao: BurnOutCaloPerDayDao,
    private val pendingDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {
    suspend fun insert(data: BurnOutCaloPerDay) {
        dao.insert(data)
        try {
            firestore.collection("accounts")
                .document(data.Uid)
                .collection("burn_out_calo_per_day")
                .document(data.DateTime.time.toString())
                .set(data)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(data)
            pendingDao.insert(
                PendingAction(
                    uid = data.Uid,
                    type = PendingActionTypes.INSERT_BURN_OUT,
                    payload = json
                )
            )
        }
    }

    suspend fun update(data: BurnOutCaloPerDay) {
        dao.update(data)
        try {
            firestore.collection("accounts")
                .document(data.Uid)
                .collection("burn_out_calo_per_day")
                .document(data.DateTime.time.toString())
                .set(data)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(data)
            pendingDao.insert(
                PendingAction(
                    uid = data.Uid,
                    type = PendingActionTypes.UPDATE_BURN_OUT,
                    payload = json
                )
            )
        }
    }

    suspend fun delete(data: BurnOutCaloPerDay) {
        dao.delete(data)
        try {
            firestore.collection("accounts")
                .document(data.Uid)
                .collection("burn_out_calo_per_day")
                .document(data.DateTime.time.toString())
                .delete()
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(data)
            pendingDao.insert(
                PendingAction(
                    uid = data.Uid,
                    type = PendingActionTypes.DELETE_BURN_OUT,
                    payload = json
                )
            )
        }
    }

    suspend fun getByDate(date: Date): BurnOutCaloPerDay? = dao.getByDate(date)

    suspend fun getTotalCaloBurnedByDate(uid: String, date: Date): Int? =
        dao.getTotalCaloBurnedByDate(uid, date)

    // ✅ Tự động tạo hoặc cập nhật bản ghi theo tổng calo đốt được trong ngày
    suspend fun generateBurnOutLog(uid: String, date: Date) {
        val totalCalo = getTotalCaloBurnedByDate(uid, date) ?: 0
        val current = getByDate(date)

        val entry = BurnOutCaloPerDay(
            DateTime = date,
            TotalCalo = totalCalo,
            Uid = uid
        )

        if (current == null) {
            insert(entry)
        } else {
            update(entry)
        }
    }
    suspend fun fetchFromRemote(uid: String) {
        try {
            Log.e("fetchFromRemote: ", "fetchFromRemote: brn out ", )
            val snapshot = firestore.collection("accounts")
                .document(uid)
                .collection("burn_out_calo_per_day")
                .get()
                .await()

            val list = snapshot.documents.mapNotNull { doc ->
                try {
                    doc.toObject(BurnOutCaloPerDay::class.java)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }

            list.forEach { entry ->
                Log.e("fetch info ", "fetchFromRemote: burnout " + entry.DateTime, )
                dao.insert(entry)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("fetchFromRemote: ", "fetchFromRemote: burnout " + e.message, )
        }
    }

}
