package com.example.health.data.local.repostories

import com.example.health.data.local.daos.HealMetricDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.HealMetric
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
    fun getAllHealthMetrics(): Flow<List<HealMetric>> = healthMetricDao.getAllHealthMetrics()

    fun getLastHealthMetric(): Flow<HealMetric?> = healthMetricDao.getLastHealthMetric()

    suspend fun insertHealthMetric(healthMetric: HealMetric) {
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
    suspend fun updateHealthMetric(healthMetric: HealMetric) {
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

    suspend fun deleteHealthMetric(metricId: String, uid: String) {
        healthMetricDao.deleteHealthMetricById(metricId)
    }
    suspend fun fetchAllFromRemote(uid: String): List<HealMetric> {
        try{
            val snapshot = firestore.collection("accounts")
                .document(uid)
                .collection("health_metrics")
                .get()
                .await()
            val remoteList = snapshot.documents.mapNotNull { it.toObject(HealMetric::class.java) }
            // Sync về local
            remoteList.forEach { healthMetricDao.insertHealthMetric(it) }

            return remoteList
        }
        catch (_: Exception) {
            return emptyList()
        }
    }

}