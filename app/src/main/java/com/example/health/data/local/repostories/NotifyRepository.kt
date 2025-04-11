//package com.example.health.data.local.repostories
//
//import com.example.health.data.local.daos.NotifyDao
//import com.example.health.data.local.daos.PendingActionDao
//import com.example.health.data.local.entities.Notify
//import com.example.health.data.local.entities.PendingAction
//import com.example.health.data.remote.sync.PendingActionTypes
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.gson.Gson
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.tasks.await
//
//class NotifyRepository(
//    private val notifyDao: NotifyDao,
//    private val firestore: FirebaseFirestore,
//    private val pendingActionDao: PendingActionDao // ✅ thêm vào
//) {
//
//    suspend fun insert(notify: Notify) {
//        try {
//            firestore.collection("accounts")
//                .document(notify.Uid)
//                .collection("notify")
//                .document(notify.id)
//                .set(notify)
//                .await()
//            notifyDao.insert(notify)
//        } catch (e: Exception) {
//            val json = Gson().toJson(notify)
//            val action = PendingAction(
//                type = PendingActionTypes.INSERT_NOTIFY,
//                uid = notify.Uid,
//                payload = json
//            )
//            pendingActionDao.insert(action)
//        }
//    }
//
//    suspend fun insertAll(notifies: List<Notify>) {
//        notifies.forEach { insert(it) }
//    }
//
//    fun getAllByUid(uid: String): Flow<List<Notify>> {
//        return notifyDao.getAllByUid(uid)
//    }
//
//    suspend fun deleteAllByUid(uid: String) {
//        try {
//            firestore.collection("accounts")
//                .document(uid)
//                .collection("notify")
//                .get()
//                .await()
//                .documents.forEach { doc ->
//                    doc.reference.delete()
//                }
//        } catch (_: Exception) {
//            // Có thể tạo PendingAction xóa nếu muốn
//        }
//        notifyDao.deleteAllByUid(uid)
//    }
//}
//

package com.example.health.data.local.repostories

import android.util.Log
import com.example.health.data.local.daos.NotifyDao
import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.entities.DietDish
import com.example.health.data.local.entities.Notify
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

class NotifyRepository(
    private val notifyDao: NotifyDao,
    private val firestore: FirebaseFirestore,
    private val pendingActionDao: PendingActionDao
) {

    suspend fun insert(notify: Notify) {
        try {
            firestore.collection("accounts")
                .document(notify.Uid)
                .collection("notify")
                .document(notify.id)
                .set(notify)
                .await()
            notifyDao.insert(notify)
        } catch (e: Exception) {
            val json = Gson().toJson(notify)
            val action = PendingAction(
                type = PendingActionTypes.INSERT_NOTIFY,
                uid = notify.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }
    }

    suspend fun insertAll(notifies: List<Notify>) {
        notifies.forEach { insert(it) }
    }


    suspend fun update(notify: Notify) {
        val sdf = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
        Log.e("NotifyRepository", "NotifyTime = ${sdf.format(notify.NotifyTime)}")
        notifyDao.insert(notify) // REPLACE in Room
        try {
            firestore.collection("accounts")
                .document(notify.Uid)
                .collection("notify")
                .document(notify.id)
                .set(notify)
                .await()
        } catch (e: Exception) {
            val json = Gson().toJson(notify)
            val action = PendingAction(
                type = PendingActionTypes.UPDATE_NOTIFY,
                uid = notify.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }
    }

    fun getAllByUid(uid: String): Flow<List<Notify>> {
        return notifyDao.getAllByUid(uid)
    }

    suspend fun deleteAllByUid(uid: String) {
        try {
            firestore.collection("accounts")
                .document(uid)
                .collection("notify")
                .get()
                .await()
                .documents.forEach { doc ->
                    doc.reference.delete()
                }
        } catch (_: Exception) {
            // Nếu cần, có thể tạo PendingAction xoá
        }
        notifyDao.deleteAllByUid(uid)
    }
}
