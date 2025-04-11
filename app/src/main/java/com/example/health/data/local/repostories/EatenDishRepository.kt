package com.example.health.data.local.repostories

import com.example.health.data.local.daos.EatenDishDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.EatenDish
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.Date

class EatenDishRepository(
    private val dao: EatenDishDao,
    private val pendingDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {

    fun getByDateAndType(date: Date, type: Int): Flow<List<EatenDish>> =
        dao.getByDateAndType(date, type)

    fun getByDate(date: Date): Flow<List<EatenDish>> =
        dao.getByDate(date)

    suspend fun insert(dish: EatenDish , uid: String) {
        dao.insert(dish)
        try {
            firestore.collection("accounts")
                .document(uid) // 👈 dùng idEatenMeal làm tham chiếu cha
                .collection("eaten_dish")
                .document(dish.id)
                .set(dish)
                .await()
        } catch (_: Exception) {
            val json = Gson().toJson(dish)
            val action = PendingAction(
                type = PendingActionTypes.INSERT_EATEN_DISH,
                uid = dish.IdEatenMeal,
                payload = json
            )
            pendingDao.insert(action)
        }
    }

    suspend fun update(dish: EatenDish , uid:String) {
        dao.update(dish)
        try {
            firestore.collection("accounts")
                .document(uid)
                .collection("eaten_dish")
                .document(dish.id)
                .set(dish)
                .await()
        } catch (_: Exception) {
            val json = Gson().toJson(dish)
            val action = PendingAction(
                type = PendingActionTypes.UPDATE_EATEN_DISH,
                uid = dish.IdEatenMeal,
                payload = json
            )
            pendingDao.insert(action)
        }
    }

    suspend fun delete(dish: EatenDish , uid:String) {
        dao.delete(dish)
        try {
            firestore.collection("accounts")
                .document(uid)
                .collection("eaten_dish")
                .document(dish.id)
                .delete()
                .await()
        } catch (_: Exception) {
            val json = Gson().toJson(dish)
            val action = PendingAction(
                type = PendingActionTypes.DELETE_EATEN_DISH,
                uid = dish.IdEatenMeal,
                payload = json
            )
            pendingDao.insert(action)
        }
    }
}
