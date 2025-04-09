package com.example.health.data.local.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.DefaultFoodRepository
import com.example.health.data.local.viewmodel.DefaultFoodViewModel

class DefaultFoodViewModelFactory(
    private val repository: DefaultFoodRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DefaultFoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DefaultFoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
