package com.example.health.data.local.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.DefaultExerciseRepository
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel

class DefaultExerciseViewModelFactory(
    private val repository: DefaultExerciseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DefaultExerciseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DefaultExerciseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
