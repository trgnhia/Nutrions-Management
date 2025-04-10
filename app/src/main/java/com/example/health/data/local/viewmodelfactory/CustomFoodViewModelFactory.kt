package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.CustomFoodRepository
import com.example.health.data.local.viewmodel.CustomFoodViewModel

class CustomFoodViewModelFactory(
    private val repository: CustomFoodRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomFoodViewModel::class.java)) {
            return CustomFoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
