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

    suspend fun insert(exercise: DefaultExercise) {
        dao.insert(exercise)
    }

    // ✅ Tải từng item và trả về qua callback
    suspend fun fetchRemoteAndInsertEach(onEach: suspend (DefaultExercise) -> Unit) {
        val snapshot = firestore.collection("default_exercise").get().await()
        for (doc in snapshot.documents) {
            val item = doc.toObject(DefaultExercise::class.java)
            if (item != null) {
                val withId = item.copy(Id = doc.id)
                onEach(withId)
            } else {
                Log.e("FIRESTORE", "❌ Convert thất bại: ${doc.id}")
            }
        }
    }
}
