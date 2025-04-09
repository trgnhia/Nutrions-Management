package com.example.health.data.local.repostories

import android.util.Log
import com.example.health.data.local.daos.DefaultExerciseDao
import com.example.health.data.local.entities.DefaultExercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class DefaultExerciseRepository(
    private val dao: DefaultExerciseDao,
    private val firestore: FirebaseFirestore
) {

    fun getAll(): Flow<List<DefaultExercise>> = dao.getAll()

    suspend fun getById(id: String): DefaultExercise? = dao.getById(id)

    suspend fun insertAll(list: List<DefaultExercise>) {
        dao.insertAll(list)
    }

    suspend fun fetchRemoteDefaultExercises(): List<DefaultExercise> {
        val snapshot = firestore.collection("default_exercise").get().await()
        return snapshot.documents.mapNotNull { doc ->
            val item = doc.toObject(DefaultExercise::class.java)
            if (item == null) {
                Log.e("FIRESTORE", "❌ Convert thất bại: ${doc.id}")
            }
            item?.copy(Id = doc.id) // lấy ID từ doc
        }
    }
}
