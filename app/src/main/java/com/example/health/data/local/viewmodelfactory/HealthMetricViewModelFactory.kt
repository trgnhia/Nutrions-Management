package com.example.health.data.local.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.HealthMetricRepository
import com.example.health.data.local.viewmodel.HealthMetricViewModel

class HealthMetricViewModelFactory(
    private val application: Application,
    private val repository: HealthMetricRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HealthMetricViewModel(application, repository) as T
    }
}