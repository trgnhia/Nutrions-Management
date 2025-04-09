package com.example.health.data.local.viewmodel.quyen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.ExerciseLog
import com.example.health.data.local.repostories.quyen.ExerciseLogRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class ExerciseLogViewModel(
    private val repository: ExerciseLogRepository
) : ViewModel() {

    fun getAllByUser(uid: String): Flow<List<ExerciseLog>> =
        repository.getAllByUser(uid)

    fun getByDate(uid: String, date: Date): Flow<List<ExerciseLog>> =
        repository.getByDate(uid, date)

    suspend fun getTotalCalo(uid: String, date: Date): Int =
        repository.getTotalCaloBurnedByDate(uid, date)

    fun insert(log: ExerciseLog) = viewModelScope.launch {
        repository.insert(log)
    }

    fun update(log: ExerciseLog) = viewModelScope.launch {
        repository.update(log)
    }

    fun delete(log: ExerciseLog) = viewModelScope.launch {
        repository.delete(log)
    }
}
