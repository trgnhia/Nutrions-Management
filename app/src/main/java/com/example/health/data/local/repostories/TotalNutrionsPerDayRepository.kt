package com.example.health.data.local.repostories

import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.daos.TotalNutrionsPerDayDao
import com.example.health.data.local.daos.NutritionAggregate
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.local.entities.TotalNutrionsPerDay
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import java.util.*

class TotalNutrionsPerDayRepository(
    private val dao: TotalNutrionsPerDayDao,
    private val pendingDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {

    fun getByDateAndUid(date: Date, uid: String): Flow<TotalNutrionsPerDay?> =
        dao.getByDateAndUid(date, uid)

    fun getAllByUser(uid: String): Flow<List<TotalNutrionsPerDay>> =
        dao.getAllByUser(uid)

    suspend fun insert(total: TotalNutrionsPerDay) {
        dao.insert(total)
        try {
            firestore.collection("accounts")
                .document(total.Uid)
                .collection("total_nutrions_per_day")
                .document(total.id)
                .set(total)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(total)
            pendingDao.insert(
                PendingAction(
                    uid = total.Uid,
                    type = PendingActionTypes.INSERT_TOTAL_NUTRITION,
                    payload = json
                )
            )
        }
    }

    suspend fun update(total: TotalNutrionsPerDay) {
        dao.update(total)
        try {
            firestore.collection("accounts")
                .document(total.Uid)
                .collection("total_nutrions_per_day")
                .document(total.id)
                .set(total)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(total)
            pendingDao.insert(
                PendingAction(
                    uid = total.Uid,
                    type = PendingActionTypes.UPDATE_TOTAL_NUTRITION,
                    payload = json
                )
            )
        }
    }

    suspend fun delete(total: TotalNutrionsPerDay) {
        dao.delete(total)
        try {
            firestore.collection("accounts")
                .document(total.Uid)
                .collection("total_nutrions_per_day")
                .document(total.id)
                .delete()
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(total)
            pendingDao.insert(
                PendingAction(
                    uid = total.Uid,
                    type = PendingActionTypes.DELETE_TOTAL_NUTRITION,
                    payload = json
                )
            )
        }
    }



    // ✅ Hàm sinh log tự động từ eaten_meal
    suspend fun generateLog(uid: String, date: Date, dietType: Int) {
        val aggregate: NutritionAggregate? = dao.getDailyNutritionAggregate(uid, date)
        val current = getByDateAndUid(date, uid).firstOrNull()

        val entry = TotalNutrionsPerDay(
            id = "$uid-${date.time}",
            Date = date,
            Uid = uid,
            TotalCalo = aggregate?.totalCalo ?: 0f,
            TotalPro = aggregate?.totalPro ?: 0f,
            TotalCarb = aggregate?.totalCarb ?: 0f,
            TotalFat = aggregate?.totalFat ?: 0f,
            DietType = dietType
        )

        if (current == null) {
            insert(entry)
        } else {
            update(entry)
        }
    }
    suspend fun getByDateAndUidOnce(date: Date, uid: String): TotalNutrionsPerDay? {
        return dao.getByDateAndUidOnce(date, uid)
    }
}
