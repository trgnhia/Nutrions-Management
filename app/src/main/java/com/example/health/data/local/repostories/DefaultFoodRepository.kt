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
        dao.insertAll(list)
    }

    suspend fun insert(item: DefaultFood) {
        dao.insert(item)
    }

    // ✅ Tải từng item và gọi callback để xử lý và insert ngay
    suspend fun fetchRemoteAndInsertEach(onEach: suspend (DefaultFood) -> Unit) {
        val snapshot = firestore.collection("default_food").get().await()
        for (doc in snapshot.documents) {
            val item = doc.toObject(DefaultFood::class.java)
            if (item != null) {
                val withId = item.copy(Id = doc.id)
                onEach(withId)
            } else {
                Log.e("FIRESTORE", "❌ Convert thất bại: ${doc.id}")
            }
        }
    }
    fun getByType(type: Int): Flow<List<DefaultFood>> = dao.getByType(type)

    // ✅ Lấy món ăn ngẫu nhiên theo loại
    suspend fun getRandomFoodsByType(count: Int, type: Int): List<DefaultFood> = dao.getRandomFoodsByType(count, type)


}

