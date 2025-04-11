package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.CustomExercise
import com.example.health.data.local.repostories.CustomExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CustomExerciseViewModel(
    private val repository: CustomExerciseRepository
) : ViewModel() {

    fun getAllByUser(uid: String): Flow<List<CustomExercise>> =
        repository.getAllByUser(uid)

    fun searchByName(uid: String, query: String): Flow<List<CustomExercise>> =
        repository.searchByName(uid, query)

    fun insert(exercise: CustomExercise) = viewModelScope.launch {
        repository.insert(exercise)
    }

    fun update(exercise: CustomExercise) = viewModelScope.launch {
        repository.update(exercise)
    }

    fun delete(exercise: CustomExercise) = viewModelScope.launch {
        repository.delete(exercise)
    }
}
