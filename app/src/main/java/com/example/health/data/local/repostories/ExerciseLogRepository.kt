package com.example.health.data.local.repostories


import android.util.Log
import com.example.health.data.local.daos.ExerciseLogDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.ExerciseLog
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class ExerciseLogRepository(
    private val dao: ExerciseLogDao,
    private val pendingDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {

    fun getAllByUser(uid: String): Flow<List<ExerciseLog>> = dao.getAllByUser(uid)

    fun getByDate(uid: String, date: java.util.Date): Flow<List<ExerciseLog>> =
        dao.getByDate(uid, date)

    suspend fun getTotalCaloBurnedByDate(uid: String, date: java.util.Date): Int =
        dao.getTotalCaloBurnedByDate(uid, date) ?: 0

    suspend fun insert(log: ExerciseLog) {
        dao.insert(log)
        try {
            firestore.collection("accounts")
                .document(log.Uid)
                .collection("exercise_log")
                .document(log.id)
                .set(log)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(log)
            pendingDao.insert(
                PendingAction(
                    uid = log.Uid,
                    type = PendingActionTypes.INSERT_EXERCISE_LOG,
                    payload = json
                )
            )
        }
    }

    suspend fun update(log: ExerciseLog) {
        dao.update(log)
        try {
            firestore.collection("accounts")
                .document(log.Uid)
                .collection("exercise_log")
                .document(log.id)
                .set(log)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(log)
            pendingDao.insert(
                PendingAction(
                    uid = log.Uid,
                    type = PendingActionTypes.UPDATE_EXERCISE_LOG,
                    payload = json
                )
            )
        }
    }

    suspend fun delete(log: ExerciseLog) {
        dao.delete(log)
        try {
            firestore.collection("accounts")
                .document(log.Uid)
                .collection("exercise_log")
                .document(log.id)
                .delete()
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(log)
            pendingDao.insert(
                PendingAction(
                    uid = log.Uid,
                    type = PendingActionTypes.DELETE_EXERCISE_LOG,
                    payload = json
                )
            )
        }
    }
    suspend fun fetchFromRemote(uid: String) {
        try {
            Log.e("fetchFromRemote: ", "fetchFromRemote: exercise log ", )
            val snapshot = firestore.collection("accounts")
                .document(uid)
                .collection("exercise_log")
                .get()
                .await()

            val logs = snapshot.documents.mapNotNull { doc ->
                try {
                    doc.toObject(ExerciseLog::class.java)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }

            logs.forEach { log ->
                Log.e("fetch info ", "fetchFromRemote: exercise log " + log.id, )
                dao.insert(log)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("fetchFromRemote: ", "fetchFromRemote: exercise log " + e.message, )
        }
    }

}
