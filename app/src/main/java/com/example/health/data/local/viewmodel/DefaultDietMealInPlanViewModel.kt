package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.DefaultDietMealInPlan
import com.example.health.data.local.repostories.DefaultDietMealInPlanRepository
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
}
