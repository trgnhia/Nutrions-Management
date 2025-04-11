package com.example.health.data.local.repostories

import android.util.Log
import com.example.health.data.local.daos.BaseInfoDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.BaseInfo
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class BaseInfoRepository(
    private val baseInfoDao: BaseInfoDao,
    private val pendingActionDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {
    fun getBaseInfo(): Flow<BaseInfo?> = baseInfoDao.getBaseInfo()

    suspend fun deleteBaseInfo() = baseInfoDao.deleteBaseInfo()

    suspend fun insertBaseInfo(baseInfo: BaseInfo) {
        baseInfoDao.insertBaseInfo(baseInfo)
        try {
            firestore.collection("accounts")
                .document(baseInfo.Uid)
                .collection("base_info")
                .document("data")
                .set(baseInfo)
                .await()
        } catch (_: Exception) {
            val json = Gson().toJson(baseInfo)
            val action = PendingAction(
                type = PendingActionTypes.INSERT_BASE_INFO,
                uid = baseInfo.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }
    }

    suspend fun updateBaseInfo(baseInfo: BaseInfo) {
        baseInfoDao.updateBaseInfo(baseInfo)
        try {
            firestore.collection("accounts")
                .document(baseInfo.Uid)
                .collection("base_info")
                .document("data")
                .set(baseInfo)
                .await()
        } catch (_: Exception) {
            val json = Gson().toJson(baseInfo)
            val action = PendingAction(
                type = PendingActionTypes.UPDATE_BASE_INFO,
                uid = baseInfo.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }
    }

    suspend fun fetchFromRemote(uid: String): BaseInfo? {
        return try {
            val snapshot = firestore.collection("accounts")
                .document(uid)
                .collection("base_info")
                .document("data")
                .get()
                .await()
            val remote = snapshot.toObject(BaseInfo::class.java)
            remote?.let {
                baseInfoDao.insertBaseInfo(it)
            }
            remote
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateIsDiet(uid: String, dietCode: Int) {
        // ✅ Cập nhật trong Room
        baseInfoDao.updateIsDiet(uid, dietCode)
        Log.d("BaseInfoRepo", "updateIsDiet Room: $uid → $dietCode")

        try {
            // ✅ Chỉ đồng bộ 1 trường cụ thể lên Firestore
            firestore.collection("accounts")
                .document(uid)
                .collection("base_info")
                .document("data")
                .update("isDiet", dietCode)
                .await()

            Log.d("BaseInfoRepo", "updateIsDiet Firestore success")
        } catch (e: Exception) {
            // Nếu lỗi Firestore → Tạo bản ghi chờ
            val fallback = mapOf("isDiet" to dietCode)
            val json = Gson().toJson(fallback)
            val action = PendingAction(
                type = PendingActionTypes.UPDATE_BASE_INFO,
                uid = uid,
                payload = json
            )
            pendingActionDao.insert(action)

            Log.e("BaseInfoRepo", "updateIsDiet Firestore failed, pending saved", e)
        }
    }

}
