package com.example.health.data.local.repostories.quyen


import com.example.health.data.local.daos.PendingActionDao
import com.example.health.data.local.daos.quyen.MacroDao
import com.example.health.data.local.entities.PendingAction
import com.example.health.data.local.entities.quyen.Macro
import com.example.health.data.remote.sync.PendingActionTypes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class MacroRepository(
    private val macroDao: MacroDao,
    private val pendingActionDao: PendingActionDao,
    private val firestore: FirebaseFirestore
) {

    fun getMacro(): Flow<Macro?> = macroDao.getMacro()

    suspend fun insert(macro: Macro) {
        macroDao.insert(macro)
        try {
            firestore.collection("accounts")
                .document(macro.Uid)
                .collection("macro")
                .document("data")
                .set(macro).await()
        } catch (_: Exception) {
            val json = Gson().toJson(macro)
            val action = PendingAction(
                type = PendingActionTypes.INSERT_MACRO,
                uid = macro.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }
    }

    suspend fun update(macro: Macro) {
        macroDao.update(macro)
        try {
            firestore.collection("accounts")
                .document(macro.Uid)
                .collection("macro")
                .document("data")
                .set(macro).await()
        } catch (_: Exception) {
            val json = Gson().toJson(macro)
            val action = PendingAction(
                type = PendingActionTypes.UPDATE_MACRO,
                uid = macro.Uid,
                payload = json
            )
            pendingActionDao.insert(action)
        }
    }

    suspend fun fetchFromRemote(uid: String): Macro? {
        return try {
            val snapshot = firestore.collection("accounts")
                .document(uid)
                .collection("macro")
                .document("data")
                .get().await()
            val remote = snapshot.toObject(Macro::class.java)
            remote?.let { macroDao.insert(it) }
            remote
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
