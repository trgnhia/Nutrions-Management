package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.DefaultDietMealInPlanRepository
import com.example.health.data.local.viewmodel.DefaultDietMealInPlanViewModel

class DefaultDietMealInPlanViewModelFactory(
    private val repository: DefaultDietMealInPlanRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DefaultDietMealInPlanViewModel::class.java)) {
            return DefaultDietMealInPlanViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
