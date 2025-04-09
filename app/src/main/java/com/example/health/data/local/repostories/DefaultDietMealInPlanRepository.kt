package com.example.health.data.local.repostories

import com.example.health.data.local.daos.DefaultDietMealInPlanDao
import com.example.health.data.local.entities.DefaultDietMealInPlan
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.*

class DefaultDietMealInPlanRepository(
    private val dao: DefaultDietMealInPlanDao,
    private val firestore: FirebaseFirestore
) {

    fun getAll(): Flow<List<DefaultDietMealInPlan>> = dao.getAll()

    suspend fun getById(id: String): DefaultDietMealInPlan? = dao.getById(id)


    suspend fun syncFromRemote() {
        val snapshot = firestore.collection("default_diet_meal_in_plan").get().await()
        val list = snapshot.documents.mapNotNull { doc ->
            val item = doc.toObject(DefaultDietMealInPlan::class.java)
            item?.copy(Id = doc.id)
        }
        dao.insertAll(list)
    }
}
