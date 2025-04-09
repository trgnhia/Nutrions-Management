package com.example.health.data.local.repostories

import android.util.Log
import com.example.health.data.local.daos.DefaultFoodDao
import com.example.health.data.local.entities.DefaultFood
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class DefaultFoodRepository(
    private val dao: DefaultFoodDao,
    private val firestore: FirebaseFirestore
) {
    fun getAll(): Flow<List<DefaultFood>> = dao.getAll()

    suspend fun getById(id: String): DefaultFood? = dao.getById(id)

    suspend fun insertAll(list: List<DefaultFood>) {
        Log.e("DefaultFoodRepository", "insertAll: load data ", )
        dao.insertAll(list)
    }

    suspend fun fetchRemoteDefaultFoods(): List<DefaultFood> {
        val snapshot = firestore.collection("default_food").get().await()
        return snapshot.documents.mapNotNull { doc ->
            val item = doc.toObject(DefaultFood::class.java)
            item?.copy(Id = doc.id)
        }
    }
}
