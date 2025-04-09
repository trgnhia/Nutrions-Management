package com.example.health.data.local.viewmodelfactory.quyen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.quyen.ExerciseLogRepository
import com.example.health.data.local.viewmodel.quyen.ExerciseLogViewModel


class ExerciseLogViewModelFactory(
    private val repository: ExerciseLogRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseLogViewModel::class.java)) {
            return ExerciseLogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
