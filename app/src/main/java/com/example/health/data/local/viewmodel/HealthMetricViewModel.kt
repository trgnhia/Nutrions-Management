package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.HealthMetric
import com.example.health.data.local.repostories.HealthMetricRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class HealthMetricViewModel(
    private val repository: HealthMetricRepository
) : ViewModel() {

    val allMetrics: StateFlow<List<HealthMetric>> = repository.getAllHealthMetrics()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val lastMetric: StateFlow<HealthMetric?> = repository.getLastHealthMetric()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun insertHealthMetric(metric: HealthMetric) = viewModelScope.launch {
        repository.insertHealthMetric(metric)
    }

    fun deleteHealthMetric(id: String) = viewModelScope.launch {
        repository.deleteHealthMetric(id)
    }

    fun deleteAllHealthMetrics() = viewModelScope.launch {
        repository.deleteAllHealthMetrics()
    }

    fun updateHealthMetric(metric: HealthMetric) = viewModelScope.launch {
        repository.updateHealthMetric(metric)
    }

    fun fetchAllFromRemote(uid: String) = viewModelScope.launch {
        repository.fetchAllFromRemote(uid)
    }
    fun syncIfNeeded(uid: String) = viewModelScope.launch {
        if (allMetrics.value.isEmpty()) {
            fetchAllFromRemote(uid)
        }
    }

}
