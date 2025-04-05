package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.HealthMetricRepository
import com.example.health.data.local.viewmodel.HealthMetricViewModel

class HealthMetricViewModelFactory(
    private val repository: HealthMetricRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HealthMetricViewModel::class.java)) {
            return HealthMetricViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
