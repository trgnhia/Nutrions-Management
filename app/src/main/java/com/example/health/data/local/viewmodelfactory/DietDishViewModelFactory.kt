package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.DietDishRepository
import com.example.health.data.local.viewmodel.DietDishViewModel

class DietDishViewModelFactory(
    private val repository: DietDishRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DietDishViewModel::class.java)) {
            return DietDishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
