package com.example.health.data.local.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.DefaultDietMealInPlan
import com.example.health.data.local.repostories.DefaultDietMealInPlanRepository
import com.example.health.data.utils.downloadImageAndSave
import com.example.health.data.utils.toSafeFileName
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultDietMealInPlanViewModel(
    private val repository: DefaultDietMealInPlanRepository
) : ViewModel() {

    val allPlans: StateFlow<List<DefaultDietMealInPlan>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun getById(id: String, callback: (DefaultDietMealInPlan?) -> Unit) = viewModelScope.launch {
        val result = repository.getById(id)
        callback(result)
    }

    fun syncFromRemote() = viewModelScope.launch {
        repository.syncFromRemote()
    }
    fun loadDefaultMeals(context: Context) = viewModelScope.launch {
        repository.fetchRemoteAndInsertEach { meal ->
            val safeFileName = "${meal.Id.toSafeFileName()}.jpg"
            val file = downloadImageAndSave(context, meal.UrlImage, safeFileName)
            val localPath = file?.absolutePath ?: meal.UrlImage
            val updated = meal.copy(UrlImage = localPath)

            repository.insert(updated)
        }
    }
    fun syncIfNeeded(context: Context) = viewModelScope.launch {
        if (allPlans.value.isEmpty()) {
            loadDefaultMeals(context)
        }
    }
}


