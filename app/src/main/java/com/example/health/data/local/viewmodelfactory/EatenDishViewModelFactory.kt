package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.EatenDishRepository
import com.example.health.data.local.viewmodel.EatenDishViewModel

class EatenDishViewModelFactory(
    private val repository: EatenDishRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EatenDishViewModel::class.java)) {
            return EatenDishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
