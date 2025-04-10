package com.example.health.data.local.repostories

import android.util.Log
import com.example.health.data.local.daos.DietDishDao
import com.example.health.data.local.entities.DefaultDietMealInPlan
import com.example.health.data.local.entities.DietDish
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DietDishRepository(
    private val dao: DietDishDao,
    private val firestore: FirebaseFirestore
) {

    suspend fun insert(dish: DietDish) = dao.insert(dish)

    suspend fun insertAll(list: List<DietDish>) = dao.insertAll(list)

    suspend fun getByMealPlanId(mealPlanId: String): List<DietDish> {
        return dao.getByMealPlanId(mealPlanId)
    }

    suspend fun fetchRemoteAndInsertEach(
        onEachFetched: suspend (DietDish) -> Unit
    ) {
        val snapshot = firestore.collection("diet_dish").get().await()
        for (doc in snapshot.documents) {
            val item = doc.toObject(DietDish::class.java)
            if (item != null) {
                Log.e("", "fetchRemoteAndInsertEach: hehe", )
                val dish = item.copy(Id = doc.id)
                onEachFetched(dish)
            }
        }
    }


}
