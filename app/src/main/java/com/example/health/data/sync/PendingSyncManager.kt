package com.example.health.data.sync

import android.content.Context
import com.example.health.data.local.appdatabase.AppDatabase
import com.example.health.data.local.entities.Account
import com.example.health.data.local.entities.BaseInfo
import com.example.health.data.local.entities.HealthMetric

import com.example.health.data.local.entities.PendingAction
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

object PendingSyncManager {
    suspend fun retryAll(context: Context) {
        val dao = AppDatabase.getDatabase(context).pendingActionDao()
        val firestore = FirebaseFirestore.getInstance()
        val gson = Gson()
        val actions = dao.getAll()
        for (action in actions) {
            when (action.type) {
                 PendingActionTypes.INSERT_ACCOUNT-> {
                    val account = gson.fromJson(action.payload, Account::class.java)
                    try {
                        firestore.collection("accounts").document(account.Uid).set(account)
                        dao.delete(action)
                    } catch (_: Exception) {}
                }
                PendingActionTypes.UPDATE_STATUS -> {
                    val uid = action.uid
                    val status = action.payload // status là String
                    try {
                        firestore.collection("accounts").document(uid).update("status", status)
                        dao.delete(action)
                    } catch (_: Exception) {}
                }
                PendingActionTypes.INSERT_BASE_INFO->{
                    val baseInfo = gson.fromJson(action.payload, BaseInfo::class.java)
                    try {
                        firestore.collection("accounts")
                            .document(baseInfo.Uid)
                            .collection("base_info")
                            .document("data")
                            .set(baseInfo)
                        dao.delete(action)
                    }
                    catch (_: Exception) {}
                }
                PendingActionTypes.UPDATE_BASE_INFO->{
                    val baseInfo = gson.fromJson(action.payload, BaseInfo::class.java)
                    try{
                        firestore.collection("accounts")
                            .document(baseInfo.Uid)
                            .collection("base_info")
                            .document("data")
                            .set(baseInfo)
                        dao.delete(action)
                    }catch (_: Exception) {}
                }
                PendingActionTypes.INSERT_HEALTH_METRIC->{
                    val healthMetric = gson.fromJson(action.payload, HealthMetric::class.java)
                    try{
                        firestore.collection("accounts")
                            .document(healthMetric.Uid)
                            .collection("health_metrics")
                            .document(healthMetric.metricId)
                            .set(healthMetric)
                        dao.delete(action)
                    }catch (_: Exception) {}
                }
                PendingActionTypes.UPDATE_HEALTH_METRIC->{
                    val healthMetric = gson.fromJson(action.payload, HealthMetric::class.java)
                    try{
                        firestore.collection("accounts")
                            .document(healthMetric.Uid)
                            .collection("health_metrics")
                            .document(healthMetric.metricId)
                            .set(healthMetric)
                        dao.delete(action)
                    }catch (_: Exception) {}
                }

            }
        }
    }
}