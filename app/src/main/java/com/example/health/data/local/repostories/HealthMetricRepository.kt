package com.example.health.data.local.repostories

import android.util.Log
import com.example.health.data.local.daos.HealMetricDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.HealthMetric
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class HealthMetricRepository(
    private val healthMetricDao: HealMetricDao,
    private val pendingActionDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {
    fun getAllHealthMetrics(): Flow<List<HealthMetric>> = healthMetricDao.getAllHealthMetrics()

    fun getLastHealthMetric(): Flow<HealthMetric?> = healthMetricDao.getLastHealthMetric()

    suspend fun insertHealthMetric(healthMetric: HealthMetric) {
        healthMetricDao.insertHealthMetric(healthMetric)
        try{
            firestore.collection("accounts")
                .document(healthMetric.Uid)
                .collection("health_metrics")
                .document(healthMetric.metricId)
                .set(healthMetric)
        }
        catch (_: Exception) {
            val json = Gson().toJson(healthMetric)
            val action = PendingAction(
                type = PendingActionTypes.INSERT_HEALTH_METRIC,
                uid = healthMetric.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }

    }
    suspend fun updateHealthMetric(healthMetric: HealthMetric) {
        try{
            healthMetricDao.updateHealthMetric(healthMetric)
            firestore.collection("accounts")
                .document(healthMetric.Uid)
                .collection("health_metrics")
                .document(healthMetric.metricId)
                .set(healthMetric)
        }
        catch (_: Exception) {
            val json = Gson().toJson(healthMetric)
            val action = PendingAction(
                type = PendingActionTypes.UPDATE_HEALTH_METRIC,
                uid = healthMetric.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }
    }

    suspend fun deleteHealthMetric(metricId: String) {
        healthMetricDao.deleteHealthMetricById(metricId)
    }
    suspend fun deleteAllHealthMetrics() {
        healthMetricDao.deleteAllHealthMetrics()
    }
    suspend fun fetchAllFromRemote(uid: String): List<HealthMetric> {
        try{
            val snapshot = firestore.collection("accounts")
                .document(uid)
                .collection("health_metrics")
                .get()
                .await()
            val remoteList = snapshot.documents.mapNotNull { it.toObject(HealthMetric::class.java) }
            // Sync về local
            remoteList.forEach { healthMetricDao.insertHealthMetric(it) }
            Log.e("HealthFetch", "Error fetching account: ")
            return remoteList
        }
        catch (e: Exception) {
            // Log chi tiết lỗi
            Log.e("FirestoreError", "Error fetching account: ${e.message}")
            e.printStackTrace() // In thông tin lỗi đầy đủ vào log
            return emptyList()
        }
    }


}