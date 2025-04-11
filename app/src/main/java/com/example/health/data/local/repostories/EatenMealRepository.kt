package com.example.health.data.local.repostories

import com.example.health.data.local.daos.EatenMealDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.EatenMeal
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.Date

class EatenMealRepository(
    private val dao: EatenMealDao,
    private val pendingDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {
    fun getAllMeals(): Flow<List<EatenMeal>> = dao.getAllMeals()

    fun getMealsByDate(date: Date): Flow<List<EatenMeal>> = dao.getMealsByDate(date)

    suspend fun getMealByDateAndType(date: Date, type: Int): EatenMeal? =
        dao.getMealByDateAndType(date, type)

    suspend fun insert(meal: EatenMeal) {
        dao.insert(meal)
        try {
            firestore.collection("accounts")
                .document(meal.Uid)
                .collection("eaten_meal")
                .document(meal.id)
                .set(meal)
                .await()
        } catch (_: Exception) {
            val json = Gson().toJson(meal)
            pendingDao.insert(
                PendingAction(
                    type = PendingActionTypes.INSERT_EATEN_MEAL,
                    uid = meal.Uid,
                    payload = json
                )
            )
        }
    }

    suspend fun update(meal: EatenMeal) {
        dao.update(meal)
        try {
            firestore.collection("accounts")
                .document(meal.Uid)
                .collection("eaten_meal")
                .document(meal.id)
                .set(meal)
                .await()
        } catch (_: Exception) {
            val json = Gson().toJson(meal)
            pendingDao.insert(
                PendingAction(
                    type = PendingActionTypes.UPDATE_EATEN_MEAL,
                    uid = meal.Uid,
                    payload = json
                )
            )
        }
    }

    suspend fun delete(meal: EatenMeal) {
        dao.delete(meal)
        try {
            firestore.collection("accounts")
                .document(meal.Uid)
                .collection("eaten_meal")
                .document(meal.id)
                .delete()
                .await()
        } catch (_: Exception) {
            val json = Gson().toJson(meal)
            pendingDao.insert(
                PendingAction(
                    type = PendingActionTypes.DELETE_EATEN_MEAL,
                    uid = meal.Uid,
                    payload = json
                )
            )
        }
    }
}
