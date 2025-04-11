package com.example.health.data.local.repostories

import com.example.health.data.local.daos.CustomFoodDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.CustomFood
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class CustomFoodRepository(
    private val dao: CustomFoodDao,
    private val pendingDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {
    fun getAllByUser(uid: String): Flow<List<CustomFood>> = dao.getAllByUser(uid)

    fun searchByName(uid: String, query: String): Flow<List<CustomFood>> =
        dao.searchByName(uid, query)

    suspend fun insert(food: CustomFood) {
        dao.insert(food)
        try {
            firestore.collection("accounts")
                .document(food.Uid)
                .collection("custom_food")
                .document(food.id)
                .set(food)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(food)
            pendingDao.insert(
                PendingAction(
                    uid = food.Uid,
                    type = PendingActionTypes.INSERT_CUSTOM_FOOD,
                    payload = json
                )
            )
        }
    }

    suspend fun update(food: CustomFood) {
        dao.update(food)
        try {
            firestore.collection("accounts")
                .document(food.Uid)
                .collection("custom_food")
                .document(food.id)
                .set(food)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(food)
            pendingDao.insert(
                PendingAction(
                    uid = food.Uid,
                    type = PendingActionTypes.UPDATE_CUSTOM_FOOD,
                    payload = json
                )
            )
        }
    }

    suspend fun delete(food: CustomFood) {
        dao.delete(food)
        try {
            firestore.collection("accounts")
                .document(food.Uid)
                .collection("custom_food")
                .document(food.id)
                .delete()
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(food)
            pendingDao.insert(
                PendingAction(
                    uid = food.Uid,
                    type = PendingActionTypes.DELETE_CUSTOM_FOOD,
                    payload = json
                )
            )
        }
    }
}
