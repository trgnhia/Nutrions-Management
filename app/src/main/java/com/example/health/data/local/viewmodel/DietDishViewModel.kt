package com.example.health.data.local.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.DietDish
import com.example.health.data.local.repostories.DietDishRepository
import com.example.health.data.utils.downloadImageAndSave
import com.example.health.data.utils.toSafeFileName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DietDishViewModel(
    private val repository: DietDishRepository
) : ViewModel() {

    private val _dishes = MutableStateFlow<List<DietDish>>(emptyList())
    val dishes: StateFlow<List<DietDish>> = _dishes

    fun loadByMealId(mealId: String) {
        viewModelScope.launch {
            Log.d("VeganDetailScreen", "Loading mealId = $mealId")
            val list = repository.getByMealPlanId(mealId)
            _dishes.value = list
            Log.d("VeganDetailScreen", "Dishes size = ${list.size}")
        }
    }
    fun loadDishesForMealIds(mealIds: List<String>) {
        viewModelScope.launch {
            val allDishes = mutableListOf<DietDish>()
            mealIds.forEach { mealId ->
                val dishes = repository.getByMealPlanId(mealId)
                allDishes.addAll(dishes)
            }
            _dishes.value = allDishes
        }
    }

    suspend fun getDishById(id: String): DietDish? {
        return repository.getById(id)
    }


    fun syncIfNeeded(context: Context) = viewModelScope.launch {
        if (_dishes.value.isEmpty()) {
            Log.d("DietDishViewModel", "Room empty → syncing from Firestore...")
            syncFromRemote(context)
        }
    }

    private fun syncFromRemote(context: Context) = viewModelScope.launch {
        repository.fetchRemoteAndInsertEach { dish ->
            val safeFileName = "${dish.Name.toSafeFileName()}.jpg"
            val file = downloadImageAndSave(context, dish.UrlImage, safeFileName)
            val localPath = file?.absolutePath ?: dish.UrlImage
            val updated = dish.copy(UrlImage = localPath)

            repository.insert(updated)
        }
    }
}
