package com.example.health.data.local.repostories

import com.example.health.data.local.daos.CustomExerciseDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.CustomExercise
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class CustomExerciseRepository(
    private val dao: CustomExerciseDao,
    private val pendingDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {
    fun getAllByUser(uid: String): Flow<List<CustomExercise>> = dao.getAllByUser(uid)

    fun searchByName(uid: String, query: String): Flow<List<CustomExercise>> =
        dao.searchByName(uid, query)

    suspend fun insert(exercise: CustomExercise) {
        dao.insert(exercise)
        try {
            firestore.collection("accounts")
                .document(exercise.Uid)
                .collection("custom_exercise")
                .document(exercise.id)
                .set(exercise)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(exercise)
            pendingDao.insert(
                PendingAction(
                    uid = exercise.Uid,
                    type = PendingActionTypes.INSERT_CUSTOM_EXERCISE,
                    payload = json
                )
            )
        }
    }

    suspend fun update(exercise: CustomExercise) {
        dao.update(exercise)
        try {
            firestore.collection("accounts")
                .document(exercise.Uid)
                .collection("custom_exercise")
                .document(exercise.id)
                .set(exercise)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(exercise)
            pendingDao.insert(
                PendingAction(
                    uid = exercise.Uid,
                    type = PendingActionTypes.UPDATE_CUSTOM_EXERCISE,
                    payload = json
                )
            )
        }
    }

    suspend fun delete(exercise: CustomExercise) {
        dao.delete(exercise)
        try {
            firestore.collection("accounts")
                .document(exercise.Uid)
                .collection("custom_exercise")
                .document(exercise.id)
                .delete()
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(exercise)
            pendingDao.insert(
                PendingAction(
                    uid = exercise.Uid,
                    type = PendingActionTypes.DELETE_CUSTOM_EXERCISE,
                    payload = json
                )
            )
        }
    }
}
