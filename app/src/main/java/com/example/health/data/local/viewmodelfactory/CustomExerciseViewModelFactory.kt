package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.CustomExerciseRepository
import com.example.health.data.local.viewmodel.CustomExerciseViewModel

class CustomExerciseViewModelFactory(
    private val repository: CustomExerciseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomExerciseViewModel::class.java)) {
            return CustomExerciseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
